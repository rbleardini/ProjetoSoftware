package servico.controle;

import java.lang.reflect.Method;

import anotacao.RollbackFor;
import anotacao.Perfil;
import singleton.SingletonPerfil;
import excecao.PerfilSemPermissaoException;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class InterceptadorDeServico implements MethodInterceptor 
{
	/* Parametros:
	 * 
	 * objeto - "this", o objeto "enhanced", isto é, o proxy.
	 * 
	 * metodo - o  método   interceptado,  isto  é,  um   método  da 
	 *          interface ProdutoDAO, LanceDAO, etc. 
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
			//Passo 3 - caso usuário n tenha perfil admin, ele n consegue executar
			SingletonPerfil singletonPerfil = SingletonPerfil.getSingletonPerfil();
			String logado = singletonPerfil.getPerfil();
			
 			if(metodo.isAnnotationPresent(Perfil.class))
 			{
				Perfil perfil = metodo.getAnnotation(Perfil.class);
				if (logado.equals(perfil.nome()))
				{
					throw new PerfilSemPermissaoException();
				}
			}
 			//Fim do passo 3 / Fim do trab 3
 			
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
			if(metodo.isAnnotationPresent(RollbackFor.class)) {
				Class<?>[] classesDeExcecao = 
						metodo.getAnnotation(RollbackFor.class).nomes();
				boolean achou = false;
				for (Class<?> classeDeExcecao: classesDeExcecao) {
					if (classeDeExcecao.isInstance(e)) {
						JPAUtil.rollbackTransaction();
						achou = true;
						break;
					}
				}
				if (!achou) {
					JPAUtil.commitTransaction();
				}
			}
			else {
				JPAUtil.commitTransaction();
			}

			System.out.println("\nOcorreu uma exceção derivada de "
			  + "exception. O método " + metodo.getName() + " sofrerá "
			  + "um rollback.");

			throw e;
		}
		finally
		{	JPAUtil.closeEntityManager();
		}
    }
}
