package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.ProfessorEvaluation;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.service.ProfessorEvaluationService;
import com.erneto13.ntic.service.ProfessorService;
import com.erneto13.ntic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
public class ProfessorEvaluationController {

    @Autowired
    private ProfessorEvaluationService evaluationService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private UserService userService;

    // Obtener todas las evaluaciones
    @GetMapping
    public ResponseEntity<List<ProfessorEvaluation>> getAllEvaluations() {
        List<ProfessorEvaluation> evaluations = evaluationService.getAllEvaluations();
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    // Obtener evaluación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorEvaluation> getEvaluationById(@PathVariable Long id) {
        Optional<ProfessorEvaluation> evaluation = evaluationService.getEvaluationById(id);
        return evaluation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear nueva evaluación
    @PostMapping
    public ResponseEntity<ProfessorEvaluation> createEvaluation(@RequestBody ProfessorEvaluation evaluation) {
        ProfessorEvaluation savedEvaluation = evaluationService.saveEvaluation(evaluation);
        return new ResponseEntity<>(savedEvaluation, HttpStatus.CREATED);
    }

    // Actualizar evaluación existente
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorEvaluation> updateEvaluation(@PathVariable Long id, @RequestBody ProfessorEvaluation evaluation) {
        if (!evaluationService.getEvaluationById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        evaluation.setId(Math.toIntExact(id));
        ProfessorEvaluation updatedEvaluation = evaluationService.saveEvaluation(evaluation);
        return new ResponseEntity<>(updatedEvaluation, HttpStatus.OK);
    }

    // Eliminar evaluación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        if (!evaluationService.getEvaluationById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        evaluationService.deleteEvaluation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener evaluaciones por profesor
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ProfessorEvaluation>> getEvaluationsByProfessor(@PathVariable Long professorId) {
        Optional<Professor> professor = professorService.getProfessorById(professorId);
        if (professor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProfessorEvaluation> evaluations = evaluationService.getEvaluationsByProfessor(professor.get());
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    // Obtener evaluaciones por evaluador
    @GetMapping("/evaluator/{evaluatorId}")
    public ResponseEntity<List<ProfessorEvaluation>> getEvaluationsByEvaluator(@PathVariable Long evaluatorId) {
        Optional<User> evaluator = userService.getUserById(evaluatorId);
        if (evaluator.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProfessorEvaluation> evaluations = evaluationService.getEvaluationsByEvaluator(evaluator.get());
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    // Obtener evaluaciones por rango de fechas
    @GetMapping("/date-range")
    public ResponseEntity<List<ProfessorEvaluation>> getEvaluationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ProfessorEvaluation> evaluations = evaluationService.getEvaluationsByDateRange(startDate, endDate);
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    // Calcular puntaje promedio para un profesor
    @GetMapping("/average-score/{professorId}")
    public ResponseEntity<Double> calculateAverageScore(@PathVariable Long professorId) {
        Optional<Professor> professor = professorService.getProfessorById(professorId);
        if (professor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        double averageScore = evaluationService.calculateAverageScore(professor.get());
        return new ResponseEntity<>(averageScore, HttpStatus.OK);
    }
}