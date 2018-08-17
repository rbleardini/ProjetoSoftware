package exercicio;

import java.util.List;


public interface ProdutoDAO
{	
	long inclui(Produto umProduto); 

	void altera(Produto umProduto)
		throws ProdutoNaoEncontradoException; 
	
	void exclui(long id) 
		throws ProdutoNaoEncontradoException; 
	
	Produto recuperaUmProduto(long numero) 
		throws ProdutoNaoEncontradoException; 
	
	List<Produto> recuperaProdutos();
}