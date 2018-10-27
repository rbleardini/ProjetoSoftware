package servico.controle;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import anotacao.Transactional;
import anotacao.Autowired;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import dao.controle.FabricaDeDAOs;
import servico.impl.ContaAppServiceImpl;

public class InterceptadorDeServico implements MethodInterceptor 
{
	
	public Object intercept (Object objeto, 
    		                 Method metodo, 
    		                 Object[] args, 
                             MethodProxy metodoDoProxy) 
    	throws Throwable 
    {
		try 
		{
			for(Field field  : ContaAppServiceImpl.class.getDeclaredFields())
			{
				if (field.isAnnotationPresent(Autowired.class))
				{
					field.set(objeto, FabricaDeDAOs.getDAO(field.getClass()));
				}
			}
			
			if(metodo.isAnnotationPresent(Transactional.class)) {
				JPAUtil.beginTransaction();
			}
			System.out.println("\nDentro do interceptador de servi�o - Executando o m�todo " + metodo.getName() 
					+ ". Acabou de abrir transa��o - vai chamar super." + metodo.getName());
			
			Object obj = metodoDoProxy.invokeSuper(objeto, args);

			if(metodo.isAnnotationPresent(Transactional.class)) {
				JPAUtil.commitTransaction();
			}

			System.out.println("\nDentro do interceptador de servi�o - Executando o m�todo " + metodo.getName() 
					+ ". Acabou de comitar a transa��o - vai retornar para o Principal");
			
			return obj;
		} 
		catch(RuntimeException e) 
		{
			JPAUtil.rollbackTransaction();

			System.out.println("\nOcorreu uma exce��o derivada de "
			  + "RuntimeException. O m�todo " + metodo.getName()  
			  + " acabou sofrer um Rollback.");

			throw e;
		}
		catch(Exception e) 
		{
			if (metodo.isAnnotationPresent(anotacao.RollbackFor.class)) 
			{
				Class<?>[] classesDeExcecao = metodo.getAnnotation(anotacao.RollbackFor.class).nomes();

				boolean achou = false;
				for (Class<?> classeDeExcecao : classesDeExcecao) 
				{
					if (classeDeExcecao.isInstance(e)) 
					{
						achou = true;
						JPAUtil.rollbackTransaction();

						System.out.println("\nO m�todo " + metodo.getName() 
							  + " est� anotado para RollBack. O Rollback acabou "
						      + "de acontecer.");
						
						break;
					}
				}
				
				if (!achou) {
					System.out.println("\nO m�todo " + metodo.getName() 
					  + " est� anotado para RollBack, mas a exce��o que "
					  + "ocorreu n�o consta da lista informada na anota��o - "
					  + classesDeExcecao);
					
					JPAUtil.commitTransaction();
				}	
			}
			else {
				JPAUtil.commitTransaction();

				System.out.println("\nOcorreu uma exce��o derivada de "
				  + "exception. O m�todo " + metodo.getName() + " n�o "
				  + "est� anotado para RollBack. Acabou de ocorrer um "
				  + "commit.");
			}

			throw e;
		}
		finally
		{	JPAUtil.closeEntityManager();
		}
    }
}
