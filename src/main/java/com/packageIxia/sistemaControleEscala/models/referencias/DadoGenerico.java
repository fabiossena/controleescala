package com.packageIxia.sistemaControleEscala.models.referencias;

public class DadoGenerico {

	private long id;
	
	private String nome;

	private String descricao;
	
	private double doubleValue;

	private String observacao;
	
	public DadoGenerico(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public DadoGenerico(long id, String nome, double doubleValue) {
		this.id = id;
		this.nome = nome;
		this.doubleValue = doubleValue;
	}
	
	public DadoGenerico(long id, String nome, double doubleValue, String descricao) {
		this.id = id;
		this.nome = nome;
		this.doubleValue = doubleValue;
		this.descricao = descricao;
	}
	
	public DadoGenerico(long id, String nome, double doubleValue, String descricao, String observacao) {
		this.id = id;
		this.nome = nome;
		this.doubleValue = doubleValue;
		this.descricao = descricao;
		this.setObservacao(observacao);
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public double getDoubleValue() {
		return doubleValue;
	}
	
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
