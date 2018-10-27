import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.ContaNaoEncontradaException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Conta;
import modelo.Produto;
import servico.ContaAppService;
import servico.ProdutoAppService;
import servico.controle.FabricaDeServico;
import util.Util;

public class Principal
{	public static void main (String[] args) 
	{	
		double saldo;
		String dataCadastro;
		Conta umaConta;

		System.out.println("\nVai criar o proxy de servi�o");
		
		ContaAppService contaAppService = FabricaDeServico.getServico(ContaAppService.class);

		System.out.println("\nCriou o proxy. Classe de implementa��o = " + contaAppService.getClass().getName());

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma conta");
			System.out.println("2. Alterar uma conta");
			System.out.println("3. Remover uma conta");
			System.out.println("4. Listar todas as contas");
			System.out.println("5. Debitar valor de conta");
			System.out.println("6. Creditar valor em conta");
			System.out.println("7. Transferir valor entre contas");
			System.out.println("8. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um n�mero entre 1 e 6:");
					
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
					
					System.out.println('\n' + "Conta n�mero " + 
					    numero + " inclu�do com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do conta que voc� deseja alterar: ");
										
					try
					{	umaConta = contaAppService.recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umaConta.getId() + 
						"    Saldo = " + umaConta.getSaldo());
												
					System.out.println('\n' + "O que voc� deseja alterar?");
					System.out.println('\n' + "1. Saldo");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite o n�mero de 1:");
					
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
									"Altera��o de nome efetuada com sucesso!");
							}
							catch(ContaNaoEncontradaException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;
					
						default:
							System.out.println('\n' + "Op��o inv�lida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero da conta que voc� deseja remover: ");
									
					try
					{	umaConta = contaAppService.
										recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umaConta.getId() + 
						"    Saldo = " + umaConta.getSaldo());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o da conta?");

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
							"Conta n�o removida.");
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
						"Digite o n�mero do conta que voc� deseja debitar: ");
										
					try
					{	umaConta = contaAppService.recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umaConta.getId() + 
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
						"Digite o n�mero do conta que voc� deseja creditar: ");
										
					try
					{	umaConta = contaAppService.recuperaUmaConta(resposta);
					}
					catch(ContaNaoEncontradaException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umaConta.getId() + 
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
							"Digite o n�mero do conta que voc� deseja debitar: ");
											
					int numContaCreditada = Console.readInt('\n' + 
						"Digite o n�mero do conta que voc� deseja creditar: ");

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
						"N�mero da conta debitada = " + contaDebitada.getId() + 
						"    Saldo = " + contaDebitada.getSaldo());
												
					System.out.println('\n' + 
							"N�mero da conta creditada = " + contaCreditada.getId() + 
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
					System.out.println('\n' + "Op��o inv�lida!");
			}
		}
		String nome;
		double lanceMinimo;
		Produto umProduto;

		ProdutoAppService produtoAppService = FabricaDeServico.getServico(ProdutoAppService.class);

		continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out.println("4. Listar todos os produtos");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um n�mero entre 1 e 5:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do produto: ");
					lanceMinimo = Console.readDouble(
						"Informe o valor do lance m�nimo: ");
					dataCadastro = Console.readLine(
						"Informe a data de cadastramento do produto: ");
						
					umProduto = new Produto(nome, lanceMinimo, Util.strToCalendar(dataCadastro));
					
					long numero = produtoAppService.inclui(umProduto);
					
					System.out.println('\n' + "Produto n�mero " + 
					    numero + " inclu�do com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do produto que voc� deseja alterar: ");
										
					try
					{	umProduto = produtoAppService.recuperaUmProduto(resposta);
					}
					catch(ProdutoNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Lance M�nimo = " + umProduto.getLanceMinimo());
												
					System.out.println('\n' + "O que voc� deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Lance M�nimo");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um n�mero de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");
							
							umProduto.setNome(novoNome);

							try
							{	produtoAppService.altera(umProduto);

								System.out.println('\n' + 
									"Altera��o de nome efetuada com sucesso!");
							}
							catch(ProdutoNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;
					
						case 2:
							double novoLanceMinimo = Console.
									readDouble("Digite o novo lance m�nimo: ");
							
							umProduto.setLanceMinimo(novoLanceMinimo);

							try
							{	produtoAppService.altera(umProduto);

								System.out.println('\n' + 
									"Altera��o de descri��o efetuada " +
									"com sucesso!");						
							}
							catch(ProdutoNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;

						default:
							System.out.println('\n' + "Op��o inv�lida!");
					}

					break;
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do produto que voc� deseja remover: ");
									
					try
					{	umProduto = produtoAppService.
										recuperaUmProduto(resposta);
					}
					catch(ProdutoNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do produto?");

					if(resp.equals("s"))
					{	try
						{	produtoAppService.exclui (umProduto.getId());
							System.out.println('\n' + 
								"Produto removido com sucesso!");
						}
						catch(ProdutoNaoEncontradoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Produto n�o removido.");
					}
					
					break;
				}

				case 4:
				{	
					List<Produto> produtos = produtoAppService.recuperaProdutos();

					for (Produto produto : produtos)
					{	
						System.out.println('\n' + 
							"Id = " + produto.getId() +
							"  Nome = " + produto.getNome() +
							"  Lance m�nimo = " + produto.getLanceMinimo() +
							"  Data Cadastro = " + produto.getDataCadastroMasc());
					}
					
					break;
				}

				case 5:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");
			}
		}
	}
}
