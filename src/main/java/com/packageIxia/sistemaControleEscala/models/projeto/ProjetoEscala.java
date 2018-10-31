package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemanaEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Entity
@Table(name = "ProjetoEscala")
public class ProjetoEscala {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	protected LocalDateTime ultimaModificacao; 

	@Transient
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="projetoId")
	transient private Projeto projeto;
	
	private long projetoId;
	
	@NotEmpty(message="Preencha o campo descrição escala")
	private String descricaoEscala;

	@Size(max = 500)
	private String observacaoEscala;
	
	@NotNull(message="Preencha o campo periodo")
	private int periodoId;

	@Size(max = 5)
	@NotEmpty(message="Preencha o campo hora início")
	private String horaInicio;
	
	@Size(max = 5)
	@NotEmpty(message="Preencha o campo hora fim")
	private String horaFim;
	
	@NotNull(message="Preencha o campo dia da semana de")
	private int diaSemanaDeId;
	
	@NotNull(message="Preencha o campo dia da semana até")
	private int diaSemanaAteId;


	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="monitorId")
	@NotNull(message="Preencha o campo monitor")
	private Usuario monitor;
	
	@NotNull(message="Preencha o campo quantidade de atendentes planejada")
	private int quantidadePrestadoresPlanejada;
	
	private int prioridadeId;
	
	@Size(max = 5)
	private String horaInicioPrioridade;

	@Size(max = 5)
	private String horaFimPrioridade;
	
	private boolean ativo;
	
	private boolean definidaPrioridade;

	private int diaSemanaAtePrioridadeId;

	private int diaSemanaDePrioridadeId;

	private boolean excluido;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataExcluido;

	transient private int quantidadePrestadoresReal;

	transient private List<AusenciaReposicao> ausenciaReposicoes;
	
	private String jsonPrestadorDiasHoras;

	transient private List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getProjetoId() {
		return this.projetoId;
	}
	
	public void setProjetoId(long projetoId) {
		this.projetoId = projetoId;
	}
	
	public String getDescricaoEscala() {
		return descricaoEscala;
	}

	public void setDescricaoEscala(String descricaoEscala) {
		this.descricaoEscala = descricaoEscala;
	}

	public String getObservacaoEscala() {
		return observacaoEscala;
	}

	public void setObservacaoEscala(String observacaoEscala) {
		this.observacaoEscala = observacaoEscala;
	}

	public int getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(int periodoId) {
		this.periodoId = periodoId;
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

	public int getDiaSemanaDeId() {
		return diaSemanaDeId;
	}

	public DiaSemana getDiaSemanaDe() {
		return DiaSemanaEnum.GetDiaSemanaFromId(diaSemanaDeId);
	}

	public DiaSemana getDiaSemanaAte() {
		return DiaSemanaEnum.GetDiaSemanaFromId(diaSemanaAteId);
	}

	public void setDiaSemanaDeId(int diaSemanaDeId) {
		this.diaSemanaDeId = diaSemanaDeId;
	}

	public int getDiaSemanaAteId() {
		return diaSemanaAteId;
	}


	public void setDiaSemanaAteId(int diaSemanatAteId) {
		this.diaSemanaAteId = diaSemanatAteId;
	}

	public Usuario getMonitor() {
		return this.monitor;
	}

	public void setMonitor(Usuario monitor) {
		this.monitor = monitor;
	}

	public int getQuantidadePrestadoresPlanejada() {
		return quantidadePrestadoresPlanejada;
	}

	public void setQuantidadePrestadoresPlanejada(int quantidadePrestadoresPlanejada) {
		this.quantidadePrestadoresPlanejada = quantidadePrestadoresPlanejada;
	}

	public int getPrioridadeId() {
		return prioridadeId;
	}

	public void setPrioridadeId(int prioridadeId) {
		this.prioridadeId = prioridadeId;
	}

	public String getHoraInicioPrioridade() {
		return horaInicioPrioridade;
	}

	public void setHoraInicioPrioridade(String horaInicioPrioridade) {
		this.horaInicioPrioridade = horaInicioPrioridade;
	}

	public String getHoraFimPrioridade() {
		return horaFimPrioridade;
	}

	public void setHoraFimPrioridade(String horaFimPrioridade) {
		this.horaFimPrioridade = horaFimPrioridade;
	}

	public int getDiaSemanaDePrioridadeId() {
		return diaSemanaDePrioridadeId;
	}

	public DiaSemana getDiaSemanaDePrioridade() {
		return DiaSemanaEnum.GetDiaSemanaFromId(diaSemanaDePrioridadeId);
	}

	public DiaSemana getDiaSemanaAtePrioridade() {
		return DiaSemanaEnum.GetDiaSemanaFromId(diaSemanaAtePrioridadeId);
	}

	public void setDiaSemanaDePrioridadeId(int diaSemantaDePrioridadeId) {
		this.diaSemanaDePrioridadeId = diaSemantaDePrioridadeId;
	}

	public int getDiaSemanaAtePrioridadeId() {
		return diaSemanaAtePrioridadeId;
	}

	public void setDiaSemanaAtePrioridadeId(int diaSemanatAtePrioridadeId) {
		this.diaSemanaAtePrioridadeId = diaSemanatAtePrioridadeId;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean getDefinidaPrioridade() {
		return definidaPrioridade;
	}

	public void setDefinidaPrioridade(boolean definidaPrioridade) {
		this.definidaPrioridade = definidaPrioridade;
	}

	public String getDescricaoCompletaEscala() {
		return this.descricaoEscala + " - " + this.horaInicio + " " + this.horaFim  + " (" + this.getDiaSemanaDe().getNome() + " à " + this.getDiaSemanaAte().getNome() + ")";
	}

	public String getDescricaoCompletaEscalaData() {
		return this.descricaoEscala + " - " + this.horaInicio + " " + this.horaFim  + " (" + this.getDiaSemanaDe().getNome() + " à " + this.getDiaSemanaAte().getNome() + ")" +
				(this.projeto == null ? "" :  " " + this.projeto.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + (this.projeto.getDataInicio() == null ? "" : " à " + this.projeto.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
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

	public void setQuantidadePrestadoresReal(int quantidadePrestadoresReal) {
		this.quantidadePrestadoresReal = quantidadePrestadoresReal;
	}

	public int getQuantidadePrestadoresReal() {
		return this.quantidadePrestadoresReal;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void setAusenciaReposicoes(List<AusenciaReposicao> ausenciaReposicoes) {
		this.ausenciaReposicoes = ausenciaReposicoes;
		
	}

	public List<AusenciaReposicao> getAusenciaReposicoes() {
		return this.ausenciaReposicoes;
	}

	public String getJsonPrestadorDiasHoras() {
		return this.jsonPrestadorDiasHoras;
	}

	public void setJsonPrestadorDiasHoras(String json) {
		this.jsonPrestadorDiasHoras = json;
	}

	public List<ProjetoEscalaPrestadorDiaHoraTrabalho> getDiasHorasTrabalho() {
		if (this.diasHorasTrabalho != null) {
			return this.diasHorasTrabalho;
		}
		
		this.diasHorasTrabalho = new ArrayList<ProjetoEscalaPrestadorDiaHoraTrabalho>();
		for(int dia = 0; dia <= 6; dia++) {
			ProjetoEscalaPrestadorDiaHoraTrabalho diaHora = new ProjetoEscalaPrestadorDiaHoraTrabalho();
			diaHora.setDiaSemana(dia+1);
			diaHora.setHoraInicio("");
			diaHora.setHoraFim("");
			
			//ProjetoEscalaPrestadorDiaHoraTrabalho diaHoraTrabalho = new ProjetoEscalaPrestadorDiaHoraTrabalho(); // this.escalaSelecionada.getPrestadorDiasHoras().stream().filter(x->x.getDiaSemana()==d).findFirst().orElse(new ProjetoEscalaPrestadorDiaHoraTrabalho());

			if (dia+1  >= this.getDiaSemanaDeId() &&  dia+1 <= this.getDiaSemanaAteId()) {
				diaHora.setHoraInicio(this.getHoraInicio());
				diaHora.setHoraFim(this.getHoraFim());
			}
			
			this.diasHorasTrabalho.add(diaHora);
		}
		
		return this.diasHorasTrabalho;
	}
}
