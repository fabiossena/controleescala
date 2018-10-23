package com.packageIxia.sistemaControleEscala.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.Strings;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.packageIxia.sistemaControleEscala.daos.UsuarioEmailPrimeiroAcessoDao;
import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioAcesso;
import com.packageIxia.sistemaControleEscala.interfaces.referencias.INotificacao;
import com.packageIxia.sistemaControleEscala.models.CadastroInicialPage;
import com.packageIxia.sistemaControleEscala.models.LoginPage;
import com.packageIxia.sistemaControleEscala.models.UsuarioEmailPrimeiroAcesso;
import com.packageIxia.sistemaControleEscala.models.referencias.PerfilAcessoEnum;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;
import com.packageIxia.sistemaControleEscala.models.referencias.Notificacao;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Service
public class UsuarioAcessoService implements IUsuarioAcesso {

	private UsuarioDao usuarioDao;
	private UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao;
	private INotificacao notificacao;
	private HttpServletRequest request;
	private Environment environment;
	private HttpSession session;
    
	public UsuarioAcessoService(
    		UsuarioEmailPrimeiroAcessoDao usuarioEmailPrimeiroAcessoDao,
    		UsuarioDao usuarioDao,
    		INotificacao notificacao,
			HttpServletRequest request,
    		Environment environment,
    		HttpSession session) {
    	this.usuarioDao = usuarioDao;
    	this.usuarioEmailPrimeiroAcessoDao = usuarioEmailPrimeiroAcessoDao;   
    	this.notificacao = notificacao;
    	this.request = request;
    	this.environment = environment;
    	this.session = session;
    }
    
	@Override
	public String efetuarLoginUsuario(
			LoginPage login) {
		
		if (login == null) {
			return "Dados inválidos!";
		}
		
		login.setMatricula(login.getMatricula().trim());
		Usuario usuario = usuarioDao.findByMatricula(login.getMatricula());
		String error = this.validateLoginUsuario(login, usuario);
		if (!Strings.isBlank(error)) {
			return error;
		}
		
		if (login.getFuncao().getId() == 0 
			|| usuario.getUsuarioFuncaoAdicionais().size() == 0) {
			return LoginUsuario(usuario, usuario.getFuncao());	
		}
		
        return LoginUsuario(usuario, usuario.getUsuarioFuncaoAdicionais().stream().filter(x->x.getFuncao().getId() == login.getFuncao().getId()).findFirst().get().getFuncao());
	}

	@Override
	public String LoginUsuario(Usuario usuario, Funcao funcao) {
		session.setAttribute("isAtendimento", funcao.getPerfilAcessoId() == PerfilAcessoEnum.atendimento.getId());
		session.setAttribute("isAdministracao", funcao.getPerfilAcessoId() == PerfilAcessoEnum.administracao.getId());
        session.setAttribute("isGerencia", funcao.getPerfilAcessoId() == PerfilAcessoEnum.gerencia.getId());
        session.setAttribute("isMonitoramento", funcao.getPerfilAcessoId() == PerfilAcessoEnum.monitoramento.getId());
        session.setAttribute("isDiretoria", funcao.getPerfilAcessoId() == PerfilAcessoEnum.diretoria.getId());
        session.setAttribute("isFinanceiro", funcao.getPerfilAcessoId() == PerfilAcessoEnum.financeiro.getId());
        usuario.setFuncao(funcao);
        session.setAttribute("usuarioLogado", usuario);
		return "";
	}

	@Override
	public String insertUsuarioCadastroInicial(
			CadastroInicialPage cadastro) {

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
			usuario.setFuncao(usuarioPrimeiroAcesso.getFuncao());
			usuario.setCentroCusto(usuarioPrimeiroAcesso.getCentroCusto());
			usuario = usuarioDao.save(usuario);
	        
	        usuarioPrimeiroAcesso.setAceito(true);
	        usuarioEmailPrimeiroAcessoDao.save(usuarioPrimeiroAcesso);
	        notificacao.save(new Notificacao(1, "Parabéns, seu cadastro inicial foi realizado com sucesso, acesse o menu 'Meu cadastro' para complementar suas informações.", "Cadastro de usuário", usuario));
	        notificacao.save(new Notificacao(2, "Clique em X para fechar suas notificações.", "Cadastro de usuário", usuario));
	        return LoginUsuario(usuario, usuario.getFuncao());
	        
		} catch (Exception e) {
			return e.toString();
		}
	}
	    
	@Override
	public String validateLoginUsuario(LoginPage login, Usuario usuario) {
		
		if (login.getMatricula().isEmpty()) {
			return "Preencha o campo matricula!";
		}
		
		if (login.getSenha().isEmpty()) {
			return "Digite uma senha válida!";
		}
		
		if (usuario == null || !usuario.getSenha().equals(login.getSenha())) {
			return "Matricula e senha inválida!";
		}
		
		if (login.getFuncao() != null && login.getFuncao().getId() > 0) {
			if (login.getFuncao().getId() != usuario.getFuncao().getId()) {
				if (!usuario.getUsuarioFuncaoAdicionais().stream().anyMatch(x->x.getFuncao().getId() == login.getFuncao().getId())) {
					return "Função escolhida não permitida!";					
				}
			}
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
	
	public String validateCadastroInicialUsuario(Usuario usuario) {

		if (usuario == null) {
			return "Dados inválidos";
		}

		if (usuario.getCpf().trim().isEmpty()) {
			return "Preencha o campo cpf";
		}

		if (usuario.getTelefone().trim().isEmpty()) {
			return "Preencha o campo telefone";
		}
		
		Usuario usuarioBase = usuarioDao.findByEmail(usuario.getEmail());		
		if (usuarioBase != null && usuarioBase.getId() != usuario.getId()) {
			return "E-mail já usado por outro  usuário";
		}	
		
		usuarioBase = usuarioDao.findByCpf(usuario.getCpf());
		if (usuarioBase != null && usuarioBase.getId() != usuario.getId()) {
			return "Cpf já usado por outro  usuário";
		}
		
		usuarioBase = usuarioDao.findByMatricula(usuario.getMatricula());
		if (usuarioBase != null && usuarioBase.getId() != usuario.getId()) {
			return "Matricula já usada por outro  usuário";
		}
				
		return "";
	}

	public String validateLoginUsuario(Usuario usuario) {

		if (usuario == null) {
			return "Dados inválidos!";
		}
		
		if (!usuario.getRepetirSenha().equals(usuario.getSenha())) {
			return "As senhas digitadas estão diferentes!";
		}
		
		return "";
	}
}
	
	
