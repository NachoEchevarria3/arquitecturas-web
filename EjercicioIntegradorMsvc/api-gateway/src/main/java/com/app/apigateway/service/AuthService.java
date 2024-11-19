package com.app.apigateway.service;

import com.app.apigateway.constant.Rol;
import com.app.apigateway.dto.InicioSesionDTO;
import com.app.apigateway.dto.RegistroUsuarioDto;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.entity.MercadoPago;
import com.app.apigateway.entity.Usuario;
import com.app.apigateway.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UsuarioDTO registrarse(RegistroUsuarioDto info) {
        if (info == null) throw new IllegalArgumentException("info no puede ser nulo.");

        if (usuarioRepository.findByEmail(info.email()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }

        if (usuarioRepository.findByUsername(info.username()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese username.");
        }

        // Obtenemos cuenta a asociar
        MercadoPago mercadoPago = mercadoPagoService.iniciarSesion(new MercadoPago(info.emailMp(), info.passwordMp()));

        String encryptedPassword = passwordEncoder.encode(info.password());

        Usuario nuevoUsuario = new Usuario(
                info.email(),
                info.username(),
                encryptedPassword,
                info.nombre(),
                info.apellido(),
                info.telefono()
        );

        // Para probar con otro tipo de usuario (ADMINISTRADOR, ENCARGADO_MANTENIMIENTO) setearlo desde la BD.
        nuevoUsuario.getRoles().add(Rol.USUARIO);

        // Conectamos cuenta de usuario con la cuenta de mercado pago
        nuevoUsuario.getCuentasMercadoPago().add(mercadoPago);

        usuarioRepository.save(nuevoUsuario);
        return new UsuarioDTO(
                nuevoUsuario.getId(),
                nuevoUsuario.getEmail(),
                nuevoUsuario.getUsername(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getApellido(),
                nuevoUsuario.getTelefono(),
                nuevoUsuario.getFechaAlta(),
                nuevoUsuario.getActivo(),
                nuevoUsuario.getCuentasMercadoPago().stream().map(MercadoPago::getId).collect(Collectors.toSet())
        );
    }

    public String iniciarSesion(InicioSesionDTO inicioSesionDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                inicioSesionDto.email(), inicioSesionDto.password()
        );

        authenticationManager.authenticate(authToken);

        Usuario usuario = usuarioRepository.findByEmail(inicioSesionDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con ese email."));

        return jwtService.generateToken(usuario, generateClaims(usuario));
    }

    private Map<String, Object> generateClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", usuario.getUsername());
        claims.put("userId", usuario.getId());
        claims.put("authorities", usuario.getAuthorities());
        return claims;
    }
}
