package com.packageIxia.sistemaControleEscala.models.usuario;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Entity
@Table(name = "UsuarioFuncaoAdicional")
public class UsuarioFuncaoAdicional {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime datatCriacao;
	
	@Column
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao;


	@ManyToOne
	@JoinColumn(name="funcaoId") 
	private Funcao funcao;
	
	@ManyToOne
	@JoinColumn(name="usuarioId")
	private Usuario usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDatatCriacao() {
		return datatCriacao;
	}

	public void setDatatCriacao(LocalDateTime datatCriacao) {
		this.datatCriacao = datatCriacao;
	}

	public LocalDateTime getUltimaModificacao() {
		return ultimaModificacao;
	}

	public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
