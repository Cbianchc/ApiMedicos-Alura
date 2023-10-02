package com.example.demo.domain.consulta;

import com.example.demo.domain.consulta.validaciones.ValidadorDeConsultas;
import com.example.demo.domain.medico.Medico;
import com.example.demo.domain.medico.MedicoRepository;
import com.example.demo.domain.paciente.PacienteRepository;
import com.example.demo.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        if(medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(medico,paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }




    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
