package com.example.demo.controller;

import jakarta.validation.Valid;

import com.example.demo.domain.consulta.AgendaDeConsultaService;
import com.example.demo.domain.consulta.DatosAgendarConsulta;
import com.example.demo.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        var dto= service.agendar(datos);
        return ResponseEntity.ok(dto);
    }


}
