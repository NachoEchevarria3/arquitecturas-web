package com.app.micromonopatin.controller;

import com.app.micromonopatin.constant.Rol;
import com.app.micromonopatin.dto.CreateMonopatinDTO;
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
public class MonopatinControllerTest {
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
    public void testGetMonopatines() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetReporteMonopatines() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin/reporte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMonopatinesConMinimoDeViajes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin/minimo-viajes/3/anio/2024")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMonopatinesDisponiblesVsMantenimiento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin/disponibles-vs-mantenimiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMonopatinById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMonopatinesByParadaId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin/parada/fbnf7qwb")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMonopatinesByUbicacion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monopatin/ubicacion/terminal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateMonopatin() throws Exception {
        CreateMonopatinDTO createMonopatinDto = new CreateMonopatinDTO(
                150,
                200,
                "ne8cwb"
        );

        String jsonRequest = objectMapper.writeValueAsString(createMonopatinDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/monopatin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteMonopatin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/monopatin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
