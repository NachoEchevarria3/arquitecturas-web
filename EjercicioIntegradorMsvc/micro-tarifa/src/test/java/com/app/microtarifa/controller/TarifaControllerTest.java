package com.app.microtarifa.controller;

import com.app.microtarifa.constant.Rol;
import com.app.microtarifa.dto.ActualizarTarifaDTO;
import com.app.microtarifa.dto.CreateTarifaDTO;
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

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class TarifaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private String email;
    private String password;

    private Rol rolUsuario = Rol.ADMINISTRADOR;

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
    public void testGetTarifaActualIs200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarifa/actual")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getHistorialTarifasis200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarifa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createTarifaIs201() throws Exception {
        CreateTarifaDTO createTarifaDto = new CreateTarifaDTO(
                50.0,
                2.5,
                3.0
        );

        String jsonRequest = objectMapper.writeValueAsString(createTarifaDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tarifa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void actualizarTarifaIs200() throws Exception {
        ActualizarTarifaDTO actualizarTarifaDto = new ActualizarTarifaDTO(
                50.0,
                2.5,
                3.0,
                LocalDate.of(2024, 11, 25)
        );

        String jsonRequest = objectMapper.writeValueAsString(actualizarTarifaDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tarifa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
