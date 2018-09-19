

import java.util.List;

import corejava.Console;
import excecao.ContaNaoEncontradaException;
import modelo.Conta;
import servico.ContaAppService;
import servico.controle.FabricaDeServico;
import util.Util;

public class Principal
{	public static void main (String[] args) 
	{	
		double saldo;
		String dataCadastro;
		Conta umaConta;

		System.out.println("\nVai criar o proxy de serviço");
		
		ContaAppService contaAppService = FabricaDeServico.getServico(ContaAppService.class);

		System.out.println("\nCriou o proxy. Classe de implementação = " + contaAppService.getClass().getName());

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma conta");
			System.out.println("2. Alterar uma conta");
			System.out.println("3. Remover uma conta");
			System.out.println("4. Listar todas as contas");
			System.out.println("5. Debitar valor de conta");
			System.out.println("6. Creditar valor em conta");
			System.out.println("7. Transferir valor entre contas");
			System.out.println("8. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 6:");
					
			switch (opcao)
			{	case 1:
				{
					saldo = Console.readDouble(
						"Informe o saldo da conta: ");
					dataCadastro = Console.readLine(
						"Informe a data de cadastramento do conta: ");
						
					umaConta = new Conta(saldo, Util.strToDate(dataCadastro));

					System.out.println("\nDentro do Principal. Vai chamar contaAppService.inclui");
					
					long numero = contaAppService.inclui(umaConta);
					
					System.out.println("\nDentro do Principal. Chamou contaAppService.inclui");
					
					System.out.println('\n' + "Conta número " + 
					    numero + " incluído com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do conta que você deseja alterar: ");
										
					try
					{	umaConta = contaAppService.recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umaConta.getId() + 
						"    Saldo = " + umaConta.getSaldo());
												
					System.out.println('\n' + "O que você deseja alterar?");
					System.out.println('\n' + "1. Saldo");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite o número de 1:");
					
					switch (opcaoAlteracao)
					{	case 1:
							double novoSaldo = Console.
										readDouble("Digite o novo saldo: ");
							
							umaConta.setSaldo(novoSaldo);

							try
							{	
								System.out.println("\nDentro do Principal. Vai chamar contaAppService.altera");
								
								contaAppService.altera(umaConta);
							
								System.out.println("\nDentro do Principal. Chamou contaAppService.altera");

								System.out.println('\n' + 
									"Alteração de nome efetuada com sucesso!");
							}
							catch(ContaNaoEncontradaException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;
					
						default:
							System.out.println('\n' + "Opção inválida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número da conta que você deseja remover: ");
									
					try
					{	umaConta = contaAppService.
										recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umaConta.getId() + 
						"    Saldo = " + umaConta.getSaldo());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remoção da conta?");

					if(resp.equals("s"))
					{	try
						{	
							System.out.println("\nDentro do Principal. Vai chamar contaAppService.exclui");
							
							contaAppService.exclui (umaConta.getId());
						
							System.out.println("\nDentro do Principal. Chamou contaAppService.exclui");
						
							System.out.println('\n' + 
								"Conta removida com sucesso!");
						}
						catch(ContaNaoEncontradaException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Conta não removida.");
					}
					
					break;
				}

				case 4:
				{	
					List<Conta> contas = contaAppService.recuperaContas();

					for (Conta conta : contas)
					{	
						System.out.println('\n' + 
							"Id = " + conta.getId() +
							"  Saldo = " + conta.getSaldo() +
							"  Data Cadastro = " + conta.getDataCadastroMasc());
					}
					
					break;
				}

				case 5:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do conta que você deseja debitar: ");
										
					try
					{	umaConta = contaAppService.recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umaConta.getId() + 
						"    Saldo = " + umaConta.getSaldo());
												
					double valor = Console.readDouble("Digite o valor a ser debitado: ");

					System.out.println("\nDentro do Principal. Vai chamar contaAppService.debita");

					try
					{	contaAppService.debita(umaConta, valor);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
							
					System.out.println("\nDentro do Principal. Chamou contaAppService.debita");

					System.out.println('\n' + "Conta debitada com sucesso!");

					break;
				}

				case 6:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do conta que você deseja creditar: ");
										
					try
					{	umaConta = contaAppService.recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umaConta.getId() + 
						"    Saldo = " + umaConta.getSaldo());
												
					double valor = Console.readDouble("Digite o valor a ser creditado: ");

					System.out.println("\nDentro do Principal. Vai chamar contaAppService.credita");

					try
					{	contaAppService.credita(umaConta, valor);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
							
					System.out.println("\nDentro do Principal. Chamou contaAppService.credita");

					System.out.println('\n' + "Conta creditada com sucesso!");

					break;
				}

				case 7:
				{	
					int numContaDebitada = Console.readInt('\n' + 
							"Digite o número do conta que você deseja debitar: ");
											
					int numContaCreditada = Console.readInt('\n' + 
						"Digite o número do conta que você deseja creditar: ");

					Conta contaDebitada;
					Conta contaCreditada;
					
					try
					{	
						contaDebitada = contaAppService.recuperaUmaConta(numContaDebitada);
						contaCreditada = contaAppService.recuperaUmaConta(numContaCreditada);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número da conta debitada = " + contaDebitada.getId() + 
						"    Saldo = " + contaDebitada.getSaldo());
												
					System.out.println('\n' + 
							"Número da conta creditada = " + contaCreditada.getId() + 
							"    Saldo = " + contaCreditada.getSaldo());
													
					double valor = Console.readDouble("Digite o valor a ser transferido: ");

					System.out.println("\nDentro do Principal. Vai chamar contaAppService.trasfere");

					try
					{	contaAppService.transfereValor(contaDebitada, contaCreditada, valor);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
							
					System.out.println("\nDentro do Principal. Chamou contaAppService.transfere");

					System.out.println('\n' + "Contas atualizadas com sucesso!");

					break;
				}

				case 8:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}		
	}
}
