package dao;

import java.util.List;

import modelo.Cliente;
import excecao.ObjetoNaoEncontradoException;

public interface ClienteDAO
{	
	public long inclui(Cliente umCliente); 

	public void altera(Cliente umCliente)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	public Cliente recuperaUmCliente(long numero) 
		throws ObjetoNaoEncontradoException; 

	public Cliente recuperaUmClienteComLock(long numero) 
			throws ObjetoNaoEncontradoException; 
	
	public List<Cliente> recuperaClientes();
	
	long recuperaQtdPeloNome(String nome);
	
	List<Cliente> recuperaPeloNome(String nome, 
         						   int deslocamento, 
            					   int linhasPorPagina);
}