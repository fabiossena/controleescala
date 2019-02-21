package com.packageIxia.sistemaControleEscala.services.projetos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packageIxia.sistemaControleEscala.daos.projeto.AusenciaReposicaoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.AusenciaSolicitacaoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.HoraAprovacaoDao;
import com.packageIxia.sistemaControleEscala.daos.projeto.ProjetoEscalaPrestadorDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoFolgaSemanal;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.INotificacao;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestadorDiaHoraTrabalho;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class ProjetoEscalaPrestadorService implements IProjetoEscalaPrestador {

	private ProjetoEscalaPrestadorDao projetoEscalaPrestadorDao;
	private IProjetoFolgaSemanal projetoFolgaSemanalService;
	private IProjeto projetoService;
	private AusenciaSolicitacaoDao ausenciaSolicitacaoDao;
	private AusenciaReposicaoDao ausenciaReposicaoDao;
	private HoraAprovacaoDao horaAprovacaoDao;
	private INotificacao notificacao;

	@Autowired
	public ProjetoEscalaPrestadorService(
			ProjetoEscalaPrestadorDao projetoEscalaPrestadorDao,
			IProjetoFolgaSemanal projetoFolgaSemanalService,
			IProjeto projetoService,
			AusenciaSolicitacaoDao ausenciaSolicitacaoDao,
			AusenciaReposicaoDao ausenciaReposicaoDao,
			HoraAprovacaoDao  horaAprovacaoDao,
			INotificacao notificacao) {
		this.projetoEscalaPrestadorDao = projetoEscalaPrestadorDao;
		this.projetoFolgaSemanalService = projetoFolgaSemanalService;
		this.projetoService = projetoService;
		this.ausenciaSolicitacaoDao = ausenciaSolicitacaoDao;
		this.ausenciaReposicaoDao = ausenciaReposicaoDao;
		this.horaAprovacaoDao = horaAprovacaoDao;
		this.notificacao = notificacao;
	}
	
	@Override
	public List<Usuario> findAllPrestadoresByProjetoEscalaId(long projetoEscalaId) {
		if (projetoEscalaId == 0) {
			return new ArrayList<Usuario>();
		}
		
		return projetoEscalaPrestadorDao.findAllByProjetoEscalaIdAndExcluido(projetoEscalaId, false)
				.stream().map(x->x.getPrestador()).collect(Collectors.toList());
	}

	@Override
	public List<ProjetoEscalaPrestador> findAllByProjetoEscalaId(long projetoEscalaId) throws IOException {
		if (projetoEscalaId == 0) {
			return new ArrayList<ProjetoEscalaPrestador>();
		}
		
		List<ProjetoEscalaPrestador> projetoEscalaPrestadores = projetoEscalaPrestadorDao.findAllByProjetoEscalaIdAndExcluido(projetoEscalaId, false);
		for (ProjetoEscalaPrestador projetoEscalaPrestador : projetoEscalaPrestadores) {
						
			//projetoEscalaPrestador.setDiasHorasTrabalhoDataView(new ProjetoEscalaPrestadorDiasHorasTrabalhoDataView(projetoEscalaPrestador.getProjetoEscalaPrestadorDiasHorasTrabalho(), projetoEscalaPrestador.getProjetoEscala()));
			
			String json = this.convertoToJson(projetoEscalaPrestador.getDiasHorasTrabalho());	
			projetoEscalaPrestador.setJsonDiasHorasTrabalho(json);
		}
		
		return projetoEscalaPrestadores;
	}

	public String convertoToJson(List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasTrabalho) throws IOException {
		String json ="";
		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(diasHorasTrabalho);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	public List<Usuario> findAllByProjetoEscalaIdExceptUsuarioId(long projetoEscalaId, long usuarioId) {
		if (projetoEscalaId == 0) {
			return new ArrayList<Usuario>();
		}
		
		List<ProjetoEscalaPrestador> projetoPrestadores = projetoEscalaPrestadorDao.findAllByProjetoEscalaIdAndExcluido(projetoEscalaId, false);
		List<Usuario> usuarios = projetoPrestadores.stream()
				.filter(x->usuarioId == 0 || x.getPrestador().getId() != usuarioId)
				.map(x->x.getPrestador()).distinct()
				.collect(Collectors.toList());
		
		return usuarios;
	}
	
	@Override
	public ProjetoEscalaPrestador findByProjetoEscalaIdAndPrestadorIdAndExcluido(long projetoEscalaId, long prestadorId) {
		if (projetoEscalaId == 0 || prestadorId == 0) {
			return new ProjetoEscalaPrestador();
		}
		
		return projetoEscalaPrestadorDao.findByProjetoEscalaIdAndPrestadorIdAndExcluido(projetoEscalaId, prestadorId, false);
	}
	
	@Override
	public ProjetoEscalaPrestador findById(long id) {
		return projetoEscalaPrestadorDao.findByIdAndExcluido(id, false);
	}
	
	@Override
	public String save(ProjetoEscalaPrestador prestador) {
		Projeto projeto = this.projetoService.findById(prestador.getProjeto().getId(), true);
		
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
		
		List<ProjetoEscalaPrestador> prestadores = this.findAllByPrestadorIdAndId(prestador.getId(), prestador.getPrestador().getId());
		if (prestadores != null && !prestadores.isEmpty() && prestador.getId() == -1) {
			return "Prestador já cadastrado para esta escala";
		}
		
		if (!prestador.getRamalIntegracaoRobo().trim().isEmpty()) {
			List<ProjetoEscalaPrestador> prest = this.findAllByRamalIntegracaoRobo(prestador.getRamalIntegracaoRobo().trim());
			if (prest != null && !prest.isEmpty() && prest.get(0).getId() != prestador.getId()) {
				return "Ramal de integração com o robô já cadastrado em outro prestador";
			}
		}

    	String message = this.getAtualizacoesPrestadorDiasHoras(prestador);
		if (message != "") {
			return message;
		}

		prestador.setAceito(1);
    	/*if (prestador.isReenviarConvite()) {
    		prestador.setAceito(1);
    	}*/
		
		ProjetoEscalaPrestador prestadorBase = prestador.getId() == 0 ? null : this.findById(prestador.getId());
    	boolean novo = prestador.getId() == 0 || (prestadorBase != null && prestadorBase.getPrestador().getId() != prestador.getId());
    	
		ProjetoEscalaPrestador prestadorSalvo = this.projetoEscalaPrestadorDao.save(prestador);
		
		if (novo) {
			notificacao.save(new Notificacao(1, "Você foi cadastrado em um novo projeto.", "Projetos", prestadorSalvo.getPrestador()));
		}
		
		prestador.setId(prestadorSalvo.getId());
		return "";
	}

	private String  getAtualizacoesPrestadorDiasHoras(ProjetoEscalaPrestador prestador) {
			//List<ProjetoEscalaPrestadorDiaHoraTrabalho> apagar, List<ProjetoEscalaPrestadorDiaHoraTrabalho> atualizar

    	if (prestador.getDiasHorasTrabalho() == null || 
			prestador.getDiasHorasTrabalho().size() == 0 ||
			prestador.getDiasHorasTrabalho().stream().allMatch(x->x.getHoraInicio().equals("") && x.getHoraFim().equals(""))) {
    		
			prestador.setProjetoEscalaPrestadorDiasHorasTrabalho(new ArrayList<ProjetoEscalaPrestadorDiaHoraTrabalho>());
		}
		else {
			
    		// valida informação de hora e verifica se os dados são igual a escala, se forem iguais apaga registros para prevalecer o que tem na escala base
    		return validaAjustaDiasHorasAndComparaComDiasHorasEscala(prestador);
		}
    	
    	return "";
	}

	private String validaAjustaDiasHorasAndComparaComDiasHorasEscala(
			ProjetoEscalaPrestador prestador) {

    	List<ProjetoEscalaPrestadorDiaHoraTrabalho> diasHorasDaEscala = prestador.getProjetoEscala().getDiasHorasTrabalho();
		boolean igualEscala = true;
		
		for (int itemIndex = 0; itemIndex < prestador.getDiasHorasTrabalho().size(); itemIndex++) {
			ProjetoEscalaPrestadorDiaHoraTrabalho item = prestador.getDiasHorasTrabalho().get(itemIndex);
			item.setProjetoEscalaPrestador(prestador);
			if ((item.getHoraInicio() == "" && item.getHoraFim() != "") ||
				(item.getHoraFim() == "" && item.getHoraInicio() != "") ||
				!Utilities.validarHoraPreenchida(item.getHoraInicio()) || !Utilities.validarHoraPreenchida(item.getHoraFim()) ||
				(Utilities.validarHora(item.getHoraInicio()) && Utilities.horaToInt(item.getHoraInicio()) > Utilities.horaToInt(item.getHoraFim()))) {				
					return "Preencha corretamente as horas customizadas"; 			
			}
			
			ProjetoEscalaPrestadorDiaHoraTrabalho diaSemanaDaEscala = diasHorasDaEscala.get(itemIndex); 
			if (!item.getHoraInicio().equals(diaSemanaDaEscala.getHoraInicio()) ||
				!item.getHoraFim().equals(diaSemanaDaEscala.getHoraFim())) {
				igualEscala = false;
			}
			
			item.setProjetoEscalaPrestador(prestador);
		}
		
		if (igualEscala) {
			prestador.setProjetoEscalaPrestadorDiasHorasTrabalho(new ArrayList<ProjetoEscalaPrestadorDiaHoraTrabalho>());
		}
		else {
			prestador.setProjetoEscalaPrestadorDiasHorasTrabalho(prestador.getDiasHorasTrabalho()
					.stream().filter(x->!x.getHoraInicio().equals("") && !x.getHoraFim().equals(""))
					.collect(Collectors.toList()));
		}
		return "";
	}

	@SuppressWarnings("unused")
	private List<ProjetoEscalaPrestador> findAllByPrestadorIdAndProjetoId(long prestadorId, long projetoId) {
		return this.projetoEscalaPrestadorDao.findAllByPrestadorIdAndProjetoIdAndExcluido(prestadorId, projetoId, false);
	}

	private List<ProjetoEscalaPrestador> findAllByPrestadorIdAndId(long id, long prestadorId) {
		return this.projetoEscalaPrestadorDao.findAllByIdAndPrestadorIdAndExcluido(id, prestadorId, false);
	}

	@Override
	public String delete(long prestadorId) {
		if (this.horaAprovacaoDao.existsByPrestadorId(prestadorId) ||
			this.ausenciaSolicitacaoDao.existsByUsuarioId(prestadorId) ||
			this.ausenciaReposicaoDao.existsByUsuarioTrocaId(prestadorId)) {
			ProjetoEscalaPrestador prestador = this.findById(prestadorId);
			prestador.setExcluido(true);
			this.projetoEscalaPrestadorDao.save(prestador);	
		}
		else {
			this.projetoEscalaPrestadorDao.deleteById(prestadorId);
		}
		
		return "";
	}

	@Override
	public List<ProjetoEscalaPrestador> findAllProjetosByPrestadorId(long id, boolean trazerInformacaoFolga, boolean trazerInformacaoStatus, boolean trazerStatusReal, boolean trazerSomenteAtivos) {
		return this.findAllProjetosByPrestadorId(id, trazerInformacaoFolga, trazerInformacaoStatus, trazerStatusReal, trazerSomenteAtivos, false, false, false);
	}
	
	@Override
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
						!x.isExcluido() &&
						x.getProjetoEscala().isAtivo() &&
						!x.getProjetoEscala().isExcluido() &&
						x.getProjeto().isAtivo()).collect(Collectors.toList());
		}

		if (trazerSomenteDataAtiva) {	
			projetosCadastrados =
					projetosCadastrados.stream().filter(x->
						x.getDataInicio().isAfter(Utilities.now2()) &&
						(x.getDataFim() == null || x.getDataFim().isBefore(Utilities.now2()))).collect(Collectors.toList());
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
						//informacaoAdicional +=  item.getAceito() == 0 ? "<br><br><i>Projeto aguardando aceite do prestador.</i>" : "";
					}
					
					//informacaoAdicional +=  item.getAceito() == 2 ? "<br><br><i>Projeto recusado pelo prestador.</i>" : "";
					
					if (!trazerSomenteInformacoesBasicas) {
						//informacaoAdicional +=  item.getAceito() == 1 ? "<br><br><i>Projeto aceito pelo prestador.</i>" : "";
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

	@Override
	public String aceitePrestador(Usuario usuarioLogado, long projetoEscalaPrestadorId, int statusAceite, String motivo) {
		ProjetoEscalaPrestador projetoEscalaPrestador = this.findById(projetoEscalaPrestadorId);
		if (projetoEscalaPrestador.getPrestador().getId() != usuarioLogado.getId() && 
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId() && 
			usuarioLogado.getFuncao().getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId()) {
			return "Operação não permitida para este usuário";
		}
		
		if (statusAceite == 2 && motivo == "") {
			return "Preencha o campo motivo recusa";
		}
		
		projetoEscalaPrestadorDao.aceiteRecusa(projetoEscalaPrestadorId, statusAceite, motivo);
		return "";
	}

	@Override
	public boolean existsByPrestadorId(long prestadorId) {
		return this.projetoEscalaPrestadorDao.existsByPrestadorId(prestadorId);
	}

	@Override
	public boolean existsByProjetoEscalaId(long projetoEscalaId) {
		return this.projetoEscalaPrestadorDao.existsByProjetoEscalaId(projetoEscalaId);
	}

	@Override
	public List<ProjetoEscalaPrestador> findAllByProjetoId(long projetoId) {
		List<ProjetoEscalaPrestador> projetosCadastrados = this.projetoEscalaPrestadorDao.findAllByProjetoId(projetoId);
		return this.trazerFiltrosAndObservacoesAdicionais(false, true, false, false, false, false, true, projetosCadastrados);
	}

	@Override
	public List<ProjetoEscalaPrestador> findAllByProjetoId(long projetoId, boolean ativo) {
		List<ProjetoEscalaPrestador> projetosCadastrados = this.projetoEscalaPrestadorDao.findAllByProjetoId(projetoId);
		return this.trazerFiltrosAndObservacoesAdicionais(false, true, false, ativo, false, false, true, projetosCadastrados);
	}

	@Override
	public List<ProjetoEscalaPrestador> findAllByPrestadorId(long prestadorId) {
		List<ProjetoEscalaPrestador> prestadores =  this.projetoEscalaPrestadorDao.findAllByPrestadorIdAndExcluidoAndAtivo(prestadorId, false, true);
		
		return prestadores.stream()
				.filter(x->
						x.getProjeto().isAtivo() &&
						x.getProjeto().getTipoApontamentoHorasId() == 1 &&
						!x.isExcluido() &&
						x.getProjetoEscala().isAtivo() && 
						!x.isExcluido()).distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<ProjetoEscalaPrestador> findAllByRamalIntegracaoRobo(String ramalRobo) {
		return this.projetoEscalaPrestadorDao.findAllByRamalIntegracaoRobo(ramalRobo);
		
	}

	@Override
	public ProjetoEscalaPrestador findByPrestadorIdAndProjetoId(long prestadorId, long projetoId) {
		return this.projetoEscalaPrestadorDao.findByPrestadorIdAndProjetoId(prestadorId, projetoId);
	}

	@Override
	public ProjetoEscalaPrestador findByPrestadorIdAndProjetoEscalaId(long prestadorId, long projetoEscalaId) {
		return this.projetoEscalaPrestadorDao.findByPrestadorIdAndProjetoEscalaId(prestadorId, projetoEscalaId);
	}
}
