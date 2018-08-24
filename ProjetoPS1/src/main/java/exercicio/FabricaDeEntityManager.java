package exercicio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FabricaDeEntityManager
{	private static FabricaDeEntityManager fabrica = null;
	private EntityManagerFactory emf = null;
			
	private FabricaDeEntityManager()
	{	
		try
		{	
			//Essa é a verdadeira fábrica de EntityManager
			emf = Persistence.createEntityManagerFactory("exercicio");
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			System.out.println(">>>>>>>>>> Mensagem de erro: " + e.getMessage());
		}
	}

	public static EntityManager criarSessao()
	{	if (fabrica == null)
		{	fabrica = new FabricaDeEntityManager();
		}	

		return fabrica.emf.createEntityManager();
	}

	public static void fecharFabricaDeEntityManager()
	{	if (fabrica != null)
			if (fabrica.emf != null)
				fabrica.emf.close();
	}
}