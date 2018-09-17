package com.packageIxia.sistemaControleEscala.models.projeto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.packageIxia.sistemaControleEscala.models.referencias.DadoGenerico;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

public class DadosAcessoSolicitacaoAusencia {

	private List<DadoGenerico> dadosAprovacao = new ArrayList<DadoGenerico>();

	private List<DadoGenerico> dadosAcesso = new ArrayList<DadoGenerico>();
	private boolean isAdminstracao = false;
	private boolean isAtendente = false;
	private boolean isMonitor = false; 
	private boolean isGerente = false;
	private boolean isResponsavelTrocaReposicao  = false;
	private boolean isGerenteReposicao = false;
	private boolean isAtendenteTrocaReposicao = false;

//	private boolean isVisivelAprovacaoPrincipal;
//	private boolean isVisivelAprovacaoTroca;
//	private boolean isVisivelAprovacaoTrocaResponsavel;

	private Usuario usuarioLogado;

	private AusenciaSolicitacao solicitacaoEditada;

	private int aceitePrincipal = 1;

	private boolean isVisivelAprovacao;
	
	public int getAceitePrincipal() {
		return aceitePrincipal;
	}
	
	public List<DadoGenerico> getDadosAcesso() {
		return dadosAcesso;
	}
	
	public boolean isAdminstracao() {
		return isAdminstracao;
	}
	
	public boolean isAtendente() {
		return isAtendente;
	}
	
	public boolean isMonitor() {
		return isMonitor;
	}
	
	public boolean isGerente() {
		return isGerente;
	}
	
	public boolean isResponsavelTrocaReposicao() {
		return isResponsavelTrocaReposicao;
	}
	
	public boolean isGerenteReposicao() {
		return isGerenteReposicao;
	}
	
	public boolean isAtendenteTrocaReposicao() {
		return isAtendenteTrocaReposicao;
	}



	public List<DadoGenerico> getDadosAprovacao() {
		return dadosAprovacao;
	}

//	public boolean isVisivelAprovacaoPrincipal() {
//		return isVisivelAprovacaoPrincipal;
//	}
//
//	public boolean isVisivelAprovacaoTroca() {
//		return isVisivelAprovacaoTroca;
//	}
//
//	public boolean isVisivelAprovacaoTrocaResponsavel() {
//		return isVisivelAprovacaoTrocaResponsavel;
//	}

	public DadosAcessoSolicitacaoAusencia(Usuario usuarioLogado, AusenciaSolicitacao solicitacaoEditada) {
	
		this.usuarioLogado = usuarioLogado;
		this.solicitacaoEditada = solicitacaoEditada;
		
		this.isAdminstracao = this.usuarioLogado.getFuncao().getId() == FuncaoEnum.administracao.funcao.getId();
		this.dadosAcesso.add(new DadoGenerico(0, isAdminstracao ? "Administador" : ""));
		
		isAtendente = this.usuarioLogado.getId() == this.solicitacaoEditada.getUsuario().getId();
		dadosAcesso.add(new DadoGenerico(0, isAtendente ? "Atendente solicitante" : ""));
		
		isMonitor = this.solicitacaoEditada.getUsuarioAprovacao() == null ? false :this.usuarioLogado.getId() == this.solicitacaoEditada.getUsuarioAprovacao().getId();
		dadosAcesso.add(new DadoGenerico(0, isMonitor ? "Monitor responsável principal" : ""));
	
		isGerente =  this.solicitacaoEditada.getGerenciaAprovacao() == null ? false : this.usuarioLogado.getId() == this.solicitacaoEditada.getGerenciaAprovacao().getId();
		dadosAcesso.add(new DadoGenerico(0, isGerente ? "Gerente responsável principal" : ""));
		
		
		
		isAtendenteTrocaReposicao = 
				(this.solicitacaoEditada.getTipoAusencia() == 2 && // é indicado outra escala
				 this.solicitacaoEditada.getAusenciaReposicoes().stream().anyMatch(x->
					x.isIndicadoOutroUsuario() && // é indicado outro usuário
					x.getUsuarioTroca() != null && x.getUsuarioTroca().getId() == this.usuarioLogado.getId()));
		dadosAcesso.add(new DadoGenerico(0, isAtendenteTrocaReposicao ? "Atendente reposição" : ""));
	

		
		isResponsavelTrocaReposicao = 
				(this.solicitacaoEditada.getTipoAusencia() == 2 && // é indicado outra escala
				 this.solicitacaoEditada.getAusenciaReposicoes().stream().anyMatch(x->
				 x.getUsuarioAprovacao() != null && x.getUsuarioAprovacao().getId() == this.usuarioLogado.getId()));
		dadosAcesso.add(new DadoGenerico(0, isResponsavelTrocaReposicao ? "Monitor responsável secundário" : ""));
		
		isGerenteReposicao =
		(this.solicitacaoEditada.getTipoAusencia() == 2 && // é indicado outra escala
				 this.solicitacaoEditada.getAusenciaReposicoes().stream().anyMatch(x->
				 x.getGerenciaAprovacao() != null && x.getGerenciaAprovacao().getId() == this.usuarioLogado.getId()));
		dadosAcesso.add(new DadoGenerico(0, isGerenteReposicao ? "Gerente responsável secundário" : ""));
		
		
	}	

	public String getDadosAcessoString() {
		String result = "";
		for (DadoGenerico dado : dadosAcesso) {
			result += (dado.getNome().isEmpty() || result.trim().isEmpty() ? "" : "<br><br>") + (dado.getNome().isEmpty() ? "" : dado.getNome());
		}
		return result;
	}

	public String getDadosAprovacaoString() {
		String result = "";
		for (DadoGenerico dado : dadosAprovacao) {
			result += (dado.getNome().isEmpty() || result.trim().isEmpty() ? "" : "<br><br>") + (dado.getNome().isEmpty() ? "" : dado.getNome());
		}
		return result;
	}
	
	public void setDadosAprovacaoVisibilidade() {
		
		dadosAprovacao.add(new DadoGenerico(0, 
				(this.solicitacaoEditada.getAtivo() == 0 ? "Não enviada" :
					(this.solicitacaoEditada.getAceito() == 0 ? "Pendente" : 
					(this.solicitacaoEditada.getAceito() == 1 ? "Aprovado" : "Recusado"))) +
					 " monitor responsável principal " 
					 + (this.solicitacaoEditada.getUsuarioAprovacao() == null || 
					    this.solicitacaoEditada.getUsuarioAprovacao().getId() == 0 ? "" : 
						  " - " + this.solicitacaoEditada.getUsuarioAprovacao().getNomeCompleto())
					 + (this.solicitacaoEditada.getDataAceiteRecusa() == null  ? "" : 
						 this.solicitacaoEditada.getDataAceiteRecusa().format(DateTimeFormatter.ofPattern("(dd/MM/yyyy hh:mm)")))
					 + (this.solicitacaoEditada.getMotivoRecusa() == null || 
					   this.solicitacaoEditada.getMotivoRecusa().isEmpty() ? "" : 
						 " <br>Observação: " + this.solicitacaoEditada.getMotivoRecusa()) ));

			boolean recusadoPorAlgum = 
					this.solicitacaoEditada.getAceito() == 2 || 
					(this.solicitacaoEditada.getAusenciaReposicoes().size() > 0 &&
	    			this.solicitacaoEditada.getAusenciaReposicoes().stream().anyMatch(x->
					(x.getAceitoUsuarioTroca() == 2) || 
					(x.getAceitoUsuarioAprovacao() == 2)));
					
			if (isAdminstracao && recusadoPorAlgum) {
	    		setVisivelAprovacao(true);
	    		this.aceitePrincipal = 2;
			}

			if ((this.solicitacaoEditada.getAtivo() == 1 &&  
				this.solicitacaoEditada.getAceito() == 0 &&
				(isGerente || isMonitor || isAdminstracao) &&
				!isVisivelAprovacao()) ||
				
				(this.solicitacaoEditada.getAtivo() == 2 && 
				this.solicitacaoEditada.getAceito() == 2 &&
				(isGerente || isMonitor || isAdminstracao) && 
				!isVisivelAprovacao())) {
	    		setVisivelAprovacao(true);
	    		this.aceitePrincipal = this.solicitacaoEditada.getAceito();	    		
			} 
			
			if (this.solicitacaoEditada.getTipoAusencia() == 2) {  // é indicado outra escala

				for (AusenciaReposicao reposicao : this.solicitacaoEditada.getAusenciaReposicoes()) {
					
					dadosAprovacao.add(new DadoGenerico(0, 
							(this.solicitacaoEditada.getAtivo() == 0 ? "Não enviada" :
							(reposicao.getAceitoUsuarioAprovacao() == 0 ? "Pendente" : 
								(reposicao.getAceitoUsuarioAprovacao() == 1 ? "Aprovado" : "Recusado")))
								 + " monitor responsável secundário"
									 + (reposicao.getUsuarioAprovacao() == null || 
									    reposicao.getUsuarioAprovacao().getId() == 0 ? "" : 
										  " - " + reposicao.getUsuarioAprovacao().getNomeCompleto())
									 + (reposicao.getDataAceiteUsuarioAprovacao() == null  ? "" : 
										  reposicao.getDataAceiteUsuarioAprovacao().format(DateTimeFormatter.ofPattern("(dd/MM/yyyy hh:mm)")))
									 + (reposicao.getMotivoRecusaUsuarioAprovacao() == null || 
									    reposicao.getMotivoRecusaUsuarioAprovacao().isEmpty() ? "" : 
										  " <br>Observação: " + reposicao.getMotivoRecusaUsuarioAprovacao()) ));
						
					isResponsavelTrocaReposicao = reposicao.getUsuarioAprovacao() == null ? false : 
							this.usuarioLogado.getId() == reposicao.getUsuarioAprovacao().getId(); 
					
					isGerenteReposicao = reposicao.getGerenciaAprovacao() == null ? false :
						reposicao.getGerenciaAprovacao().getId() == this.usuarioLogado.getId();
					
					if ((this.solicitacaoEditada.getAtivo() == 1 &&  
						 reposicao.getAceitoUsuarioAprovacao() == 0 &&
						 (isResponsavelTrocaReposicao || isGerenteReposicao) &&
						 !isVisivelAprovacao()) ||
						
						(this.solicitacaoEditada.getAtivo() == 2 && 
						 reposicao.getAceitoUsuarioAprovacao() == 2 &&
						 (isResponsavelTrocaReposicao || isGerenteReposicao) && 
						 !isVisivelAprovacao())) {	
			    		setVisivelAprovacao(true);
			    		this.aceitePrincipal = reposicao.getAceitoUsuarioAprovacao();			    		
					}
					
					
		    		
		    		if (reposicao.isIndicadoOutroUsuario()) {
		    			
		    			dadosAprovacao.add(new DadoGenerico(0, 
								(this.solicitacaoEditada.getAtivo() == 0 ? "Não enviada" :
									(reposicao.getAceitoUsuarioTroca() == 0 ? "Pendente" : 
									(reposicao.getAceitoUsuarioTroca() == 1 ? "Aprovado" : "Recusado")))
									 + " usuário troca secundário "
										 + (reposicao.getUsuarioTroca() == null || 
										    reposicao.getUsuarioTroca().getId() == 0 ? "" : 
											  " - " + reposicao.getUsuarioTroca().getNomeCompleto())
										 + (reposicao.getDataAceiteUsuarioTroca() == null  ? "" : 
											  reposicao.getDataAceiteUsuarioTroca().format(DateTimeFormatter.ofPattern("(dd/MM/yyyy hh:mm)")))
										 + (reposicao.getMotivoRecusaUsuarioTroca() == null || 
										    reposicao.getMotivoRecusaUsuarioTroca().isEmpty() ? "" : 
											  " <br>Observação: " + reposicao.getMotivoRecusaUsuarioTroca()) ));
		    			
		    			isAtendenteTrocaReposicao = reposicao.getUsuarioTroca().getId() == this.usuarioLogado.getId(); 
		    			if ((this.solicitacaoEditada.getAtivo() == 1 &&  
								 reposicao.getAceitoUsuarioTroca() == 0 &&
								 isAtendenteTrocaReposicao &&
								 !isVisivelAprovacao()) ||
								
								(this.solicitacaoEditada.getAtivo() == 2 && 
								 reposicao.getAceitoUsuarioTroca() == 2 &&
								 isAtendenteTrocaReposicao && 
								 !isVisivelAprovacao())) {
				    		setVisivelAprovacao(true);
				    		this.aceitePrincipal = reposicao.getAceitoUsuarioTroca();
				    		
						}
		    		}
				}
			}
			
			dadosAprovacao = dadosAprovacao.stream().distinct().collect(Collectors.toList());

	}

	public boolean isVisivelAprovacao() {
		return this.isVisivelAprovacao;
	}

	private void setVisivelAprovacao(boolean isVisivelAprovacao) {
		this.isVisivelAprovacao = isVisivelAprovacao;
	}
}
