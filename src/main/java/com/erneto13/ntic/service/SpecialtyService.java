package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Specialty;
import com.erneto13.ntic.repository.SpecialtyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    // CRUD básico para especialidades
    @Transactional
    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Transactional
    public Specialty getSpecialtyById(Long id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));
    }

    @Transactional
    public Specialty getSpecialtyByName(String name) {
        return specialtyRepository.findByName(name);
    }

    @Transactional
    public Specialty createSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Transactional
    public Specialty updateSpecialty(Long id, Specialty specialtyDetails) {
        Specialty specialty = getSpecialtyById(id);
        specialty.setName(specialtyDetails.getName());
        return specialtyRepository.save(specialty);
    }

    @Transactional
    public void deleteSpecialty(Long id) {
        Specialty specialty = getSpecialtyById(id);
        specialtyRepository.delete(specialty);
    }

    @Transactional
    public Set<Professor> getSpecialtyProfessors(Long specialtyId) {
        Specialty specialty = getSpecialtyById(specialtyId);
        return specialty.getProfessors();
    }
}
