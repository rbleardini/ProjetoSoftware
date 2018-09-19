package servico.impl;

import java.util.List;

import anotacao.RollbackFor;
import dao.ContaDAO;
import dao.controle.FabricaDeDAOs;
import excecao.ClienteNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.ContaNaoEncontradaException;
import modelo.Conta;
import servico.ContaAppService;

public class ContaAppServiceImpl implements ContaAppService
{	
	private static ContaDAO contaDAO = FabricaDeDAOs.getDAO(ContaDAO.class);

	public long inclui(Conta umaConta) 
	{	
		System.out.println("\nDentro de ContaAppServiceImpl. Vai chamar o m�todo inclui() de ContaDAOImpl.");
		
		long numero = contaDAO.inclui(umaConta);
		
		System.out.println("\nDentro de ContaAppServiceImpl. Chamou o m�todo inclui() de ContaDAOImpl.");
		
		return numero;
	}

	
	@RollbackFor(nomes={ContaNaoEncontradaException.class, 
			            ClienteNaoEncontradoException.class})
	public void altera(Conta umaConta)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o m�todo altera() de ContaDAOImpl.");

			contaDAO.altera(umaConta);
			
			System.out.println("\nChamou o m�todo altera() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ContaNaoEncontradaException("Conta n�o encontrada");
		}
	}
	
	public void exclui(long numero) 
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("Vai chamar o m�todo exclui() de ContaDAOImpl.");

			contaDAO.exclui(numero);

			System.out.println("Chamou o m�todo exclui() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
		    throw new ContaNaoEncontradaException("Conta n�o encontrada");
		}
	}

	public void debita(Conta umaConta, double valor)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o m�todo debita() de ContaDAOImpl.");

			contaDAO.debita(umaConta, valor);
			
			System.out.println("\nChamou o m�todo debita() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ContaNaoEncontradaException("Conta n�o encontrada");
		}
	}
		
	public void credita(Conta umaConta, double valor)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o m�todo credita() de ContaDAOImpl.");

			contaDAO.credita(umaConta, valor);
			
			System.out.println("\nChamou o m�todo credita() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ContaNaoEncontradaException("Conta n�o encontrada");
		}
	}
	
	@Override
	public void transfereValor(Conta contaDebitada, Conta contaCreditada, double valor)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o m�todo debita() de ContaAppService.");

			debita(contaDebitada, valor);

			System.out.println("\nVai chamou o m�todo debita() de ContaAppService.");
			
			System.out.println("\nVai chamar o m�todo credita() de ContaAppService.");
			
			credita(contaCreditada, valor);
			
			System.out.println("\nChamou o m�todo credita() ContaAppService.");
		} 
		catch(ContaNaoEncontradaException e)
		{	
			throw new ContaNaoEncontradaException("Conta n�o encontrada");
		}
	}
		
	public Conta recuperaUmaConta(long numero) 
		throws ContaNaoEncontradaException
	{	
		try
		{	
			// System.out.println("Vai chamar o m�todo recuperaUmaConta() de ContaDAOImpl.");

			Conta conta = contaDAO.recuperaUmaConta(numero);
			
			// System.out.println("Chamou o m�todo recuperaUmaConta() de ContaDAOImpl.");
			
			return conta;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ContaNaoEncontradaException("Conta n�o encontrada");
		}
	}

	public List<Conta> recuperaContas() 
	{	
		// System.out.println("Vai chamar o m�todo recuperaContas() de ContaDAOImpl.");

		List<Conta> contas = contaDAO.recuperaContas();
		
		// System.out.println("Chamou o m�todo recuperaContas() de ContaDAOImpl.");

		return contas;
	}
}