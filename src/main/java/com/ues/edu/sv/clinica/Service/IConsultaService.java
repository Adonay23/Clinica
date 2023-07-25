package com.ues.edu.sv.clinica.Service;

import com.ues.edu.sv.clinica.Entity.Consulta;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IConsultaService extends ICRUD<Consulta>{

    void generarReporteConsultaPorMedico(HttpServletResponse response) throws IOException;

    void generarReporteConsultaPorEspecialidad(HttpServletResponse response,int idEspecialdadParam,String fechaConsultaParam) throws IOException;

    void generarReportePorConsultaGraficoBarras( HttpServletResponse response) throws IOException;

    void generarReporteConsultasMedicosyEspecialidad(HttpServletResponse response,int idMedicoParam, int idEspecialidadParam) throws IOException;



}
