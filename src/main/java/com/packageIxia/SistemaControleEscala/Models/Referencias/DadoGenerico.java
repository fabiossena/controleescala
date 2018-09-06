package com.packageIxia.SistemaControleEscala.Models.Referencias;

public class DadoGenerico {

	private long id;
	
	private String nome;

	private double doubleValue;
	
	public DadoGenerico(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public DadoGenerico(long id, String nome, double doubleValue) {
		this.id = id;
		this.nome = nome;
		this.doubleValue = doubleValue;
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
}
