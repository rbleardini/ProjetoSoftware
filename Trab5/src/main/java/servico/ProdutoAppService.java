package servico;

import java.util.List;

import anotacao.Autowired;
import anotacao.Transactional;

import dao.ProdutoDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;

// Funciona com ou sem essa anota��o pelo fato do bean de servi�o 
// ter sido especificado no arquivo de configura��o do Spring.

// @Service
public class ProdutoAppService
{	
	@Autowired
	private ProdutoDAO produtoDAO = null;
	
	@Transactional
	public long inclui(Produto umProduto) 
	{	return produtoDAO.inclui(umProduto);
	}

	@Transactional
	public void altera(Produto umProduto)
		throws ProdutoNaoEncontradoException
	{	
		try
		{	produtoDAO.altera(umProduto);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
		
	}
	
	@Transactional
	public void exclui(long numero) 
		throws ProdutoNaoEncontradoException
	{	try
		{	produtoDAO.exclui(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
	}

	public Produto recuperaUmProduto(long numero) 
		throws ProdutoNaoEncontradoException
	{	try
		{	return produtoDAO.recuperaUmProduto(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
	}

	public List<Produto> recuperaProdutos() 
	{	return produtoDAO.recuperaProdutos();
	}
}