package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.referencias.Funcao;

@Service
public interface IFuncao {
	Funcao findById(long id);
	List<Funcao> findAll();
	List<Funcao> findByPerfilAcessoId(int perfilAcessoId);
}