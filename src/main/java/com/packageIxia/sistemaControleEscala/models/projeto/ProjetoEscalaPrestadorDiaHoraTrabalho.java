package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ProjetoEscalaPrestadorDiaHoraTrabalho")
public class ProjetoEscalaPrestadorDiaHoraTrabalho {
	
	public ProjetoEscalaPrestadorDiaHoraTrabalho(int diaSemana, String horaInicio, String horaFim) {
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
	}
	
	public ProjetoEscalaPrestadorDiaHoraTrabalho() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;	

	private int diaSemana;

	@Size(max = 5)
	private String horaInicio;
	
	@NotEmpty(message="Preencha o campo hora fim")
	private String horaFim;
	

	@ManyToOne
	@JoinColumn(name="projetoEscalaPrestadorId")
	private ProjetoEscalaPrestador projetoEscalaPrestador;


	public ProjetoEscalaPrestador getProjetoEscalaPrestador() {
		return projetoEscalaPrestador;
	}

	public void setProjetoEscalaPrestador(ProjetoEscalaPrestador projetoEscalaPrestador) {
		this.projetoEscalaPrestador = projetoEscalaPrestador;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
