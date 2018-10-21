package servico;

import java.util.List;

import excecao.ContaNaoEncontradaException;
import modelo.Conta;

public interface ContaAppService
{	
	long inclui(Conta umaConta); 
	
	void altera(Conta umaConta) throws ContaNaoEncontradaException;
	
	void exclui(long numero) throws ContaNaoEncontradaException;
	
	void debita(Conta umaConta, double valor) throws ContaNaoEncontradaException;

	void credita(Conta umaConta, double valor) throws ContaNaoEncontradaException;

	void transfereValor(Conta contaDebitada, Conta contaCreditada, double valor) throws ContaNaoEncontradaException;

	Conta recuperaUmaConta(long numero) throws ContaNaoEncontradaException;
	
	List<Conta> recuperaContas(); 
}