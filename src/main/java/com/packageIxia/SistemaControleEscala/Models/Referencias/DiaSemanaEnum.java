package com.packageIxia.SistemaControleEscala.Models.Referencias;

public enum DiaSemanaEnum {

	domingo(1, "Domingo"), segunda(2, "Segunda-feira"), terca(3, "Terça-feira"), quarta(4, "Quarta-feira"), quinta(5, "Quinta-feira"), sexta(6, "Sexta-feira"), sabado(7, "Sabádo");

    public DiaSemana diaSemana;
    
    DiaSemanaEnum(int id, String nome) {
    	this.diaSemana = new DiaSemana(id, nome);
    }
    
    public DiaSemana getDiaSemana() {
    	return this.diaSemana;
    }

    public static DiaSemana GetDiaSemanaFromId(int id) {
    	for (DiaSemanaEnum item : values()) {
            if (item.diaSemana.getId() == id) {
            	return item.diaSemana;            	
            }
        }
        
        return new DiaSemana(0, "none");
    }

}
