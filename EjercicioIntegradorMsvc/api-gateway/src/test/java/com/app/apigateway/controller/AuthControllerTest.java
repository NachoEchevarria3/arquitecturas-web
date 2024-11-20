package com.app.apigateway.controller;

import com.app.apigateway.dto.InicioSesionDTO;
import com.app.apigateway.dto.RegistroUsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegistrarse() throws Exception {
        RegistroUsuarioDTO registroUsuarioDto = new RegistroUsuarioDTO(
                "felipePerez",
                "feli9021@gmail.com",
                "12345",
                "Felipe",
                "Perez",
                "2463854872",
                "testUserMp@gmail.com",
                "testUserMp"
        );

        String jsonRequest = objectMapper.writeValueAsString(registroUsuarioDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registrarse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testIniciarSesion() throws Exception {
        InicioSesionDTO inicioSesionDto = new InicioSesionDTO(
                "feli9021@gmail.com",
                "12345"
        );

        String jsonRequest = objectMapper.writeValueAsString(inicioSesionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/iniciar-sesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
