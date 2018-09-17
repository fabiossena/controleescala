package com.packageIxia.sistemaControleEscala.models.projeto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "FuncaoConfiguracao")
public class FuncaoConfiguracao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String chave;

	@Column(precision = 8, scale = 2)
	@Type(type = "big_decimal")
	private double valorMinuto;

	public FuncaoConfiguracao(String key, double valorMinuto) {
		this.chave = key;
		this.valorMinuto = valorMinuto;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public double getValorMinuto() {
		return valorMinuto;
	}

	public void setValorMinuto(double valorMinuto) {
		this.valorMinuto = valorMinuto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
