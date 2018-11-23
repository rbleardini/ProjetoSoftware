package modelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTE2")

public class Cliente 
{
	private Long numero;
	private String nome;
	private String sexo;
	private int idade;
	private boolean newsLetter;
	
	public Cliente(String nome, String sexo, int idade, boolean newsLetter) 
	{
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.idade = idade;
		this.newsLetter = newsLetter;
	}

	public Cliente() 
	{	
	}

	public String toString()
	{	return "Numero = " + numero + " nome = " + nome + " sexo = " + sexo + " idade = " + idade + " newsLetter = " + newsLetter;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getNumero() 
	{	return numero;
	}

	@SuppressWarnings("unused")
	private void setNumero(Long numero) 
	{	this.numero = numero;
	}
	
	public String getNome() 
	{	return nome.toUpperCase();
	}
	
	public void setNome(String nome) 
	{	this.nome = nome;
	}
	
	public String getSexo() 
	{	return sexo;
	}
	
	public void setSexo(String sexo) 
	{	this.sexo = sexo;
	}
	
	public int getIdade() 
	{	return idade;
	}
	
	public void setIdade(int idade) 
	{	this.idade = idade;
	}
	
	@Column(name="NEWS_LETTER")
	public boolean isNewsLetter() 
	{	return newsLetter;
	}
	
	public void setNewsLetter(boolean newsLetter) 
	{	this.newsLetter = newsLetter;
	}
}
