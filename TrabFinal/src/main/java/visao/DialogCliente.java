package visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.Cliente;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import servico.ClienteService;
import excecao.ClienteNaoEncontradoException;

public class DialogCliente extends JDialog implements ActionListener 
{
	private static final long serialVersionUID = 1L;

	private static ClienteService clienteService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	clienteService = (ClienteService)fabrica.getBean ("clienteService");
    }
	
	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	
	private JTextField nomeTextField;
	private JTextField dataTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel nomeMensagem;
	private JLabel dataMensagem;
	private JLabel sexoMensagem;
	//private JLabel idadeMensagem;

	private JPanel panel;
	
	private Cliente umCliente;
	
	
	public void designaClienteAFrame(Cliente umCliente)
	{
		this.umCliente = umCliente;
		
		nomeTextField.setText(umCliente.getNome());
		dataTextField.setText(umCliente.getData());
		/*
		if(umCliente.getSexo().equals("M"))
			sexoMascRadioButton.setSelected(true);
		else
			sexoFemRadioButton.setSelected(true);
		
		idadeComboBox.setSelectedIndex(umCliente.getIdade());
		newsLetterCheckBox.setSelected(umCliente.isNewsLetter());
		*/
		
		nomeMensagem.setText("");
		dataMensagem.setText("");
		//sexoMensagem.setText("");
		//idadeMensagem.setText("");
	}
	
	public DialogCliente(JFrame frame)
	{
		super(frame);
		
		setBounds(105, 147, 618, 330);
		setTitle("Cadastro de Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 602, 292);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastro de Clientes");
		cadastrarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(189, 21, 190, 26);
		panel.add(cadastrarLabel);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(62, 78, 46, 14);
		panel.add(nomeLabel);
		
		nomeTextField = new JTextField();
		nomeLabel.setLabelFor(nomeTextField);
		nomeTextField.setBounds(138, 75, 278, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(50);
		nomeTextField.setBackground(SystemColor.window);
		
		JLabel dataLabel = new JLabel("Data Nasc");
		dataLabel.setBounds(58, 108, 60, 14);
		panel.add(dataLabel);
		
		nomeMensagem = new JLabel("");
		nomeMensagem.setForeground(Color.RED);
		nomeMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		nomeMensagem.setBounds(138, 92, 241, 14);
		panel.add(nomeMensagem);
		
		//sexoMensagem = new JLabel("");
		//sexoMensagem.setForeground(Color.RED);
		//sexoMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		//sexoMensagem.setBounds(138, 127, 241, 14);
		//panel.add(sexoMensagem);
		
		dataMensagem = new JLabel("");
		dataMensagem.setForeground(Color.RED);
		dataMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dataMensagem.setBounds(138, 170, 241, 14);
		panel.add(dataMensagem);
		
		novoButton = new JButton("Novo");
		novoButton.setBounds(452, 25, 96, 23);
		panel.add(novoButton);
		novoButton.addActionListener(this);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(452, 74, 96, 23);
		panel.add(cadastrarButton);
		cadastrarButton.addActionListener(this);
		
		editarButton = new JButton("Editar");
		editarButton.setBounds(452, 47, 96, 23);
		panel.add(editarButton);
		editarButton.addActionListener(this);
		
		alterarButton = new JButton("Alterar");
		alterarButton.setBounds(452, 104, 96, 23);
		panel.add(alterarButton);
		alterarButton.addActionListener(this);

		removerButton = new JButton("Remover");
		removerButton.setBounds(452, 136, 96, 23);
		panel.add(removerButton);
		removerButton.addActionListener(this);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(452, 170, 96, 23);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(this);

		buscarButton = new JButton("Buscar");
		buscarButton.setBounds(452, 204, 96, 23);
		panel.add(buscarButton);
		
		dataTextField = new JTextField();
		dataTextField.setColumns(50);
		dataTextField.setBackground(Color.WHITE);
		dataTextField.setBounds(138, 105, 196, 20);
		panel.add(dataTextField);
		/*
		JLabel dataMensagem = new JLabel("");
		dataMensagem.setForeground(Color.RED);
		dataMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dataMensagem.setBounds(138, 129, 241, 14);
		panel.add(dataMensagem);
		buscarButton.addActionListener(this);
		*/
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if (obj == novoButton)
		{	novo();
		}
		else if (obj == cadastrarButton)
		{
			boolean deuErro = validaCliente();
			
			if(!deuErro)
			{	
				umCliente = new Cliente();
				umCliente.setNome(nomeTextField.getText().toUpperCase());
				umCliente.setData(dataTextField.getText().toUpperCase());
				/*
				umCliente.setSexo(sexoMascRadioButton.isSelected() ? "M" : "F");
				umCliente.setIdade(idadeComboBox.getSelectedIndex());
				umCliente.setNewsLetter(newsLetterCheckBox.isSelected());
				umCliente.setIdade(Integer.parseInt(dataTextField.getText().toUpperCase()));
				*/
				clienteService.inclui(umCliente);
				
				salvo();
				
				JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(obj == editarButton)
		{
			editavel();
		}
		else if(obj == alterarButton)
		{
			boolean deuErro = validaCliente();
			
			if(!deuErro)
			{	
				umCliente.setNome(nomeTextField.getText().toUpperCase());
				umCliente.setData(dataTextField.getText().toUpperCase());
				/*
				umCliente.setIdade(Integer.parseInt(dataTextField.getText().toUpperCase()));
				umCliente.setSexo(sexoMascRadioButton.isSelected() ? "M" : "F");
				umCliente.setIdade(idadeComboBox.getSelectedIndex());
				umCliente.setNewsLetter(newsLetterCheckBox.isSelected());
				*/

				try 
				{
					clienteService.altera(umCliente);
					
					salvo();
					
					JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (ClienteNaoEncontradoException e1) 
				{
					novo();
					
					JOptionPane.showMessageDialog(this, "Cliente não encontrado", "", 
						JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(obj == removerButton)
		{
			try 
			{
				clienteService.exclui(umCliente);

				removido();
				
				JOptionPane.showMessageDialog(this, "Cliente removido com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ClienteNaoEncontradoException e1) 
			{
				novo();
				
				JOptionPane.showMessageDialog(this, "Cliente não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == cancelarButton)
		{
			try 
			{
				umCliente = clienteService.recuperaUmCliente(umCliente.getNumero());

				nomeTextField.setText(umCliente.getNome());
				dataTextField.setText(umCliente.getData());
				/*
				if(umCliente.getSexo().equals("M"))
					sexoMascRadioButton.setSelected(true);
				else
					sexoFemRadioButton.setSelected(true);
				
				idadeComboBox.setSelectedIndex(umCliente.getIdade());
				newsLetterCheckBox.setSelected(umCliente.isNewsLetter());
				*/
				
				cancelado();
			} 
			catch (ClienteNaoEncontradoException e1) 
			{
				novo();
				
				JOptionPane.showMessageDialog(this, "Cliente não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == buscarButton)
		{	
			DialogTabelaCliente dialog = new DialogTabelaCliente(this);
			dialog.setVisible(true);
		}
	}
	
	private boolean validaCliente()
	{
		boolean deuErro = false;
		if (nomeTextField.getText().trim().length() == 0)
		{	deuErro = true;
			nomeMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	nomeMensagem.setText("");
		}
		if (dataTextField.getText().trim().length() == 0)
		{	deuErro = true;
			dataMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	dataMensagem.setText("");
		}
		/*
		if (!sexoMascRadioButton.isSelected() && !sexoFemRadioButton.isSelected())
		{	deuErro = true;
			sexoMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	sexoMensagem.setText("");
		}

		if (idadeComboBox.getSelectedIndex() == 0)
		{	deuErro = true;
			idadeMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	idadeMensagem.setText("");
		}
		*/
		return deuErro;
	}
	
	public void novo()
	{
		nomeTextField.setEnabled(true);
		dataTextField.setEnabled(true);
		/*
		sexoMascRadioButton.setEnabled(true);
		sexoFemRadioButton.setEnabled(true);
		idadeComboBox.setEnabled(true);
		newsLetterCheckBox.setEnabled(true);
		*/
		
		nomeTextField.setText("");
		dataTextField.setText("");
		buttonGroup.clearSelection();
		/*
		idadeComboBox.setSelectedIndex(0);
		newsLetterCheckBox.setSelected(false);
		*/
		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void salvo()
	{
		nomeTextField.setEnabled(false);
		dataTextField.setEnabled(false);
		/*
		sexoMascRadioButton.setEnabled(false);
		sexoFemRadioButton.setEnabled(false);
		idadeComboBox.setEnabled(false);
		newsLetterCheckBox.setEnabled(false);
		*/

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void editavel()
	{
		nomeTextField.setEnabled(true);
		dataTextField.setEnabled(true);
		/*
		sexoMascRadioButton.setEnabled(true);
		sexoFemRadioButton.setEnabled(true);
		idadeComboBox.setEnabled(true);
		newsLetterCheckBox.setEnabled(true);
		*/
		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(false);
	}

	public void removido()
	{
		nomeTextField.setEnabled(false);
		dataTextField.setEnabled(false);
		/*
		sexoMascRadioButton.setEnabled(false);
		sexoFemRadioButton.setEnabled(false);
		idadeComboBox.setEnabled(false);
		newsLetterCheckBox.setEnabled(false);
		*/
		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void cancelado()
	{
		nomeTextField.setEnabled(false);
		dataTextField.setEnabled(false);
		/*
		sexoMascRadioButton.setEnabled(false);
		sexoFemRadioButton.setEnabled(false);
		idadeComboBox.setEnabled(false);
		newsLetterCheckBox.setEnabled(false);
		*/
		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}
}
