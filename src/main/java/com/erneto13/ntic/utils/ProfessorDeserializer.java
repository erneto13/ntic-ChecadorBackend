package com.erneto13.ntic.utils;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.repository.ProfessorRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProfessorDeserializer extends JsonDeserializer<Professor> {

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public Professor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (p.isExpectedStartObjectToken()) {
            return ctxt.readValue(p, Professor.class);
        } else {
            int professorId = p.getValueAsInt();
            return professorRepository.findById((long) professorId)
                    .orElseThrow(() -> new RuntimeException("Professor no encontrado con ID: " + professorId));
        }
    }
}