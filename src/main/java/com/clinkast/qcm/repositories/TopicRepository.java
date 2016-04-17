package com.clinkast.qcm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinkast.qcm.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
