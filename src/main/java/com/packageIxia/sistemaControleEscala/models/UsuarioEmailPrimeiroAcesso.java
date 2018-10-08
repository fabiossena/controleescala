package com.packageIxia.sistemaControleEscala.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.packageIxia.sistemaControleEscala.models.referencias.CentroCusto;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Entity
@Table(name = "UsuarioEmailPrimeiroAcesso", uniqueConstraints = @UniqueConstraint(columnNames = { "matricula" }))
public class UsuarioEmailPrimeiroAcesso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

    @NotNull
    @Pattern(regexp="^(.+)@(.+)$")
    @NotEmpty(message="Preencha o campo e-mail")
    private String email;

    @NotNull
    @NotEmpty(message="Preencha o campo matrícula")
    private String matricula;

	private boolean aceito;

	@CreationTimestamp
	@Column(updatable=false)
	protected LocalDateTime dataCriacao;

	@Column
	@UpdateTimestamp
	protected LocalDateTime ultimaModificacao;

    @NotNull(message="Preencha o campo função")
	@ManyToOne
	@JoinColumn(name="funcaoId")
	private Funcao funcao;  


    @NotNull(message="Preencha o campo centro de custo")
	@ManyToOne
	@JoinColumn(name="centroCustoId") 
	private CentroCusto centroCusto;

	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getMatricula()
    {
        return this.matricula;
    }

    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }

	public boolean isAceito() {
		return aceito;
	}

	public void setAceito(boolean aceito) {
		this.aceito = aceito;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	public Funcao getFuncao() {  	
		return this.funcao;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}
}
