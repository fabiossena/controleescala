package com.packageIxia.sistemaControleEscala.services;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.UsuarioEmailPrimeiroAcessoDao;
import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.UsuarioEmailPrimeiroAcesso;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class UsuarioAcessoService {

	private UsuarioDao usuarioDao;
    private UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao;
    
    @Autowired
    public UsuarioAcessoService(
    		UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao,
    		UsuarioDao usuarioDao) {
    	this.usuarioDao = usuarioDao;
    	this.usuarioEmailPrimeiroAcessoDao = usuarioEmailPrimeiroAcessoDao;    	
    }
    
	public String efetuarLoginUsuario(
			LoginPage login, 
    		HttpSession session) {

		login.setMatricula(login.getMatricula().trim());
		Usuario usuario = usuarioDao.findByMatricula(login.getMatricula());
		String error = this.validateLoginUsuario(login, usuario);
		if (!Strings.isBlank(error)) {
			return error;
		}

        return LoginUsuario(session, usuario);
	}

	public String LoginUsuario(HttpSession session, Usuario usuario) {
		session.setAttribute("isAtendimento", usuario.getFuncaoId() == FuncaoEnum.atendimento.funcao.getId());
		session.setAttribute("isAdministracao", usuario.getFuncaoId() == FuncaoEnum.administracao.funcao.getId());
        session.setAttribute("isGerencia", usuario.getFuncaoId() == FuncaoEnum.gerencia.funcao.getId());
        session.setAttribute("isMonitoramento", usuario.getFuncaoId() == FuncaoEnum.monitoramento.funcao.getId());
        session.setAttribute("isDiretoria", usuario.getFuncaoId() == FuncaoEnum.diretoria.funcao.getId());
        session.setAttribute("isFinanceiro", usuario.getFuncaoId() == FuncaoEnum.financeiro.funcao.getId());
        usuario.setFuncao(FuncaoEnum.GetFuncaoFromId(usuario.getFuncaoId()));
        session.setAttribute("usuarioLogado", usuario);
		return "";
	}

	public String insertUsuarioCadastroInicial(
			CadastroInicialPage cadastro, 
			HttpSession session) {

		try {
			
			UsuarioEmailPrimeiroAcesso usuarioPrimeiroAcesso = this.usuarioEmailPrimeiroAcessoDao.findByMatricula(cadastro.getMatricula());
			String error = this.validateUsuarioCadastroInicial(cadastro, usuarioPrimeiroAcesso);
			if (!Strings.isBlank(error)) {
				return error;
			}
			
			Usuario usuario = new Usuario();
			usuario.setMatricula(cadastro.getMatricula());
			usuario.setEmail(cadastro.getEmail());
			usuario.setPrimeiroNome(cadastro.getPrimeiroNome());
			usuario.setSobrenome(cadastro.getSobrenome());
			usuario.setSenha(cadastro.getSenha());
			usuario.setAtivo(true);
			usuario.setFuncaoId(usuarioPrimeiroAcesso.getFuncaoId() == 0 ? 1 : usuarioPrimeiroAcesso.getFuncaoId());
			usuario = usuarioDao.save(usuario);
	        
	        usuarioPrimeiroAcesso.setAceito(true);
	        usuarioEmailPrimeiroAcessoDao.save(usuarioPrimeiroAcesso);
	        return LoginUsuario(session, usuario);
	        
		} catch (Exception e) {
			return e.toString();
		}
	}
    
	public String validateLoginUsuario(LoginPage login, Usuario usuario) {
		
		if (login == null) {
			return "Dados inválidos!";
		}
		
		if (login.getMatricula().isEmpty()) {
			return "Preencha o campo matricula!";
		}
		
		if (login.getSenha().isEmpty()) {
			return "Digite uma senha válida!";
		}
		
		if (usuario == null || !usuario.getSenha().equals(login.getSenha())) {
			return "Matricula e senha inválida!";
		}
        
		return "";
	}	

	private String validateUsuarioCadastroInicial(CadastroInicialPage cadastro, UsuarioEmailPrimeiroAcesso usuarioPrimeiroAcesso) {

		if (cadastro == null) {
			return "Dados inválidos!";
		}

		cadastro.setMatricula(cadastro.getMatricula().trim());
		cadastro.setEmail(cadastro.getEmail().trim());
		
		if (!cadastro.getRepetirSenha().equals(cadastro.getSenha())) {
			return "As senhas digitadas estão diferentes!";
		}
		
		if (!usuarioEmailPrimeiroAcessoDao.existsByMatriculaAndEmail(cadastro.getMatricula(), cadastro.getEmail())) {
			return "Matricula e e-mail não autorizados!";			
		}
		
		if (usuarioPrimeiroAcesso.isAceito()) {
			return "Não permitido registrar este usuário";
		}
		
		if (usuarioDao.existsByEmail(cadastro.getEmail())) {
			return "E-mail inválido!";
		}
		
		if (usuarioDao.existsByMatricula(cadastro.getMatricula())) {
			return "Matricula inválida!";
		}
		
		return "";
	}
}
	
	
