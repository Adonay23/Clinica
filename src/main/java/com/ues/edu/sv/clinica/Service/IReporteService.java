package com.ues.edu.sv.clinica.Service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IReporteService<T> {
    void generarReporte(InputStream stream, HttpServletResponse response, List<T> data)throws IOException;

    void generarReporteParam(InputStream stream, HttpServletResponse response, List<T> data)throws IOException;

    void generarReporteGrafico(InputStream stream, HttpServletResponse response, List<T> data)throws IOException;

    void generarReporteParam2(InputStream stream, HttpServletResponse response, List<T> data)throws IOException;


}

