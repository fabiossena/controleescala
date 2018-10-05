package com.packageIxia.sistemaControleEscala.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.UsuarioEmailPrimeiroAcessoDao;
import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioAcesso;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.INotificacao;
import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.UsuarioEmailPrimeiroAcesso;
import com.packageIxia.sistemaControleEscala.models.referencias.FuncaoEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class UsuarioAcessoService implements IUsuarioAcesso {

	private UsuarioDao usuarioDao;
    private UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao;
	private INotificacao notificacao;
	private HttpServletRequest request;
	private Environment environment;
    
    @Autowired
    public UsuarioAcessoService(
    		UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao,
    		UsuarioDao usuarioDao,
    		INotificacao notificacao,
			HttpServletRequest request,
    		Environment environment) {
    	this.usuarioDao = usuarioDao;
    	this.usuarioEmailPrimeiroAcessoDao = usuarioEmailPrimeiroAcessoDao;   
    	this.notificacao = notificacao;
    	this.request = request;
    	this.environment = environment;
    }
    
	@Override
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

	@Override
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

	@Override
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
	        notificacao.save(new Notificacao(1, "Parabéns, seu cadastro inicial foi realizado com sucesso, acesse o menu 'Meu cadastro' para complementar suas informações.", "Cadastro de usuário", usuario));
	        return LoginUsuario(session, usuario);
	        
		} catch (Exception e) {
			return e.toString();
		}
	}
    
	@Override
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

	public String esqueceuSenha(CadastroInicialPage esqueceuSenha) {

		if (esqueceuSenha == null) {
			return "Dados inválidos!";
		}
		
		if (esqueceuSenha.getMatricula().trim().isEmpty()) {
			return "Preencha o campo matricula!";
		}
		
		if (esqueceuSenha.getEmail().trim().isEmpty()) {
			return "Preencha o campo e-mail!";
		}
		
		Usuario usuario = usuarioDao.findByMatricula(esqueceuSenha.getMatricula().trim());
		if (usuario == null || !usuario.getEmail().equals(esqueceuSenha.getEmail().trim())) {
			return "Matricula e e-mail não existem!";			
		}

		String url = request.getRequestURL().toString().replace("esqueceuSenha", "");
		String mensagem = "Olá, segue sua senha de acesso ao sistema de escala Ixia."
				+ "<br>"
        		+ " Faça o login no endereço <a href='" + url + "login?matriculacadastro=" + esqueceuSenha.getMatricula().trim() + "'>" + url + "login</a> usando a matrícula " + esqueceuSenha.getMatricula().trim() + " e senha " + usuario.getSenha()
        		+ "<br><br>"
        		+ "Atenciosamente administração";
		
		try {
			new EnvioEmail(environment, esqueceuSenha.getEmail().trim(), "Ixia - Recuperação de senha", mensagem).start();
		} catch (Exception e) {
			System.out.println("erro envio e-mail");
			System.out.println(e.toString());
		}
		
		return "";
	}
}
	
	
