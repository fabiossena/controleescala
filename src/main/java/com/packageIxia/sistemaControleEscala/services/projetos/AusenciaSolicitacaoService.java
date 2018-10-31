package com.packageIxia.sistemaControleEscala.services.projetos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.packageIxia.sistemaControleEscala.daos.projeto.AusenciaReposicaoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.AusenciaSolicitacaoDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IAusenciaReposicao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IAusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.INotificacao;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaReposicao;
import com.packageIxia.sistemaControleEscala.models.projeto.AusenciaSolicitacao;
import com.packageIxia.sistemaControleEscala.models.projeto.DadosAcessoSolicitacaoAusencia;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class AusenciaSolicitacaoService implements IAusenciaSolicitacao {

	private AusenciaSolicitacaoDao ausenciaSolicitacaoDao;
	private AusenciaReposicaoDao ausenciaReposicaoDao;
	private HttpSession session;
	private IProjetoEscalaPrestador projetoEscalaPrestadorService;
	private IProjeto projetoService;
	private IProjetoEscala projetoEscalaService;
	private INotificacao notificacaoService;
	private IAusenciaReposicao ausenciaReposicaoService;
	
	@Autowired
	public AusenciaSolicitacaoService(
			IProjetoEscalaPrestador projetoEscalaPrestadorService,
			IProjetoEscala projetoEscalaService,
			IProjeto projetoService,
			AusenciaSolicitacaoDao ausenciaSolicitacaoDao,
			AusenciaReposicaoDao ausenciaReposicaoDao,
			INotificacao notificacaoService,
			IAusenciaReposicao ausenciaReposicaoService, 
			HttpSession session) {
		this.ausenciaSolicitacaoDao = ausenciaSolicitacaoDao;
		this.ausenciaReposicaoDao = ausenciaReposicaoDao;
		this.session = session;
		this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
		this.projetoEscalaService = projetoEscalaService;
		this.projetoService = projetoService;
		this.notificacaoService = notificacaoService;
		this.ausenciaReposicaoService = ausenciaReposicaoService;
	}
	
	@Override
	public List<AusenciaSolicitacao> findAll() {
		return this.findAll(false);
	}
	
	@Override
	public List<AusenciaSolicitacao> findAll(boolean isSomenteComAcesso) {

		List<AusenciaSolicitacao> ausencias = new ArrayList<AusenciaSolicitacao>();
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		
		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId()) {
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
	
	@Override
	@Transactional
	public String save(AusenciaSolicitacao solicitacao) {		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId()) {
			solicitacao.setUsuario(usuarioLogado);
		}
		
    	Usuario usuario = usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() ? solicitacao.getUsuario() : usuarioLogado;
		if (usuario == null || usuario.getId() == 0) {
	    	return "Preencha o campo prestador";
	    }
		
		if (solicitacao.getProjetoEscala() == null || solicitacao.getProjetoEscala().getId() == 0) {
	    	return "Preencha o campo escala";
	    }
		
    	ProjetoEscalaPrestador projetoEscalaPrestador = projetoEscalaPrestadorService.findByProjetoEscalaIdAndPrestadorIdAndExcluido(
    			solicitacao.getProjetoEscala().getId(),  
    			usuario.getId());
    	
    	if (projetoEscalaPrestador == null) {
	    	return "Prestador não pertence a esta escala";
    	}  
    	
    	ProjetoEscala escala = projetoEscalaPrestador.getProjetoEscala(); 

		if (solicitacao.getTipoAusencia() == 2 && (solicitacao.getAusenciaReposicoes() == null || solicitacao.getAusenciaReposicoes().size() == 0)) {
			solicitacao.setTipoAusencia(0);
		}
		
    	String errorMessage = this.validaDataHoraSolicitacao(solicitacao, projetoEscalaPrestador, escala);
		if (!errorMessage.isEmpty()) {
			return errorMessage;
		}
		
		//solicitacao.setProjetoEscalaPrestador(projetoEscalaPrestador);
    	solicitacao.setUsuarioAprovacao(projetoEscalaPrestador.getProjetoEscala().getMonitor());
    	
    	this.ausenciaReposicaoService.validaReposicao(solicitacao, usuario);
    			
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
			
		//this.ausenciaSolicitacaoDao.save(solicitacao);
		if (solicitacaoEditada.getAusenciaReposicoes() != null && solicitacaoEditada.getAusenciaReposicoes().size() > 0) {
			solicitacao.setAusenciaReposicoes(solicitacaoEditada.getAusenciaReposicoes());
			for (int i = 0; i < solicitacao.getAusenciaReposicoes().size();i++) {
				AusenciaReposicao reposicao = solicitacao.getAusenciaReposicoes().get(i);
				reposicao.setId(0);
				reposicao.setAusenciaSolicitacao(solicitacao);
			}
		}
		
		this.ausenciaSolicitacaoDao.save(solicitacao);
		
		//solicitacaoEditada = solicitacao;
		
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
		
		
		if (!projetoEscalaPrestador.getHasDiasHorasTrabalho() &&
			(!Utilities.dataEstaEntreDiasDaSemana(
				solicitacao.getDataInicio(),
				projetoEscalaPrestador.getProjetoEscala().getDiaSemanaDe().getId(), 
				projetoEscalaPrestador.getProjetoEscala().getDiaSemanaAte().getId()) ||
			!Utilities.dataEstaEntreDiasDaSemana(
					solicitacao.getDataFim(),
					projetoEscalaPrestador.getProjetoEscala().getDiaSemanaDe().getId(), 
					projetoEscalaPrestador.getProjetoEscala().getDiaSemanaAte().getId())) ) {
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

	@Override
	public String delete(long ausenciaSolicitacaoId) {
		this.ausenciaSolicitacaoDao.deleteById(ausenciaSolicitacaoId);
		return "";
	}

	@Override
	public AusenciaSolicitacao findById(long id) {
		return this.ausenciaSolicitacaoDao.findById(id).orElse(null);
	}

	@Override
	public String aceita(
			long id,
			boolean aceita, 
			String motivo) {
		
		if (!aceita && motivo.isEmpty()) {
			return "Preencha o campo observação com o motivo da recusa";
		}
		
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		boolean isAdmin = usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId();
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
			this.notificacaoService.save(new Notificacao(3, "Sua solicitação de ausência foi ''recusada''! Por favor verifique e caso necessário realize os ajustes.", "Solicitação de ausência", item.getUsuario()));
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
			this.notificacaoService.save(new Notificacao(1, "Sua solicitação de ausência foi aceita!", "Solicitação de ausência", item.getUsuario()));
		}
		
		this.ausenciaSolicitacaoDao.save(item);
		return "";
	}

	@Override
	public List<AusenciaSolicitacao> findAllByProjetoId(int year, int mount, long projetoId, int aceito) {
		List<Long> ids = this.projetoEscalaService.findAllByProjetoId(projetoId).stream().map(x->x.getId()).collect(Collectors.toList());
		List<AusenciaSolicitacao> solicitacaoAusencias = new ArrayList<AusenciaSolicitacao>();
		for (Long id : ids) {
			solicitacaoAusencias.addAll(this.findAllByProjetoEscalaId(year, mount, id, aceito));
		}
		
		return solicitacaoAusencias;		
	}

	@Override
	public List<AusenciaSolicitacao> findAllByProjetoEscalaId(int year, int mount, long projetoEscalaId, int aceito) { 
		// todo: usar query sql
		List<AusenciaSolicitacao> solicitacaoAusencias = Utilities.toList(ausenciaSolicitacaoDao.findAllByProjetoEscalaId(projetoEscalaId));
		return solicitacaoAusencias.stream().filter(x->
			x.getAceito() >= aceito && 
			x.getDataInicio().getYear() == year && 
			x.getDataInicio().getMonthValue() == mount).collect(Collectors.toList());		
	}

	@Override
	public boolean existsByUsuarioId(long prestadorId) {
		return this.ausenciaSolicitacaoDao.existsByUsuarioId(prestadorId);
	}
}