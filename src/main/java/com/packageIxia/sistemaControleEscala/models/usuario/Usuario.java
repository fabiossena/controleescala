package com.packageIxia.sistemaControleEscala.models.usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Entity
@Table(name = "Usuario", uniqueConstraints = @UniqueConstraint(columnNames = { "matricula", "email", "cpf" }))
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	@CreationTimestamp
	@Column(updatable=false)
	protected LocalDateTime dataCriacao;
	
	@Column
	@UpdateTimestamp
	protected LocalDateTime ultimaModificacao;
	
	@Size(max = 100)
    @NotEmpty(message="Preencha o campo nome")
    private String primeiroNome;

    @Size(max = 150)
    @NotEmpty(message="Preencha o campo sobrenome")
    private String sobrenome;

	@Size(max = 100)
    @NotEmpty(message="Preencha o campo e-mail")
    @Pattern(regexp="^(.+)@(.+)$", message="Digite um e-mail válido")
    private String email;
    																			
    @NotEmpty(message="Preencha o campo matrícula")
    private String matricula;

	@Size(max = 50)
    private String cpf;

	@Size(max = 50)
    private String rg;

	@Size(max = 50)
    private String skype;

	@Size(max = 50)
    private String telefone;

	@Size(max = 50)
    private String ramal;

	@Size(max = 50)
    private String celular;

	@Size(max = 50)
    private String telefoneRecado;

	@Size(max = 50)
    @Length(min = 6, message = "O campo senha deve ter no mínimo 6 caracteres")
    @NotEmpty(message="Preencha o campo senha")
    private String senha;

    @Transient
    private String repetirSenha;

	@Size(max = 1)
    private String sexo;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
   
    private boolean ativo = true;

	@Size(max = 20)
    private String cep;

	@Size(max = 100)
    private String logradouro;

	@Size(max = 20)
    private String numeroLogradouro;

	@Size(max = 100)
    private String bairro;

	@Size(max = 100)
    private String informacoesAdicionaisEndereco;

	@Size(max = 100)
    private String paisString;
	
	@Size(max = 150)
    private String estadoString;

	@Size(max = 150)
	private String cidadeString;
    
    private int cidadeId;
    
    private int bancoId;

	@Size(max = 50)
    private String agencia;

	@Size(max = 50)
    private String conta;

	@Size(max = 10)
    private String digito;

	private boolean contaCorrente;

	@Size(max = 250)
    private String nomeCompletoCadastradoBanco;
    
    private int funcaoId;
    
    private int periodoDisponivelId;

    @Transient
	private Funcao funcao;

	private boolean excluido;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataExcluido;

	private String observacaoConta;
	
	private String observacaoAdicionais;

	private int bancoHoras;
	
	private int folgasDisponiveisAno;

	private int valorMinuto;
	
	public long getId() {
		return id;
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
    
    public String getPrimeiroNome()
    {
        return this.primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome)
    {
        this.primeiroNome = primeiroNome;
    }
    
    public String getSobrenome()
    {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome)
    {
        this.sobrenome = sobrenome;
    }
    
    public String getCpf()
    {
        return this.cpf;
    }

    public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }
    
    public String getRg()
    {
        return this.rg;
    }

    public void setRg(String rg)
    {
        this.rg = rg;
    }
    
    public String getSkype()
    {
        return this.skype;
    }

    public void setSkype(String skype)
    {
        this.rg = skype;
    }
    
    public String getTelefone()
    {
        return this.telefone;
    }

    public void setTelefone(String telefone)
    {
        this.telefone = telefone;
    }
    
    public String getRamal()
    {
        return this.ramal;
    }

    public void setRamal(String ramal)
    {
        this.ramal = ramal;
    }
    
    public String getCelular()
    {
        return this.celular;
    }

    public void setCelular(String celular)
    {
        this.celular = celular;
    }

    public void setTelefonRecado(String telefoneRecado)
    {
        this.telefoneRecado = telefoneRecado;
    }
    
    public String getTelefoneRecado()
    {
        return this.telefoneRecado;
    }    
    
    public String getSenha()
    {
        return this.senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }
    
    public String getRepetirSenha()
    {
        return this.repetirSenha;
    }

    public void setRepetirSenha(String repetirSenha)
    {
        this.repetirSenha = repetirSenha;
    }

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeroLogradouro() {
		return numeroLogradouro;
	}

	public void setNumeroLogradouro(String numeroLogradouro) {
		this.numeroLogradouro = numeroLogradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getInformacoesAdicionaisEndereco() {
		return informacoesAdicionaisEndereco;
	}

	public void setInformacoesAdicionaisEndereco(String informacoesAdicionaisEndereco) {
		this.informacoesAdicionaisEndereco = informacoesAdicionaisEndereco;
	}

	public int getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(int cidadeId) {
		this.cidadeId = cidadeId;
	}

	public int getBancoId() {
		return bancoId;
	}

	public void setBancoId(int bancoId) {
		this.bancoId = bancoId;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDigito() {
		return digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	public String getNomeCompletoCadastradoBanco() {
		return nomeCompletoCadastradoBanco;
	}

	public void setNomeCompletoCadastradoBanco(String nomeCompletoCadastradoBanco) {
		this.nomeCompletoCadastradoBanco = nomeCompletoCadastradoBanco;
	}

	public int getFuncaoId() {
		return funcaoId;
	}
	
	public void setFuncaoId(int funcaoId) {
		this.funcaoId = funcaoId;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getPeriodoDisponivelId() {
		return periodoDisponivelId;
	}

	public void setPeriodoDisponivelId(int periodoDisponivelId) {
		this.periodoDisponivelId = periodoDisponivelId;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	public Funcao getFuncao() {  	
		return this.funcao;
	}

	@Transient
	public String getNomeCompleto() {
		return this.primeiroNome.trim() + " " + this.sobrenome.trim();
	}
	
	@Transient
	public String getNomeCompletoMatricula() {
		String nomeCompletoMatricula = this.matricula + (this.excluido ? "(excluído)" : "") + " - " + this.primeiroNome.trim() + " " + this.sobrenome.trim();
		return nomeCompletoMatricula;
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

	public String getPaisString() {
		return paisString;
	}

	public void setPaisString(String paisString) {
		this.paisString = paisString;
	}

	public String getEstadoString() {
		return estadoString;
	}

	public void setEstadoString(String estadoString) {
		this.estadoString = estadoString;
	}

	public String getCidadeString() {
		return cidadeString;
	}

	public void setCidadeString(String cidadeString) {
		this.cidadeString = cidadeString;
	}

	public boolean isContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(boolean contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getObservacaoConta() {
		return observacaoConta;
	}

	public void setObservacaoConta(String observacaoConta) {
		this.observacaoConta = observacaoConta;
	}

	public String getObservacaoAdicionais() {
		return observacaoAdicionais;
	}

	public void setObservacaoAdicionais(String observacaoAdicionais) {
		this.observacaoAdicionais = observacaoAdicionais;
	}

	public int getBancoHoras() {
		return bancoHoras;
	}

	public void setBancoHoras(int bancoHoras) {
		this.bancoHoras = bancoHoras;
	}

	public int getFolgasDisponiveisAno() {
		return folgasDisponiveisAno;
	}

	public void setFolgasDisponiveisAno(int folgasDisponiveisAno) {
		this.folgasDisponiveisAno = folgasDisponiveisAno;
	}

	public int getValorMinuto() {
		return valorMinuto;
	}

	public void setValorMinuto(int valorMinuto) {
		this.valorMinuto = valorMinuto;
	}
}
