package com.packageIxia.sistemaControleEscala.models.referencias;

public enum PerfilAcessoEnum {

	atendimento(1, "Atendimento", true, 1), monitoramento(2, "Monitoramento", false), administracao(3, "Administração", false), gerencia(4, "Gerencia", false), financeiro(5, "Financeiro", false), diretoria(6, "Diretoria", false);

    public PerfilAcesso acesso;
    
    PerfilAcessoEnum(int id, String nome, boolean compartilhado) {
    	this.acesso = new PerfilAcesso(id, nome, compartilhado, 0);
    }
    
    PerfilAcessoEnum(int id, String nome, boolean compartilhado, int tipo) {
    	this.acesso = new PerfilAcesso(id, nome, compartilhado, tipo);
    }
    
    PerfilAcessoEnum(int id, String nome, boolean compartilhado, int tipo, String descricao) {
    	this.acesso = new PerfilAcesso(id, nome, compartilhado, tipo);
    }
    
    public PerfilAcesso getPerfilAcesso() {
    	return this.acesso;
    }
    
    public int getId() {
    	return this.acesso.getId();
    }

    public static PerfilAcesso GetFromId(int id) {
    	for (PerfilAcessoEnum aces : values()) {
            if (aces.acesso.getId() == id) {
            	return aces.acesso;            	
            }
        }
        
        return null;
    }
}
