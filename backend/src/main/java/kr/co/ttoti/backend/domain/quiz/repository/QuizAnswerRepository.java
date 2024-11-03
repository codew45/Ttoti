package kr.co.ttoti.backend.domain.quiz.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Integer> {

	List<QuizAnswer> findByTtotiIdOrderByQuizDateDesc(Integer ttotiId);

	List<QuizAnswer> findByRoomId(Integer roomId);

	QuizAnswer findByTtotiIdAndQuizDate(Integer ttotiId, LocalDate quizDate);
}
