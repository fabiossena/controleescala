package com.packageIxia.sistemaControleEscala.interfaces.projeto;

import java.util.List;
import org.springframework.stereotype.Service;
import com.packageIxia.sistemaControleEscala.models.projeto.FuncaoConfiguracao;

@Service
public interface IFuncaoConfiguracao {

	List<FuncaoConfiguracao> findAll();

}