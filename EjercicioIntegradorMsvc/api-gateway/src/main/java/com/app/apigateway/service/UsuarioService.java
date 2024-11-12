package com.app.apigateway.service;

import com.app.apigateway.dto.RegistroUsuarioDto;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.entity.MercadoPago;
import com.app.apigateway.entity.Usuario;
import com.app.apigateway.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    public UsuarioDTO registrar(RegistroUsuarioDto info) {
        if (usuarioRepository.findByEmail(info.email()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }

        // Obtenemos cuenta a asociar
        MercadoPago mercadoPago = mercadoPagoService.iniciarSesion(new MercadoPago(info.emailMp(), info.passwordMp()));

        Usuario nuevoUsuario = new Usuario(
                info.nombre(),
                info.apellido(),
                info.email(),
                info.password(),
                info.telefono()
        );

        // Conectamos cuenta de usuario con la cuenta de mercado pago
        nuevoUsuario.getCuentasMercadoPago().add(mercadoPago);

        usuarioRepository.save(nuevoUsuario);
        return new UsuarioDTO(
                nuevoUsuario.getId(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getApellido(),
                nuevoUsuario.getEmail(),
                nuevoUsuario.getTelefono(),
                nuevoUsuario.getFechaAlta(),
                nuevoUsuario.getActivo(),
                nuevoUsuario.getCuentasMercadoPago().stream().map(MercadoPago::getId).collect(Collectors.toSet())
        );
    }

    public UsuarioDTO findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        Usuario usuario =  usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
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
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getFechaAlta(),
                usuario.getActivo(),
                usuario.getCuentasMercadoPago().stream().map(MercadoPago::getId).collect(Collectors.toSet())
        );
    }
}