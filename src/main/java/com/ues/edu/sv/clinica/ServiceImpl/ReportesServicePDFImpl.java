package com.ues.edu.sv.clinica.ServiceImpl;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ues.edu.sv.clinica.Service.IReporteService;
import jakarta.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;


@Service
public class ReportesServicePDFImpl<T> implements IReporteService<T> {
    @Override
    public void generarReporte(InputStream stream, HttpServletResponse response, List<T> data) throws IOException {
        // TODO Auto-generated method stub
        try {
            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Admin");
            final File imgLogo= ResourceUtils.getFile("classpath:images/logobufmpues.jpg");
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=App_report_en.pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generarReporteParam(InputStream stream, HttpServletResponse response, List<T> data) throws IOException {
        try {
            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Admin");

            //Nuevos parametros de idespecialidad y fecha consulta
            final File imgLogo= ResourceUtils.getFile("classpath:images/logobufmpues.jpg");
            parameters.put("idEspecialidadParam","idEspecialidadParam");
            parameters.put("fechaConsultaParam","fechaConsultaParam");
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            //Fin de nuevos parametros

            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=App_report_en.pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generarReporteGrafico(InputStream stream, HttpServletResponse response, List<T> data) throws IOException {
        try {
            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Admin");

            //Nuevos parametros de idespecialidad y fecha consulta
            final File imgLogo= ResourceUtils.getFile("classpath:images/logobufmpues.jpg");

            System.out.println("imgLogo: "+imgLogo.getAbsolutePath());

            parameters.put("imgLogo",new FileInputStream(imgLogo));
            //Fin de nuevos parametros

            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=App_report_grafico_en.pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generarReporteParam2(InputStream stream, HttpServletResponse response, List<T> data) throws IOException {
        try {
            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Admin");

            //Nuevos parametros de idespecialidad y fecha consulta
            final File imgLogo= ResourceUtils.getFile("classpath:images/logobufmpues.jpg");
            parameters.put("idEspecialidad","idEspecialidad");
            parameters.put("idMedico","idMedico");
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            //Fin de nuevos parametros

            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, source);
            response.setContentType("application/x-pdf");
            response.setHeader("Content-disposition", "inline; filename=Reporte_Parametro.pdf");
            final OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (JRException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


}