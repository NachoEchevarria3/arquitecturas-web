package com.app.microparada.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paradas")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Parada {
    @Id
    private String id;

    private String ubicacion;

    public Parada(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
