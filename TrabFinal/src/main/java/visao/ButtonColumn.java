package visao;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import modelo.Cliente;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import servico.ClienteService;
import excecao.ClienteNaoEncontradoException;

// Essa classe tem os seguintes métodos:
// - getTableCellRendererComponent() - Método que renderiza o botão
// - getTableCellEditorComponent() - Método que indica qual botão foi clicado
// - actionPerformed() - Método listener do botão
public class ButtonColumn extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton button;
	private DialogTabelaCliente dialogTabelaCliente;
	private DialogCliente dialogCliente;
	
	private static ClienteService clienteService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	clienteService = (ClienteService)fabrica.getBean ("clienteService");
    }

	public ButtonColumn(JTable table, int coluna, 
			            DialogTabelaCliente dialogTabelaCliente, 
			            DialogCliente dialogCliente)
	{
		//super();
		this.table = table;
		this.dialogTabelaCliente = dialogTabelaCliente;
		this.dialogCliente = dialogCliente;
		
		button = new JButton();
		button.setText("Editar");
		button.addActionListener(this);

		TableColumnModel tableColumnModel = table.getColumnModel();
		
		// Designa um renderizador (o objeto corrente) para o botão
		tableColumnModel.getColumn(coluna).setCellRenderer(this);
			// getTableCellRendererComponent()
			
		// Designa um editor (o objeto corrente) para o botão
		tableColumnModel.getColumn(coluna).setCellEditor(this);
			// getTableCellEditorComponent()
			// getCellEditorValue()
	}
	
	// Esse método retorna o botão que será exibido - RESPONSÁVEL PELA RENDERIZAÇÃO DO BOTÃO (APARÊNCIA)
	@Override	// Método de TableCellRenderer
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		// A cor utilizada para exibir as letras será a do componente (table), isto é, preto
		button.setForeground(table.getForeground());
		
		// Será utilizada como cor de fundo a cor default para os botões
		button.setBackground(UIManager.getColor("Button.background"));

		return button; // Esse é o botão que será exibido.
		
		// hasFocus = true quando navegamos com TAB e entramos na célula do botão
		// isSelected = true quando clicamos na linha do botão em alguma outra célula
		// hasFocus = false e isSelected = false quando a janela é exibida.
	}

	// retorna o botão que foi clicado. No clique no botão, por exemplo, o botão pode mudar de cor.
	@Override	// Método de TableCellEditor
	public Component getTableCellEditorComponent(JTable table, Object value, 
			boolean isSelected, int row, int column)
	{
		// IMPORTANTE
		
		// Quando um botão é clicado, esse método é executado, e em seguida o 
		// listener do botão (actionPerformed()) é executado.
		
		return button;  // Se mudar para null o clique do botão pára de funcionar.
						
	}

	@Override	// Método de TableCellEditor - Não é executado
	public Object getCellEditorValue()
	{
		return null;
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Cliente umCliente = clienteService.recuperaUmCliente((Long)table
				.getValueAt(table.getSelectedRow(), 0));
			dialogCliente.designaClienteAFrame(umCliente);
			dialogCliente.editavel();
			dialogTabelaCliente.dispose();
		} 
		catch (ClienteNaoEncontradoException e1)
		{ 
			dialogCliente.novo();
			dialogTabelaCliente.dispose();
			JOptionPane.showMessageDialog(dialogCliente, "Cliente não encontrado", "", JOptionPane.ERROR_MESSAGE);
		}
	}
}
