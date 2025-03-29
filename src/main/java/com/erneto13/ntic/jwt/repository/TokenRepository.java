package com.erneto13.ntic.jwt.repository;

import com.erneto13.ntic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
                SELECT t FROM Token t 
                INNER JOIN t.user u
                WHERE u.id = :id AND (t.isExpired = false OR t.isRevoked = false)
            """)
    List<Token> findAllValidTokenByUser(Integer id);


    Optional<Token> findByToken(String token);
    List<Token> findAllByUser(User user);
    void deleteAllByUserId(Integer userId);
}
