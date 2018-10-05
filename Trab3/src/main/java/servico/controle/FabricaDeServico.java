package servico.controle;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.sf.cglib.proxy.Enhancer;

public class FabricaDeServico
{
	private static ResourceBundle prop;

	static
	{	try
		{	prop = ResourceBundle.getBundle("servico");
		}
		catch(MissingResourceException e)
		{	System.out.println("Aquivo servico.properties não encontrado.");
			throw new RuntimeException(e);
		}
	}

	// Esse método pode ser executado de 2 formas:
	// 1. produtoAppService = FabricaDeServico.<ProdutoAppService>getServico(ProdutoAppService.class);
	// 2. produtoAppService = FabricaDeServico.getServico(ProdutoAppService.class);
    
	@SuppressWarnings("unchecked")
	public static <T> T getServico(Class<T> tipo)
	{			
		String nomeDaClasse = null; 
	
		try
		{	nomeDaClasse = prop.getString(tipo.getSimpleName());

			Class<?> classeDoServico = Class.forName(nomeDaClasse);
			
			return (T)Enhancer.create (classeDoServico, new InterceptadorDeServico());
	
	        // O  proxy  deve  estender a  classe (ProdutoAppService por exemplo).
	        // O proxy deve ainda chamar o método intercept() da classe interceptadora, 
			// isto é, da classe InterceptadorDeServico (classe callback).
	        
	        // Enhancer enhancer = new Enhancer();
	        // enhancer.setSuperclass(classeDoServico);             // Superclasse do Servico  
	        // enhancer.setCallback(new InterceptadorDeServico());  // Interceptador do Servico
	
	        // return (T) enhancer.create();
		} 
		catch (ClassNotFoundException e)
		{	System.out.println("Classe " + nomeDaClasse + " não encontrada");
			throw new RuntimeException(e);
		}
		catch(MissingResourceException e)
		{	System.out.println("Chave " + tipo + " não encontrada em dao.properties");
			throw new RuntimeException(e);
		}
    }
}