package com.packageIxia.SistemaControleEscala.Models.Projeto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.packageIxia.SistemaControleEscala.Models.Referencias.DiaSemana;
import com.packageIxia.SistemaControleEscala.Models.Referencias.MotivoAusencia;

@Entity
@Table(name = "ProjetoFolgaSemanal")
public class ProjetoFolgaSemanal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;

	@Column
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao;  

	@NotNull
	private long projetoEscalaPrestadorId;
	
	@NotEmpty
	private String horaInicio, horaFim;

	@NotNull
	private int motivoId, diaSemanaId;
	
    @Transient
    private MotivoAusencia motivo;
	
    @Transient
    private DiaSemana diaSemana;

	public ProjetoFolgaSemanal() {
	
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.setDiaSemanaId(diaSemana.getId());
		this.diaSemana = diaSemana;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public MotivoAusencia getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoAusencia motivo) {
		this.setMotivoId(motivo	.getId());
		this.motivo = motivo;
	}

	public int getDiaSemanaId() {
		return diaSemanaId;
	}

	public void setDiaSemanaId(int diaSemanaId) {
		this.diaSemanaId = diaSemanaId;
	}

	public int getMotivoId() {
		return motivoId;
	}
	
	public void setMotivoId(int motivoId) {
		this.motivoId = motivoId;
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
	
	public long getProjetoEscalaPrestadorId() {
		return projetoEscalaPrestadorId;
	}
	
	public void setProjetoEscalaPrestadorId(long projetoEscalaPrestadorId) {
		this.projetoEscalaPrestadorId = projetoEscalaPrestadorId;
	}

}
