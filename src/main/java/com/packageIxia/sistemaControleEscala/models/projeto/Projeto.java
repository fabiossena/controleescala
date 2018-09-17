package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.models.referencias.TipoApontamentoHoras;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "Projeto")
public class Projeto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	protected LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	protected LocalDateTime ultimaModificacao; 

	@Size(max = 150)
	@NotEmpty(message="Preencha o campo nome")
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="gerenteId")
	private Usuario gerente;
  
	@Size(max = 200)
	private String descricaoProjeto;

	@Size(max = 500)
	private String observacaoProjeto;

	@Size(max = 50)
	private String integracaoRoboId;
	
	@NotNull(message="Preencha o campo data inicio")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;
	
	private boolean ativo;

	private boolean excluido;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataExcluido;

	@Transient
	private TipoApontamentoHoras tipoApontamentoHoras;

	@NotNull(message="Preencha o campo tipo apontamento horas")
	private int tipoApontamentoHorasId;
	
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
	
	public String getDescricaoProjeto() {
		return descricaoProjeto;
	}
	
	public void setDescricaoProjeto(String descricaoProjeto) {
		this.descricaoProjeto = descricaoProjeto;
	}
	
	public String getObservacaoProjeto() {
		return observacaoProjeto;
	}
	
	public void setObservacaoProjeto(String observacaoProjeto) {
		this.observacaoProjeto = observacaoProjeto;
	}
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public LocalDate getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public LocalDateTime getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(LocalDateTime dataExcluido) {
		this.dataExcluido = dataExcluido;
	}

	public Usuario getGerente() {
		return gerente;
	}

	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}

	public String getIntegracaoRoboId() {
		return integracaoRoboId;
	}

	public void setIntegracaoRoboId(String integracaoRoboId) {
		this.integracaoRoboId = integracaoRoboId;
	}

	public TipoApontamentoHoras getTipoApontamentoHoras() {
		return tipoApontamentoHoras;
	}

	public void setTipoApontamentoHoras(TipoApontamentoHoras tipoApontamentoHoras) {
		this.tipoApontamentoHoras = tipoApontamentoHoras;
	}

	public int getTipoApontamentoHorasId() {
		return tipoApontamentoHorasId;
	}

	public void setTipoApontamentoHorasId(int tipoApontamentoHorasId) {
		this.tipoApontamentoHorasId = tipoApontamentoHorasId;
	}
}
