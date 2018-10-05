package excecao;

public class ClienteNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public ClienteNaoEncontradoException()
	{	super();
	}

	public ClienteNaoEncontradoException(String msg)
	{	super(msg);
	}
}	