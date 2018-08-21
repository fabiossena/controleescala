package com.packageIxia.SistemaControleEscala.Models.Projeto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.Referencias.MotivoAusencia;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Entity
@Table(name = "AusenciaSolicitacao")
public class AusenciaSolicitacao {	
	
	public AusenciaSolicitacao() {	
	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao; 

	@NotNull(message="Preencha o campo usuário")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="usuarioId")
	private Usuario usuario;

	@NotNull(message="Preencha o campo motivo ausência")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="motivoAusenciaId")
	private MotivoAusencia motivoAusencia;

	@NotNull(message="Preencha o campo prestador")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="projetoEscalaId")
	private ProjetoEscala projetoEscala;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="Preencha o campo data início")
	private LocalDate dataInicio;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotEmpty(message="Preencha o campo hora início")
	private String horaInicio;

	@NotEmpty(message="Preencha o campo hora fim")
	private String horaFim;
	
	private String observacao;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="usuarioAprovacaoId")
	private Usuario usuarioAprovacao;


	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="gerenciaAprovacaoId")
	private Usuario gerenciaAprovacao;
	
	private int aceito;
	
	private String motivoRecusa;
	
	private LocalDateTime dataAceiteRecusa;
	
	private boolean excluido;

	private int tipoAusencia;

	transient private boolean colocarHorarioDisposicao;

	transient private boolean indicarHorarioParaRepor;
	
	transient private String acesso;
	
	@Fetch(value = FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy="ausenciaSolicitacao", cascade = CascadeType.ALL)
    private List<AusenciaReposicao> ausenciaReposicoes;

	transient private String observacaoComplemento;

	transient private DadosAcessoSolicitacaoAusencia dadosAcesso;
	
	private int ativo;
    
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MotivoAusencia getMotivoAusencia() {
		return motivoAusencia;
	}

	public void setMotivoAusencia(MotivoAusencia motivoAusencia) {
		this.motivoAusencia = motivoAusencia;
	}

	public ProjetoEscala getProjetoEscala() {
		return projetoEscala;
	}

	public void setProjetoEscala(ProjetoEscala projetoEscala) {
		this.projetoEscala = projetoEscala;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
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

	public int getAceito() {
		return aceito;
	}

	public void setAceito(int aceito) {
		this.aceito = aceito;
	}

	public String getMotivoRecusa() {
		return motivoRecusa;
	}

	public void setMotivoRecusa(String motivoRecusa) {
		this.motivoRecusa = motivoRecusa;
	}

	public LocalDateTime getDataAceiteRecusa() {
		return dataAceiteRecusa;
	}

	public void setDataAceiteRecusa(LocalDateTime dataAceiteRecusa) {
		this.dataAceiteRecusa = dataAceiteRecusa;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public int getTipoAusencia() {
		return tipoAusencia;
	}

	public void setTipoAusencia(int tipoAusencia) {
		this.tipoAusencia = tipoAusencia;
	}

	public List<AusenciaReposicao> getAusenciaReposicoes() {
		return ausenciaReposicoes;
	}

	public void setAusenciaReposicoes(List<AusenciaReposicao> ausenciaReposicoes) {
		this.ausenciaReposicoes = ausenciaReposicoes;
	}

	public boolean getColocarHorarioDisposicao() {
		return colocarHorarioDisposicao;
	}

	public void setColocarHorarioDisposicao(boolean colocarHorarioDisposicao) {
		this.colocarHorarioDisposicao = colocarHorarioDisposicao;
	}

	public boolean getIndicarHorarioParaRepor() {
		return indicarHorarioParaRepor;
	}

	public void setIndicarHorarioParaRepor(boolean indicarHorarioParaRepor) {
		this.indicarHorarioParaRepor = indicarHorarioParaRepor;
	}

	public String getAcesso() {
		return acesso;
	}

	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}

	public Usuario getGerenciaAprovacao() {
		return gerenciaAprovacao;
	}

	public void setGerenciaAprovacao(Usuario gerenciaAprovacao) {
		this.gerenciaAprovacao = gerenciaAprovacao;
	}

	public Usuario getUsuarioAprovacao() {
		return usuarioAprovacao;
	}

	public void setUsuarioAprovacao(Usuario usuarioAprovacao) {
		this.usuarioAprovacao = usuarioAprovacao;
	}

	public void setObservacaoComplemento(String observacaoComplemento) {
		this.observacaoComplemento = observacaoComplemento;		
	}
	
	public String getObservacaoComplemento() {
		return this.observacaoComplemento;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public DadosAcessoSolicitacaoAusencia getDadosAcesso() {
		return dadosAcesso;
	}

	public void setDadosAcesso(DadosAcessoSolicitacaoAusencia dadosAcessoSolicitacaoAusencia) {
		this.dadosAcesso = dadosAcessoSolicitacaoAusencia;
	}

	public String getHoras() {
		return Utilities.horaDiff(horaInicio, horaFim);
	}
	
}
