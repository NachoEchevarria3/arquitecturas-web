package com.app.apigateway.service;

import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.entity.MercadoPago;
import com.app.apigateway.entity.Usuario;
import com.app.apigateway.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

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
                        u.getCuentasMercadoPago().stream().map(MercadoPago::getId).collect(Collectors.toSet())
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
                usuario.getCuentasMercadoPago().stream().map(MercadoPago::getId).collect(Collectors.toSet())
        );
    }

    public void asociarMercadoPago(Long usuarioId, MercadoPago cuentaMercadoPago) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));
        MercadoPago mercadoPago = mercadoPagoService.iniciarSesion(cuentaMercadoPago);

        usuario.getCuentasMercadoPago().add(mercadoPago);
        usuarioRepository.save(usuario);
    }

    public void desacociarMercadoPago(Long usuarioId, Long mercadoPagoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));
        MercadoPago mercadoPago = mercadoPagoService.findById(mercadoPagoId);

        usuario.getCuentasMercadoPago().remove(mercadoPago);
        usuarioRepository.save(usuario);
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
                usuario.getCuentasMercadoPago().stream().map(MercadoPago::getId).collect(Collectors.toSet())
        );
    }
}