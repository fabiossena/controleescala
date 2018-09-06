package com.packageIxia.SistemaControleEscala.Models.Projeto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Entity
@Table(name = "HoraAprovacao")
public class HoraAprovacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	private LocalDateTime ultimaModificacao; 
	
	@ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name="prestadorId") 
	private Usuario prestador;
	
	@ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name="aprovadorId") 
	private Usuario aprovador;
	
	private int aceiteAprovador;
	private int aceitePrestador;
	
	private String motivoRecusaPrestador;
	private String motivoRecusaAprovador;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;

	private double totalHoras;

	private double totalValor;
	
	transient private DadosAcessoAprovacaoHoras dadosAcessoAprovacaoHoras;
	
	@Fetch(value = FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy="horaAprovacao", cascade = CascadeType.ALL)
    private List<HoraTrabalhada> horasTrabalhadas;
	
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

	public Usuario getPrestador() {
		return prestador;
	}

	public void setPrestador(Usuario prestador) {
		this.prestador = prestador;
	}

	public int getAceiteAprovador() {
		return aceiteAprovador;
	}

	public void setAceiteAprovador(int aceiteAprovador) {
		this.aceiteAprovador = aceiteAprovador;
	}

	public int getAceitePrestador() {
		return aceitePrestador;
	}

	public void setAceitePrestador(int aceitePrestador) {
		this.aceitePrestador = aceitePrestador;
	}

	public String getMotivoRecusaPrestador() {
		return motivoRecusaPrestador;
	}

	public void setMotivoRecusaPrestador(String motivoRecusaPrestador) {
		this.motivoRecusaPrestador = motivoRecusaPrestador;
	}

	public String getMotivoRecusaAprovador() {
		return motivoRecusaAprovador;
	}

	public void setMotivoRecusaAprovador(String motivoRecusaAprovador) {
		this.motivoRecusaAprovador = motivoRecusaAprovador;
	}

	public double getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(double totalHoras) {
		this.totalHoras = totalHoras;
	}

	public double getTotalValor() {
		return totalValor;
	}

	public void setTotalValor(double totalValor) {
		this.totalValor = totalValor;
	}

	public Usuario getAprovador() {
		return aprovador;
	}

	public void setAprovador(Usuario aprovador) {
		this.aprovador = aprovador;
	}

	public List<HoraTrabalhada> getHorasTrabalhadas() {
		return horasTrabalhadas;
	}

	public void setHorasTrabalhadas(List<HoraTrabalhada> horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public DadosAcessoAprovacaoHoras getDadosAcessoAprovacaoHoras() {
		return dadosAcessoAprovacaoHoras;
	}

	public void setDadosAcessoAprovacaoHoras(DadosAcessoAprovacaoHoras dadosAcessoAprovacaoHoras) {
		this.dadosAcessoAprovacaoHoras = dadosAcessoAprovacaoHoras;
	}
}
