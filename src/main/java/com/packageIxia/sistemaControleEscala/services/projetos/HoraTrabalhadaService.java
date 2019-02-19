package com.packageIxia.sistemaControleEscala.services.projetos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.projeto.HoraTrabalhadaDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraAprovacao;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IHoraTrabalhada;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraAprovacao;
import com.packageIxia.sistemaControleEscala.models.projeto.HoraTrabalhada;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class HoraTrabalhadaService implements IHoraTrabalhada {

	private HoraTrabalhadaDao horaTrabalhadaDao;
	private HttpSession session;
	private IHoraAprovacao horaAprovacaoService;
	private IProjetoEscala projetoEscalaService;

	@Autowired
	public HoraTrabalhadaService(HoraTrabalhadaDao horaTrabalhadaDao, IHoraAprovacao horaAprovacaoService,
			IProjetoEscala projetoEscalaService, HttpSession session) {
		this.horaTrabalhadaDao = horaTrabalhadaDao;
		this.horaAprovacaoService = horaAprovacaoService;
		this.session = session;
		this.projetoEscalaService = projetoEscalaService;
	}

	@Override
	public List<HoraTrabalhada> findByHoraAprovacaoId(Long id) {
		List<HoraTrabalhada> horasAprovacoes = this.horaTrabalhadaDao.findAllByHoraAprovacaoId(id).stream()
				.sorted(Comparator.comparing(HoraTrabalhada::getDataHoraInicio)).collect(Collectors.toList());
		return horasAprovacoes;
	}

	@Override
	public HoraTrabalhada findById(Long id) {
		return this.horaTrabalhadaDao.findById(id).orElse(null);
	}

	@Override
	public List<HoraTrabalhada> findByPrestadorId(Long id) {
		return this.horaTrabalhadaDao.findByPrestadorId(id).stream()
				.sorted(Comparator.comparing(HoraTrabalhada::getDataHoraInicio)).collect(Collectors.toList());
	}

	@Override
	public HoraTrabalhada findLastByPrestadorId(long prestadorId) throws Exception {

		List<HoraTrabalhada> all = this.findByPrestadorId(prestadorId);
		if (all == null || all.size() == 0) {
			return null;
		}

		return all.get(all.size() - 1);
	}

	@Override
	public HoraTrabalhada findLastStartedByPrestadorId(long prestadorId) throws Exception {

		List<HoraTrabalhada> all = this.findByPrestadorId(prestadorId).stream().filter(x -> x.getTipoAcao() == 1)
				.collect(Collectors.toList());
		if (all == null || all.size() == 0) {
			return null;
		}

		return all.get(all.size() - 1);
	}

	@Override
	public String save(long escalaId, int tipoAcao, boolean novoSomenteDataInicial, String motivoAcao)
			throws Exception {
		// tipoAcao | 1 = start, 2 = pause
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado.getFuncao().getPerfilAcessoId() != PerfilAcessoEnum.atendimento.getId()
				&& usuarioLogado.getFuncao().getPerfilAcessoId() != PerfilAcessoEnum.monitoramento.getId()) {
			return "Não permitido efetuar esta ação com o usuário atual";
		}

		if (escalaId == 0 || tipoAcao == 0) {
			return "Preencha os parametros obrigatórios";
		}

		HoraTrabalhada horaTrabalhada = null;
		horaTrabalhada = this.findLastStartedByPrestadorId(usuarioLogado.getId());

		if (horaTrabalhada != null && tipoAcao == 1 && novoSomenteDataInicial && horaTrabalhada.getTipoAcao() == 1
				&& horaTrabalhada.getDataHoraFim() == null) {
			return "Para iniciar uma escala não pode existir uma escala já iniciada";
		}

		HoraAprovacao horaAprovacao = null;
		if (tipoAcao == 1 && novoSomenteDataInicial) {
			horaAprovacao = this.horaAprovacaoService.findLastByPrestadorIdOrInsert(usuarioLogado.getId(),
					Utilities.now().getYear(), Utilities.now().getMonthValue());
			horaTrabalhada = new HoraTrabalhada();
			horaTrabalhada.setTipoAcao(1);
			horaTrabalhada.setProjetoEscala(this.projetoEscalaService.findById(escalaId));
			horaTrabalhada.setHoraAprovacao(horaAprovacao);
			horaTrabalhada.setDataHoraInicio(Utilities.now());
			horaTrabalhada.setUsuarioCriacao(usuarioLogado);
		} else if (tipoAcao == 1 && !novoSomenteDataInicial) {
			horaTrabalhada.setDataHoraFim(Utilities.now());
		} else if (tipoAcao == 2) {
			horaTrabalhada = this.findLastByPrestadorId(usuarioLogado.getId());
			if (horaTrabalhada == null) {
				return "Não existe uma escala iniciada";
			}

			if (tipoAcao == 2 && horaTrabalhada.getTipoAcao() == 2 && novoSomenteDataInicial
					&& horaTrabalhada.getDataHoraFim() == null) {
				return "A escala já esta pausada";
			}

			if (novoSomenteDataInicial) {
				horaAprovacao = this.horaAprovacaoService.findByDateAndPrestadorId(usuarioLogado.getId(),
						Utilities.now().getYear(), Utilities.now().getMonthValue());
				if (horaAprovacao == null) {
					return "Não existe uma escala iniciada";
				}

				if (horaTrabalhada.getProjetoEscala().getId() != escalaId) {
					return "A escala selecionada é diferente da escala iniciada";
				}

				horaTrabalhada = new HoraTrabalhada();
				horaTrabalhada.setProjetoEscala(this.projetoEscalaService.findById(escalaId));
				horaTrabalhada.setTipoAcao(2);
				horaTrabalhada.setMotivoPausa(motivoAcao);
				horaTrabalhada.setHoraAprovacao(horaAprovacao);
				horaTrabalhada.setDataHoraInicio(Utilities.now());
				horaTrabalhada.setUsuarioCriacao(usuarioLogado);
			} else {
				if (horaTrabalhada.getDataHoraFim() != null) {
					return "A escala esta parada";
				}

				horaTrabalhada.setDataHoraFim(Utilities.now());
			}
		}

		horaTrabalhada.setAprovadoResponsavel(0);
		this.horaAprovacaoService.updateAprovacaoReset(horaTrabalhada.getHoraAprovacao().getId(),
				horaTrabalhada.getHoraAprovacao().getTotalHoras(), horaTrabalhada.getHoraAprovacao().getTotalValor());
		this.horaTrabalhadaDao.save(horaTrabalhada);

		return "";
	}

	@Override
	public String setAndSave(HoraTrabalhada horaTrabalhada, HoraTrabalhada horaTrabalhadaAnterior) {

		HoraTrabalhada hora = findById(horaTrabalhada.getId());
		if (hora == null) {
			hora = horaTrabalhada;
		} else {
			hora.setDataHoraInicio(horaTrabalhada.getDataHoraInicio());
			hora.setDataHoraFim(horaTrabalhada.getDataHoraFim());
			hora.setExcluido(horaTrabalhada.isExcluido());
			hora.setProjetoEscala(horaTrabalhada.getProjetoEscala());
			hora.setMotivoPausa(horaTrabalhada.getMotivoPausa());
			hora.setTipoAcao(horaTrabalhada.getTipoAcao());
		}

		hora.setHoraAprovacao(horaTrabalhada.getHoraAprovacao());

		return save(hora, horaTrabalhadaAnterior);
	}

	@Override
	public String save(HoraTrabalhada horaTrabalhada, HoraTrabalhada horaTrabalhadaAnterior) {

		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

		if (horaTrabalhada.getDataHoraInicio() == null) {
			return "Preencha a data início";
		}

		if (horaTrabalhada.getDataHoraFim() == null) {
			return "Preencha a data fim";
		}

		if (horaTrabalhada.getTipoAcao() == 2 && horaTrabalhada.getMotivoPausa().isEmpty()) {
			return "Preencha o campo motivo";
		}

		// List<HoraTrabalhada> betweenData =
		// horaTrabalhadaDao.findAnyBetweenDataInicioAndFim(horaTrabalhada.getHoraAprovacao().getPrestador().getId(),
		// horaTrabalhada.getDataHoraInicio(), horaTrabalhada.getDataHoraFim());
		// if (betweenData == null || horaTrabalhada.getTipoAcao() == 1 &&
		// betweenData.size() > 0 && (horaTrabalhada.getId() == 0 ||
		// betweenData.stream().filter(x -> x.getId() != horaTrabalhada.getId()).count()
		// > 0)) {
		// return "A data não pode estar entre uma escala existente";
		// }
		// else if (betweenData == null || horaTrabalhada.getTipoAcao() == 2 &&
		// betweenData.size() > 0 && (horaTrabalhada.getId() == 0 ||
		// betweenData.stream().filter(x -> x.getId() != horaTrabalhada.getId()).count()
		// > 0)) {
		// {
		// if (betweenData.stream().findFirst().get().getProjetoEscala().getId() !=
		// horaTrabalhada.getProjetoEscala().getId()) {
		// return "Selecione a escala correta! A data hora da ação desta pausa esta
		// entre uma escala diferente da selecionada";
		// }
		// }

		if (horaTrabalhada.getDataHoraInicio().isAfter(horaTrabalhada.getDataHoraFim())) {
			return "A data hora início da pausa deve ser menor que a data fim";
		}

		List<HoraTrabalhada> datas = horaTrabalhada.getHoraAprovacao().getHorasTrabalhadas(); // horaTrabalhadaAnterior.getHoraAprovacao().getHorasTrabalhadas();
																								// //
																								// horaTrabalhadaDao.findAllByHoraAprovacaoId(horaTrabalhada.getHoraAprovacao().getId());
		if (datas != null) {
			boolean pausaValida = false;

			for (HoraTrabalhada data : datas) {
				LocalDateTime dataHoraFim = data.getDataHoraFim() == null ? Utilities.now() : data.getDataHoraFim();
				if (horaTrabalhada.getTipoAcao() == 1) {
					if (horaTrabalhada.getId() != data.getId() && ((horaTrabalhada.getDataHoraInicio().plusSeconds(1)
							.isAfter(data.getDataHoraInicio())
							&& horaTrabalhada.getDataHoraInicio().plusSeconds(1).isBefore(dataHoraFim))
							|| (horaTrabalhada.getDataHoraFim().minusSeconds(1).isAfter(data.getDataHoraInicio())
									&& horaTrabalhada.getDataHoraFim().minusSeconds(1).isBefore(dataHoraFim)))) {
						return "A data não pode estar entre um apontamento existente";
					}
				} else if (horaTrabalhada.getTipoAcao() == 2 && data.getTipoAcao() == 2) {
					if (horaTrabalhada.getId() != data.getId() && ((horaTrabalhada.getDataHoraInicio().plusSeconds(1)
							.isAfter(data.getDataHoraInicio())
							&& horaTrabalhada.getDataHoraInicio().plusSeconds(1).isBefore(dataHoraFim))
							|| (horaTrabalhada.getDataHoraFim().minusSeconds(1).isAfter(data.getDataHoraInicio())
									&& horaTrabalhada.getDataHoraFim().minusSeconds(1).isBefore(dataHoraFim)))) {
						return "A data da pausa não pode estar entre um apontamento(pausa) existente";
					}
				} else if (horaTrabalhada.getTipoAcao() == 2 && data.getTipoAcao() == 1) {
					if ((horaTrabalhada.getDataHoraInicio().plusSeconds(1).isAfter(data.getDataHoraInicio())
							&& horaTrabalhada.getDataHoraInicio().plusSeconds(1).isBefore(dataHoraFim)
							|| (horaTrabalhada.getDataHoraFim().isAfter(data.getDataHoraInicio())
									&& horaTrabalhada.getDataHoraFim().isBefore(dataHoraFim)))) {
						pausaValida = true;
					}
				}
			}

			if (horaTrabalhada.getTipoAcao() == 2 && !pausaValida) {
				return "A data da pausa deve estar entre um apontamento existente";
			}
		}

		if (horaTrabalhada.getId() == 0 && !horaTrabalhada.isIncluidoRobo()) {
			horaTrabalhada.setIncluidoManualmente(true);
			horaTrabalhada.setUsuarioCriacao(usuarioLogado);
		} else if (horaTrabalhadaAnterior != null) {
			this.setLogAlteracao(horaTrabalhada, horaTrabalhadaAnterior, usuarioLogado);
		}

		if (horaTrabalhada.isExcluido()) {
			horaTrabalhada.setUsuarioExclusao(usuarioLogado);
			horaTrabalhada.setDataHoraExclusao(Utilities.now());
			if (horaTrabalhada.getTipoAcao() == 1 && horaTrabalhada.getDataHoraFim() != null) {

				for (HoraTrabalhada data : datas) {
					if (data.getTipoAcao() == 2 && !data.isExcluido()) {
						if (data.getDataHoraInicio().plusSeconds(1).isAfter(horaTrabalhada.getDataHoraInicio()) && 
							data.getDataHoraInicio().minusSeconds(1).isBefore(horaTrabalhada.getDataHoraFim()) &&
							data.getDataHoraInicio().isAfter(horaTrabalhada.getDataHoraInicio()) && 
							data.getDataHoraInicio().minusSeconds(1).isBefore(horaTrabalhada.getDataHoraFim())) {
							data.setUsuarioExclusao(usuarioLogado);
							data.setExcluido(true);
							data.setDataHoraExclusao(Utilities.now());
							this.horaTrabalhadaDao.save(data);
						}						
					}
				}
			}
		}

		horaTrabalhada.setAprovadoResponsavel(0);
		this.horaAprovacaoService.updateAprovacaoReset(horaTrabalhada.getHoraAprovacao().getId(),
				horaTrabalhada.getHoraAprovacao().getTotalHoras(), horaTrabalhada.getHoraAprovacao().getTotalValor());
		this.horaTrabalhadaDao.save(horaTrabalhada);

		return "";
	}

	private void setLogAlteracao(HoraTrabalhada horaTrabalhada, HoraTrabalhada horaTrabalhadaAnterior,
			Usuario usuarioLogado) {
		
		String usuarioAlteracao = " <i>(" + usuarioLogado.getNomeCompletoMatricula() + "|"
				+ Utilities.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")</i>";
		
		if (!horaTrabalhadaAnterior.getDataHoraInicio().isEqual(horaTrabalhada.getDataHoraInicio())) {

			horaTrabalhada.setHistoricoCorrecao(
					(horaTrabalhada.getHistoricoCorrecao() == null ? "" : horaTrabalhada.getHistoricoCorrecao())
							+ "<br>Data início alterada de "
							+ horaTrabalhadaAnterior.getDataHoraInicio()
									.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"))
							+ " para "
							+ horaTrabalhada.getDataHoraInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"))
							+ usuarioAlteracao);

		}

		if ((horaTrabalhadaAnterior.getDataHoraFim() == null && horaTrabalhada.getDataHoraFim() != null) ||
			!horaTrabalhadaAnterior.getDataHoraFim().isEqual(horaTrabalhada.getDataHoraFim())) {
			horaTrabalhada.setHistoricoCorrecao(
					(horaTrabalhada.getHistoricoCorrecao() == null ? "" : horaTrabalhada.getHistoricoCorrecao())
							+ "<br>Data fim alterada de "
							+ (horaTrabalhadaAnterior.getDataHoraFim() == null ? "(vazia)" : 
								horaTrabalhadaAnterior.getDataHoraFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")))
							+ " para "
							+ horaTrabalhada.getDataHoraFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"))
							+ usuarioAlteracao);

		}

		horaTrabalhadaAnterior.setMotivoPausa(
				horaTrabalhadaAnterior.getMotivoPausa() == null ? "" : horaTrabalhadaAnterior.getMotivoPausa());
		if (!horaTrabalhadaAnterior.getMotivoPausa().equals(horaTrabalhada.getMotivoPausa())) {
			horaTrabalhada.setHistoricoCorrecao(
					(horaTrabalhada.getHistoricoCorrecao() == null ? "" : horaTrabalhada.getHistoricoCorrecao())
							+ "<br>Motivo pausa alterado de " + horaTrabalhadaAnterior.getMotivoPausa() + " para "
							+ horaTrabalhada.getMotivoPausa() + usuarioAlteracao);
		}

		if (horaTrabalhadaAnterior.isExcluido() != horaTrabalhada.isExcluido()) {
			horaTrabalhada.setHistoricoCorrecao(
					(horaTrabalhada.getHistoricoCorrecao() == null ? "" : horaTrabalhada.getHistoricoCorrecao())
							+ "<br>Hora trabalhada"
							+ (horaTrabalhada.isExcluido() ? "(excluida)" : "(exclusão desfeita)") + usuarioAlteracao);
		}

		if (horaTrabalhadaAnterior.getTipoAcao() != horaTrabalhada.getTipoAcao()) {
			horaTrabalhada.setHistoricoCorrecao(
					(horaTrabalhada.getHistoricoCorrecao() == null ? "" : horaTrabalhada.getHistoricoCorrecao())
							+ "<br>Tipo ação alterado de " + horaTrabalhadaAnterior.getTipoAcao() + " para "
							+ horaTrabalhada.getTipoAcao() + usuarioAlteracao);
		}

		if (horaTrabalhadaAnterior.getProjetoEscala().getId() != horaTrabalhada.getProjetoEscala().getId()) {
			horaTrabalhada.setHistoricoCorrecao(
					(horaTrabalhada.getHistoricoCorrecao() == null ? "" : horaTrabalhada.getHistoricoCorrecao())
							+ "<br>Escala alterada de "
							+ horaTrabalhadaAnterior.getProjetoEscala().getDescricaoCompletaEscala() + " para "
							+ horaTrabalhada.getProjetoEscala().getDescricaoCompletaEscala() + usuarioAlteracao);
		}
	}

	@Override
	public List<HoraTrabalhada> findByProjetoEscalaId(long id, int ano, int mes) {
		List<HoraTrabalhada> hora = this.horaTrabalhadaDao.findByProjetoEscalaIdAndDate(id, ano, mes);
		return Utilities.toList(
				this.horaTrabalhadaDao.findAllById(Utilities.streamLongToIterable(hora.stream().map(x -> x.getId()))));
	}

	@Override
	public void deleteAllByMonthYear(int monthValue, int year) {
		this.horaTrabalhadaDao.deleteAllByMonthYear(monthValue, year);
	}
}
