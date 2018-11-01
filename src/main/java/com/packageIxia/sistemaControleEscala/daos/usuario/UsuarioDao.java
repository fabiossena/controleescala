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
	
//	@Query("select u from Usuario u inner join Funcao f ON f.id = funcao_id left join UsuarioFuncaoAdicional ufa on u.id = usuario_id where (perfil_acesso_id=:perfilAcessoId OR f.perfilAcessoId:perfilAcessoId=:perfilAcessoId) and excluido=:excluido")
//	public List<Usuario> findAllByPerfilAcessoIdAndExcluido(@Param("perfilAcessoId")int perfilAcessoId, @Param("excluido")boolean excluido);
	
	public Usuario findByCpf(String cpf);
	public Iterable<Usuario> findAllByExcluido(boolean excluido);
	public Iterable<Usuario> findAllByFuncaoId(int id);
	
//	@Query("select u from Usuario u inner join Funcao f ON f.id = funcao_id left join UsuarioFuncaoAdicional ufa on u.id = usuario_id where (perfil_acesso_id in (:perfilAcessoIds)  OR f.perfilAcessoId=:perfilAcessoId in (:perfilAcessoIds)) and excluido=:excluido")
//	public List<Usuario> findAllByPerfilAcessoIdAndExcluido(@Param("perfilAcessoIds")List<Integer> ids, @Param("excluido")boolean excluido);

}
