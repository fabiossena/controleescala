package com.packageIxia.sistemaControleEscala.models.referencias;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Funcao")
public class Funcao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nome;
	private int perfilAcessoId;
	private double valorMinuto;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;

	@Column
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao;  

	public Funcao(String nome, double valorMinuto) {
		this.nome = nome;
		this.valorMinuto = valorMinuto; 
	}

	public Funcao() {
		// TODO Auto-generated constructor stub
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
	
	public String getNomeCompleto() {
		return nome + " (" + this.getPerfilAcesso().getNome() + ")";
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public PerfilAcesso getPerfilAcesso() {
		return PerfilAcessoEnum.GetFromId(this.perfilAcessoId);
	}
	
	public int getPerfilAcessoId() {
		return perfilAcessoId;
	}
	
	public void setPerfilAcessoId(int perfilAcessoId) {
		this.perfilAcessoId = perfilAcessoId;
	}
	
	public double getValorMinuto() {
		return this.valorMinuto;
	}

	public void setValorMinuto(double valorMinuto) {
		this.valorMinuto = valorMinuto;
	}

	public LocalDateTime getUltimaModificacao() {
		return ultimaModificacao;
	}

	public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}
}
