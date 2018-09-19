package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.Projeto;

@Service
public interface IProjeto {

	List<Projeto> findAllByUsuarioLogado();

	Projeto findById(long id);

	String saveProjeto(Projeto projeto);

	String delete(long id);

	boolean existsByGerenteId(long id);

	List<Projeto> findAll();

}