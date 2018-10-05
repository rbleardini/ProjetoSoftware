package singleton;

//Classe sugerida pelo professor no passo 2
public class SingletonPerfil 
{ 
	private static SingletonPerfil singletonPerfil = null;
	private String perfil;
	private SingletonPerfil() { }
	
	public static SingletonPerfil getSingletonPerfil()
	{ 
		if (singletonPerfil == null)
		{ 
			singletonPerfil = new SingletonPerfil();
		}
		return singletonPerfil;
	}
	
	public String getPerfil() 
	{ 
		return perfil;
	}
	public void setPerfil(String perfil)
	{ 
		this.perfil = perfil;
	}
}