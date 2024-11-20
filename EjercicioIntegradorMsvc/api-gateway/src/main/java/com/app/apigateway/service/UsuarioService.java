package com.app.apigateway.service;

import com.app.apigateway.dto.MercadoPagoDTO;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.entity.MercadoPago;
import com.app.apigateway.entity.Usuario;
import com.app.apigateway.exception.UnauthorizedException;
import com.app.apigateway.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private JwtService jwtService;

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron usuarios.");
        }

        return usuarios.stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getTelefono(),
                        u.getFechaAlta(),
                        u.getActivo(),
                        u.getCuentasMercadoPago().stream()
                                .map(mp -> new MercadoPagoDTO(mp.getId(), mp.getUsername())).toList()
                ))
                .toList();
    }

    public UsuarioDTO findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        Usuario usuario =  usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getTelefono(),
                usuario.getFechaAlta(),
                usuario.getActivo(),
                usuario.getCuentasMercadoPago().stream()
                        .map(mp -> new MercadoPagoDTO(mp.getId(), mp.getUsername())).toList()
        );
    }

    public UsuarioDTO anularCuenta(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));

        usuario.setActivo(false);

        usuarioRepository.save(usuario);

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getTelefono(),
                usuario.getFechaAlta(),
                usuario.getActivo(),
                usuario.getCuentasMercadoPago().stream()
                        .map(mp -> new MercadoPagoDTO(mp.getId(), mp.getUsername())).toList()
        );
    }

    public void asociarCuentaMercadoPago(Long usuarioId, Long mercadoPagoId, String jwt) {
        if (!jwtService.extractUserId(jwt).equals(usuarioId)) throw new UnauthorizedException("No tienes autorización para acceder a este recurso.");
        if (usuarioId == null || usuarioId <= 0) throw new IllegalArgumentException("ID de usuario invalido.");

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));
        MercadoPago mercadoPago = mercadoPagoService.findById(mercadoPagoId);

        List<MercadoPago> cuentasMercadoPago = usuario.getCuentasMercadoPago();

        if (cuentasMercadoPago.contains(mercadoPago)) throw new IllegalArgumentException("El usuario ya tiene asociada esa cuenta de mercado pago.");

        cuentasMercadoPago.add(mercadoPago);

        usuarioRepository.save(usuario);
    }

    public List<MercadoPagoDTO> getCuentasMercadoPago(Long usuarioId, String jwt) {
        if (!jwtService.extractUserId(jwt).equals(usuarioId)) throw new UnauthorizedException("No tienes autorización para acceder a este recurso.");
        if (usuarioId == null || usuarioId <= 0) throw new IllegalArgumentException("ID de usuario invalido.");

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));

        List<MercadoPago> cuentasMercadoPago = usuario.getCuentasMercadoPago();

        return cuentasMercadoPago.stream()
                .map(mp -> new MercadoPagoDTO(mp.getId(), mp.getUsername()))
                .toList();
    }
}