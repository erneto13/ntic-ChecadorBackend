package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    Career findByName(String name);
}
