package com.packageIxia.sistemaControleEscala.daos.projeto;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.packageIxia.sistemaControleEscala.models.projeto.HoraExtrato;

@Repository
public interface HoraExtratoDao extends CrudRepository<HoraExtrato, Long>  {

	List<HoraExtrato> findAllByUsuarioId(Long usuarioId);
	
//	@Query("from HoraExtrato h where h.usuarioId = :usuarioId AND year(h.data)=:ano AND month(h.data)=:mes")
//	List<HoraExtrato> findAllByUsuarioIdAndData(@Param("usuarioId")Long usuarioId, @Param("ano")int ano, @Param("mes")int mes);
	
	//@Query("from HoraExtrato h where h.id = (select max(id) as max_id from HoraExtrato where usuario_id = :usuarioId)")
	//HoraExtrato findLastByUsuarioId(@Param("usuarioId")Long usuarioId);
}
