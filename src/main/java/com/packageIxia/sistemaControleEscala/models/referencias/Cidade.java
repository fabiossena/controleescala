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
@Table(name = "Cidade")
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long estadoId;
	
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

	public long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(long estadoId) {
		this.estadoId = estadoId;
	}

}
