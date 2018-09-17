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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "AusenciaReposicao")
public class AusenciaReposicao {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao; 

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="projetoEscalaTrocaId")
	private ProjetoEscala projetoEscalaTroca;
	

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@NotNull(message="Preencha o campo data")
	private LocalDate data;

	//@NotEmpty(message="Preencha o campo hora início")
	private String horaInicio;

	//@NotEmpty(message="Preencha o campo hora fim")
	private String horaFim;
	
	private String observacao;

	private int aceitoUsuarioTroca;
	
	private String motivoRecusaUsuarioTroca;

	private LocalDateTime dataAceiteUsuarioTroca;
	
	private boolean excluido;
	
	private boolean indicadoOutroUsuario;
	
	//@NotNull(message="Preencha o campo ausência")
	@ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="ausenciaSolicitacaoId")
	private AusenciaSolicitacao ausenciaSolicitacao;

	//private long ausenciaSolicitacaoId;
	
	
	@ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name="usuarioTrocaId") //, nullable = true, insertable = false, updatable = false)
	private Usuario usuarioTroca;

	@ManyToOne // (fetch = FetchType.EAGER)
	@JoinColumn(name="usuarioAprovacaoId") // , nullable = true
	private Usuario usuarioAprovacao;

	private int aceitoUsuarioAprovacao;
	
	private String motivoRecusaUsuarioAprovacao;

	private LocalDateTime dataAceiteUsuarioAprovacao;

	@ManyToOne //(fetch = FetchType.EAGER)
	@JoinColumn(name="gerenciaAprovacaoId") // , nullable = true
	private Usuario gerenciaAprovacao;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id= id;
	}

	public Usuario getGerenciaAprovacao() {
		return gerenciaAprovacao;
	}

	public void setGerenciaAprovacao(Usuario gerenciaAprovacao) {
		this.gerenciaAprovacao = gerenciaAprovacao;
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

	public ProjetoEscala getProjetoEscalaTroca() {
		return projetoEscalaTroca;
	}

	public void setProjetoEscalaTroca(ProjetoEscala projetoEscalaTroca) {
		this.projetoEscalaTroca = projetoEscalaTroca;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getAceitoUsuarioTroca() {
		return aceitoUsuarioTroca;
	}

	public void setAceitoUsuarioTroca(int aceitoUsuarioTroca) {
		this.aceitoUsuarioTroca = aceitoUsuarioTroca;
	}

	public LocalDateTime getDataAceiteUsuarioTroca() {
		return dataAceiteUsuarioTroca;
	}

	public void setDataAceiteUsuarioTroca(LocalDateTime dataAceiteUsuarioTroca) {
		this.dataAceiteUsuarioTroca = dataAceiteUsuarioTroca;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public AusenciaSolicitacao getAusenciaSolicitacao() {
		return ausenciaSolicitacao;
	}

	public void setAusenciaSolicitacao(AusenciaSolicitacao ausenciaSolicitacao) {
		this.ausenciaSolicitacao = ausenciaSolicitacao;
	}

	public long getAusenciaSolicitacaoId() {
		return this.ausenciaSolicitacao.getId();
	}

	public String getMotivoRecusaUsuarioTroca() {
		return motivoRecusaUsuarioTroca;
	}

	public void setMotivoRecusaUsuarioTroca(String motivoRecusaUsuarioTroca) {
		this.motivoRecusaUsuarioTroca = motivoRecusaUsuarioTroca;
	}

	public Usuario getUsuarioAprovacao() {
		return usuarioAprovacao;
	}

	public void setUsuarioAprovacao(Usuario usuarioAprovacao) {
		this.usuarioAprovacao = usuarioAprovacao;
	}

	public Usuario getUsuarioTroca() {
		return usuarioTroca;
	}

	public void setUsuarioTroca(Usuario usuarioTroca) {
		this.usuarioTroca = usuarioTroca;
	}

	public int getAceitoUsuarioAprovacao() {
		return aceitoUsuarioAprovacao;
	}

	public void setAceitoUsuarioAprovacao(int aceitoUsuarioAprovacao) {
		this.aceitoUsuarioAprovacao = aceitoUsuarioAprovacao;
	}

	public String getMotivoRecusaUsuarioAprovacao() {
		return motivoRecusaUsuarioAprovacao;
	}

	public String getHoras() {
		return Utilities.horaDiff(horaInicio, horaFim);
	}

	public void setMotivoRecusaUsuarioAprovacao(String motivoRecusaUsuarioAprovacao) {
		this.motivoRecusaUsuarioAprovacao = motivoRecusaUsuarioAprovacao;
	}

	public LocalDateTime getDataAceiteUsuarioAprovacao() {
		return dataAceiteUsuarioAprovacao;
	}

	public void setDataAceiteUsuarioAprovacao(LocalDateTime dataAceiteUsuarioAprovacao) {
		this.dataAceiteUsuarioAprovacao = dataAceiteUsuarioAprovacao;
	}

	public boolean isIndicadoOutroUsuario() {
		return indicadoOutroUsuario;
	}

	public void setIndicadoOutroUsuario(boolean indicadoOutroUsuario) {
		this.indicadoOutroUsuario = indicadoOutroUsuario;
	}
}
