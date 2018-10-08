package com.packageIxia.sistemaControleEscala.daos.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.packageIxia.sistemaControleEscala.models.usuario.Usuario;

@Repository
public interface UsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByMatricula(String matricula);
	public boolean existsByMatricula(String matricula);
	public boolean existsByEmail(String email);
	public Usuario findByEmail(String email);
	
	@Query("select u from Usuario u inner join Funcao f on funcao_id = f.id where f.perfilAcessoId=:perfilAcessoId and excluido=:excluido")
	public List<Usuario> findAllByPerfilAcessoIdAndExcluido(@Param("perfilAcessoId")int perfilAcessoId, @Param("excluido")boolean excluido);
	
	public Usuario findByCpf(String cpf);
	public Iterable<Usuario> findAllByExcluido(boolean excluido);
	public Iterable<Usuario> findAllByFuncaoId(int id);
	
	@Query("select u from Usuario u inner join Funcao f on funcao_id = f.id where f.perfilAcessoId in (:perfilAcessoIds) and excluido=:excluido")
	public List<Usuario> findAllByPerfilAcessoIdAndExcluido(@Param("perfilAcessoIds")int[] ids, @Param("excluido")boolean excluido);

}
