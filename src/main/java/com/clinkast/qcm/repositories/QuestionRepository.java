package com.clinkast.qcm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinkast.qcm.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select q from Question q where q.topic.id=:topicId")
    List<Question> findByTopic(@Param("topicId") int topicId);

    @Query("select q from Question q where q.topic.id=:topicId")
    List<Question> findByTopicAndLimit(int topicId, int numberOfQuestions);

    @Query("select q from Question q where q.topic.id=:topicId and q.level=:level")
    List<Question> findByTopicAndLevel(@Param("topicId")int topicId,@Param("level") int level);

}
