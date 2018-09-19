package servico.controle;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class InterceptadorDeServico implements MethodInterceptor 
{
	/* Parametros:
	 * 
	 * objeto - "this", o objeto "enhanced", isto é, o proxy.
	 * 
	 * metodo - o  método   interceptado,  isto  é,  um   método  da 
	 *          interface ContaDAO, LanceDAO, etc. 
	 * 
	 * args - um  array  de args; tipos  primitivos são empacotados.
	 *        Contém   os   argumentos  que  o  método  interceptado 
	 *        recebeu.
	 * 
	 * metodoProxy - utilizado para executar um método super. Veja o
	 *               comentário abaixo.
	 * 
	 * MethodProxy  -  Classes  geradas pela  classe Enhancer passam 
	 * este objeto para o objeto MethodInterceptor registrado quando
	 * um método  interceptado é  executado.  Ele pode ser utilizado
	 * para  invocar o  método  original,  ou  chamar o mesmo método
	 * sobre um objeto diferente do mesmo tipo.
	 * 
	 */
	
	public Object intercept (Object objeto, 
    		                 Method metodo, 
    		                 Object[] args, 
                             MethodProxy metodoDoProxy) 
    	throws Throwable 
    {
		try 
		{	
			JPAUtil.beginTransaction();

			System.out.println("\nDentro do interceptador - Executando o método " + metodo.getName() 
					+ ". Acabou de abrir transação - vai chamar super." + metodo.getName());
			
			Object obj = metodoDoProxy.invokeSuper(objeto, args);

			JPAUtil.commitTransaction();

			System.out.println("\nDentro do interceptador - Executando o método " + metodo.getName() 
					+ ". Acabou de comitar a transação - vai retornar para o Principal");
			
			return obj;
		} 
		catch(RuntimeException e) 
		{
			JPAUtil.rollbackTransaction();

			System.out.println("\nOcorreu uma exceção derivada de "
			  + "RuntimeException. O método " + metodo.getName()  
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

						System.out.println("\nO método " + metodo.getName() 
							  + " está anotado para RollBack. O Rollback acabou "
						      + "de acontecer.");
						
						break;
					}
				}
				
				if (!achou) {
					System.out.println("\nO método " + metodo.getName() 
					  + " está anotado para RollBack, mas a exceção que "
					  + "ocorreu não consta da lista informada na anotação - "
					  + classesDeExcecao);
					
					JPAUtil.commitTransaction();
				}	
			}
			else {
				JPAUtil.commitTransaction();

				System.out.println("\nOcorreu uma exceção derivada de "
				  + "exception. O método " + metodo.getName() + " não "
				  + "está anotado para RollBack. Acabou de ocorrer um "
				  + "commit.");
			}

			throw e;
		}
		finally
		{	JPAUtil.closeEntityManager();
		}
    }
}
