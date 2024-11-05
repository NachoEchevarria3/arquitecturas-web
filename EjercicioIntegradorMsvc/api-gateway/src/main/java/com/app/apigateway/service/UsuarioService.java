package com.app.apigateway.service;

import com.app.apigateway.dto.RegistroUsuarioDto;
import com.app.apigateway.entity.MercadoPago;
import com.app.apigateway.entity.Usuario;
import com.app.apigateway.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    public Usuario registrar(RegistroUsuarioDto info) {
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

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese id."));
    }

    public void asociarMercadoPago(Long usuarioId, MercadoPago cuentaMercadoPago) {
        Usuario usuario = findById(usuarioId);
        MercadoPago mercadoPago = mercadoPagoService.iniciarSesion(cuentaMercadoPago);

        usuario.getCuentasMercadoPago().add(mercadoPago);
        usuarioRepository.save(usuario);
    }

    public void desacociarMercadoPago(Long usuarioId, Long mercadoPagoId) {
        Usuario usuario = findById(usuarioId);
        MercadoPago mercadoPago = mercadoPagoService.findById(mercadoPagoId);

        usuario.getCuentasMercadoPago().remove(mercadoPago);
        usuarioRepository.save(usuario);
    }
}