package com.app.micromantmonopatin.controller;


import com.app.micromantmonopatin.constant.Rol;
import com.app.micromantmonopatin.dto.CreateMantenimientoDTO;
import com.app.micromantmonopatin.dto.FinalizarMantenimientoDTO;
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
public class MantenimientoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private String email;
    private String password;

    private Rol rolUsuario = Rol.ENCARGADO_MANTENIMIENTO;

    @BeforeEach
    public void loginAndSetToken() throws Exception {
        if (rolUsuario.equals(Rol.ADMINISTRADOR)) {
            email = "testAdmin@gmail.com";
            password = "testAdmin";
        } else if (rolUsuario.equals(Rol.USUARIO)) {
            email = "testUser@gmail.com";
            password = "testUser";
        } else if (rolUsuario.equals(Rol.ENCARGADO_MANTENIMIENTO)) {
            email = "testEncargadoMant@gmail.com";
            password = "testEncargadoMant";
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
    public void testGetMantenimientos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mantenimiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMantenimientosByIdMonopatin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mantenimiento/monopatin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testIniciarMantenimiento() throws Exception {
        CreateMantenimientoDTO createMantenimientoDto = new CreateMantenimientoDTO(
                1L
        );

        String jsonRequest = objectMapper.writeValueAsString(createMantenimientoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mantenimiento/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFinalizarMantenimiento() throws Exception {
        FinalizarMantenimientoDTO finalizarMantenimientoDto = new FinalizarMantenimientoDTO(
                1L,
                "cjqef98uhjq8"
        );

        String jsonRequest = objectMapper.writeValueAsString(finalizarMantenimientoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mantenimiento/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
