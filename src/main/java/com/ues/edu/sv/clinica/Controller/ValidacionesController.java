package com.ues.edu.sv.clinica.Controller;

import com.ues.edu.sv.clinica.Dtos.PacienteValidDTO;
import com.ues.edu.sv.clinica.Entity.GenericResponse;
import com.ues.edu.sv.clinica.Entity.Paciente;
import com.ues.edu.sv.clinica.Exceptions.InvalidDataException;
import com.ues.edu.sv.clinica.Service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/validar")
public class ValidacionesController {

    private final IPacienteService pacienteService;

    public ValidacionesController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/insertar")
    public ResponseEntity<GenericResponse<PacienteValidDTO>> guardarPaciente(@Valid @RequestBody PacienteValidDTO paciente) {

        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<PacienteValidDTO> response = new GenericResponse<>(0, "ERROR DE ALMACENAMIENTO DEL PACIENTE", paciente);
        Optional<PacienteValidDTO> opt = Optional.of(paciente);
        if (opt.isPresent()) {
            Paciente paciente1 = new Paciente();
            paciente1.setNombrePaciente(paciente.getNombrePaciente());
            paciente1.setApellidoPaciente(paciente.getApellidoPaciente());
            paciente1.setIdentPaciente(paciente.getIdentPaciente());
            paciente1.setDireccionPaciente(paciente.getDireccionPaciente());
            paciente1.setTelefonoPaciente(paciente.getTelefonoPaciente());
            paciente1.setEmailPaciente(paciente.getEmailPaciente());
            paciente1.setTieneExpediente(paciente.isTieneExpediente());
            paciente1.setFechaNacimiento(paciente.getFechaNacimiento());
            paciente1.setFechaVencimientoDui(paciente.getFechaVencimientoDui());

            try {
                pacienteService.registrar(paciente1);
                response.setCode(1);
                response.setMessage("Paciente almacenado con exito");
                http = HttpStatus.OK;
            } catch (Exception e) {
                response.setMessage(e.getMessage());
                System.out.println(e.getMessage());
            }

        }
        return new ResponseEntity<>(response, http);

    }

    @PostMapping
    public ResponseEntity<PacienteValidDTO> guardarPaciente
            (@Valid @RequestBody PacienteValidDTO paciente,BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.ok(paciente);
    }

}
