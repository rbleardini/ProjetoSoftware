package servico;

import java.util.List;

import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;

public interface ProdutoAppService
{	
	long inclui(Produto umProduto); 
	
	void altera(Produto umProduto) throws ProdutoNaoEncontradoException;
	
	void exclui(long numero) throws ProdutoNaoEncontradoException;
	
	Produto recuperaUmProduto(long numero) throws ProdutoNaoEncontradoException;
	
	List<Produto> recuperaProdutos(); 
}