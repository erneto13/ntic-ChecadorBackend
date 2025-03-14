package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.ProfessorEvaluation;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.ProfessorEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorEvaluationService {

    @Autowired
    private ProfessorEvaluationRepository evaluationRepository;

    public List<ProfessorEvaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Optional<ProfessorEvaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    public List<ProfessorEvaluation> getEvaluationsByProfessor(Professor professor) {
        return evaluationRepository.findByProfessor(professor);
    }

    public List<ProfessorEvaluation> getEvaluationsByEvaluator(User evaluator) {
        return evaluationRepository.findByEvaluator(evaluator);
    }

    public List<ProfessorEvaluation> getEvaluationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return evaluationRepository.findByEvaluationDateBetween(startDate, endDate);
    }

    public ProfessorEvaluation saveEvaluation(ProfessorEvaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    public double calculateAverageScore(Professor professor) {
        List<ProfessorEvaluation> evaluations = evaluationRepository.findByProfessor(professor);
        if (evaluations.isEmpty()) {
            return 0.0;
        }

        int totalScore = evaluations.stream()
                .mapToInt(ProfessorEvaluation::getScore)
                .sum();

        return (double) totalScore / evaluations.size();
    }
}