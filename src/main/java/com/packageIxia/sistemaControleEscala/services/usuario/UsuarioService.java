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
import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioDadosAcesso;
import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioDadosPrincipais;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjeto;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscala;
import com.packageIxia.sistemaControleEscala.interfaces.projeto.IProjetoEscalaPrestador;
import com.packageIxia.sistemaControleEscala.interfaces.usuario.IUsuario;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;
import com.packageIxia.sistemaControleEscala.services.UsuarioAcessoService;

@Service
public class UsuarioService implements IUsuario {

    private UsuarioDao usuarioDao;
	private UsuarioAcessoService usuarioAcessoService;
    private IUsuarioDadosAcesso usuarioDadosAcessoService;
    private IUsuarioDadosPrincipais usuarioDadosPrincipaisService;
	private IProjeto projetoService;
	private IProjetoEscala projetoEscalaService;
	private IProjetoEscalaPrestador projetoEscalaPrestadorService;

    @Autowired
    public UsuarioService(
    		UsuarioDao usuarioDao,
    		UsuarioAcessoService usuarioAcessoService,
    		IUsuarioDadosAcesso usuarioDadosAcessoService,
    		IUsuarioDadosPrincipais usuarioDadosPrincipaisService,
    		IProjeto projetoService,
    		IProjetoEscala projetoEscalaService,
    		IProjetoEscalaPrestador projetoEscalaPrestadorService) 
    {
    	this.usuarioDao = usuarioDao;
    	this.usuarioAcessoService = usuarioAcessoService;
    	this.usuarioDadosAcessoService = usuarioDadosAcessoService;
    	this.usuarioDadosPrincipaisService = usuarioDadosPrincipaisService;
    	this.projetoService = projetoService;
    	this.projetoEscalaService = projetoEscalaService;
    	this.projetoEscalaPrestadorService = projetoEscalaPrestadorService;
    }

	@Override
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

	@Override
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

	@Override
	public List<Usuario> findAllByUsuarioLogado(Usuario usuarioLogado) {
    	if (usuarioLogado.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId()) {
			return new ArrayList<Usuario>();
    	}
    	
    	List<Usuario> usuarios = Utilities.toList(this.usuarioDao.findAllByExcluido(false));
    	// refresh
    	usuarios.stream().forEach(x -> x.setFuncao(FuncaoEnum.GetFuncaoFromId(x.getFuncaoId())));
    	System.out.println(usuarios.get(0).getFuncao().getNome());
		return usuarios;
	}

	@Override
	public Usuario findByUsuarioId(long usuarioId) {
		return usuarioDao.findById(usuarioId).orElse(null);
	}

	@Override
	public List<Usuario> findByFuncaoId(int id) {
		List<Usuario> usuarios = Utilities.toList(usuarioDao.findAllByFuncaoIdAndExcluido(id, false));
		return usuarios.stream().sorted(Comparator.comparing(Usuario::getNomeCompleto).reversed().thenComparing(Usuario::getNomeCompleto)).collect(Collectors.toList());
	}

	@Override
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
