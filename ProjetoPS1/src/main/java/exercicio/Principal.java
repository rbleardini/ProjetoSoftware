package exercicio;

import java.util.List;
import corejava.Console;

public class Principal
{	public static void main (String[] args) 
	{	
		String nome;
		double lanceMinimo;
		String dataCadastro;
		Produto umProduto;

		ProdutoDAO produtoDAO = FabricaDeDAOs.getDAO(ProdutoDAO.class);

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
					
					produtoDAO.inclui(umProduto);
					
					System.out.println('\n' + "Produto número " + 
					    umProduto.getId() + " incluído com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja alterar: ");
										
					try
					{	umProduto = produtoDAO.recuperaUmProduto(resposta);
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
							{	produtoDAO.altera(umProduto);

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
							{	produtoDAO.altera(umProduto);

								System.out.println('\n' + 
									"Alteração de lance mínimo efetuada " +
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
					{	umProduto = produtoDAO.
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
						{	produtoDAO.exclui (umProduto.getId());
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
					List<Produto> produtos = produtoDAO.recuperaProdutos();

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
//AULA 1 --------------------------------------------------------------------------
//ESTUDANDO PADRÕES DE PROJETO
//Maven -> Instala dependencias | pasta .m2
//Maven.txt -> é importante, na pasta "outros"
//deletar .m2 -> baixar .m2.zip e extrair
//src/main/java -> código fonte
//src/main/resources -> log4j
//persistence.xml -> source
//Principal chama JPAProdutoDAO(-inclui;-altera;-exclui)
//JPAProdutoDAO(classe de implementação) implementa(interface) ProdutoDAO
//Primeiro Padrão: DAO -> Necessita de uma fabrica de objetos (FabricaDeDAOs)
//- Incluir             \
//- Alterar              \ ProdutoDAO.java (interface)
//- Excluir              /
//- Recuperar           /

//Este padrão permite que possamos mudar a forma de persistência 
//sem que isso influencie em nada na lógica de negócio, 
//além de tornar nossas classes mais legíveis.

//Classes DAO são responsáveis por trocar informações com o SGBD 
//e fornecer operações CRUD e de pesquisas, elas devem ser capazes
//de buscar dados no banco e transformar esses em objetos ou lista de objetos,
//fazendo uso de listas genéricas, também deverão receber os objetos,
//converter em instruções SQL e mandar para o banco de dados.

//Toda interação com a base de dados se dará através destas classes, 
//nunca das classes de negócio, muito menos de formulários.

//Instanciar um objeto da classe -> getDAO(ProdutoDAO.class)

//AULA 2 --------------------------------------------------------------------
//Tutorialzinho: window -> preferences -> java -> buildpath -> classpath -> 
//criar variável ambiente -> new -> CONSOLE -> console.jar
//https://drive.google.com/drive/folders/1yRNpsNLlJh6TsTDPbUVG-_GE1GOBDvag?usp=sharing
//criar relação de produto com modelo relacional
/*
C:\Users\Aluno.L304-5B\.m2\repository\corejava\console\1.0

talvez tenha q consertar o nome do Aluno
aí cola aquele console-1.0 dele lá


Tipo, eu coloquei já nessa pasta aí
Mas tem que buildar como maven, s


tem q clicar no projeto e ir em Maven > update project só
aqui funcionou



Pegar console em https://drive.google.com/drive/folders/1yRNpsNLlJh6TsTDPbUVG-_GE1GOBDvag e colocar na pasta C:\Users\Aluno.L304-3A\.m2\repository\corejava\console\1.0
No eclipse, clicar com o direito no projeto ir em Maven -> Update Project...

Em JPAProdutoDAO, na primeira "==>", inserir:

			em = FabricaDeEntityManager.criarSessao();
			
			tx = em.getTransaction();
			tx.begin();
			em.persist(umProduto);
			umProduto.setNome("abc");
			tx.commit();

segunda "==>":
			em.close();

terceira "==>":
			em.merge(umProduto);

quarto e quinto "==>":
				tx.rollback();
				throw ProdutoNaoEncontradoException;
*/