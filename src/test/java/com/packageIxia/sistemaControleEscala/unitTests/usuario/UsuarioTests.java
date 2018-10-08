package com.packageIxia.sistemaControleEscala.unitTests.usuario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.packageIxia.sistemaControleEscala.daos.usuario.UsuarioDao;
import com.packageIxia.sistemaControleEscala.interfaces.IUsuarioAcesso;
import com.packageIxia.sistemaControleEscala.services.UsuarioAcessoService;
import com.packageIxia.sistemaControleEscala.unitTests.ApplicationTestBase;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioTests {

	UsuarioConfiguraDados usuarioDadosTeste = new UsuarioConfiguraDados();
	
    @Configuration
    static class TestConfig {
         
    }
    
	@Test
	public void testValidation() {
		UsuarioDao usuarioMockup = new UsuarioMockup();
		usuarioMockup.save(this.usuarioDadosTeste.getUsuarioTestBase());
		
		IUsuarioAcesso usuarioAcesso = new UsuarioAcessoService(null, usuarioMockup, null, ApplicationTestBase.request, ApplicationTestBase.environment, ApplicationTestBase.session);
			
		String mensagem  = usuarioAcesso.efetuarLoginUsuario(null);
		assertEquals("Dados inválidos!", mensagem);
		
		mensagem  = usuarioAcesso.efetuarLoginUsuario(this.usuarioDadosTeste.getDadosBaseLoginPage("", "123456"));
		assertEquals("Preencha o campo matricula!", mensagem);
		
		mensagem  = usuarioAcesso.efetuarLoginUsuario(this.usuarioDadosTeste.getDadosBaseLoginPage("1234", ""));
		assertEquals("Digite uma senha válida!", mensagem);
		
		mensagem  = usuarioAcesso.efetuarLoginUsuario(this.usuarioDadosTeste.getDadosBaseLoginPage("1234", "X123456"));
		assertEquals("Matricula e senha inválida!", mensagem);
		
		mensagem  = usuarioAcesso.efetuarLoginUsuario(this.usuarioDadosTeste.getDadosBaseLoginPage("X1234", "123456"));
		assertEquals("Matricula e senha inválida!", mensagem);
		
		mensagem  = usuarioAcesso.efetuarLoginUsuario(this.usuarioDadosTeste.getDadosBaseLoginPage("1234", "123456"));
		assertEquals("Mensagem de erro sendo apresentada para dados validos", mensagem, "");
	}

}
