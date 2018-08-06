package com.packageIxia.SistemaControleEscala.Models.Referencias;

public class Funcao {
	
	private int id;
	private String nome;
	private boolean compatilhado;
	private int tipo;
	
	public Funcao(int id, String nome, boolean compatilhado) {
		this.id = id;
		this.nome = nome;
		this.compatilhado = compatilhado;
	}
	
	public Funcao(int id, String nome, boolean compatilhado, int tipo) {
		this.id = id;
		this.nome = nome;
		this.compatilhado = compatilhado;
		this.tipo = tipo;
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
	
	public boolean getCompartilhado() {
		return this.compatilhado;
	}
	
	public void setCompartilhado(boolean compatilhado) {
		this.compatilhado = compatilhado;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
