package dao;

import java.util.List;

import excecao.ObjetoNaoEncontradoException;
import modelo.Conta;

public interface ContaDAO
{	
	long inclui(Conta umaConta); 

	void altera(Conta umaConta)
		throws ObjetoNaoEncontradoException; 
	
	void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	void debita(Conta umaConta, double valor)
			throws ObjetoNaoEncontradoException; 
		
	void credita(Conta umaConta, double valor)
			throws ObjetoNaoEncontradoException; 
		
	Conta recuperaUmaConta(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	List<Conta> recuperaContas();
}