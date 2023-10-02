package com.example.demo.domain.paciente;

import com.example.demo.domain.direccion.Direccion;

public record DatosDetallesPaciente(Long id, String nombre, String email, String documento, String telefono, Direccion direccion) {

    public DatosDetallesPaciente(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento(),
                paciente.getTelefono(),
                paciente.getDireccion());
    }
}
