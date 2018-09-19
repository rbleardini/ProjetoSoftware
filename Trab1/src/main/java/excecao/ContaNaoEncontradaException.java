package excecao;

public class ContaNaoEncontradaException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public ContaNaoEncontradaException()
	{	super();
	}

	public ContaNaoEncontradaException(String msg)
	{	super(msg);
	}
}	