package com.app.micromonopatin.constant;

public enum TipoReporte {
    KILOMETROS("kilometros"),
    TIEMPO_CON_PAUSAS("conPausa"),
    TIEMPO_SIN_PAUSAS("sinPausa");

    private final String valor;

    // Constructor privado
    TipoReporte(String valor) {
        this.valor = valor;
    }

    // Método para obtener el valor personalizado
    public String getValor() {
        return valor;
    }
}
