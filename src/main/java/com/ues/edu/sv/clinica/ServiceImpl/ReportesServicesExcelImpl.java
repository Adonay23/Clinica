package com.ues.edu.sv.clinica.ServiceImpl;


import com.ues.edu.sv.clinica.Dtos.IConsultasMedicasDtoReporte;
import com.ues.edu.sv.clinica.Entity.Consulta;
import com.ues.edu.sv.clinica.Entity.Paciente;
import com.ues.edu.sv.clinica.Repository.IConsultaRepo;
import com.ues.edu.sv.clinica.Repository.IPacienteRepo;
import com.ues.edu.sv.clinica.Service.IReporteExcel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReportesServicesExcelImpl implements IReporteExcel {

    @Autowired
    private IPacienteRepo pacienteRepo;

    @Autowired
    private IConsultaRepo consultaRepo;



    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pacientes");
        Row filaTitulo= sheet.createRow(0);
        Cell celda= filaTitulo.createCell(1);
        celda.setCellValue("LISTADO GENERAL DE PACIENTES");
        Row filaData =sheet.createRow(2);
        int dataRowIndice=1;
        String[] columns={"ID","NOMBRE","APELLIDO","IDENTIFICACION","DIRECCION","TELEFONO","E-MAIL"};
        for (int i = 0; i < columns.length; i++) {
            celda=  filaData.createCell(i);
            celda.setCellValue(columns[i]);
        }
        int dataRowIndex=4;
        List<Paciente>pacientes= this.pacienteRepo.findAll();
for (Paciente paciente: pacientes) {
            filaData= sheet.createRow(dataRowIndex);
            filaData.createCell(0).setCellValue(paciente.getIdPaciente());
            filaData.createCell(1).setCellValue(paciente.getNombrePaciente());
            filaData.createCell(2).setCellValue(paciente.getApellidoPaciente());
            filaData.createCell(3).setCellValue(paciente.getIdentPaciente());
            filaData.createCell(4).setCellValue(paciente.getDireccionPaciente());
            filaData.createCell(5).setCellValue(paciente.getTelefonoPaciente());
            filaData.createCell(6).setCellValue(paciente.getEmailPaciente());
            dataRowIndex++;
        }

        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }

    @Override
    public void generarExcelConsulta(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook  = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Pacientes");
        Row filaTitulo= sheet.createRow(0);
        Cell celda= filaTitulo.createCell(1);
        celda.setCellValue("LISTADO DE CONSULTAS");
        Row filaData =sheet.createRow(2);
        int dataRowIndice=1;
        String[] columns={"ID","NOMBRE MEDICO","NOMBRE PACIENTE","NOMBRE ESPECIALIDAD","NUM. CONSULTORIO","FECHA CONSULTA","HORA CONSULTA"};

        for (int i = 0; i < columns.length; i++) {
            celda=  filaData.createCell(i);
            celda.setCellValue(columns[i]);
        }

        int dataRowIndex=4;

        List<IConsultasMedicasDtoReporte>consultas= this.consultaRepo.totalConsultasPacientes();
        int index=1;
        for (IConsultasMedicasDtoReporte consulta: consultas) {
            filaData= sheet.createRow(dataRowIndex);
            filaData.createCell(0).setCellValue(index);
            filaData.createCell(1).setCellValue(consulta.getNombreCompletoMedico());
            filaData.createCell(2).setCellValue(consulta.getNombreCompletoPaciente());
            filaData.createCell(3).setCellValue(consulta.getNombreEspecialidad());
            filaData.createCell(4).setCellValue(consulta.getNumConsultorio());
            filaData.createCell(5).setCellValue(consulta.getFechaConsulta());
            filaData.createCell(6).setCellValue(consulta.getHoraConsulta());
            dataRowIndex++;
            index++;
        }

        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
