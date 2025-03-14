package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.ProfessorEvaluation;
import com.erneto13.ntic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfessorEvaluationRepository extends JpaRepository<ProfessorEvaluation, Long> {
    List<ProfessorEvaluation> findByProfessor(Professor professor);
    List<ProfessorEvaluation> findByEvaluator(User evaluator);
    List<ProfessorEvaluation> findByEvaluationDateBetween(LocalDate startDate, LocalDate endDate);
}