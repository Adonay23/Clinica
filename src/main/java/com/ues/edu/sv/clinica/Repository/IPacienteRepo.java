package com.ues.edu.sv.clinica.Repository;

import com.ues.edu.sv.clinica.Dtos.ItotalpacientesPorEspecialidad;
import com.ues.edu.sv.clinica.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPacienteRepo extends JpaRepository<Paciente, Integer> {

    @Query(value="SELECT e.nombre_especiadad as nombreEspecialidad,c.fecha_consulta as fechaConsulta,count(p.id_paciente) " +
            "as totalPacientes FROM especialidad e INNER JOIN consulta c ON e.id_especialidad=c.id_especialidad " +
            "inner join paciente p on c.id_paciente =p.id_paciente GROUP BY e.id_especialidad,c.fecha_consulta " +
            "ORDER BY e.id_especialidad desc",nativeQuery = true)
    List<ItotalpacientesPorEspecialidad> totalConsultasPacientesPorEspecialidad();



}
