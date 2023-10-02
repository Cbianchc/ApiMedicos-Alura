package com.example.demo.domain.consulta.validaciones.desafio;

import com.example.demo.domain.consulta.Consulta;
import com.example.demo.domain.consulta.ConsultaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia extends ValidadorCancelamientoDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar() {
        var consulta :Consulta = repository.getReferenceById((datos.idConsulta());
        var ahora :LocalDateTime = LocalDateTime.now();
        var diferenciaEnHoras :Long = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24) {
            throw  new ValidationException("La consulta solo puede ser cancelada con minimo 24hs de antelacion.")
        }
    }
}
