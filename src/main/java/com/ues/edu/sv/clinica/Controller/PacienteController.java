package com.ues.edu.sv.clinica.Controller;

import com.ues.edu.sv.clinica.Entity.GenericResponse;
import com.ues.edu.sv.clinica.Entity.Paciente;
import com.ues.edu.sv.clinica.Exceptions.ModeloNotFoundException;
import com.ues.edu.sv.clinica.Service.IPacienteService;
import com.ues.edu.sv.clinica.ServiceImpl.ReportesServicesExcelImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paciente")
@CrossOrigin(origins = "http://localhost:4200")
public class PacienteController {

    private final IPacienteService pacienteService;

    private final ReportesServicesExcelImpl reportesServicesExcelImpl;

    @GetMapping
    public ResponseEntity<List<Paciente>> mostrarEspecialidad(){
        List<Paciente> pacientes = this.pacienteService.listar();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{idpaciente}")
    public ResponseEntity<Paciente> pacienteById(@PathVariable("idpaciente") Integer idPaciente){
        System.out.println("idpaciente: "+idPaciente);
        Paciente paciente= this.pacienteService.leerPorId(idPaciente);

        if(paciente.getIdPaciente()==null){
            throw new ModeloNotFoundException("Paciente no encontrado");
        }

        return new ResponseEntity<>(paciente,HttpStatus.OK);
    }

    @GetMapping("/pdfPaciente")
    public void pacientePdf(ModelAndView model, HttpServletResponse response) throws Exception {
    	this.pacienteService.generarReportePacientes(response);
    }


    @GetMapping("/pdfGraficaPaciente")
    public void pacientegrafica(ModelAndView model, HttpServletResponse response) throws Exception {
        this.pacienteService.generarReportePacientesGraficaLinea(response);
    }




    //Ingresar informacion
    @PostMapping
    public Paciente guardarPaciente(@RequestBody Paciente paciente) {
        return this.pacienteService.registrar(paciente);
    }

    // editar informacion existente
    @PutMapping
    public ResponseEntity<GenericResponse<Paciente>> editarPaciente(@RequestBody Paciente paciente) {
        Optional<Paciente> opt = Optional.ofNullable(this.pacienteService.leerPorId(paciente.getIdPaciente()));
        GenericResponse<Paciente> resp;
        Paciente medicoResponse;
      //  System.out.println("prev "+medico.getIdMedico()+" "+medico.getNombreMedico()+" "+ medico.getApellidoMedico());
        if(opt.isPresent()) {
            medicoResponse=guardarPaciente(paciente);
        //    System.out.println(medico.getNombreMedico()+" "+ medico.getApellidoMedico());
            resp = new GenericResponse<Paciente>(1,"Paciente guardado con exito",medicoResponse);
            return new ResponseEntity<GenericResponse<Paciente>>(resp,HttpStatus.OK);
        }else {
            resp = new GenericResponse<Paciente>(0,"Paciente no fue guardado",paciente);
            return new ResponseEntity<GenericResponse<Paciente>>(resp,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Eliminar medicos
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Paciente>> eliminarPaciente(@PathVariable("id") Integer id){
        Optional<Paciente> opt = Optional.ofNullable(this.pacienteService.leerPorId(id));
        GenericResponse<Paciente> resp=new GenericResponse<Paciente>();
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        if(opt.isPresent()) {
            if(this.pacienteService.eliminar(opt.get())) {
                resp.setCode(1);
                resp.setMessage("Exito - Se elimino Medico");
                resp.setResponse(opt.get());
            }else {
                resp.setCode(0);
                resp.setMessage("Fallo - No pudo eliminarse medico");
                resp.setResponse(opt.get());
            }
        }else {
            resp.setCode(0);
            resp.setMessage("Fallo - No hay medico que eliminar");
        }
        return new ResponseEntity<GenericResponse<Paciente>>(resp,http);
    }


    @GetMapping("/excel")
    public void generarReporteExcel(HttpServletResponse response)throws Exception
    {
    	response.setContentType("application/octet-stream");
    	String headerKey = "Content-Disposition";
    	String headerValue = "attachment; filename=pacientes.xls";
    	response.setHeader(headerKey, headerValue);

        reportesServicesExcelImpl.generateExcel(response);
        response.flushBuffer();
    }


}
