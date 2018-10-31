package com.packageIxia.sistemaControleEscala.daos.projeto;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.packageIxia.sistemaControleEscala.models.projeto.ProjetoEscalaPrestadorDiaHoraTrabalho;

@Repository
public interface ProjetoEscalaPrestadorDiaHoraTrabalhoDao extends CrudRepository<ProjetoEscalaPrestadorDiaHoraTrabalho, Long> {
	
	@Transactional
	@Modifying
	@Query("delete from ProjetoEscalaPrestadorDiaHoraTrabalho where projeto_escala_prestador_id=:projetoEscalaPrestadorId")
	void deleteAllByProjetoEscalaPrestadorId(@Param("projetoEscalaPrestadorId")long projetoEscalaPrestadorId);
	
	@Transactional
	@Modifying
	@Query("delete from ProjetoEscalaPrestadorDiaHoraTrabalho where projeto_escala_prestador_id in (:projetoEscalaPrestadorIds)")
	void deleteAllByProjetoEscalaPrestadorId(@Param("projetoEscalaPrestadorIds")long[] projetoEscalaPrestadorId);
	
	@Transactional
	@Modifying
	@Query("delete from ProjetoEscalaPrestadorDiaHoraTrabalho where id=:id")
	void deleteAllById(@Param("id")long id);
}
