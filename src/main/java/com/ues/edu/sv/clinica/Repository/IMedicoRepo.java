package com.ues.edu.sv.clinica.Repository;

import com.ues.edu.sv.clinica.Dtos.ItotalpacientesPorEspecialidad;
import com.ues.edu.sv.clinica.Dtos.MedicosPorEspecialidadDTO;
import com.ues.edu.sv.clinica.Entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMedicoRepo extends JpaRepository<Medico,Integer> {

// para el filtro de busqueda

    @Query("from Medico m where (LOWER(m.nombreMedico) like %:filtro% or LOWER(m.apellidoMedico) like %:filtro%)")
    List<Medico> buscarMedico(@Param("filtro") String filtro);

    @Query(value = "SELECT e.nombre_especiadad as nombreEspecialidad,\n" +
            "count(DISTINCT c.id_medico) as cantidadMedico\n" +
            "FROM especialidad e \n" +
            "INNER JOIN consulta c\n" +
            "ON e.id_especialidad=c.id_especialidad\n" +
            "GROUP BY e.id_especialidad\n" +
            "ORDER BY e.id_especialidad desc", nativeQuery = true)
    List<MedicosPorEspecialidadDTO>totalconsultaspormedico();


}
