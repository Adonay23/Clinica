package com.ues.edu.sv.clinica.Service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IReporteExcel {
    void generateExcel(HttpServletResponse response)throws IOException;

    void generarExcelConsulta(HttpServletResponse response)throws IOException;

}
