package com.packageIxia.sistemaControleEscala.controllers;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;
import com.packageIxia.sistemaControleEscala.services.referencias.NotificacaoService;

@Controller
public class NotificacaoController {

	private NotificacaoService notificacaoService;
	private HttpSession session;

	public NotificacaoController(
			NotificacaoService notificacaoService,
			HttpSession session) {
		this.notificacaoService = notificacaoService;
		this.session = session;
	}

    @ResponseBody
    @RequestMapping(value = "/notificacao", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Notificacao> notificacao(@RequestParam(value = "ler", defaultValue = "false") boolean ler)  {
    	Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");		
    	return this.notificacaoService.findAndAbrirAllByUsuarioId(usuarioLogado.getId(), ler);
	}

    @PostMapping(value = "/notificacao/{id}")
    public String ler(@PathVariable("id") long id) {	
    	Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");	
		Notificacao notificacao = this.notificacaoService.findByIdAndUsuarioId(id, usuarioLogado.getId());
		if (notificacao == null) {
			return "Notificação não encontrada";
		}
		
		notificacao.setLeitura(LocalDateTime.now());
		return this.notificacaoService.save(notificacao);
    }
    		
}
