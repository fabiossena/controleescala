package com.packageIxia.sistemaControleEscala.models.referencias;

public enum TipoApontamentoHorasEnum {

	sistema(1, "Sistema"), robo(2, "Robô");

    public TipoApontamentoHoras tipoApontamentoHoras;
    
	TipoApontamentoHorasEnum(int id, String nome) {
    	this.tipoApontamentoHoras = new TipoApontamentoHoras(id, nome);
    }
    
    public TipoApontamentoHoras getTipoApontamentoHoras() {
    	return this.tipoApontamentoHoras;
    }

    public static TipoApontamentoHoras GetTipoApontamentoHorasFromId(int id) {
    	for (TipoApontamentoHorasEnum item : values()) {
            if (item.tipoApontamentoHoras.getId() == id) {
            	return item.tipoApontamentoHoras;            	
            }
        }
        
        return null;
    }
    
}
