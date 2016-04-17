package com.clinkast.qcm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinkast.qcm.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    // recherche d'un rôle via son nom
    @Query("select r from Role r where r.name = :name")
    Role findRoleByName(@Param("name") String name);

}
