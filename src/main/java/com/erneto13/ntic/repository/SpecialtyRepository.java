package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Specialty findByName(String name);
}
