package com.packageIxia.sistemaControleEscala.models.usuario;

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

import com.packageIxia.sistemaControleEscala.models.referencias.DiaSemana;
import com.packageIxia.sistemaControleEscala.models.referencias.MotivoAusencia;

@Entity
@Table(name = "FolgaSemanalPlanejadaUsuario")
public class FolgaSemanalPlanejadaUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;

	@Column
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao;  

	@NotNull
	private long usuarioId;
	
	@NotEmpty
	private String horaInicio, horaFim;

	@NotNull
	private int motivoId, diaSemanaId;
	
    @Transient
    private MotivoAusencia motivo;
	
    @Transient
    private DiaSemana diaSemana;

	public FolgaSemanalPlanejadaUsuario() {
	
	}
	
	public FolgaSemanalPlanejadaUsuario(int id, int motivoId, int diaSemanaId, String horaInicio, String horaFim) {
		this.setId(id);
		this.setMotivoId(motivoId);
		this.setDiaSemanaId(diaSemanaId);
		this.setHoraInicio(horaInicio);
		this.setHoraFim(horaFim);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
	
	public long getUsuarioId() {
		return usuarioId;
	}
	
	public void setUsuarioId(long usuarioId) {
		this.usuarioId = usuarioId;
	}

}
