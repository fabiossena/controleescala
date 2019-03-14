package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "ProjetoEscalaPrestador")
public class ProjetoEscalaPrestador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name="sequence", sequenceName="usersequence")
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

	@ManyToOne
	@JoinColumn(name="funcaoId") 
	private Funcao funcao;
	
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
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy="projetoEscalaPrestador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjetoEscalaPrestadorDiaHoraTrabalho> projetoEscalaPrestadorDiaHoraTrabalhos = new ArrayList<ProjetoEscalaPrestadorDiaHoraTrabalho>();
    
	// todo: ajustar
    transient private List<ProjetoFolgaSemanal> projetoFolgasSemanais;

    transient private List<AusenciaSolicitacao> ausenciaSolicitacoes;

    transient private List<AusenciaReposicao> ausenciaReposicoes;

    transient private String json;

	transient private List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho;

	transient private String observacaoHorasEscala;
	
	public String getJsonDiasHorasTrabalho() {
		return this.json;
	}
	
	public void setJsonDiasHorasTrabalho(String json) {
		this.json= json;
	}
	
	public boolean getHasDiasHorasTrabalho() {
		return this.getProjetoEscalaPrestadorDiasHorasTrabalho() != null && this.getProjetoEscalaPrestadorDiasHorasTrabalho().size() > 0;
	}
	
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
	
	public Funcao getFuncao() {
		return funcao;
	}
	
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
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

	public List<ProjetoFolgaSemanal> getProjetoFolgasSemanais() {
		return projetoFolgasSemanais;
	}

	public void setProjetoFolgasSemanais(List<ProjetoFolgaSemanal> projetoFolgasSemanais) {
		this.projetoFolgasSemanais = projetoFolgasSemanais;
	}
	
	public boolean isIndicadaFolgaSemana() {
		return indicadaFolgaSemana;
	}

	public void setIndicadaFolgaSemana(boolean indicadafolgaSemana) {
		this.indicadaFolgaSemana = indicadafolgaSemana;
	}

	public void setAusenciaSolicitacoes(List<AusenciaSolicitacao> ausenciaSolicitacoes) {
		this.ausenciaSolicitacoes = ausenciaSolicitacoes;		
	}

	public List<AusenciaSolicitacao> getAusenciaSolicitacoes() {
		return this.ausenciaSolicitacoes;		
	}

	public void setAusenciaReposicoes(List<AusenciaReposicao> ausenciaReposicoes) {
		this.ausenciaReposicoes = ausenciaReposicoes;		
	}

	public List<AusenciaReposicao> getAusenciaReposicoes() {
		return	this.ausenciaReposicoes;		
	}

	public List<ProjetoEscalaPrestadorDiaHoraTrabalho> getDiasHorasTrabalho() {
		if (this.diasHorasTrabalho != null) {
			return this.diasHorasTrabalho;
		}
		
		this.diasHorasTrabalho = new ArrayList<ProjetoEscalaPrestadorDiaHoraTrabalho>();

		if (getProjetoEscalaPrestadorDiasHorasTrabalho() == null || getProjetoEscalaPrestadorDiasHorasTrabalho().size() == 0) {
			if (getProjetoEscala() != null) {
				this.diasHorasTrabalho.addAll(getProjetoEscala().getDiasHorasTrabalho());
			}
		}
		else {
			for(int dia = 0; dia <= 6; dia++) {
				ProjetoEscalaPrestadorDiaHoraTrabalho diaHora = new ProjetoEscalaPrestadorDiaHoraTrabalho();
				int d = dia+1;
				diaHora.setDiaSemana(d);
				ProjetoEscalaPrestadorDiaHoraTrabalho diaHoraTrabalho = getProjetoEscalaPrestadorDiasHorasTrabalho().stream().filter(x->x.getDiaSemana()==d).findFirst().orElse(new ProjetoEscalaPrestadorDiaHoraTrabalho());

				diaHora.setId(diaHoraTrabalho.getId()); 
				diaHora.setHoraInicio(diaHoraTrabalho.getHoraInicio());
				diaHora.setHoraFim(diaHoraTrabalho.getHoraFim());
				this.diasHorasTrabalho.add(diaHora);
			}
		}
		
		return this.diasHorasTrabalho;
	}

	public void setDiasHorasTrabalho(
			List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho) {
		this.diasHorasTrabalho = diasHorasTrabalho;
	}

	public List<ProjetoEscalaPrestadorDiaHoraTrabalho> getProjetoEscalaPrestadorDiasHorasTrabalho() {
		return projetoEscalaPrestadorDiaHoraTrabalhos;
	}

	public void setProjetoEscalaPrestadorDiasHorasTrabalho(List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho) {
		this.projetoEscalaPrestadorDiaHoraTrabalhos = diasHorasTrabalho;
	}

	

	public String getObservacaoHorasEscala(int mes, int ano) {
		if (this.observacaoHorasEscala == null || this.observacaoHorasEscala == "") {
			List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho = getDiasHorasTrabalho();
			int dias = 1;
			double horas = 0;
			for(int dia = 1; dia <= LocalDate.of(ano, mes, 1).plusMonths(1).minusDays(1).getDayOfMonth(); dia++) {
				int diaDoMes = dia;
				ProjetoEscalaPrestadorDiaHoraTrabalho diaTrabalhado = diasHorasTrabalho.stream().filter(x->x.getDiaSemana()==LocalDate.of(ano, mes, diaDoMes).getDayOfWeek().getValue()).findFirst().orElseGet(null);
				if (diaTrabalhado != null && diaTrabalhado.getHoraInicio() != null && !diaTrabalhado.getHoraInicio().isEmpty()) {
					dias++;
					horas += Utilities.horaValueDiff(diaTrabalhado.getHoraInicio(), diaTrabalhado.getHoraFim());
				}
			}
			
			this.observacaoHorasEscala = Utilities.converterSecToTime((int)(horas*60*60)) + "hrs " + (dias-1) + " dias";
		}
		
		return this.observacaoHorasEscala;
	}

}
