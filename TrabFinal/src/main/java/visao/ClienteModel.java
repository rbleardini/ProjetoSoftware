package visao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import modelo.Cliente;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import servico.ClienteService;
import excecao.ClienteNaoEncontradoException;

public class ClienteModel extends AbstractTableModel 
{
	private static final long serialVersionUID = 1L;
	
	public static final int COLUNA_NUMERO = 0;
	public static final int COLUNA_NOME = 1;
	public static final int COLUNA_DATANASC = 2;
	//public static final int COLUNA_SEXO = 2;
	//public static final int COLUNA_IDADE = 3;
	//public static final int COLUNA_NEWS_LETTER = 4;
	public static final int COLUNA_ACAO = 3;
	
    private final static int NUMERO_DE_LINHAS_POR_PAGINA = 4;

	//private String[] idades = {"", "até 30 anos", "de 31 a 40 anos", "de 41 a 50 anos", "acima de 50 anos" };

	private static ClienteService clienteService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	clienteService = (ClienteService)fabrica.getBean ("clienteService");
    }

    private Map<Integer, Cliente> cache;
    private int rowIndexAnterior = 0;
    private Integer qtd;
    private String nomeCliente;
    
    public ClienteModel()
	{	
    	this.qtd = null;
		this.cache = new HashMap<Integer, Cliente>(NUMERO_DE_LINHAS_POR_PAGINA * 4 / 75 / 100 + 2);
	}

    public void setNomeCliente(String nomeCliente)
    {
    	this.nomeCliente = nomeCliente;
    }
    
	public String getColumnName(int c)
	{
		if(c == COLUNA_NUMERO) return "Número";
		if(c == COLUNA_NOME) return "Nome";
		if(c == COLUNA_DATANASC) return "Data";
		/*
		if(c == COLUNA_SEXO) return "Sexo";
		if(c == COLUNA_IDADE) return "Idade";
		if(c == COLUNA_NEWS_LETTER) return "News Letter";
		*/
		if(c == COLUNA_ACAO) return "Ação";
		return null;
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		if(qtd == null)
			qtd = (int)clienteService.recuperaQtdPeloNome(nomeCliente);

		return qtd;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{   
		if (!cache.containsKey(rowIndex)) 
		{	
			if(cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 3))
			{	
				cache.clear();
				
				if(rowIndex >= rowIndexAnterior) 
				{
					List<Cliente> resultados = clienteService.recuperaPeloNome(nomeCliente, rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);
				
					for (int j = 0; j < resultados.size(); j++) 
					{	Cliente cliente = resultados.get(j);
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, cliente);
					}
				}
				else
				{
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0) inicio = 0;
				
					List<Cliente> resultados = clienteService.recuperaPeloNome(nomeCliente, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					
					for (int j = 0; j < resultados.size(); j++) 
					{	Cliente cliente = resultados.get(j);
						cache.put(inicio + j, cliente);
					}
				}
			}
			else
			{
				if(rowIndex >= rowIndexAnterior) 
				{
					List<Cliente> resultados = clienteService.recuperaPeloNome(nomeCliente, rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					
					for (int j = 0; j < resultados.size(); j++) 
					{	Cliente cliente = resultados.get(j);
						cache.put(rowIndex + j, cliente);
					}
				}
				else
				{
					int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
					if (inicio < 0) inicio = 0;

					List<Cliente> resultados = clienteService.recuperaPeloNome(nomeCliente, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					
					for (int j = 0; j < resultados.size(); j++) 
					{	Cliente cliente = resultados.get(j);
						cache.put(inicio + j, cliente);
					}
				}
			}
        }

		rowIndexAnterior = rowIndex;
        
        Cliente cliente = cache.get(rowIndex);

		if(columnIndex == COLUNA_NUMERO)
			return cliente.getNumero();
		else if (columnIndex == COLUNA_NOME)
			return cliente.getNome();
		else if (columnIndex == COLUNA_DATANASC)
			return cliente.getData();
		/*
		else if (columnIndex == COLUNA_SEXO)
			return cliente.getSexo();
		else if (columnIndex == COLUNA_IDADE)
			return idades[cliente.getIdade()];
		else if (columnIndex == COLUNA_NEWS_LETTER)
			return cliente.isNewsLetter();
		*/
		else
			return null;
	}
	
	// Para que os campos booleanos sejam renderizados como check box.
	// Neste caso, não há campo boleano.
	public Class<?> getColumnClass(int c)
	{
		Class<?> classe = null;
		if(c == COLUNA_NUMERO) classe = Integer.class;
		if(c == COLUNA_NOME) classe = String.class;
		if(c == COLUNA_DATANASC) classe = String.class;
		/*
		if(c == COLUNA_SEXO) classe = String.class;
		if(c == COLUNA_IDADE) classe = Integer.class;
		if(c == COLUNA_NEWS_LETTER) classe = Boolean.class;
		*/
		if(c == COLUNA_ACAO) classe = ButtonColumn.class;
		return classe;
	}
	
	// Para que as células referentes às colunas 1 em diante possam ser editadas
	public boolean isCellEditable(int r, int c)
	{
		return c != 0;
	}

	@Override
	public void setValueAt(Object obj, int r, int c) 
	{
		Cliente umCliente = cache.get(r);

		if(c == COLUNA_NOME) umCliente.setNome((String)obj);
		if(c == COLUNA_DATANASC) umCliente.setData((String)obj);
		//if(c == COLUNA_SEXO) umCliente.setSexo((String)obj);
		/*
		if(c == COLUNA_IDADE)
		{
			if(((String)obj).equals("até 30 anos"))
				umCliente.setIdade(1);
			else if(((String)obj).equals("de 31 a 40 anos"))
				umCliente.setIdade(2);
			else if(((String)obj).equals("de 41 a 50 anos"))
				umCliente.setIdade(3);
			else if(((String)obj).equals("acima de 50 anos"))
				umCliente.setIdade(4);
		}
		*/
		//if(c == COLUNA_NEWS_LETTER) umCliente.setNewsLetter((Boolean)obj);

		try 
		{	clienteService.altera(umCliente);
		} 
		catch (ClienteNaoEncontradoException e) 
		{	e.printStackTrace();
		}
	}
}
