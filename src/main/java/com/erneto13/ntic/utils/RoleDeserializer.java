package com.erneto13.ntic.utils;

import com.erneto13.ntic.model.Role;
import com.erneto13.ntic.repository.RoleRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleDeserializer extends JsonDeserializer<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String roleStr = p.getValueAsString();
        try {
            // Intenta convertir el string a enum
            Role.ERole eRole = Role.ERole.valueOf(roleStr);
            // Busca el rol por su nombre enum
            return roleRepository.findByName(eRole)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        } catch (IllegalArgumentException e) {
            try {
                // Si no es un enum válido, intenta con el ID
                int roleId = Integer.parseInt(roleStr);
                return roleRepository.findById((long) roleId)
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado con ID: " + roleId));
            } catch (NumberFormatException ex) {
                throw new RuntimeException("Error: Formato de rol inválido: " + roleStr);
            }
        }
    }
}