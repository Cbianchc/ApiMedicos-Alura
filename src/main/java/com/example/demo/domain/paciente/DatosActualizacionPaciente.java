package com.example.demo.domain.paciente;

import jakarta.validation.constraints.NotNull;
import com.example.demo.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion) {
}