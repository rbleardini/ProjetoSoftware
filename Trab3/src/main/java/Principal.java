

import java.util.List;


import corejava.Console;
import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;
import servico.ProdutoAppService;
import servico.controle.FabricaDeServico;
import util.Util;
import singleton.SingletonPerfil;

public class Principal
{	public static void main (String[] args) 
	{	
		String nome;
		double lanceMinimo;
		String dataCadastro;
		Produto umProduto;
		
		//Definir o perfil do usuário, passo 2
		SingletonPerfil singletonPerfil = SingletonPerfil.getSingletonPerfil();
		singletonPerfil.setPerfil("admin"); 
		// Aqui estamos simulando que o usuário "logado" possui o perfil "admin".
		
		System.out.println("\nVai criar o proxy de serviço");
		
		ProdutoAppService produtoAppService = FabricaDeServico.getServico(ProdutoAppService.class);

		System.out.println("\nCriou o proxy. Classe de implementação = " + produtoAppService.getClass().getName());

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um produto");
			System.out.println("2. Alterar um produto");
			System.out.println("3. Remover um produto");
			System.out.println("4. Listar todos os produtos");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 5:");
					
			switch (opcao)
			{	case 1:
				{
					nome = Console.readLine('\n' + 
						"Informe o nome do produto: ");
					lanceMinimo = Console.readDouble(
						"Informe o valor do lance mínimo: ");
					dataCadastro = Console.readLine(
						"Informe a data de cadastramento do produto: ");
						
					umProduto = new Produto(nome, lanceMinimo, Util.strToDate(dataCadastro));

					System.out.println("\nDentro do Principal. Vai chamar produtoAppService.inclui");
					
					long numero = produtoAppService.inclui(umProduto);
					
					System.out.println("\nDentro do Principal. Chamou produtoAppService.inclui");
					
					System.out.println('\n' + "Produto número " + 
					    numero + " incluído com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja alterar: ");
										
					try
					{	umProduto = produtoAppService.recuperaUmProduto(resposta);
					}
					catch(ProdutoNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
						"    Lance Mínimo = " + umProduto.getLanceMinimo());
												
					System.out.println('\n' + "O que você deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Lance Mínimo");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um número de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");
							
							umProduto.setNome(novoNome);

							try
							{	
								System.out.println("\nDentro do Principal. Vai chamar produtoAppService.altera");
								
								produtoAppService.altera(umProduto);
							
								System.out.println("\nDentro do Principal. Chamou produtoAppService.altera");

								System.out.println('\n' + 
									"Alteração de nome efetuada com sucesso!");
							}
							catch(ProdutoNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
								
							break;
					
						case 2:
							double novoLanceMinimo = Console.
									readDouble("Digite o novo lance mínimo: ");
							
							umProduto.setLanceMinimo(novoLanceMinimo);

							try
							{	produtoAppService.altera(umProduto);

								System.out.println('\n' + 
									"Alteração de descrição efetuada " +
									"com sucesso!");						
							}
							catch(ProdutoNaoEncontradoException e)
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
						"Digite o número do produto que você deseja remover: ");
									
					try
					{	umProduto = produtoAppService.
										recuperaUmProduto(resposta);
					}
					catch(ProdutoNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remoção do produto?");

					if(resp.equals("s"))
					{	try
						{	
							System.out.println("\nDentro do Principal. Vai chamar produtoAppService.exclui");
							
							produtoAppService.exclui (umProduto.getId());
						
							System.out.println("\nDentro do Principal. Chamou produtoAppService.exclui");
						
							System.out.println('\n' + 
								"Produto removido com sucesso!");
						}
						catch(ProdutoNaoEncontradoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Produto não removido.");
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
							"  Lance mínimo = " + produto.getLanceMinimo() +
							"  Data Cadastro = " + produto.getDataCadastroMasc());
					}
					
					break;
				}

				case 5:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			}
		}		
	}
}
