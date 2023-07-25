package com.ues.edu.sv.clinica.Service;

import com.ues.edu.sv.clinica.Entity.Paciente;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IPacienteService extends ICRUD<Paciente>{

    void generarReportePacientes(HttpServletResponse response) throws IOException;

    void generarReportePacientesGraficaLinea(HttpServletResponse response) throws IOException;

}
