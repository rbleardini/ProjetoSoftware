package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import dao.ContaDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Conta;
import servico.controle.JPAUtil;

public class ContaDAOImpl implements ContaDAO
{	
	public long inclui(Conta umaConta) 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			em.persist(umaConta);
			
			return umaConta.getId();
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void altera(Conta umaConta) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			Conta conta = em.find(Conta.class, umaConta.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(conta == null)
			{	throw new ObjetoNaoEncontradoException();
			}
		
			em.merge(umaConta);
		}
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void exclui(long id) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();
		
			Conta conta = em.find(Conta.class, id, LockModeType.PESSIMISTIC_WRITE);
			
			if(conta == null)
			{	throw new ObjetoNaoEncontradoException();
			}
	
			em.remove(conta);
		}
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void debita(Conta umaConta, double valor) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			Conta conta = em.find(Conta.class, umaConta.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(conta == null)
			{	throw new ObjetoNaoEncontradoException();
			}
		
			conta.setSaldo(conta.getSaldo() - valor);

			em.merge(umaConta);
		}
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public void credita(Conta umaConta, double valor) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			Conta conta = em.find(Conta.class, umaConta.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(conta == null)
			{	throw new ObjetoNaoEncontradoException();
			}
		
			conta.setSaldo(conta.getSaldo() + valor);

			em.merge(umaConta);
		}
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Conta recuperaUmaConta(long id) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			Conta umaConta = (Conta)em
				.find(Conta.class, new Long(id));
			
			if (umaConta == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umaConta;
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}

	public Conta recuperaUmaContaComLock(long id) 
		throws ObjetoNaoEncontradoException 
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			Conta umaConta = em.find(Conta.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (umaConta == null)
			{	throw new ObjetoNaoEncontradoException();
			}

			return umaConta;
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Conta> recuperaContas()
	{	try
		{	
			EntityManager em = JPAUtil.getEntityManager();

			List<Conta> contas = em
				.createQuery("select c from Conta c " +
						     "order by c.id asc")
				.getResultList();

			return contas;
		} 
		catch(RuntimeException e)
		{	throw new InfraestruturaException(e);
		}
	}
}