package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "HoraTrabalhada")
public class HoraTrabalhada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;

	@ManyToOne
	@JoinColumn(name="usuarioCriacaoId", updatable = false) 
	private Usuario usuarioCriacao;

	@ManyToOne
	@JoinColumn(name="usuarioExclusaoId") 
	private Usuario usuarioExclusao;
	
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao; 

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraInicio;	

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraFim;

	@Min(value = 0, message = "Tipo ação deve ser maior que zero")
	@Max(value = 4, message = "Tipo ação deve ser menor que quatro")
	private int tipoAcao; // 0 start - 1 pause - 2 stop - 4 restarted
	
	@Size(max = 150)
	private String motivoPausa;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="projetoEscalaId")
	private ProjetoEscala projetoEscala;

	private boolean incluidoManualmente;
	private boolean incluidoRobo;
	private boolean excluido;

	@Size(max = 4000)
	private String historicoCorrecao;

	//@NotNull(message="Preencha o campo ausência")
	@ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="horaAprovacaoId")
	private HoraAprovacao horaAprovacao;

	private int aprovadoResponsavel;

	@ManyToOne
	@JoinColumn(name="responsavelAprovacaoId") 
	private Usuario responsavelAprovacao;

	private LocalDateTime dataHoraExclusao; 
	
	@Size(max = 350)
	private String motivoRecusa;

	
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
	
	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}
	
	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	
	public Usuario getUsuarioExclusao() {
		return usuarioExclusao;
	}
	
	public void setUsuarioExclusao(Usuario usuarioExclusao) {
		this.usuarioExclusao = usuarioExclusao;
	}
	
	public LocalDateTime getUltimaModificacao() {
		return ultimaModificacao;
	}
	
	public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}
	
	public boolean isIncluidoManualmente() {
		return incluidoManualmente;
	}
	
	public void setIncluidoManualmente(boolean incluidoManualmente) {
		this.incluidoManualmente = incluidoManualmente;
	}
	
	public boolean isExcluido() {
		return excluido;
	}
	
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}
	
	public String getHistoricoCorrecao() {
		return historicoCorrecao;
	}
	public void setHistoricoCorrecao(String historicoCorrecao) {
		this.historicoCorrecao = historicoCorrecao;
	}
	
	public HoraAprovacao getHoraAprovacao() {
		return horaAprovacao;
	}
	
	public void setHoraAprovacao(HoraAprovacao horaAprovacao) {
		this.horaAprovacao = horaAprovacao;
	}
	
	public int getTipoAcao() {
		return tipoAcao;
	}
	
	public void setTipoAcao(int tipoAcao) {
		this.tipoAcao = tipoAcao;
	}
	
	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public String getHorasFormatada() {
		return Utilities.converterToTime((int)this.getSegundos());
	}
	
	public double getHoras() {
		return Utilities.Round(Double.valueOf(getSegundos())/60/60, 3);
	}
	
	public double getSegundos() {
		return Utilities.Round(Double.valueOf(ChronoUnit.SECONDS.between(getDataHoraInicio(), getDataHoraFim() == null ? Utilities.now() : getDataHoraFim())), 3);
	}
	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	
	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}
	
	public void setDataHoraFim(LocalDateTime dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	
	public String getMotivoPausa() {
		return motivoPausa;
	}
	
	public void setMotivoPausa(String motivoPausa) {
		this.motivoPausa = motivoPausa;
	}

	public ProjetoEscala getProjetoEscala() {
		return projetoEscala;
	}

	public void setProjetoEscala(ProjetoEscala projetoEscala) {
		this.projetoEscala = projetoEscala;
	}


	public int getAprovadoResponsavel() {
		return this.aprovadoResponsavel;
	}
	
	public void setAprovadoResponsavel(int aprovadoResponsavel) {
		this.aprovadoResponsavel = aprovadoResponsavel;
		
	}

	public Usuario getResponsavelAprovacao() {
		return responsavelAprovacao;
	}

	public void setResponsavelAprovacao(Usuario responsavelAprovacao) {
		this.responsavelAprovacao = responsavelAprovacao;
	}

	public String getMotivoRecusa() {
		return motivoRecusa;
	}

	public void setMotivoRecusa(String motivoRecusa) {
		this.motivoRecusa = motivoRecusa;
	}

	public LocalDateTime getDataHoraExclusao() {
		return dataHoraExclusao;
	}

	public void setDataHoraExclusao(LocalDateTime dataHoraExclusao) {
		this.dataHoraExclusao = dataHoraExclusao;
	}

	public boolean isIncluidoRobo() {
		return incluidoRobo;
	}

	public void setIncluidoRobo(boolean incluidoRobo) {
		this.incluidoRobo = incluidoRobo;
	}

}
