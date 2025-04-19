package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Career;
import com.erneto13.ntic.model.Subject;
import com.erneto13.ntic.repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    public Career getCareerById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public Career getCareerByName(String name) {
        return careerRepository.findByName(name);
    }

    public Career createCareer(Career career) {
        return careerRepository.save(career);
    }

    public Career updateCareer(Long id, Career career) {
        Career existingCareer = careerRepository.findById(id).orElse(null);
        if (existingCareer != null) {
            existingCareer.setName(career.getName());
            existingCareer.setSubjects(career.getSubjects());
            return careerRepository.save(existingCareer);
        }
        return null;
    }

    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }

    public Set<Subject> getCareerSubjects(Long id) {
        Career career = careerRepository.findById(id).orElse(null);
        if (career != null) {
            return career.getSubjects();
        }
        return null;
    }
}