package servico.impl;

import java.util.List;

import anotacao.RollbackFor;
import anotacao.Transactional;
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

	@Transactional
	public long inclui(Conta umaConta) 
	{	
		System.out.println("\nDentro de ContaAppServiceImpl. Vai chamar o método inclui() de ContaDAOImpl.");
		
		long numero = contaDAO.inclui(umaConta);
		
		System.out.println("\nDentro de ContaAppServiceImpl. Chamou o método inclui() de ContaDAOImpl.");
		
		return numero;
	}

	
	@RollbackFor(nomes={ContaNaoEncontradaException.class, 
			            ClienteNaoEncontradoException.class})
	@Transactional
	public void altera(Conta umaConta)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o método altera() de ContaDAOImpl.");

			contaDAO.altera(umaConta);
			
			System.out.println("\nChamou o método altera() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Transactional
	public void exclui(long numero) 
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("Vai chamar o método exclui() de ContaDAOImpl.");

			contaDAO.exclui(numero);

			System.out.println("Chamou o método exclui() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
		    throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Transactional
	public void debita(Conta umaConta, double valor)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o método debita() de ContaDAOImpl.");

			contaDAO.debita(umaConta, valor);
			
			System.out.println("\nChamou o método debita() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Transactional
	public void credita(Conta umaConta, double valor)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o método credita() de ContaDAOImpl.");

			contaDAO.credita(umaConta, valor);
			
			System.out.println("\nChamou o método credita() de ContaDAOImpl.");
		} 
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}
	
	@Override
	@Transactional
	public void transfereValor(Conta contaDebitada, Conta contaCreditada, double valor)
		throws ContaNaoEncontradaException
	{	
		try
		{	
			System.out.println("\nVai chamar o método debita() de ContaAppService.");

			debita(contaDebitada, valor);

			System.out.println("\nVai chamou o método debita() de ContaAppService.");
			
			System.out.println("\nVai chamar o método credita() de ContaAppService.");
			
			credita(contaCreditada, valor);
			
			System.out.println("\nChamou o método credita() ContaAppService.");
		} 
		catch(ContaNaoEncontradaException e)
		{	
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}
		
	public Conta recuperaUmaConta(long numero) 
		throws ContaNaoEncontradaException
	{	
		try
		{	
			// System.out.println("Vai chamar o método recuperaUmaConta() de ContaDAOImpl.");

			Conta conta = contaDAO.recuperaUmaConta(numero);
			
			// System.out.println("Chamou o método recuperaUmaConta() de ContaDAOImpl.");
			
			return conta;
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	public List<Conta> recuperaContas() 
	{	
		// System.out.println("Vai chamar o método recuperaContas() de ContaDAOImpl.");

		List<Conta> contas = contaDAO.recuperaContas();
		
		// System.out.println("Chamou o método recuperaContas() de ContaDAOImpl.");

		return contas;
	}
}