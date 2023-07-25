package com.ues.edu.sv.clinica.Controller;


import com.ues.edu.sv.clinica.Dtos.*;
import com.ues.edu.sv.clinica.Entity.*;
import com.ues.edu.sv.clinica.Repository.IConsultaRepo;
import com.ues.edu.sv.clinica.Service.*;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    private final IMedicoService serviceMedico;

    private final IConsultaService serviceConsulta;

    private final IPacienteService pacienteService;

    private final IEspecialidadService serviceEspecialidad;

    private final IReporteExcel serviceReporte;


    private Medico medico;

    private Consulta consulta;
    private Especialidad especialidad;
    private Paciente paciente;

    private final IConsultaRepo consultaRepo;

    @GetMapping("/hateos")
    public ResponseEntity<List<ConsultaDTO>>getAllHateosConsulta(){
      List<Consulta>listConsulta=this.serviceConsulta.listar();
      List<ConsultaDTO>listConsultaDto=new ArrayList<>();
      if(listConsulta.size()>0){
          listConsulta.stream().forEach(c->{
              ConsultaDTO consultaDto=new ConsultaDTO(
                      c.getIdConsulta(),
                      c.getPaciente().getIdPaciente(),
                      c.getMedico().getIdMedico(),
                      c.getEspecialidad().getIdEspecialidad(),
                      c.getNumConsultorio(),
                      c.getFechaConsulta(),
                      c.getHoraConsulta()
              );

              Link medicoLink=linkTo(methodOn(MedicoController.class)
                      .medicoById(c.getMedico().getIdMedico())).withSelfRel();

              Link especialidadLink=linkTo(methodOn(EspecialidadController.class)
                      .especialidadById(c.getEspecialidad().getIdEspecialidad())).withSelfRel();

              Link pacienteLink=linkTo(methodOn(PacienteController.class)
                      .pacienteById(c.getPaciente().getIdPaciente())).withSelfRel();

                consultaDto.add(pacienteLink);

              consultaDto.add(medicoLink);
                consultaDto.add(especialidadLink);
                listConsultaDto.add(consultaDto);

          });
      }
    return new ResponseEntity<List<ConsultaDTO>>(listConsultaDto,HttpStatus.OK);
  }


  @GetMapping("/all")
    public ResponseEntity<List<IConsultasMedicasDtoReporte>>getConsultas(){
      List<IConsultasMedicasDtoReporte>listConsulta=this.consultaRepo.totalConsultasPacientes();
      return new ResponseEntity<>(listConsulta,HttpStatus.OK);
  }

@PostMapping("/insertar")
    public ResponseEntity<GenericResponse<ConsultaDtoIN>>guardarConsulta(@RequestBody ConsultaDtoIN consultaDtoIN){
      HttpStatus http=HttpStatus.INTERNAL_SERVER_ERROR;
      GenericResponse<ConsultaDtoIN>response=new GenericResponse<>(0,"Error al guardar la consulta",consultaDtoIN);
    Optional<ConsultaDtoIN>opt= Optional.of(consultaDtoIN);

    if(opt.isPresent()) {
        this.medico = new Medico();
        this.especialidad = new Especialidad();
        this.paciente = new Paciente();
        this.consulta = new Consulta();

        this.consulta.setMedico(this.serviceMedico.leerPorId(consultaDtoIN.getIdMedico()));
        this.consulta.setEspecialidad(this.serviceEspecialidad.leerPorId(consultaDtoIN.getIdEspecialidad()));
        this.consulta.setPaciente(this.pacienteService.leerPorId(consultaDtoIN.getIdPaciente()));
        this.consulta.setFechaConsulta(consultaDtoIN.getFechaConsulta());
        this.consulta.setHoraConsulta(consultaDtoIN.getHoraConsulta());
        this.consulta.setNumConsultorio(consultaDtoIN.getNumConsultorio());

        if (consultaDtoIN.getDetalleConsulta().size() > 0) {

            consultaDtoIN.getDetalleConsulta().stream().peek(d ->
                    d.setConsulta(consulta)).collect(Collectors.toList());
            this.consulta.setDetalleConsulta(consultaDtoIN.getDetalleConsulta());
            try {
                this.serviceConsulta.registrar(consulta);
                response.setCode(1);
                response.setMessage("Consulta registrada con exito");
                http = HttpStatus.OK;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
        return new ResponseEntity<>(response,http);

}

    @PutMapping("/modificar")
    public ResponseEntity<GenericResponse<ConsultaDtoIN>>modificarConsulta(@RequestBody ConsultaDtoIN consultaDtoIN){
        HttpStatus http=HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponse<ConsultaDtoIN>response=new GenericResponse<>(0,"Error al editar la consulta",consultaDtoIN);
        Optional<ConsultaDtoIN>opt= Optional.of(consultaDtoIN);

        if(opt.isPresent()) {
            this.medico = new Medico();
            this.especialidad = new Especialidad();
            this.paciente = new Paciente();
            this.consulta=new Consulta();
            //this.consulta = this.serviceConsulta.leerPorId(consultaDtoIN.getIdConsulta());
            this.consulta.setIdConsulta(consultaDtoIN.getIdConsulta());
            this.consulta.setMedico(this.serviceMedico.leerPorId(consultaDtoIN.getIdMedico()));
            this.consulta.setEspecialidad(this.serviceEspecialidad.leerPorId(consultaDtoIN.getIdEspecialidad()));
            this.consulta.setPaciente(this.pacienteService.leerPorId(consultaDtoIN.getIdPaciente()));
            this.consulta.setFechaConsulta(consultaDtoIN.getFechaConsulta());
            this.consulta.setHoraConsulta(consultaDtoIN.getHoraConsulta());
            this.consulta.setNumConsultorio(consultaDtoIN.getNumConsultorio());

            if (consultaDtoIN.getDetalleConsulta().size() > 0) {

                consultaDtoIN.getDetalleConsulta().stream().peek(d ->
                        d.setConsulta(consulta)).collect(Collectors.toList());
                this.consulta.setDetalleConsulta(consultaDtoIN.getDetalleConsulta());

                try {
                    this.serviceConsulta.modificar(consulta);
                    response.setCode(1);
                    response.setMessage("Consulta modificada con exito");
                    http = HttpStatus.OK;
                } catch (Exception e) {
                    System.out.println("ERROR AQUI:"+e.getMessage());
                }
            }
        }
        return new ResponseEntity<>(response,http);

    }
    @GetMapping("/dtoout")
    public ResponseEntity<List<ConsultaDtoOut>>getAllConsulta(){
        List<Consulta> consultas = this.serviceConsulta.listar();
        List<ConsultaDtoOut>consultasDtoOut = new ArrayList<>();
        if(consultas.size()>0){
            consultas.stream().forEach(c ->{
                ConsultaDtoOut consultaDtoOut = new ConsultaDtoOut(c.getIdConsulta(),
                        c.getPaciente().getNombrePaciente()+ " " +c.getPaciente().getApellidoPaciente(),
                        c.getMedico().getNombreMedico()+" " + c.getMedico().getApellidoMedico(),
                        c.getEspecialidad().getNombreEspeciadad(),c.getNumConsultorio(), c.getFechaConsulta(),
                        c.getHoraConsulta());
                consultasDtoOut.add(consultaDtoOut);
            });
        }
        return new ResponseEntity<List<ConsultaDtoOut>>(consultasDtoOut,HttpStatus.OK);
    }
@PostMapping("/insertarsinDto")
    public ResponseEntity<GenericResponse<Consulta>>guardarConsulta(@RequestBody Consulta consulta){
    GenericResponse<Consulta>resp=new GenericResponse<>(0,"Error al guardar la consulta",consulta);
    Optional<Consulta>opt=Optional.ofNullable(consulta);
    Consulta conSelect=new Consulta();
    HttpStatus http=HttpStatus.INTERNAL_SERVER_ERROR;
    if(opt.isPresent()){
        if(consulta.getDetalleConsulta().size()>0){
            consulta.getDetalleConsulta().stream()
                    .peek(dt->dt.setConsulta(consulta))
                    .collect(Collectors.toList());

            try{
                conSelect=this.serviceConsulta.registrar(consulta);
                resp.setCode(1);
                resp.setMessage("Consulta registrada con exito");
                resp.setResponse(conSelect);
            }catch (Exception e){
                resp.setMessage("Error :"+e.getMessage());
            }
        }
    }
    return new ResponseEntity<>(resp,HttpStatus.OK);
}


@GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> consultaById(@PathVariable("id")Integer id){
        Consulta consulta=this.serviceConsulta.leerPorId(id);
        ConsultaDTO consultaDTO=null;

        if(consulta!=null) {
            consultaDTO = new ConsultaDTO(
                    consulta.getIdConsulta(),
                    consulta.getPaciente().getIdPaciente(),
                    consulta.getMedico().getIdMedico(),
                    consulta.getEspecialidad().getIdEspecialidad(),
                    consulta.getNumConsultorio(),
                    consulta.getFechaConsulta(),
                    consulta.getHoraConsulta()
            );
        }
            return new ResponseEntity<>(consultaDTO,HttpStatus.OK);
        }


   @GetMapping("/pdf")
   public void listConsultaMedicoPorEspecialidad(ModelAndView model, HttpServletResponse response)throws IOException{
        this.serviceConsulta.generarReporteConsultaPorMedico(response);
   }
    @GetMapping("/excel")
    public void listConsultaMedico(ModelAndView model, HttpServletResponse response)throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Consultas.xls";
        response.setHeader(headerKey, headerValue);
        this.serviceReporte.generarExcelConsulta(response);
        response.flushBuffer();
    }

    @GetMapping("/pdfParam")
    public void listConsultaMedicoPorEspecialidadPdf(ModelAndView model, HttpServletResponse response, @RequestParam int codigoEspecialidadParam, @RequestParam String fechaConsultaParam)throws IOException{
        this.serviceConsulta.generarReporteConsultaPorEspecialidad(response,codigoEspecialidadParam,fechaConsultaParam);
    }

    @GetMapping("/pdfParam2")
    public void MedicoPorEspecialidadPdf(ModelAndView model, HttpServletResponse response, @RequestParam int idMedicoParam, @RequestParam int idEspecialidadParam)throws IOException{
        this.serviceConsulta.generarReporteConsultasMedicosyEspecialidad(response,idMedicoParam,idEspecialidadParam);
    }

    @GetMapping("/report/barra")
    public void cantidadConsultaMedico(ModelAndView model, HttpServletResponse response)throws IOException{
        this.serviceConsulta.generarReportePorConsultaGraficoBarras(response);
    }


}
