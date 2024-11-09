package com.app.microparada.service;

import com.app.microparada.client.MonopatinClient;
import com.app.microparada.dto.ApiResponse;
import com.app.microparada.dto.CreateParadaDTO;
import com.app.microparada.dto.MonopatinDTO;
import com.app.microparada.dto.ParadaDTO;
import com.app.microparada.entity.Parada;
import com.app.microparada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {
    @Autowired
    private ParadaRepository paradaRepository;

    @Autowired
    private MonopatinClient monopatinClient;

    public void create(CreateParadaDTO createParadaDTO) {
        if (createParadaDTO == null) throw new IllegalArgumentException("createParadaDTO no puede ser nulo.");
        paradaRepository.save(new Parada(createParadaDTO.ubicacion()));
    }

    public List<ParadaDTO> findAll() {
        List<Parada> paradas = paradaRepository.findAll();

        if (paradas.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron paradas");
        }

        return paradas.stream().map(p -> new ParadaDTO(p.getId(), p.getUbicacion())).toList();
    }

    public ParadaDTO findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro la parada con el id " + id));
        return new ParadaDTO(parada.getId(), parada.getUbicacion());
    }

    public void deleteById(Long id) {
        ApiResponse<List<MonopatinDTO>> monopatinesDeParada = monopatinClient.getMonopatinesByParadaId(id);

        if (!monopatinesDeParada.data().isEmpty()) throw new IllegalArgumentException("No se puede eliminar la parada porque tiene monopatines.");

        paradaRepository.deleteById(id);
    }
}
