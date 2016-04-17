package com.clinkast.qcm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinkast.qcm.entities.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query("select r from Result r where r.user.id=:userId")
    List<Result> findByUser(@Param("userId")int userId);

}
