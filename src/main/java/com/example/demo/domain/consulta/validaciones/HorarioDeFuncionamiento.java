package com.example.demo.domain.consulta.validaciones;

import com.example.demo.domain.consulta.DatosAgendarConsulta;
import jakarta.validation.ValidationException;

import java.time.DayOfWeek;

public class HorarioDeFuncionamiento implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos) {

        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        var antesdDeApertura=datos.fecha().getHour()<7;
        var despuesDeCierre=datos.fecha().getHour()>19;

        if(domingo || antesdDeApertura || despuesDeCierre){
            throw  new ValidationException("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
