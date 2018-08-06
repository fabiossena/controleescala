package com.packageIxia.SistemaControleEscala.Models.Projeto;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.SistemaControleEscala.Models.Referencias.Funcao;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Entity
@Table(name = "ProjetoEscalaPrestador")
public class ProjetoEscalaPrestador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	protected LocalDateTime ultimaModificacao; 

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="projetoId")
	private Projeto projeto;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="projetoEscalaId")
	private ProjetoEscala projetoEscala;

	@Size(max = 50)
	private String ramalIntegracaoRobo;

	private int funcaoId;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="prestadorId")
	private Usuario prestador;
	
	@NotNull(message="Preencha o campo data inicio")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;

	@Size(max = 500)
	private String observacaoPrestador;
	
	private boolean ativo;

	private int aceito; // 0 pendente; 1 aceito; 3 rejeitado
	
	private String motivoRecusa;
	
	transient private boolean reenviarConvite;

	private boolean excluido;

	transient private boolean indicadaFolgaSemana;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataExcluido;

	// todo: ajustar
    transient private ProjetoFolgaSemanal[] projetoFolgasSemanais;
    
	public Projeto getProjeto() {
		return this.projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public ProjetoEscala getProjetoEscala() {
		return projetoEscala;
	}

	public void setProjetoEscala(ProjetoEscala projetoEscala) {
		this.projetoEscala = projetoEscala;
	}

	public int getFuncaoId() {
		return funcaoId;
	}

	public Funcao getFuncao() {
		return FuncaoEnum.GetFuncaoFromId(funcaoId);
	}

	public void setFuncaoId(int funcaoId) {
		this.funcaoId = funcaoId;
	}

	public Usuario getPrestador() {
		return this.prestador;
	}

	public void setPrestador(Usuario prestador) {
		this.prestador = prestador;
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

	public String getObservacaoPrestador() {
		return observacaoPrestador;
	}

	public void setObservacaoPrestador(String observacaoPrestador) {
		this.observacaoPrestador = observacaoPrestador;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getRamalIntegracaoRobo() {
		return ramalIntegracaoRobo;
	}

	public void setRamalIntegracaoRobo(String ramalIntegracaoRobo) {
		this.ramalIntegracaoRobo = ramalIntegracaoRobo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isReenviarConvite() {
		return reenviarConvite;
	}

	public void setReenviarConvite(boolean reenviarConvite) {
		this.reenviarConvite = reenviarConvite;
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

	public ProjetoFolgaSemanal[] getProjetoFolgasSemanais() {
		return projetoFolgasSemanais;
	}

	public void setProjetoFolgasSemanais(ProjetoFolgaSemanal[] projetoFolgasSemanais) {
		this.projetoFolgasSemanais = projetoFolgasSemanais;
	}
	
	public boolean isIndicadaFolgaSemana() {
		return indicadaFolgaSemana;
	}

	public void setIndicadaFolgaSemana(boolean indicadafolgaSemana) {
		this.indicadaFolgaSemana = indicadafolgaSemana;
	}

}
