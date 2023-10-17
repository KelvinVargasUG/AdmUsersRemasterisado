package com.kjvargas.admusers.Repositories;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "execute KJVARGAS.SAVEUSUARIO(" +
            ":p_nombre" +
            ":p_apellido " +
            ":p_email " +
            ":p_password)", nativeQuery = true)
    public void insert(
            @Param("p_nombre") String nombre,
            @Param("p_apellido") String apellido,
            @Param("p_email") String email,
            @Param("p_password") String password
    );

}
