package dao;

import java.util.List;

import modelo.Produto;
import excecao.ObjetoNaoEncontradoException;

public interface ProdutoDAO
{	
	public long inclui(Produto umProduto); 

	public void altera(Produto umProduto)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	public Produto recuperaUmProduto(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public List<Produto> recuperaProdutos();
}