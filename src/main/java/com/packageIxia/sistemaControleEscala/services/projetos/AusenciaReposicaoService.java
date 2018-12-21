package com.packageIxia.sistemaControleEscala.services.projetos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.AusenciaReposicaoDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IAusenciaReposicao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaReposicao;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class AusenciaReposicaoService implements IAusenciaReposicao {
	
	private AusenciaReposicaoDao ausenciaReposicaoDao;
	private IProjetoEscala projetoEscalaService;
	private IProjeto projetoService;
	private IProjetoEscalaPrestador projetoEscalaPrestadorService;

	@Autowired
	public AusenciaReposicaoService(
			AusenciaReposicaoDao ausenciaReposicaoDao,
			IProjetoEscala projetoEscalaService,
			IProjetoEscalaPrestador projetoEscalaPrestadorService,
			IProjeto projetoService) {
		this.ausenciaReposicaoDao = ausenciaReposicaoDao;
		this.projetoEscalaService = projetoEscalaService;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.projetoService = projetoService;
	}
	
	@Override
	public String save(AusenciaReposicao ausenciaReposicao) {
		this.ausenciaReposicaoDao.save(ausenciaReposicao);
		return "";
	}

	@Override
	public String delete(long ausenciaReposicaoId) {
		this.ausenciaReposicaoDao.deleteById(ausenciaReposicaoId);
		return "";
	}

	@Override
	public long preSaveReposicoes(AusenciaReposicao ausenciaReposicao, AusenciaSolicitacao solicitacaoEditada) {
			long id = ausenciaReposicao.getId();
			if (solicitacaoEditada.getAusenciaReposicoes() == null) {
				solicitacaoEditada.setAusenciaReposicoes(new ArrayList<AusenciaReposicao>());
			}
			
			if(ausenciaReposicao.getId() == 0) {
				id = solicitacaoEditada.getAusenciaReposicoes().size() == 0 ? 0 : solicitacaoEditada.getAusenciaReposicoes().stream().min(Comparator.comparing(AusenciaReposicao::getId)).get().getId();
				id = id > 0 ? -1 : id - 1; // atribui o menor id encontrado encontrado para adicionar este item quando salvar
				ausenciaReposicao.setId(id);
				
				// todo: remover depois que colocar join
				ProjetoEscala escala = projetoEscalaService.findById(ausenciaReposicao.getProjetoEscalaTroca().getId());
				escala.setProjeto(projetoService.findById(escala.getProjetoId(), true));
				ausenciaReposicao.setProjetoEscalaTroca(escala);
				
				solicitacaoEditada.getAusenciaReposicoes().add(ausenciaReposicao);
			}
			else {
				for (int i = 0; i < solicitacaoEditada.getAusenciaReposicoes().size();i++) {
					if(solicitacaoEditada.getAusenciaReposicoes().get(i).getId() == ausenciaReposicao.getId()) {
						solicitacaoEditada.getAusenciaReposicoes().get(i).setData(ausenciaReposicao.getData());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraInicio(ausenciaReposicao.getHoraInicio());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraFim(ausenciaReposicao.getHoraFim());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setProjetoEscalaTroca(this.projetoEscalaService.findById(ausenciaReposicao.getProjetoEscalaTroca().getId()));
						solicitacaoEditada.getAusenciaReposicoes().get(i).setUsuarioAprovacao(
							solicitacaoEditada.getAusenciaReposicoes().get(i).getProjetoEscalaTroca().getMonitor());
						solicitacaoEditada.getAusenciaReposicoes().get(i).setIndicadoOutroUsuario(ausenciaReposicao.isIndicadoOutroUsuario());
						
						if (solicitacaoEditada.getAusenciaReposicoes().get(i).isIndicadoOutroUsuario()) {
							solicitacaoEditada.getAusenciaReposicoes().get(i).setUsuarioTroca(ausenciaReposicao.getUsuarioTroca());
							solicitacaoEditada.getAusenciaReposicoes().get(i).setDataTroca(ausenciaReposicao.getDataTroca());
							solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraInicioTroca(ausenciaReposicao.getHoraInicioTroca());
							solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraFimTroca(ausenciaReposicao.getHoraFimTroca());
						}
						else {
							solicitacaoEditada.getAusenciaReposicoes().get(i).setUsuarioTroca(null);
							solicitacaoEditada.getAusenciaReposicoes().get(i).setDataTroca(null);
							solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraInicioTroca(null);
							solicitacaoEditada.getAusenciaReposicoes().get(i).setHoraFimTroca(null);
						}
						
						solicitacaoEditada.getAusenciaReposicoes().get(i).setObservacao(ausenciaReposicao.getObservacao());
						break;
					}
				}
			}
			return id;
		}

	@Override
	public List<AusenciaReposicao> findAllByProjetoEscalaId(int anoAtual, int mesAtual, long escalaId, int aceito) {
		// todo: usar query sql
		List<AusenciaReposicao> solicitacaoReposicoes = Utilities.toList(this.ausenciaReposicaoDao.findAllByProjetoEscalaTrocaId(escalaId));
		
		return solicitacaoReposicoes.stream().filter(x->
			x.getAusenciaSolicitacao().getAceito() >= aceito && 
			x.getData().getYear() == anoAtual && 
			x.getData().getMonthValue() == mesAtual).collect(Collectors.toList());
				
	}

	@Override
	public List<AusenciaReposicao> findAllByProjetoId(int anoAtual, int mesAtual, long projetoId, int aceito) {
		List<Long> ids = this.projetoEscalaService.findAllByProjetoId(projetoId).stream().map(x->x.getId()).collect(Collectors.toList());
		List<AusenciaReposicao> solicitacaoReposicoes = new ArrayList<AusenciaReposicao>();
		for (Long id : ids) {
			solicitacaoReposicoes.addAll(this.findAllByProjetoEscalaId(anoAtual, mesAtual, id, aceito));
		}
		
		return solicitacaoReposicoes;		
	}

	@Override
	public boolean existsByUsuarioTrocaId(long prestadorId) {
		return this.ausenciaReposicaoDao.existsByUsuarioTrocaId(prestadorId);
	}
	
	@Override
	public String validaReposicao(AusenciaSolicitacao solicitacao, Usuario usuario) {
		String errorMessage;
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

				ProjetoEscalaPrestador projetoEscalaPrestadorTroca = null;
				ProjetoEscala projetoEscalaTroca = null;
				
				if (reposicao.isIndicadoOutroUsuario()) {    					
					if (reposicao.getUsuarioTroca() == null || reposicao.getUsuarioTroca().getId() == 0) {
						return "Usuário troca não preenchido";
					}
					
					if (solicitacao.getUsuario().getId() == reposicao.getUsuarioTroca().getId()) {
						return "Usuário solicitação não pode ser o mesmo que o usuário troca";
					}

    				projetoEscalaPrestadorTroca = projetoEscalaPrestadorService.findByProjetoEscalaIdAndPrestadorIdAndExcluido(
    						reposicao.getProjetoEscalaTroca().getId(),  
    						reposicao.getUsuarioTroca().getId());

    		    	if (projetoEscalaPrestadorTroca == null) {
    			    	return "Prestador troca não pertence a escala selecionada";
    		    	}  
    		    	
    				projetoEscalaTroca = projetoEscalaPrestadorTroca.getProjetoEscala();
    				projetoEscalaTroca.setProjeto(projetoEscalaPrestadorTroca.getProjeto());
    				
					//reposicao.setUsuarioTroca(usuarioService.findByUsuarioId(reposicao.getUsuarioTroca().getId()));
				}
				else {
					reposicao.setUsuarioTroca(null);
					reposicao.setDataTroca(null);
					reposicao.setHoraInicioTroca(null);
					reposicao.setHoraFimTroca(null);    					

    				projetoEscalaTroca = projetoEscalaService.findById(
    						reposicao.getProjetoEscalaTroca().getId());
    				
    				if (projetoEscalaTroca.getProjeto() == null) {
    					projetoEscalaTroca.setProjeto(projetoService.findById(projetoEscalaTroca.getProjetoId(), true));
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
						// todo: remove
    					solicitacao.getAusenciaReposicoes().get(i).setUsuarioTroca(null);
    					solicitacao.getAusenciaReposicoes().get(i).setDataTroca(null);
    					solicitacao.getAusenciaReposicoes().get(i).setHoraInicioTroca(null);
    					solicitacao.getAusenciaReposicoes().get(i).setHoraFimTroca(null);
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
		
		return "";
	}

	private String validaDataHoraReposicao(
			AusenciaSolicitacao solicitacao,
			ProjetoEscalaPrestador projetoEscalaPrestadorTroca,
			ProjetoEscala projetoEscalaTroca,
			AusenciaReposicao reposicao) {
		
		if (reposicao.isIndicadoOutroUsuario()) {
			
	    	if (projetoEscalaPrestadorTroca == null) {
		    	return "Prestador da troca não pertence a escala escolhida";
	    	}  
	    	
			if (reposicao.getDataTroca() != null && 
			reposicao.getDataTroca().isBefore(solicitacao.getDataInicio())) {
				return "Data início da troca não pode ser menor do que a data inicio da solicitação";
			}
		
			if (reposicao.getDataTroca() != null && solicitacao.getDataFim() != null &&
					reposicao.getDataTroca().isAfter(solicitacao.getDataFim())) {
				return "Data fim da troca não pode ser maior do que a data fim da solicitação";
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
		
		if (Utilities.horaToInt(reposicao.getHoraFim()) > Integer.parseInt(projetoEscalaTroca.getHoraFim().replace(":", ""))) {
			return "Nas reposições o campo 'Hora fim' deve ser menor que a 'Hora fim' da escala selecionada na troca";
		}

		
		if (Utilities.horaToInt(reposicao.getHoraInicio()) < Integer.parseInt(projetoEscalaTroca.getHoraInicio().replace(":", ""))) {
			return "Nas reposições o campo 'Hora início' deve ser maior que a 'Hora início' da escala selecionada na troca";
		}
		
		if (Utilities.horaToInt(reposicao.getHoraFim()) > Integer.parseInt(projetoEscalaTroca.getHoraFim().replace(":", ""))) {
			return "Nas reposições o campo 'Hora fim' deve ser menor que a 'Hora fim' da escala selecionada na troca";
		}
		

		if (reposicao.isIndicadoOutroUsuario()) {
			
			if (reposicao.getHoraInicioTroca() == "") {
				return "Nas reposições o campo 'Hora início troca' deve ser preenchido";
			}
			
			if (reposicao.getHoraFimTroca() == "") {
				return "Nas reposições o campo 'Hora fim troca' deve ser preenchido";
			}

			if (!Utilities.validarHora(reposicao.getHoraInicioTroca())) {
				return "Nas reposições o campo 'Hora início troca' esta inválido";
			}
			if (!Utilities.validarHora(reposicao.getHoraFimTroca())) {
				return "Nas reposições o campo 'Hora fim troca' esta inválido";
			}
			
			    				
			if (Utilities.horaToInt(reposicao.getHoraInicioTroca()) > Utilities.horaToInt(reposicao.getHoraFimTroca())) {
				return "Nas reposições o campo 'Hora início troca' deve ser menor que a 'Hora fim'";
			}
			    				
			if (Utilities.horaToInt(reposicao.getHoraInicioTroca()) < Utilities.horaToInt(projetoEscalaTroca.getHoraInicio())) {
				return "Nas reposições o campo 'Hora início troca' deve ser maior que a 'Hora início' da escala selecionada na troca";
			}
			
			if (Utilities.horaToInt(reposicao.getHoraFimTroca()) > Utilities.horaToInt(projetoEscalaTroca.getHoraFim())) {
				return "Nas reposições o campo 'Hora fim troca' deve ser menor que a 'Hora fim' da escala selecionada na troca";
			}

			
			if (Utilities.horaToInt(reposicao.getHoraInicioTroca()) < Utilities.horaToInt(projetoEscalaTroca.getHoraInicio())) {
				return "Nas reposições o campo 'Hora início troca' deve ser maior que a 'Hora início' da escala selecionada na troca";
			}
			
			if (Utilities.horaToInt(reposicao.getHoraFimTroca()) > Utilities.horaToInt(projetoEscalaTroca.getHoraFim())) {
				return "Nas reposições o campo 'Hora fim troca' deve ser menor que a 'Hora fim' da escala selecionada na troca";
			}			


			
			if (Utilities.horaToInt(reposicao.getHoraInicioTroca()) < Utilities.horaToInt(solicitacao.getHoraInicio())) {
				return "Nas reposições o campo 'Hora início troca' deve ser maior ou igual que a 'Hora início' da solicitação";
			}
			
			if (Utilities.horaToInt(reposicao.getHoraFimTroca()) > Utilities.horaToInt(solicitacao.getHoraFim())) {
				return "Nas reposições o campo 'Hora fim troca' deve ser menor ou igual que a 'Hora fim' da solicitação";
			}
		}	
		
		return "";
	}
}