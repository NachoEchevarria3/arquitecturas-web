package com.app.apigateway.controller;

import com.app.apigateway.constant.Rol;
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
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
    public void getUsuariosIs200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCuentasMercadoPagoByUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario/2/cuentas-mp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAnularCuenta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/usuario/3/anular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
