package com.example.demo.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import com.example.demo.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(

        @NotNull
        Long idPaciente,
        Long idMedico,
        @NotNull
        @Future
        LocalDateTime fecha,
        Especialidad especialidad) {

}
