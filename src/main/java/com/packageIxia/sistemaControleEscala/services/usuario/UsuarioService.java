package com.packageIxia.sistemaControleEscala.services.usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.helpers.Utilities;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;
import com.packageIxia.sistemaControleEscala.services.UsuarioAcessoService;
import com.packageIxia.sistemaControleEscala.services.UsuarioDadosAcessoService;
import com.packageIxia.sistemaControleEscala.services.UsuarioDadosPrincipaisService;
import com.packageIxia.sistemaControleEscala.services.projetos.ProjetoEscalaPrestadorService;
import com.packageIxia.sistemaControleEscala.services.projetos.ProjetoEscalaService;
import com.packageIxia.sistemaControleEscala.services.projetos.ProjetoService;

@Service
public class UsuarioService {

    private UsuarioDao usuarioDao;
	private UsuarioAcessoService usuarioAcessoService;
    private UsuarioDadosAcessoService usuarioDadosAcessoService;
    private UsuarioDadosPrincipaisService usuarioDadosPrincipaisService;
	private ProjetoService projetoService;
	private ProjetoEscalaService projetoEscalaService;
	private ProjetoEscalaPrestadorService projetoEscalaPrestadorService;

    @Autowired
    public UsuarioService(
    		UsuarioDao usuarioDao,
    		UsuarioAcessoService usuarioAcessoService,
    		UsuarioDadosAcessoService usuarioDadosAcessoService,
    		UsuarioDadosPrincipaisService usuarioDadosPrincipaisService,
    		ProjetoService projetoService,
    		ProjetoEscalaService projetoEscalaService,
    		ProjetoEscalaPrestadorService projetoEscalaPrestadorService) 
    {
    	this.usuarioDao = usuarioDao;
    	this.usuarioAcessoService = usuarioAcessoService;
    	this.usuarioDadosAcessoService = usuarioDadosAcessoService;
    	this.usuarioDadosPrincipaisService = usuarioDadosPrincipaisService;
    	this.projetoService = projetoService;
    	this.projetoEscalaService = projetoEscalaService;
    	this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
    }

	public String saveUsuario(
			Usuario usuario, 
			HttpSession session, 
			boolean isCadastroUsuarioPage) {
		String error = "";

		this.higienizaUsuario(usuario);
		
		error = this.usuarioDadosPrincipaisService.validateUsuario(usuario);
		if (!Strings.isBlank(error)) {
			return error;
		}

		error = this.usuarioDadosAcessoService.validateUsuario(usuario);
		if (!Strings.isBlank(error)) {
			return error;
		}

		error = this.validaSeguranca(usuario, session, isCadastroUsuarioPage);
		if (!Strings.isBlank(error)) {
			return error;
		}

		System.out.println("Id");
		System.out.println(usuario.getId());
		this.usuarioDao.save(usuario);

		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		if (usuarioLogado.getId() == usuario.getId()) {
			this.usuarioAcessoService.LoginUsuario(session, usuario);
			session.setAttribute("usuarioLogado", usuario);
		}
		
		return "";
	}

	private void higienizaUsuario(Usuario usuario) {
		if (usuario != null) {

			if (!usuario.getMatricula().trim().isEmpty()) {
				usuario.setMatricula(usuario.getMatricula().trim());
			}

			if (!usuario.getEmail().trim().isEmpty()) {
				usuario.setEmail(usuario.getEmail().trim());
			}
		}
	}

	public String validaSeguranca(
			Usuario usuario, 
			HttpSession session, boolean isCadastroUsuarioPage) {

		Usuario usuarioLogado = (Usuario)session.getAttribute("usuarioLogado");
		
    	if (usuarioLogado.getId() == usuario.getId() || !isCadastroUsuarioPage) {
    		
    		if (!usuarioLogado.isAtivo()) {
    			return "Não permitida a alteração do campo ativo pelo usuário atual";
    		}
    		
    		if (!usuario.getMatricula().equals(usuarioLogado.getMatricula())) {
    			return "Não permitida a alteração do campo matrícula pelo usuário atual";
    		}
    		
    		if (usuario.getFuncaoId() != usuarioLogado.getFuncaoId()) {
    			return "Não permitida a alteração do campo função pelo usuário atual";
    		}

    		if (usuario.isAtivo() != usuarioLogado.isAtivo()) {
    			return "Não permitida a alteração do campo 'usuário ativo' pelo usuário atual";
    		}
    	}
    	
		return "";
    	
	}

	public List<Usuario> findAllByUsuarioLogado(Usuario usuarioLogado) {
    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId()) {
			return new ArrayList<Usuario>();
    	}
    	
    	List<Usuario> usuarios = Utilities.toList(this.usuarioDao.findAllByExcluido(false));
    	//System.out.println(usuarios.get(0).getFuncao().getNome());
    	// refresh
    	usuarios.stream().forEach(x -> x.setFuncao(FuncaoEnum.GetFuncaoFromId(x.getFuncaoId())));
    	System.out.println(usuarios.get(0).getFuncao().getNome());
		return usuarios;
	}

	public Usuario findByUsuarioId(long usuarioId) {
		return usuarioDao.findById(usuarioId).orElse(null);
	}

	public List<Usuario> findByFuncaoId(int id) {
		List<Usuario> usuarios = Utilities.toList(usuarioDao.findAllByFuncaoIdAndExcluido(id, false));
		return usuarios.stream().sorted(Comparator.comparing(Usuario::getNomeCompleto).reversed().thenComparing(Usuario::getNomeCompleto)).collect(Collectors.toList());
	}

	public String delete(long id) {
		Usuario usuario = this.findByUsuarioId(id);
		
		if (this.projetoEscalaPrestadorService.existsByPrestadorId(id) ||
			this.projetoEscalaService.existsByMonitorId(id) || 
			this.projetoService.existsByGerenteId(id)) {
			usuario.setExcluido(true);
			usuarioDao.save(usuario);
		}
		else {
			usuarioDao.deleteById(id);
		}
		
		return "";
	}
}
