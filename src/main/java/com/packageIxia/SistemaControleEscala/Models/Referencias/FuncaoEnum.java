package com.packageIxia.SistemaControleEscala.Models.Referencias;

public enum FuncaoEnum {

	atendimento(1, "Atendimento", true, 1), monitoramento(2, "Monitoramento", false), administracao(3, "Administração", false), gerencia(4, "Gerencia", false), financeiro(5, "Financeiro", false), diretoria(6, "Diretoria", false);

    public Funcao funcao;
    
    FuncaoEnum(int id, String nome, boolean compartilhado) {
    	this.funcao = new Funcao(id, nome, compartilhado, 0);
    }
    
    FuncaoEnum(int id, String nome, boolean compartilhado, int tipo) {
    	this.funcao = new Funcao(id, nome, compartilhado, tipo);
    }
    
    public Funcao getFuncao() {
    	return this.funcao;
    }

    public static Funcao GetFuncaoFromId(int id) {
    	for (FuncaoEnum func : values()) {
            if (func.funcao.getId() == id) {
            	return func.funcao;            	
            }
        }
        
        return null;
    }
}
