package com.packageIxia.SistemaControleEscala.Models.Referencias;

public class DadoGenerico {

	private int id;
	
	private String nome;
	
	public DadoGenerico(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

}
