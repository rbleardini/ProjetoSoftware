package excecao;

public class ProdutoNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public ProdutoNaoEncontradoException()
	{	super();
	}

	public ProdutoNaoEncontradoException(String msg)
	{	super(msg);
	}
}	