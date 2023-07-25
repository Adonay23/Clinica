package com.ues.edu.sv.clinica.Controller;



import com.ues.edu.sv.clinica.Entity.Especialidad;
import com.ues.edu.sv.clinica.Entity.GenericResponse;
import com.ues.edu.sv.clinica.Entity.Medico;
import com.ues.edu.sv.clinica.Service.IEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {

    private final IEspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<Especialidad>> mostrarEspedialidades(){
        List<Especialidad> especialidads = this.especialidadService.listar();
        return new ResponseEntity<>(especialidads, HttpStatus.OK);
    }

    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<Especialidad> especialidadById(@PathVariable("idEspecialidad") Integer idEspecialidad){
        Especialidad especialidad= this.especialidadService.leerPorId(idEspecialidad);
        return new ResponseEntity<>(especialidad,HttpStatus.OK);
    }

    @PostMapping
    public Especialidad guardaEspecialidad(@RequestBody Especialidad especialidad) {
        return this.especialidadService.registrar(especialidad);
    }

    @PutMapping
    public ResponseEntity<GenericResponse<Especialidad>> editarEspecialidad(@RequestBody Especialidad especialidad) {
        Optional<Especialidad> opt = Optional.ofNullable(this.especialidadService.leerPorId(especialidad.getIdEspecialidad()));
        GenericResponse<Especialidad> resp;
        Especialidad especialidadresponse;
        //System.out.println("prev "+medico.getIdMedico()+" "+medico.getNombreMedico()+" "+ medico.getApellidoMedico());
        if(opt.isPresent()) {
            especialidadresponse=guardaEspecialidad(especialidad);
            //System.out.println(medico.getNombreMedico()+" "+ medico.getApellidoMedico());
            resp = new GenericResponse<Especialidad>(1,"Especialidad guardado con exito",especialidadresponse);
            return new ResponseEntity<>(resp,HttpStatus.OK);
        }else {
            resp = new GenericResponse<Especialidad>(0,"Especialidad no fue guardado",especialidad);
            return new ResponseEntity<>(resp,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Especialidad>> eliminarEspecialidad(@PathVariable("id") Integer id){
        Optional<Especialidad> opt = Optional.ofNullable(this.especialidadService.leerPorId(id));
        GenericResponse<Especialidad> resp=new GenericResponse<Especialidad>();
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        if(opt.isPresent()) {
            if(this.especialidadService.eliminar(opt.get())) {
                resp.setCode(1);
                resp.setMessage("Exito - Se elimino Especialidad");
                resp.setResponse(opt.get());
            }else {
                resp.setCode(0);
                resp.setMessage("Fallo - No pudo eliminarse Especialidad");
                resp.setResponse(opt.get());
            }
        }else {
            resp.setCode(0);
            resp.setMessage("Fallo - No hay Especialidad que eliminar");
        }
        return new ResponseEntity<GenericResponse<Especialidad>>(resp,http);
    }

}
