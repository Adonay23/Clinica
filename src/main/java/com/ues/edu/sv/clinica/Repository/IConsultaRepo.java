package com.ues.edu.sv.clinica.Repository;

import com.ues.edu.sv.clinica.Dtos.IConsultasMedicasDtoReporte;
import com.ues.edu.sv.clinica.Dtos.ITotalConsultasMedicasPorEspecReporteDto;
import com.ues.edu.sv.clinica.Entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IConsultaRepo extends JpaRepository<Consulta, Integer> {

    @Query(value = "SELECT consulta.fecha_consulta as fechaConsulta, \n" +
            "consulta.hora_consulta as horaConsulta, consulta.num_consultorio as \n" +
            "numConsultorio, especialidad.nombre_especiadad as nombreEspecialidad, \n" +
            "concat_ws(' ', medico.nombre_medico, medico.apellido_medico) as \n" +
            "nombreCompletoMedico, concat_ws(' ', paciente.nombre_paciente, \n" +
            "paciente.apellido_paciente) as nombreCompletoPaciente FROM consulta \n" +
            "INNER JOIN especialidad ON consulta.id_especialidad = \n" +
            "especialidad.id_especialidad INNER JOIN medico ON consulta.id_medico = \n" +
            "medico.id_medico INNER JOIN paciente ON consulta.id_paciente = \n" +
            "paciente.id_paciente ORDER BY \n" +
            "especialidad.nombre_especiadad", nativeQuery = true)
    List<IConsultasMedicasDtoReporte>totalConsultasPacientes();


    @Query(value="SELECT especialidad.id_especialidad as codigoEspecialidad, consulta.fecha_consulta as fechaConsulta," +
            " consulta.hora_consulta as horaConsulta, consulta.num_consultorio as numConsultorio, especialidad.nombre_especiadad as nombreEspecialidad," +
            " concat_ws(' ', medico.nombre_medico, medico.apellido_medico) as nombreCompletoMedico, " +
            "concat_ws(' ', paciente.nombre_paciente, paciente.apellido_paciente) as nombreCompletoPaciente " +
            "FROM consulta INNER JOIN especialidad ON consulta.id_especialidad = especialidad.id_especialidad" +
            " INNER JOIN medico ON consulta.id_medico = medico.id_medico INNER JOIN paciente ON consulta.id_paciente = paciente.id_paciente" +
            " HAVING especialidad.id_especialidad=:codigoEspecialidadParam AND consulta.fecha_consulta=:fechaConsultaParam",nativeQuery = true)
      List<IConsultasMedicasDtoReporte>totalConsultasPacientesPorEspecialidad(int codigoEspecialidadParam,String fechaConsultaParam);

    @Query(value = "SELECT e.nombre_especiadad as nombreEspecialidad,count(id_consulta) as cantidadConsulta FROM especialidad e INNER JOIN consulta c ON e.id_especialidad=c.id_especialidad GROUP BY e.id_especialidad ORDER BY e.id_especialidad DESC", nativeQuery = true)
            List<ITotalConsultasMedicasPorEspecReporteDto> cantidadConsultaPorEspecialidadGrafBarras();

    @Query(value="SELECT e.id_especialidad as codigoEspecialidad, c.fecha_consulta as \n" +
            "fechaConsulta, c.hora_consulta as horaConsulta, c.num_consultorio as \n" +
            "numConsultorio, e.nombre_especiadad as nombreEspecialidad, concat_ws(' ', \n" +
            "m.nombre_medico, m.apellido_medico) as nombreCompletoMedico, concat_ws(' ', \n" +
            "paciente.nombre_paciente, paciente.apellido_paciente) as nombreCompletoPaciente FROM \n" +
            "consulta c INNER JOIN especialidad e ON c.id_especialidad =e.id_especialidad \n" +
            "INNER JOIN medico m ON c.id_medico = m.id_medico INNER JOIN paciente ON \n" +
            "c.id_paciente = paciente.id_paciente WHERE  e.id_especialidad=:idEspecialidadParam AND m.id_medico =:idMedicoParam",nativeQuery = true)
    List<IConsultasMedicasDtoReporte>totalConsultasMedicosyEspecialidad(int idMedicoParam, int idEspecialidadParam);



}
