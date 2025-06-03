package com.erneto13.ntic.service;

import com.erneto13.ntic.model.ClassSession;
import com.erneto13.ntic.repository.ClassSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassSessionService {

    @Autowired
    private ClassSessionRepository csessionRepository;

    @Transactional
    public List<ClassSession> getAllClassSessions() {
        return csessionRepository.findAll();
    }

    @Transactional
    public ClassSession createClassSession(ClassSession classSession) {
        return csessionRepository.save(classSession);
    }

    @Transactional
    public ClassSession getClassSessionById(Long id) {
        return csessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada con id: " + id));
    }

    @Transactional
    public void deleteClassSession(Long id) {
        ClassSession subject = getClassSessionById(id);
        csessionRepository.delete(subject);
    }
}
