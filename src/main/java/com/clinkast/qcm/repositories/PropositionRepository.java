package com.clinkast.qcm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinkast.qcm.entities.Proposition;

public interface PropositionRepository extends JpaRepository<Proposition, Integer> {

    @Query("select p from Proposition p where p.question.id=:questionId")
    List<Proposition> findByQuestion(@Param("questionId") int questionId);

}
