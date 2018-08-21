package com.packageIxia.SistemaControleEscala.Services.Projetos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.SistemaControleEscala.Daos.ProjetoEscalaPrestadorDao;
import com.packageIxia.SistemaControleEscala.Helper.Utilities;
import com.packageIxia.SistemaControleEscala.Models.Projeto.Projeto;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscala;
import com.packageIxia.SistemaControleEscala.Models.Projeto.ProjetoEscalaPrestador;
import com.packageIxia.SistemaControleEscala.Models.Referencias.FuncaoEnum;
import com.packageIxia.SistemaControleEscala.Models.Usuario.Usuario;

@Service
public class ProjetoEscalaPrestadorService {

	private ProjetoEscalaPrestadorDao projetoEscalaPrestadorDao;
	private ProjetoFolgaSemanalService projetoFolgaSemanalService;
	private ProjetoService projetoService;

	@Autowired
	public ProjetoEscalaPrestadorService(
			ProjetoEscalaPrestadorDao projetoEscalaPrestadorDao,
			ProjetoFolgaSemanalService projetoFolgaSemanalService,
			ProjetoService projetoService) {
		this.projetoEscalaPrestadorDao = projetoEscalaPrestadorDao;
		this.projetoFolgaSemanalService = projetoFolgaSemanalService;
		this.projetoService = projetoService;
	}
	
	public List<Usuario> findAllPrestadoresByProjetoEscalaId(long projetoEscalaId) {
		if (projetoEscalaId == 0) {
			return new ArrayList<Usuario>();
		}
		
		return projetoEscalaPrestadorDao.findAllByProjetoEscalaIdAndExcluido(projetoEscalaId, false)
				.stream().map(x->x.getPrestador()).collect(Collectors.toList());
	}


	public List<ProjetoEscalaPrestador> findAllByProjetoEscalaId(long projetoEscalaId) {
		if (projetoEscalaId == 0) {
			return new ArrayList<ProjetoEscalaPrestador>();
		}
		
		return projetoEscalaPrestadorDao.findAllByProjetoEscalaIdAndExcluido(projetoEscalaId, false);
	}
	
	public List<Usuario> findAllByProjetoEscalaIdExceptUsuarioId(long projetoEscalaId, long usuarioId) {
		if (projetoEscalaId == 0) {
			return new ArrayList<Usuario>();
		}
		
		return projetoEscalaPrestadorDao.findAllByProjetoEscalaIdAndExcluido(projetoEscalaId, false)
				.stream().filter(x->usuarioId == 0 || x.getPrestador().getId() != usuarioId).map(x->x.getPrestador()).collect(Collectors.toList());
	}
	
	public ProjetoEscalaPrestador findByProjetoEscalaIdAndPrestadorIdAndExcluido(long projetoEscalaId, long prestadorId) {
		if (projetoEscalaId == 0 || prestadorId == 0) {
			return new ProjetoEscalaPrestador();
		}
		
		return projetoEscalaPrestadorDao.findByProjetoEscalaIdAndPrestadorIdAndExcluido(projetoEscalaId, prestadorId, false);
	}
	
	public ProjetoEscalaPrestador findById(long id) {
		return projetoEscalaPrestadorDao.findByIdAndExcluido(id, false);
	}
	
	public String save(ProjetoEscalaPrestador prestador) {
		Projeto projeto = this.projetoService.findById(prestador.getProjeto().getId());
		
		if (prestador.getDataInicio() != null && prestador.getDataFim() != null &&
				prestador.getDataInicio().isAfter(prestador.getDataFim())) {
			return "Data início do prestador não pode ser menor do que a data fim";
		}
		
		if (prestador.getDataInicio().isBefore(projeto.getDataInicio())) {
			return "Data início do prestador não pode ser menor do que a data início do projeto";
		}
		
		if (prestador.getDataFim() != null && projeto.getDataFim() != null &&
				prestador.getDataFim().isAfter(projeto.getDataFim())) {
			return "Data fim do prestador não pode ser maior do que a data fim do projeto";
		}
		
		List<ProjetoEscalaPrestador> prestadores = this.findAllByPrestadorIdAndProjetoId(prestador.getPrestador().getId(), projeto.getId());
		if (prestadores != null && !prestadores.isEmpty() && prestador.getId() == -1) {
			return "Prestador já cadastrado para este projeto";
		}
		
		ProjetoEscalaPrestador prestadorSalvo = this.projetoEscalaPrestadorDao.save(prestador);
		prestador.setId(prestadorSalvo.getId());
		return "";
	}

	private List<ProjetoEscalaPrestador> findAllByPrestadorIdAndProjetoId(long prestadorId, long projetoId) {
		return this.projetoEscalaPrestadorDao.findAllByPrestadorIdAndProjetoIdAndExcluido(prestadorId, projetoId, false);
	}

	public String delete(long prestadorId) {		
		this.projetoEscalaPrestadorDao.deleteById(prestadorId);

//		if (hasRelacionamento) {
//			ProjetoEscalaPrestador prestador = this.findById(prestadorId);
//			prestador.setExcluido(true);
//			this.projetoEscalaPrestadorDao.save(prestador);	
//		}
		return "";
	}

//	public List<ProjetoEscalaPrestador> findAllProjetosByPrestadorId(long id, boolean trazerInformacaoFolga, boolean trazerInformacaoStatus, boolean trazerStatusReal) {
//		return this.findAllProjetosByPrestadorId(id, trazerInformacaoFolga, trazerInformacaoStatus, trazerStatusReal, false, false, false, false);
//	}

	public List<ProjetoEscalaPrestador> findAllProjetosByPrestadorId(long id, boolean trazerInformacaoFolga, boolean trazerInformacaoStatus, boolean trazerStatusReal, boolean trazerSomenteAtivos) {
		return this.findAllProjetosByPrestadorId(id, trazerInformacaoFolga, trazerInformacaoStatus, trazerStatusReal, trazerSomenteAtivos, false, false, false);
	}
	
	public List<ProjetoEscalaPrestador> findAllProjetosByPrestadorId(long id, boolean trazerInformacaoFolga, boolean trazerInformacaoStatus, boolean trazerStatusReal, boolean trazerSomenteAtivos, boolean trazerSomentePendentes, boolean trazerSomenteDataAtiva, boolean trazerSomenteInformacoesBasicas) {
		if (id == 0) {
			return new ArrayList<ProjetoEscalaPrestador>();
		}
		
		List<ProjetoEscalaPrestador> projetosCadastrados = Utilities.toList(this.projetoEscalaPrestadorDao.findAllByPrestadorIdAndExcluido(id, false));

		projetosCadastrados = trazerFiltrosAndObservacoesAdicionais(trazerInformacaoFolga, trazerInformacaoStatus,
				trazerStatusReal, trazerSomenteAtivos, trazerSomentePendentes, trazerSomenteDataAtiva, trazerSomenteInformacoesBasicas, 
				projetosCadastrados);
				
		return projetosCadastrados;
		
	}

	private List<ProjetoEscalaPrestador> trazerFiltrosAndObservacoesAdicionais(boolean trazerInformacaoFolga,
			boolean trazerInformacaoStatus, boolean trazerStatusReal, boolean trazerSomenteAtivos,
			boolean trazerSomentePendentes, boolean trazerSomenteDataAtiva, boolean trazerSomenteInformacoesBasicas,
			List<ProjetoEscalaPrestador> projetosCadastrados) {
		if (trazerSomenteAtivos) {
			projetosCadastrados =
					projetosCadastrados.stream().filter(x->
						x.isAtivo() && 
						x.getProjetoEscala().isAtivo() &&
						x.getProjeto().isAtivo()).collect(Collectors.toList());
		}

		if (trazerSomenteDataAtiva) {	
			projetosCadastrados =
					projetosCadastrados.stream().filter(x->
						x.getDataInicio().isAfter(LocalDate.now()) &&
						(x.getDataFim() == null || x.getDataFim().isBefore(LocalDate.now()))).collect(Collectors.toList());
		}

		if (trazerSomentePendentes) {
			projetosCadastrados = projetosCadastrados.stream().filter(x-> x.getAceito() == 0).collect(Collectors.toList());
		}
		
		if (trazerInformacaoFolga || trazerInformacaoStatus || trazerStatusReal) {
			for (ProjetoEscalaPrestador item : projetosCadastrados) {
				String informacaoAdicional = "";
				
				if (trazerInformacaoStatus) {
					informacaoAdicional = !item.isAtivo() ? "<br>Usuário desativado neste projeto." : "";
					informacaoAdicional += !item.getProjetoEscala().isAtivo() ? "<br>Escala desativada." : "";
					informacaoAdicional += !item.getProjeto().isAtivo() ? "<br>Projeto desativado." : "";
					
					if (item.isAtivo() &&
						item.getProjetoEscala().isAtivo() &&
						item.getProjeto().isAtivo()) {
						informacaoAdicional +=  item.getAceito() == 0 ? "<br><br><i>Projeto aguardando aceite do prestador.</i>" : "";
					}
					
					informacaoAdicional +=  item.getAceito() == 2 ? "<br><br><i>Projeto recusado pelo prestador.</i>" : "";
					
					if (!trazerSomenteInformacoesBasicas) {
						informacaoAdicional +=  item.getAceito() == 1 ? "<br><br><i>Projeto aceito pelo prestador.</i>" : "";
					}
				}
				
				String observacaoPrestador = item.getObservacaoPrestador();
				String folgaSemanal = "";
				if (trazerInformacaoFolga) {
					folgaSemanal = this.projetoFolgaSemanalService.findEscalaFolgaSemanal(item.getId());
				}
				
				item.setObservacaoPrestador(
						(Strings.isBlank(observacaoPrestador) ? folgaSemanal : 
						(Strings.isBlank(folgaSemanal) ? "" :  "Observação projeto: " + observacaoPrestador + "<br>" + folgaSemanal)) +
						informacaoAdicional);

				if (trazerStatusReal) {	
					item.setAtivo(
							item.isAtivo() && 
							item.getProjetoEscala().isAtivo() &&
							item.getProjeto().isAtivo()
						);
				}

				boolean removeTags = true;
				while (removeTags) {				
					if (item.getObservacaoPrestador().trim().length() > 4 &&
							item.getObservacaoPrestador().trim().startsWith("<br>")) {
						item.setObservacaoPrestador(item.getObservacaoPrestador().trim().substring(4, item.getObservacaoPrestador().trim().length()));					
					} else if (item.getObservacaoPrestador().trim().length() > 4 &&
							item.getObservacaoPrestador().trim().endsWith("<br>")) {
						item.setObservacaoPrestador(item.getObservacaoPrestador().trim().substring(0, item.getObservacaoPrestador().trim().length()-4));					
					}
					else {
						removeTags = false;
					}
				}
			}
		}
		return projetosCadastrados;
	}

	public String aceitePrestador(Usuario usuarioLogado, long projetoEscalaPrestadorId, int statusAceite, String motivo) {
		ProjetoEscalaPrestador projetoEscalaPrestador = this.findById(projetoEscalaPrestadorId);
		if (projetoEscalaPrestador.getPrestador().getId() != usuarioLogado.getId() && 
			usuarioLogado.getFuncaoId() == FuncaoEnum.administracao.funcao.getId() && 
			usuarioLogado.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId()) {
			return "Operação não permitida para este usuário";
		}
		
		if (statusAceite == 2 && motivo == "") {
			return "Preencha o campo motivo recusa";
		}
		
		projetoEscalaPrestador.setAceito(statusAceite);
		projetoEscalaPrestador.setMotivoRecusa(motivo);
		
		return save(projetoEscalaPrestador);
	}

	public boolean existsByPrestadorId(long prestadorId) {
		return this.projetoEscalaPrestadorDao.existsByPrestadorId(prestadorId);
	}

	public boolean existsByProjetoEscalaId(long projetoEscalaId) {
		return this.projetoEscalaPrestadorDao.existsByProjetoEscalaId(projetoEscalaId);
	}

	public List<ProjetoEscalaPrestador> findAllByProjetoId(long projetoId) {
		List<ProjetoEscalaPrestador> projetosCadastrados = Utilities.toList(this.projetoEscalaPrestadorDao.findAllByProjetoId(projetoId));
		return this.trazerFiltrosAndObservacoesAdicionais(false, true, false, false, false, false, true, projetosCadastrados);
	}

	public List<ProjetoEscalaPrestador> findAllByPrestadorId(long prestadorId) {
		List<ProjetoEscalaPrestador> prestadores =  this.projetoEscalaPrestadorDao.findAllByPrestadorIdAndExcluidoAndAtivo(prestadorId, false, true);
		
		return prestadores.stream()
				.filter(x->x.getProjeto().isAtivo() && !x.isExcluido())
				.filter(x->x.getProjetoEscala().isAtivo() && !x.isExcluido()).distinct()
				.collect(Collectors.toList());
	}
}
