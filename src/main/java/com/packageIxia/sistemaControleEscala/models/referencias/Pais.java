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
@Table(name = "Pais")
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;

	@Column
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao;  

	private String nome;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

}
