package com.kjvargas.admusers.Repositories;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
