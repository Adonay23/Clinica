package com.ues.edu.sv.clinica.Service;

import com.ues.edu.sv.clinica.Entity.Medico;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface IMedicoService extends ICRUD<Medico> {
    List<Medico> buscarMedico(String filtro);

    void generarReporteMedicoPorEspecialidad(HttpServletResponse response) throws IOException;

    void generarReporteMedico(HttpServletResponse response) throws IOException;

}
