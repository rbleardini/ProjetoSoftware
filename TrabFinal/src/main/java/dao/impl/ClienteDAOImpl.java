package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import modelo.Cliente;

import org.springframework.stereotype.Repository;

import dao.ClienteDAO;
import excecao.ObjetoNaoEncontradoException;

@Repository
public class ClienteDAOImpl implements ClienteDAO
{	
	@PersistenceContext
	private EntityManager em;

    public long inclui(Cliente umCliente) 
	{	
    	em.persist(umCliente);
		return umCliente.getNumero();
	}

	public void altera(Cliente umCliente) 
		throws ObjetoNaoEncontradoException 
	{	
		Cliente cliente = em.find(Cliente.class, umCliente.getNumero(), LockModeType.PESSIMISTIC_WRITE);
		
		if(cliente == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		em.merge(umCliente);
	}

    public void exclui(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Cliente cliente = em.find(Cliente.class, id, LockModeType.PESSIMISTIC_WRITE);
		
		if(cliente == null)
		{	throw new ObjetoNaoEncontradoException();
		}
		
		em.remove(cliente);
	}

    public Cliente recuperaUmCliente(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Cliente umCliente = (Cliente)em
			.find(Cliente.class, new Long(numero));
			
		if (umCliente == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umCliente;
	}

	public Cliente recuperaUmClienteComLock(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Cliente umCliente = (Cliente)em
			.find(Cliente.class, new Long(numero), LockModeType.PESSIMISTIC_WRITE);

		if (umCliente == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umCliente;
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> recuperaClientes()
	{	
		List<Cliente> clientes = em
			.createQuery("select c from Cliente c " +
					     "order by c.id asc")
			.getResultList();

		return clientes;
	}

	public long recuperaQtdPeloNome(String nome) 
	{	
		long qtd = (Long) em.createQuery("select count(c) from Cliente c where c.nome like :nome")
						    .setParameter("nome", nome.toUpperCase())
							.getSingleResult();
		return qtd;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> recuperaPeloNome(String nome, 
            							  int deslocamento, 
            							  int linhasPorPagina)
	{	
		List<Cliente> clientes = em
			.createQuery("select c from Cliente c "
					   + "where c.nome like :nome order by c.nome asc")
			.setParameter("nome", nome.toUpperCase())
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();

		return clientes;
	}
}