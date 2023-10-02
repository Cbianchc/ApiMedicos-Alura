package com.example.demo.domain.medico;

import com.example.demo.domain.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarMedico(
        @NotNull //el id es el unico obligatorio, notnull para asegurarse.
        Long id,
        String nombre,
        String documento,
        DatosDireccion direccion) {
}
