package com.packageIxia.SistemaControleEscala.Services.Projetos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.packageIxia.SistemaControleEscala.Daos.AusenciaReposicaoDao;
import com.packageIxia.SistemaControleEscala.Daos.AusenciaSolicitacaoDao;
import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.Projeto.AusenciaReposicao;
import com.packageIxia.SistemaControleEscala.Models.Projeto.AusenciaSolicitacao;
import com.packageIxia.SistemaControleEscala.Models.Projeto.DadosAcessoSolicitacaoAusencia;
import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Service
public class AusenciaSolicitacaoService {

	private AusenciaSolicitacaoDao ausenciaSolicitacaoDao;
	private AusenciaReposicaoDao ausenciaReposicaoDao;
	private HttpSession session;
	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;
	private ProjetoService projetoService;
	private ProjetoEscalaService projetoEscalaService;
	
	@Autowired
	public AusenciaSolicitacaoService(
			ProjetoEscalaPrestadorService projetoEscalaPrestadorService,
			ProjetoEscalaService projetoEscalaService,
			ProjetoService projetoService,
			AusenciaSolicitacaoDao ausenciaSolicitacaoDao,
			AusenciaReposicaoDao ausenciaReposicaoDao,
			HttpSession session) {
		this.ausenciaSolicitacaoDao = ausenciaSolicitacaoDao;
		this.ausenciaReposicaoDao = ausenciaReposicaoDao;
		this.session = session;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.projetoEscalaService = projetoEscalaService;
		this.projetoService = projetoService;
	}
	
	public List<AusenciaSolicitacao> findAll() {
		return this.findAll(false);
	}
	
	public List<AusenciaSolicitacao> findAll(boolean isSomenteComAcesso) {

		List<AusenciaSolicitacao> ausencias = new ArrayList<AusenciaSolicitacao>();
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		
		if (usuarioLogado.getFuncao().getId() == FuncaoEnum.administracao.getFuncao().getId()) {
			if (isSomenteComAcesso) {
				return ausencias;
			}
			
			List<AusenciaSolicitacao> solicitacoes = Utilities.toList(this.ausenciaSolicitacaoDao.findAll()).stream().collect(Collectors.toList()).stream().filter(x->x.getAtivo() >= 0 && x.getAtivo() < 4).collect(Collectors.toList());
			if (solicitacoes.size() > 0) {
				ausencias.addAll(solicitacoes);
			}
			
		}
		else {
			List<AusenciaSolicitacao> solicitacoesMinhas = this.ausenciaSolicitacaoDao.findAllByUsuarioId(usuarioLogado.getId()).stream().filter(x->x.getAtivo() >= 0 && x.getAtivo() < 4).collect(Collectors.toList());
			if (solicitacoesMinhas.size() > 0) {
				ausencias.addAll(solicitacoesMinhas);
			}
			
			List<AusenciaSolicitacao> solicitacoesAprovacaoSolicitacao = this.ausenciaSolicitacaoDao.findAllByUsuarioAprovacaoId(usuarioLogado.getId()).stream().filter(x->x.getAtivo() > 0 && x.getAtivo() < 4).collect(Collectors.toList());
			for (AusenciaSolicitacao paraInserir : solicitacoesAprovacaoSolicitacao) {
				if (!ausencias.stream().anyMatch(x->x.getId() == paraInserir.getId())) {
					ausencias.add(paraInserir);					
				}
			}
			
			
			List<AusenciaSolicitacao> solicitacoesAprovacaoSolicitacaoGerente = this.ausenciaSolicitacaoDao.findAllByGerenciaAprovacaoId(usuarioLogado.getId()).stream().filter(x->x.getAtivo() > 0 && x.getAtivo() < 4).collect(Collectors.toList());
			for (AusenciaSolicitacao paraInserir : solicitacoesAprovacaoSolicitacaoGerente) {
				if (!ausencias.stream().anyMatch(x->x.getId() == paraInserir.getId())) {
					ausencias.add(paraInserir);					
				}
			}			
			
			List<AusenciaSolicitacao> solicitacoesParaMim = this.ausenciaReposicaoDao.findAllByUsuarioTrocaId(usuarioLogado.getId()).stream().map(x->x.getAusenciaSolicitacao()).collect(Collectors.toList()).stream().filter(x->x.getAtivo() > 0 && x.getAtivo() < 4).collect(Collectors.toList());
			for (AusenciaSolicitacao paraInserir : solicitacoesParaMim) {
				if (!ausencias.stream().anyMatch(x->x.getId() == paraInserir.getId())) {
					ausencias.add(paraInserir);					
				}
			}
			
			List<AusenciaSolicitacao> solicitacoesAprovacaoResposicao = this.ausenciaReposicaoDao.findAllByUsuarioAprovacaoId(usuarioLogado.getId()).stream().map(x->x.getAusenciaSolicitacao()).collect(Collectors.toList()).stream().filter(x->x.getAtivo() > 0 && x.getAtivo() < 4).collect(Collectors.toList());
			for (AusenciaSolicitacao paraInserir : solicitacoesAprovacaoResposicao) {
				if (!ausencias.stream().anyMatch(x->x.getId() == paraInserir.getId())) {
					ausencias.add(paraInserir);					
				}
			}
			
	
			List<AusenciaSolicitacao> solicitacoesAprovacaoReposicaoGerente = this.ausenciaReposicaoDao.findAllByGerenciaAprovacaoId(usuarioLogado.getId()).stream().map(x->x.getAusenciaSolicitacao()).collect(Collectors.toList()).stream().filter(x->x.getAtivo() > 0 && x.getAtivo() < 4).collect(Collectors.toList());
			for (AusenciaSolicitacao paraInserir : solicitacoesAprovacaoReposicaoGerente) {
				if (!ausencias.stream().anyMatch(x->x.getId() == paraInserir.getId())) {
					ausencias.add(paraInserir);					
				}
			}
		}

		if (ausencias.size() > 0) {
			List<Projeto> projetos = this.projetoService.findAll();
			for (AusenciaSolicitacao ausencia : ausencias) {
				DadosAcessoSolicitacaoAusencia dados = new DadosAcessoSolicitacaoAusencia(usuarioLogado, ausencia);
				ausencia.setAcesso(dados.getDadosAcessoString());
				dados.setDadosAprovacaoVisibilidade();
				ausencia.setObservacaoComplemento(dados.getDadosAprovacaoString());
				ausencia.getProjetoEscala().setProjeto(projetos.stream().filter(x->x.getId()==ausencia.getProjetoEscala().getProjetoId()).findFirst().get());
				ausencia.setDadosAcesso(dados);
			}
		}
		
		return ausencias.size() == 0 ? new ArrayList<AusenciaSolicitacao>() : 
			ausencias.stream().filter(x-> !isSomenteComAcesso || x.getDadosAcesso().isVisivelAprovacao())
				.sorted(Comparator.comparing(AusenciaSolicitacao::getAceito)
				.thenComparing(Comparator.comparing(AusenciaSolicitacao::getDataCriacao).reversed())).collect(Collectors.toList());
	}
	
	@Transactional
	public String save(AusenciaSolicitacao solicitacao) {

		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.getFuncao().getId()) {
			solicitacao.setUsuario(usuarioLogado);
		}
		
    	Usuario usuario = usuarioLogado.getFuncaoId() == FuncaoEnum.administracao.getFuncao().getId() ? solicitacao.getUsuario() : usuarioLogado;
		if (usuario == null || usuario.getId() == 0) {
	    	return "Preencha o campo prestador";
	    }
		
    	ProjetoEscalaPrestador projetoEscalaPrestador = projetoEscalaPrestadorService.findByProjetoEscalaIdAndPrestadorIdAndExcluido(
    			solicitacao.getProjetoEscala().getId(),  
    			usuario.getId());
    	
    	if (projetoEscalaPrestador == null) {
	    	return "Prestador não pertence a esta escala";
    	}  
    	
    	ProjetoEscala escala = projetoEscalaPrestador.getProjetoEscala(); 
    	
    	String errorMessage = this.validaDataHoraSolicitacao(solicitacao, projetoEscalaPrestador, escala);
		if (!errorMessage.isEmpty()) {
			return errorMessage;
		}
		
		//solicitacao.setProjetoEscalaPrestador(projetoEscalaPrestador);
    	solicitacao.setUsuarioAprovacao(projetoEscalaPrestador.getProjetoEscala().getMonitor());
    	
    	if (solicitacao.getIndicarHorarioParaRepor()) {
    		//if (teveAlteracaoReposicoes) {
    		
	    		if (solicitacao.getAusenciaReposicoes() == null) {
	    			return "Preencha ao menos um horário de reposição";
	    		}

	    		if (solicitacao.getAusenciaReposicoes().stream().anyMatch(x->x.getProjetoEscalaTroca() != null && x.getProjetoEscalaTroca().getId() == solicitacao.getProjetoEscala().getId())) {
	    			return "O projeto/escala da reposição não pode ser o mesmo da solicitação";
	    		}

	    		if (solicitacao.getAusenciaReposicoes().stream().anyMatch(x->x.getUsuarioTroca() != null &&  x.getUsuarioTroca().getId() == solicitacao.getUsuario().getId())) {
	    			return "O prestador da reposição não pode ser o mesmo da solicitação";
	    		}
	    		
    			//solicitacao.setAusenciaReposicoes(solicitacaoEditada.getAusenciaReposicoes());
				
    			for (int i = 0; i < solicitacao.getAusenciaReposicoes().size();i++) {

					if (solicitacao.getId() > 0) {
						solicitacao.getAusenciaReposicoes().get(i).setAusenciaSolicitacao(solicitacao);
					}

    				AusenciaReposicao reposicao = solicitacao.getAusenciaReposicoes().get(i);

    				if (reposicao.getProjetoEscalaTroca().getId() == solicitacao.getProjetoEscala().getId()) {
    			    	return "Escala selecionada na troca não pode ser a mesma da solicitação";				
    				}
    				
    				if (projetoEscalaPrestadorService.findByProjetoEscalaIdAndPrestadorIdAndExcluido(
    						reposicao.getProjetoEscalaTroca().getId(),  
    		    			usuario.getId()) == null) {
    			    	return "Escala selecionada na troca não pertence ao prestador principal da solicitação";
    		    	}  
    				
    				solicitacao.getAusenciaReposicoes().get(i).setProjetoEscalaTroca(projetoEscalaService.findById(solicitacao.getAusenciaReposicoes().get(i).getProjetoEscalaTroca().getId()));
    				
    				if (reposicao.isIndicadoOutroUsuario()) {    					
    					if (reposicao.getUsuarioTroca() == null || reposicao.getUsuarioTroca().getId() == 0) {
    						return "Usuário troca não preenchido";
    					}
    					
    					if (solicitacao.getUsuario().getId() == reposicao.getUsuarioTroca().getId()) {
    						return "Usuário solicitação não pode ser o mesmo que o usuário troca";
    					}
    					
    					//reposicao.setUsuarioTroca(usuarioService.findByUsuarioId(reposicao.getUsuarioTroca().getId()));
    				}
    				else {
    					reposicao.setUsuarioTroca(null);
    				}
    			
    				ProjetoEscalaPrestador projetoEscalaPrestadorTroca = null;
    				ProjetoEscala projetoEscalaTroca = null;
					if (reposicao.isIndicadoOutroUsuario()) {
	    				projetoEscalaPrestadorTroca = projetoEscalaPrestadorService.findByProjetoEscalaIdAndPrestadorIdAndExcluido(
	    						reposicao.getProjetoEscalaTroca().getId(),  
	    						reposicao.getUsuarioTroca().getId());

	    		    	if (projetoEscalaPrestadorTroca == null) {
	    			    	return "Prestador troca não pertence a escala selecionada";
	    		    	}  
	    		    	
	    				projetoEscalaTroca = projetoEscalaPrestadorTroca.getProjetoEscala();
	    				projetoEscalaTroca.setProjeto(projetoEscalaPrestadorTroca.getProjeto());
					}
					else {
	    				projetoEscalaTroca = projetoEscalaService.findById(
	    						reposicao.getProjetoEscalaTroca().getId());
	    				
	    				if (projetoEscalaTroca.getProjeto() == null) {
	    					projetoEscalaTroca.setProjeto(projetoService.findById(projetoEscalaTroca.getProjetoId()));
	    				}
					}
					
			    	errorMessage = this.validaDataHoraReposicao(solicitacao, projetoEscalaPrestadorTroca, projetoEscalaTroca, reposicao); 
					if (!errorMessage.isEmpty()) {
						return errorMessage;
					}		
    				

    				if (reposicao.getAusenciaSolicitacao() != null && reposicao.getAusenciaSolicitacao().getId() == 0) {
    					reposicao.setAusenciaSolicitacao(null);
    				}		
    				
    				if(solicitacao.getAusenciaReposicoes().get(i).getId() < 1) {
    					solicitacao.getAusenciaReposicoes().get(i).setId(0);
    					break;
    				}
    				
    				//if (solicitacaoEditada.getAtivo() == 1) {
    					if (reposicao.isIndicadoOutroUsuario()) {
	    					solicitacao.getAusenciaReposicoes().get(i).setUsuarioTroca(projetoEscalaPrestadorTroca.getPrestador());
	    					solicitacao.getAusenciaReposicoes().get(i).setUsuarioAprovacao(projetoEscalaPrestadorTroca.getProjetoEscala().getMonitor());
	    					solicitacao.getAusenciaReposicoes().get(i).setGerenciaAprovacao(projetoEscalaPrestadorTroca.getProjeto().getGerente());
    					}
    					else {
	    					solicitacao.getAusenciaReposicoes().get(i).setUsuarioTroca(null);
	    					solicitacao.getAusenciaReposicoes().get(i).setUsuarioAprovacao(projetoEscalaTroca.getMonitor());
	    					solicitacao.getAusenciaReposicoes().get(i).setGerenciaAprovacao(projetoEscalaTroca.getProjeto().getGerente());    						
    					}
    					
    					solicitacao.getAusenciaReposicoes().get(i).setAceitoUsuarioAprovacao(0);
    					solicitacao.getAusenciaReposicoes().get(i).setAceitoUsuarioTroca(0);
    					solicitacao.getAusenciaReposicoes().get(i).setDataAceiteUsuarioAprovacao(null);
    					solicitacao.getAusenciaReposicoes().get(i).setDataAceiteUsuarioTroca(null);
    					solicitacao.getAusenciaReposicoes().get(i).setMotivoRecusaUsuarioTroca(null);
    					solicitacao.getAusenciaReposicoes().get(i).setMotivoRecusaUsuarioAprovacao(null);
    				//}
    			}
    		//}
    	}
    	else {
    		solicitacao.setAusenciaReposicoes(new ArrayList<AusenciaReposicao>());
    	}
    			
		if (solicitacao.getUsuario() != null && solicitacao.getUsuario().getId() == 0) {
			solicitacao.setUsuario(null);
		}
		//if (solicitacaoEditada.getAtivo() == 1) {
			solicitacao.setUsuarioAprovacao(projetoEscalaPrestador.getProjetoEscala().getMonitor());
			solicitacao.setGerenciaAprovacao(projetoEscalaPrestador.getProjeto().getGerente());
			solicitacao.setDataAceiteRecusa(null);
			solicitacao.setMotivoRecusa(null);
		//}
		

		AusenciaSolicitacao solicitacaoEditada = new AusenciaSolicitacao();
		if (solicitacao.getAusenciaReposicoes() != null && solicitacao.getAusenciaReposicoes().size() > 0) {
			solicitacaoEditada.setAusenciaReposicoes(solicitacao.getAusenciaReposicoes());
			solicitacao.setAusenciaReposicoes(null);
		}
			
		this.ausenciaSolicitacaoDao.save(solicitacao);
		if (solicitacaoEditada.getAusenciaReposicoes() != null && solicitacaoEditada.getAusenciaReposicoes().size() > 0) {
			solicitacao.setAusenciaReposicoes(solicitacaoEditada.getAusenciaReposicoes());
			for (int i = 0; i < solicitacao.getAusenciaReposicoes().size();i++) {
				AusenciaReposicao reposicao = solicitacao.getAusenciaReposicoes().get(i);
				reposicao.setAusenciaSolicitacao(solicitacao);
			}
			
			this.ausenciaSolicitacaoDao.save(solicitacao);
		}
		
		//solicitacaoEditada = solicitacao;
		
		return "";
	}

	private String validaDataHoraReposicao(AusenciaSolicitacao solicitacao,
			ProjetoEscalaPrestador projetoEscalaPrestadorTroca,
			ProjetoEscala projetoEscalaTroca,
			AusenciaReposicao reposicao) {
		
//		if (reposicao.getData() != null && 
//				reposicao.getData().isBefore(solicitacao.getDataInicio())) {
//			return "Data início da troca não pode ser menor do que a data inicio da solicitação";
//		}
//
//		if (reposicao.getData() != null && solicitacao.getDataFim() != null &&
//				reposicao.getData().isAfter(solicitacao.getDataFim())) {
//			return "Data fim da troca não pode ser maior do que a data fim da solicitação";
//		}
//	
		

		if (reposicao.isIndicadoOutroUsuario()) {
			
	    	if (projetoEscalaPrestadorTroca == null) {
		    	return "Prestador da troca nao não pertence a escala escolhida";
	    	}  
		}		
		
		if (reposicao.getData() != null && 
				reposicao.getData().isBefore(projetoEscalaTroca.getProjeto().getDataInicio() )) {
						//projetoEscalaPrestadorTroca == null ? projetoEscalaTroca.getProjeto().getDataInicio() : projetoEscalaPrestadorTroca.getDataInicio() )) {
			return "Data início da reposição não pode ser menor do que a data inicio do projeto";
		}

		LocalDate dataFim = projetoEscalaTroca.getProjeto().getDataFim(); // projetoEscalaPrestadorTroca.getDataInicio()  // projetoEscalaPrestadorTroca == null ? projetoEscalaTroca.getProjeto().getDataFim() : projetoEscalaPrestadorTroca.getDataFim();
		if (reposicao.getData() != null && dataFim != null &&
				reposicao.getData().isAfter(dataFim)) {
			return "Data fim da reposição não pode ser maior do que a data fim do projeto";
		}
		

		if (!Utilities.dataEstaEntreDiasDaSemana(
				reposicao.getData(),
				projetoEscalaTroca.getDiaSemanaDe().getId(), 
				projetoEscalaTroca.getDiaSemanaAte().getId())) {
			return "Dia da semana da reposição deve estar entra os dias da semana da escala do projeto";
		}
		

		if (reposicao.getHoraInicio() == "") {
			return "Nas reposições o campo 'Hora início' deve ser preenchido";
		}
		
		if (reposicao.getHoraFim() == "") {
			return "Nas reposições o campo 'Hora fim' deve ser preenchido";
		}

		if (!Utilities.validarHora(reposicao.getHoraInicio())) {
			return "Nas reposições o campo 'Hora início' esta inválido";
		}
		if (!Utilities.validarHora(reposicao.getHoraFim())) {
			return "Nas reposições o campo 'Hora fim' esta inválido";
		}
		    				
		if (Utilities.horaToInt(reposicao.getHoraInicio()) > Utilities.horaToInt(reposicao.getHoraFim())) {
			return "Nas reposições o campo 'Hora início' deve ser menor que a 'Hora fim'";
		}
		    				
		if (Utilities.horaToInt(reposicao.getHoraInicio()) < Utilities.horaToInt(projetoEscalaTroca.getHoraInicio())) {
			return "Nas reposições o campo 'Hora início' deve ser maior que a 'Hora início' da escala selecionada na troca";
		}
		
		if (Integer.parseInt(reposicao.getHoraFim().replace(":", "")) > Integer.parseInt(projetoEscalaTroca.getHoraFim().replace(":", ""))) {
			return "Nas reposições o campo 'Hora fim' deve ser menor que a 'Hora fim' da escala selecionada na troca";
		}

		
		if (Integer.parseInt(reposicao.getHoraInicio().replace(":", "")) < Integer.parseInt(projetoEscalaTroca.getHoraInicio().replace(":", ""))) {
			return "Nas reposições o campo 'Hora início' deve ser maior que a 'Hora início' da escala selecionada na troca";
		}
		
		if (Integer.parseInt(reposicao.getHoraFim().replace(":", "")) > Integer.parseInt(projetoEscalaTroca.getHoraFim().replace(":", ""))) {
			return "Nas reposições o campo 'Hora fim' deve ser menor que a 'Hora fim' da escala selecionada na troca";
		}
		
		return "";
	}

	private String validaDataHoraSolicitacao(AusenciaSolicitacao solicitacao,
			ProjetoEscalaPrestador projetoEscalaPrestador, ProjetoEscala escala) {
		if (solicitacao.getDataInicio() != null && solicitacao.getDataFim() != null &&
				solicitacao.getDataInicio().isAfter(solicitacao.getDataFim())) {
			return "Data início da solicitação não pode ser menor do que a data fim";
		}

		if (solicitacao.getDataInicio() != null && 
				solicitacao.getDataInicio().isBefore(projetoEscalaPrestador.getDataInicio())) {
			return "Data início da solicitação não pode ser menor do que a data inicio do prestador no projeto";
		}

		if (solicitacao.getDataFim() != null && projetoEscalaPrestador.getDataFim() != null &&
				solicitacao.getDataFim().isAfter(projetoEscalaPrestador.getDataFim())) {
			return "Data fim da solicitação não pode ser maior do que a data fim do prestador no projeto";
		}
		
		
		if (!Utilities.dataEstaEntreDiasDaSemana(
				solicitacao.getDataInicio(),
				projetoEscalaPrestador.getProjetoEscala().getDiaSemanaDe().getId(), 
				projetoEscalaPrestador.getProjetoEscala().getDiaSemanaAte().getId()) ||
			!Utilities.dataEstaEntreDiasDaSemana(
					solicitacao.getDataFim(),
					projetoEscalaPrestador.getProjetoEscala().getDiaSemanaDe().getId(), 
					projetoEscalaPrestador.getProjetoEscala().getDiaSemanaAte().getId())
				) {
			return "O dia da semana início/fim da solicitação deve estar entra os dias da semana da escala do projeto";
		}

		if (solicitacao.getHoraInicio() == "") {
			return "O campo 'Hora início' deve ser preenchido";
		}
		
		if (solicitacao.getHoraFim() == "") {
			return "O campo 'Hora fim' deve ser preenchido";
		}

		if (!Utilities.validarHora(solicitacao.getHoraInicio())) {
			return "O campo 'Hora início' esta inválido";
		}
		if (!Utilities.validarHora(solicitacao.getHoraFim())) {
			return "O campo 'Hora fim' esta inválido";
		}		
		
		if (Integer.parseInt(solicitacao.getHoraInicio().replace(":", "")) < Integer.parseInt(escala.getHoraInicio().replace(":", ""))) {
			return "Nas reposições o campo 'Hora início' deve ser maior que a 'Hora início' da escala selecionada na troca";
		}
		
		if (Integer.parseInt(solicitacao.getHoraFim().replace(":", "")) > Integer.parseInt(escala.getHoraFim().replace(":", ""))) {
			return "Nas reposições o campo 'Hora fim' deve ser menor que a 'Hora fim' da escala selecionada na troca";
		}
		
		return "";
	}

	public String delete(long ausenciaSolicitacaoId) {
		this.ausenciaSolicitacaoDao.deleteById(ausenciaSolicitacaoId);
		return "";
	}

	public AusenciaSolicitacao findById(long id) {
		return this.ausenciaSolicitacaoDao.findById(id).orElse(null);
	}

	public String aceita(
			long id,
			boolean aceita, 
			String motivo) {
		
		if (!aceita && motivo.isEmpty()) {
			return "Preencha o campo observação com o motivo da recusa";
		}
		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		boolean isAdmin = usuarioLogado.getFuncao().getId() == FuncaoEnum.administracao.getFuncao().getId();
		AusenciaSolicitacao item = this.findById(id);

		boolean permitido = true;
		
		if (item.getAtivo() == 1) {
			permitido = true;
		}
		else if (item.getAtivo() == 2) {
			if (!isAdmin) {
				permitido = false;
				if (item.getAceito() == 2 &&
					usuarioLogado.getId() == item.getUsuarioAprovacao().getId()) {
					permitido = true;
				} else if (item.getTipoAusencia() == 2) {
					List<AusenciaReposicao> reposicoes = item.getAusenciaReposicoes();
					for (AusenciaReposicao reposicao : reposicoes) {
						if ((reposicao.getAceitoUsuarioTroca() == 2 &&
							usuarioLogado.getId() == reposicao.getUsuarioTroca().getId()) ||
							(reposicao.getAceitoUsuarioAprovacao() == 2 &&
							(usuarioLogado.getId() == reposicao.getUsuarioAprovacao().getId() || usuarioLogado.getId() == reposicao.getGerenciaAprovacao().getId()))) {
							permitido = true;
							break;
						} 
						
					}	
				}
			}	
		}
		else {
			permitido = false;				
		}
		
		if (!permitido) {
			return "Não é permitido aceitar esta solicitação, a mesma " + (item.getAtivo() == 0 ? " não esta iniciada":  (item.getAtivo() == 2 ? " esta recusada": " esta finalizada"));
		}
		
		if (isAdmin || 
			usuarioLogado.getId() == item.getUsuarioAprovacao().getId() ||
			usuarioLogado.getId() == item.getGerenciaAprovacao().getId()) {
			if (item.getAceito() != 1) {

				item.setAceito(aceita ? 1 : 2);
				item.setMotivoRecusa((motivo.isEmpty() ? "" : "("+ usuarioLogado.getFuncao().getNome() +") " +  motivo));
				item.setDataAceiteRecusa(LocalDateTime.now());
			}
		}
		
		if (item.getTipoAusencia() == 2) {
			
			List<AusenciaReposicao> reposicoes = item.getAusenciaReposicoes().stream().filter(
					x->x.getUsuarioTroca() != null && x.getUsuarioTroca().getId() == usuarioLogado.getId()).collect(Collectors.toList());
			for (AusenciaReposicao reposicao : reposicoes) {
				if (isAdmin || 
					reposicao.getAceitoUsuarioTroca() != 1) {

					reposicao.setAceitoUsuarioTroca(aceita ? 1 : 2);
					reposicao.setMotivoRecusaUsuarioTroca((motivo.isEmpty() ? "" : motivo) +  "("+ usuarioLogado.getFuncao().getNome() +")");
					reposicao.setDataAceiteUsuarioTroca(LocalDateTime.now());
				}
			}

			List<AusenciaReposicao> reposicaoAprovacoes = item.getAusenciaReposicoes().stream().filter(
					x->x.getUsuarioAprovacao().getId() == usuarioLogado.getId() ||
					x.getGerenciaAprovacao().getId() == usuarioLogado.getId()).collect(Collectors.toList());
			for (AusenciaReposicao reposicao : reposicaoAprovacoes) {
				if (isAdmin || 
					reposicao.getAceitoUsuarioAprovacao() != 1) {

					reposicao.setAceitoUsuarioAprovacao(aceita ? 1 : 2);
					reposicao.setMotivoRecusaUsuarioAprovacao((motivo.isEmpty() ? "" : motivo) +  "("+ usuarioLogado.getFuncao().getNome() +")");
					reposicao.setDataAceiteUsuarioAprovacao(LocalDateTime.now());
				}
			}
		}
		
		if (!aceita) {
			item.setAtivo(2);
		}
		else {
			item.setAtivo(1);
		}
		

		boolean todosAceitos = 
				item.getAceito() == 1;
		
		if (item.getTipoAusencia() == 2) {
			for (AusenciaReposicao reposicao : item.getAusenciaReposicoes()) {
				if (reposicao.isIndicadoOutroUsuario() && 
					(reposicao.getAceitoUsuarioTroca() != 1 || 
					reposicao.getAceitoUsuarioAprovacao() != 1)) {
					todosAceitos = false;
					break;
				}

				if (!reposicao.isIndicadoOutroUsuario() && 
					reposicao.getAceitoUsuarioAprovacao() != 1) {
					todosAceitos = false;
					break;
				}
			}
		}
		
		if (todosAceitos) {
			item.setAtivo(3); // finalizado
		}
		
		this.ausenciaSolicitacaoDao.save(item);
		return "";
	}

	public List<AusenciaSolicitacao> findAllByProjetoId(int year, int mount, long projetoId, int aceito) {
		List<Long> ids = this.projetoEscalaService.findAllByProjetoId(projetoId).stream().map(x->x.getId()).collect(Collectors.toList());
		List<AusenciaSolicitacao> solicitacaoAusencias = new ArrayList<AusenciaSolicitacao>();
		for (Long id : ids) {
			solicitacaoAusencias.addAll(this.findAllByProjetoEscalaId(year, mount, id, aceito));
		}
		
		return solicitacaoAusencias;		
	}

	public List<AusenciaSolicitacao> findAllByProjetoEscalaId(int year, int mount, long projetoEscalaId, int aceito) { 
		// todo: usar query sql
		List<AusenciaSolicitacao> solicitacaoAusencias = Utilities.toList(ausenciaSolicitacaoDao.findAllByProjetoEscalaId(projetoEscalaId));
		return solicitacaoAusencias.stream().filter(x->
			x.getAceito() >= aceito && 
			x.getDataInicio().getYear() == year && 
			x.getDataInicio().getMonthValue() == mount).collect(Collectors.toList());		
	}
}
