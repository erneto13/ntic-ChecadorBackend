package com.erneto13.ntic.utils;

import com.erneto13.ntic.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GroupCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_PART_LENGTH = 3;
    private static final String SEPARATOR = "-";

    @Autowired
    private CourseRepository courseRepository;

    private final SecureRandom random = new SecureRandom();

    public String generateUniqueGroupCode() {
        String groupCode;
        boolean isUnique = false;

        do {
            groupCode = generateRandomCode();
            isUnique = !courseRepository.existsByGroupCode(groupCode);
        } while (!isUnique);

        return groupCode;
    }

    private String generateRandomCode() {
        StringBuilder firstPart = new StringBuilder(CODE_PART_LENGTH);
        StringBuilder secondPart = new StringBuilder(CODE_PART_LENGTH);

        for (int i = 0; i < CODE_PART_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            firstPart.append(CHARACTERS.charAt(randomIndex));
        }

        for (int i = 0; i < CODE_PART_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            secondPart.append(CHARACTERS.charAt(randomIndex));
        }

        return firstPart.toString() + SEPARATOR + secondPart.toString();
    }
}