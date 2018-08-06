package com.packageIxia.SistemaControleEscala.Models.Referencias;

import java.time.LocalDate;

public class DiasMes {

	private int diaSemana;
	
	private String descricao;

	private LocalDate data;
	
	public DiasMes(int id, String descricao, LocalDate data) {
		this.diaSemana = id;
		this.descricao = descricao;
		this.data = data;
	}
	
	public int getDiaSemana() {
		return diaSemana;
	}
	
	public void setDiaSemana(int id) {
		this.diaSemana = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

}
