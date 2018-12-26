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
@Table(name = "MotivoAusencia")
public class MotivoAusencia {

	public MotivoAusencia() {
	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;

	@Column
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao;  

	private String nome;

	private int tipo;
    
	// 1,2,3 
	// tipo 1 = Não descontada no banco de horas  
	// tipo 2 = Descontada no banco de horas      
	// tipo 3 = Remunerada
	private String opcoes;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getUltimaModificacao() {
		return ultimaModificacao;
	}

	public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(String opcoes) {
		this.opcoes = opcoes;
	}
}
