package com.packageIxia.sistemaControleEscala.models.referencias;

public enum DiaSemanaEnum {

	segunda(1, "Segunda-feira"), terca(2, "Terça-feira"), quarta(3, "Quarta-feira"), quinta(4, "Quinta-feira"), sexta(5, "Sexta-feira"), sabado(6, "Sabádo"), domingo(7, "Domingo");

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
