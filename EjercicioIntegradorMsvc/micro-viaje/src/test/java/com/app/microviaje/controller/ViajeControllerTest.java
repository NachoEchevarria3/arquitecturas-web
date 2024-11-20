package com.app.microviaje.controller;

import com.app.microviaje.constant.Rol;
import com.app.microviaje.dto.ComenzarViajeDTO;
import com.app.microviaje.dto.FinalizarViajeDTO;
import com.app.microviaje.dto.ReanudarViajeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ViajeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private String email;
    private String password;

    private Rol rolUsuario = Rol.USUARIO;

    @BeforeEach
    public void loginAndSetToken() throws Exception {
        if (rolUsuario.equals(Rol.ADMINISTRADOR)) {
            email = "testAdmin@gmail.com";
            password = "testAdmin";
        } else if (rolUsuario.equals(Rol.USUARIO)) {
            email = "testUser@gmail.com";
            password = "testUser";
        }

        String loginPayload = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password);
        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/iniciar-sesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        token = new ObjectMapper().readTree(loginResult.getResponse()
                        .getContentAsString())
                .get("data")
                .get("token")
                .asText();
    }

    @Test
    public void testIniciarViaje() throws Exception {
        ComenzarViajeDTO comenzarViajeDto = new ComenzarViajeDTO(
                1L,
                2L,
                2L
        );

        String jsonRequest = objectMapper.writeValueAsString(comenzarViajeDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/viaje")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPausarViaje() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/viaje/1/pausar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testReanudarViaje() throws Exception {
        FinalizarViajeDTO finalizarViajeDto = new FinalizarViajeDTO(
                "fnaeu9fn",
                300
        );

        String jsonRequest = objectMapper.writeValueAsString(finalizarViajeDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/viaje/1/finalizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
