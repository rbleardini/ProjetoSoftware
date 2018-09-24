package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import anotacao.PersistenceContext;
import dao.ContaDAO;
import excecao.ObjetoNaoEncontradoException;
import modelo.Conta;

public class ContaDAOImpl implements ContaDAO 
{	
	@PersistenceContext
	private EntityManager em;
	
	public long inclui(Conta umaConta) 
	{	
		em.persist(umaConta);
		
		return umaConta.getId();
	}

	public void altera(Conta umaConta) 
		throws ObjetoNaoEncontradoException 
	{	
		Conta conta = em.find(Conta.class, umaConta.getId(), LockModeType.PESSIMISTIC_WRITE);
		
		if(conta == null)
		{	throw new ObjetoNaoEncontradoException();
		}
	
		em.merge(umaConta);
	}

	public void exclui(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Conta conta = em.find(Conta.class, id, LockModeType.PESSIMISTIC_WRITE);
		
		if(conta == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		em.remove(conta);
	}

	public void debita(Conta umaConta, double valor) 
		throws ObjetoNaoEncontradoException 
	{	
		Conta conta = em.find(Conta.class, umaConta.getId(), LockModeType.PESSIMISTIC_WRITE);
		
		if(conta == null)
		{	throw new ObjetoNaoEncontradoException();
		}
	
		conta.setSaldo(conta.getSaldo() - valor);
	}

	public void credita(Conta umaConta, double valor) 
		throws ObjetoNaoEncontradoException 
	{	
		Conta conta = em.find(Conta.class, umaConta.getId(), LockModeType.PESSIMISTIC_WRITE);
		
		if(conta == null)
		{	throw new ObjetoNaoEncontradoException();
		}
	
		conta.setSaldo(conta.getSaldo() + valor);
	}

	public Conta recuperaUmaConta(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Conta umaConta = (Conta)em
			.find(Conta.class, new Long(id));
		
		if (umaConta == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umaConta;
	}

	public Conta recuperaUmaContaComLock(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Conta umaConta = em.find(Conta.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (umaConta == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umaConta;
	}
	
	@SuppressWarnings("unchecked")
	public List<Conta> recuperaContas()
	{	
		List<Conta> contas = em
			.createQuery("select c from Conta c " +
					     "order by c.id asc")
			.getResultList();

		return contas;
	}
}