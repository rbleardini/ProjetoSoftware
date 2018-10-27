package modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import util.Util;

@Entity
@Table(name="produto")

public class Produto
{	
	private Long id;
	private String nome;
	private double lanceMinimo;
	private Calendar dataCadastro;
	private Calendar dataVenda;

	// ********* Construtores *********

	public Produto()
	{
	}

	public Produto(String nome, 
	               double lanceMinimo, 
	               Calendar dataCadastro)
	{	this.nome = nome;
		this.lanceMinimo = lanceMinimo;
		this.dataCadastro = dataCadastro;	
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId()
	{	return id;
	}
	
	public String getNome()
	{	return nome;
	}
	
	@Column(name="LANCE_MINIMO")
	public double getLanceMinimo()
	{	return lanceMinimo;
	}
	
	@Transient
	public String getLanceMinimoMasc()
	{	return Util.doubleToStr(lanceMinimo);
	}

	@Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.DATE)
	public Calendar getDataCadastro()
	{	return dataCadastro;
	}
	
	@Transient
	public String getDataCadastroMasc()
	{	return Util.calendarToStr(dataCadastro);
	}

	@Column(name="DATA_VENDA")
	@Temporal(TemporalType.DATE)
	public Calendar getDataVenda()
	{	return dataVenda;
	}
	
	@Transient
	public String getDataVendaMasc()
	{	return Util.calendarToStr(dataVenda);
	}

	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}
	
	public void setLanceMinimo(double lanceMinimo)
	{	this.lanceMinimo = lanceMinimo;
	}
	
	public void setDataCadastro(Calendar dataCadastro)
	{	this.dataCadastro = dataCadastro;	
	}
	
	public void setDataVenda(Calendar dataVenda)
	{	this.dataVenda = dataVenda;
	}
}

