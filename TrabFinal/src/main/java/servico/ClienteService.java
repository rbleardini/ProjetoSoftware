package servico;

import java.util.List;

import modelo.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.ClienteDAO;
import excecao.ClienteNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;

//@Service
public class ClienteService
{	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@Transactional
	public long inclui(Cliente umCliente) 
	{	return clienteDAO.inclui(umCliente);
	}

	@Transactional
	public void altera(Cliente umCliente)
		throws ClienteNaoEncontradoException
	{	try
		{	clienteDAO.altera(umCliente);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	@Transactional
	public void exclui(Cliente umCliente) 
		throws ClienteNaoEncontradoException
	{	try
		{	
			clienteDAO.exclui(umCliente.getNumero());
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	public Cliente recuperaUmCliente(long numero) 
		throws ClienteNaoEncontradoException
	{	try
		{	return clienteDAO.recuperaUmCliente(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ClienteNaoEncontradoException("Cliente não encontrado");
		}
	}

	public List<Cliente> recuperaClientes()
	{	return clienteDAO.recuperaClientes();
	}

	public long recuperaQtdPeloNome(String nome) 
	{	
		return clienteDAO.recuperaQtdPeloNome(nome + "%");
	}
	
	public List<Cliente> recuperaPeloNome(String nome, int deslocamento, int linhasPorPagina) 
	{	
		List<Cliente> clientes = clienteDAO.recuperaPeloNome(nome + "%", deslocamento, linhasPorPagina);

		return clientes;
	}
}