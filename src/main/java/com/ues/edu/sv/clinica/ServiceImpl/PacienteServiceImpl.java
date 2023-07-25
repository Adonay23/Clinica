package com.ues.edu.sv.clinica.ServiceImpl;

import com.ues.edu.sv.clinica.Entity.Medico;
import com.ues.edu.sv.clinica.Entity.Paciente;
import com.ues.edu.sv.clinica.Repository.IEspecialidadRepo;
import com.ues.edu.sv.clinica.Repository.IPacienteRepo;
import com.ues.edu.sv.clinica.Service.IPacienteService;
import com.ues.edu.sv.clinica.Service.IReporteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PacienteServiceImpl implements IPacienteService {

    private final IPacienteRepo repoPaciente;

    private final IReporteService servicePDF;
    @Override
    public Paciente registrar(Paciente obj) {
        return this.repoPaciente.save(obj);
    }

    @Override
    public Paciente modificar(Paciente obj) {
        return this.repoPaciente.save(obj);
    }

    @Override
    public List<Paciente> listar() {
        List<Paciente> listPaciente= this.repoPaciente.findAll();
        System.out.println(listPaciente.get(0));
        return listPaciente;
    }

    @Override
    public Paciente leerPorId(Integer id) {
        return this.repoPaciente.findById(id).orElse(new Paciente());
    }

    @Override
    public boolean eliminar(Paciente obj) {
        try {
            this.repoPaciente.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception

            return false;
        }
    }

    @Override
    public void generarReportePacientes(HttpServletResponse response) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/pacientesRep.jrxml");
        this.servicePDF.generarReporte(stream,response,listar());

    }

    @Override
    public void generarReportePacientesGraficaLinea(HttpServletResponse response) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/graficoLinea.jrxml");
        this.servicePDF.generarReporte(stream,response,repoPaciente.totalConsultasPacientesPorEspecialidad());
    }
}
