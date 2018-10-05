package servico.impl;

import java.util.List;

import anotacao.RollbackFor;
import anotacao.Perfil;	
import dao.ProdutoDAO;
import dao.controle.FabricaDeDAOs;
import excecao.ClienteNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;
import servico.ProdutoAppService;

public class ProdutoAppServiceImpl implements ProdutoAppService
{	
	private static ProdutoDAO produtoDAO = FabricaDeDAOs.getDAO(ProdutoDAO.class);
	
	@Perfil(nome="admin")
	public long inclui(Produto umProduto) 
	{	
		System.out.println("\nDentro de ProdutoAppServiceImpl. Vai chamar o m�todo inclui() de ProdutoDAOImpl.");
		
		long numero = produtoDAO.inclui(umProduto);
		
		System.out.println("\nDentro de ProdutoAppServiceImpl. Chamou o m�todo inclui() de ProdutoDAOImpl.");
		
		return numero;
	}

	@RollbackFor(nomes={ProdutoNaoEncontradoException.class,
			            ClienteNaoEncontradoException.class})
	@Perfil(nome="admin")
	public void altera(Produto umProduto)
		throws ProdutoNaoEncontradoException
	{	
		try
		{	
			System.out.println("\nVai chamar o m�todo altera() de ProdutoDAOImpl.");

			produtoDAO.altera(umProduto);
			
			System.out.println("\nChamou o m�todo altera() de ProdutoDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
	}
	
	@Perfil(nome="admin")
	public void exclui(long numero) 
		throws ProdutoNaoEncontradoException
	{	
		try
		{	
			System.out.println("Vai chamar o m�todo exclui() de ProdutoDAOImpl.");

			produtoDAO.exclui(numero);

			System.out.println("Chamou o m�todo exclui() de ProdutoDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
		    throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
	}
	
	@Perfil(nome="admin")
	public Produto recuperaUmProduto(long numero) 
		throws ProdutoNaoEncontradoException
	{	
		try
		{	
			// System.out.println("Vai chamar o m�todo recuperaUmProduto() de ProdutoDAOImpl.");

			Produto produto = produtoDAO.recuperaUmProduto(numero);
			
			// System.out.println("Chamou o m�todo recuperaUmProduto() de ProdutoDAOImpl.");
			
			return produto;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ProdutoNaoEncontradoException("Produto n�o encontrado");
		}
	}
	
	@Perfil(nome="admin")
	public List<Produto> recuperaProdutos() 
	{	
		// System.out.println("Vai chamar o m�todo recuperaProdutos() de ProdutoDAOImpl.");

		List<Produto> produtos = produtoDAO.recuperaProdutos();
		
		// System.out.println("Chamou o m�todo recuperaProdutos() de ProdutoDAOImpl.");

		return produtos;
	}
}