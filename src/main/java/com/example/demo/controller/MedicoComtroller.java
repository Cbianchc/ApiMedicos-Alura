package com.example.demo.controller;

import com.example.demo.domain.direccion.DatosDireccion;
import com.example.demo.domain.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoComtroller {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentBuilder){
        //System.out.println("Nuevo request con record de medico");
        //medicoRepository.save(new Medico(datosRegistroMedico));
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        );
        URI url = uriComponentBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    //GET----------------------------------------------------------------------------------------
    //Este es el primer GET, para traer junto con el findAll
    //@GetMapping
    //public List<Medico> listaMedicos(){
    //    return medicoRepository.findAll();
    //}

    //Ahora viene como hacer solo por partes
    //@GetMapping
    //public List<DatosListadoMedico> listaMedicos(){
    //    return medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();
    //}

    //Lo mismo pero con paginacion
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listaMedicos(Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    //PUT UPDATE------------------------------------------------------------------------------
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        ));
    }

    //DELETE Full de la base -----------------------------------------------------------------
    //@DeleteMapping("/{id}")
    //@Transactional
    //public void eliminarMedico(@PathVariable Long id) {
    //    Medico medico = medicoRepository.getReferenceById(id);
    //    medicoRepository.delete(medico);
    //}

    //DELETE LOGICO - sin borrar la base
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    //Metodo para entrar a la url que te da header, cuando creas medico
    @GetMapping ("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad().toString(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        );
        return ResponseEntity.ok(datosMedicos);
    }


}
