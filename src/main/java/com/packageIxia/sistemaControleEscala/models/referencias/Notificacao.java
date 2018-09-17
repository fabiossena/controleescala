package com.packageIxia.sistemaControleEscala.models.referencias;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "Notificacao")
public class Notificacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String titulo;
	
	@NotNull
	@Size(max=8000)
	private String mensagem;

	@ManyToOne
	@JoinColumn(name="usuarioId") 
	private Usuario usuario;

	private LocalDateTime leitura;  

	private LocalDateTime aberta; 
	
	private int nivel;  
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime envio;  

	public Notificacao (){
	
	}
	
	public Notificacao(int nivel, String mensagem) {
		this.nivel = nivel;
		this.mensagem = mensagem;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getLeitura() {
		return leitura;
	}

	public void setLeitura(LocalDateTime leitura) {
		this.leitura = leitura;
	}

	public LocalDateTime getEnvio() {
		return envio;
	}

	public void setEnvio(LocalDateTime envio) {
		this.envio = envio;
	}

	public LocalDateTime getAberta() {
		return aberta;
	}

	public void setAberta(LocalDateTime aberta) {
		this.aberta = aberta;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
