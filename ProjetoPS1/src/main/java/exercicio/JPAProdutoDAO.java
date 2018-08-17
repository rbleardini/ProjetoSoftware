package exercicio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

public class JPAProdutoDAO implements ProdutoDAO
{	
	public long inclui(Produto umProduto) 
	{	EntityManager em = null;
		EntityTransaction tx = null;
		
		try
		{	// transiente - objeto novo: ainda não persistente
			// persistente - apos ser persistido 
			// destacado - objeto persistente não vinculado a um entity manager
==>		

			
			
			
			
			
			
			return umProduto.getId();
		} 
		catch(RuntimeException e)
		{	if (tx != null)
			{	
				try
				{	tx.rollback();
				}
				catch(RuntimeException he)
				{ }
			}
			throw e;
		}
		finally
==>		{		
		}
	}

	public void altera(Produto umProduto) throws ProdutoNaoEncontradoException
	{	EntityManager em = null;
		EntityTransaction tx = null;
		Produto produto = null;
		try
		{	
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();
			
==>
			
			if(produto == null)
			{
==>	
==>
			}
==>	
			tx.commit();
		} 
		catch(RuntimeException e)
		{ 
			if (tx != null)
		    {   
				try
		        {	tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public void exclui(long numero) throws ProdutoNaoEncontradoException 
	{	EntityManager em = null;
		EntityTransaction tx = null;
		
		try
		{	
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

==>			Produto produto = em.find(Produto.class, new Long(numero), LockModeType.PESSIMISTIC_WRITE);
			
			if(produto == null)
			{	tx.rollback();
				throw new ProdutoNaoEncontradoException("Produto não encontrado");
			}

==>			
			tx.commit();
		} 
		catch(RuntimeException e)
		{   
			if (tx != null)
		    {   
				try
		        {	tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public Produto recuperaUmProduto(long numero) throws ProdutoNaoEncontradoException
	{	EntityManager em = null;
		
		try
		{	
			em = FabricaDeEntityManager.criarSessao();

==>			Produto umProduto = em.find(Produto.class, numero);
			
			// Características no método find():
			// 1. É genérico: não requer um cast.
			// 2. Retorna null caso a linha não seja encontrada no banco.

			if(umProduto == null)
			{	throw new ProdutoNaoEncontradoException("Produto não encontrado");
			}
			return umProduto;
		} 
		finally
		{   em.close();
		}
	}

	public List<Produto> recuperaProdutos()
	{	EntityManager em = null;
		
		try
		{	em = FabricaDeEntityManager.criarSessao();

==>

			// Retorna um List vazio caso a tabela correspondente esteja vazia.
			
			return produtos;
		} 
		finally
		{   em.close();
		}
	}
}