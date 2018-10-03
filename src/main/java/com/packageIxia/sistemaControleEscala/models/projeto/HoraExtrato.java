package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "HoraExtrato")
public class HoraExtrato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao; 

	@Size(max = 500)
	private String descricao;
	
	private int minutosEntradaSaida;	

	private int minutosTotalDisponiveis;

	@ManyToOne
	@JoinColumn(name="usuarioId")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="usuarioCriacaoId")
	private Usuario usuarioCriacao;

	@ManyToOne
	@JoinColumn(name="usuarioAtualizacaoId")
	private Usuario usuarioAtualizacao;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime data; 
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getUltimaModificacao() {
		return ultimaModificacao;
	}

	public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getMinutosEntradaSaida() {
		return minutosEntradaSaida;
	}

	public void setMinutosEntradaSaida(int minutosEntradaSaida) {
		this.minutosEntradaSaida = minutosEntradaSaida;
	}

	public int getMinutosTotalDisponiveis() {
		return minutosTotalDisponiveis;
	}

	public void setMinutosTotalDisponiveis(int minutosTotalDisponiveis) {
		this.minutosTotalDisponiveis = minutosTotalDisponiveis;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public Usuario getUsuarioAtualizacao() {
		return usuarioAtualizacao;
	}

	public void setUsuarioAtualizacao(Usuario usuarioAtualizacao) {
		this.usuarioAtualizacao = usuarioAtualizacao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

}
