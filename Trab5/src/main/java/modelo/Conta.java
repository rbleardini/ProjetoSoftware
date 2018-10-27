package modelo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import util.Util;

@Entity
@Table(name="conta")
//@SequenceGenerator(name="SEQUENCIA", 
//		           sequenceName="SEQ_PRODUTO",
//		           allocationSize=1)

public class Conta
{	
	private Long id;
	private double saldo;
	private Date dataCadastro;

	// ********* Construtores *********

	public Conta()
	{
	}

	public Conta(double saldo, Date dataCadastro)
	{	
		this.saldo = saldo;
		this.dataCadastro = dataCadastro;	
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId()
	{	return id;
	}
	
	public double getSaldo()
	{	return saldo;
	}
	
	@Transient
	public String getSaldoMasc()
	{	return Util.doubleToStr(saldo);
	}

	@Column(name="DATA_CADASTRO")
	public Date getDataCadastro()
	{	return dataCadastro;
	}
	
	@Transient
	public String getDataCadastroMasc()
	{	return Util.dateToStr(dataCadastro);
	}

	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setSaldo(double saldo)
	{	this.saldo = saldo;
	}
	
	public void setDataCadastro(Date dataCadastro)
	{	this.dataCadastro = dataCadastro;	
	}
}

