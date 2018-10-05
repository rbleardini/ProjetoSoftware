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
	 * objeto - "this", o objeto "enhanced", isto �, o proxy.
	 * 
	 * metodo - o  m�todo   interceptado,  isto  �,  um   m�todo  da 
	 *          interface ProdutoDAO, LanceDAO, etc. 
	 * 
	 * args - um  array  de args; tipos  primitivos s�o empacotados.
	 *        Cont�m   os   argumentos  que  o  m�todo  interceptado 
	 *        recebeu.
	 * 
	 * metodoProxy - utilizado para executar um m�todo super. Veja o
	 *               coment�rio abaixo.
	 * 
	 * MethodProxy  -  Classes  geradas pela  classe Enhancer passam 
	 * este objeto para o objeto MethodInterceptor registrado quando
	 * um m�todo  interceptado �  executado.  Ele pode ser utilizado
	 * para  invocar o  m�todo  original,  ou  chamar o mesmo m�todo
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
			//Passo 3 - caso usu�rio n tenha perfil admin, ele n consegue executar
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

			System.out.println("\nDentro do interceptador - Executando o m�todo " + metodo.getName() 
					+ ". Acabou de abrir transa��o - vai chamar super." + metodo.getName());
			
			Object obj = metodoDoProxy.invokeSuper(objeto, args);

			JPAUtil.commitTransaction();

			System.out.println("\nDentro do interceptador - Executando o m�todo " + metodo.getName() 
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

			System.out.println("\nOcorreu uma exce��o derivada de "
			  + "exception. O m�todo " + metodo.getName() + " sofrer� "
			  + "um rollback.");

			throw e;
		}
		finally
		{	JPAUtil.closeEntityManager();
		}
    }
}
