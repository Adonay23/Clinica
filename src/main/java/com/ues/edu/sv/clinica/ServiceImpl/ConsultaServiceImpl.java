package com.ues.edu.sv.clinica.ServiceImpl;

import com.ues.edu.sv.clinica.Entity.Consulta;
import com.ues.edu.sv.clinica.Repository.IConsultaRepo;
import com.ues.edu.sv.clinica.Service.IConsultaService;
import com.ues.edu.sv.clinica.Service.IReporteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor //ESTO SUSTITUYE A @Autowired
@Service
public class ConsultaServiceImpl implements IConsultaService {
    private final IConsultaRepo consultaRepo;

    private final IReporteService servicePDF;

    // @RequiredArgsConstructor ESTO SUSTITUYE A @Autowired
	/*
	@Autowired
	public ConsultaServiceImpl(IConsultaRepo consultaRepo) {
		// TODO Auto-generated constructor stub
		this.consultaRepo = consultaRepo;
	}
*/

    @Override
    public Consulta registrar(Consulta obj) {
        return this.consultaRepo.save(obj);
    }

    @Override
    public Consulta modificar(Consulta obj) {
        return this.consultaRepo.save(obj);
    }

    @Override
    public List<Consulta> listar() {
        List<Consulta> listConsultas = this.consultaRepo.findAll();
        return listConsultas;
    }

    @Override
    public Consulta leerPorId(Integer id) {
        return this.consultaRepo.findById(id).get();
    }

    @Override
    public boolean eliminar(Consulta obj) {
        try {
            this.consultaRepo.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }


    @Override
    public void generarReporteConsultaPorMedico(HttpServletResponse response) throws IOException {
       final InputStream stream=this.getClass().getResourceAsStream("/reports/consultaMedicaPorEspecialidadCE.jrxml");
       this.servicePDF.generarReporte(stream,response,consultaRepo.totalConsultasPacientes());
    }



    @Override
    public void generarReporteConsultaPorEspecialidad(HttpServletResponse response,int idEspecialdadParam,String fechaConsultaParam) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/consultaMedicaEspecifica.jrxml");
        this.servicePDF.generarReporteParam(stream,response,consultaRepo.totalConsultasPacientesPorEspecialidad(idEspecialdadParam,fechaConsultaParam));
    }

    @Override
    public void generarReportePorConsultaGraficoBarras(HttpServletResponse response) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/consultaGrafico.jrxml");
        this.servicePDF.generarReporteGrafico(stream,response,consultaRepo.cantidadConsultaPorEspecialidadGrafBarras());
    }

    @Override
    public void generarReporteConsultasMedicosyEspecialidad(HttpServletResponse response, int idMedicoParam, int idEspecialidadParam) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/consultaPorespcialidadYmedico.jrxml");
        this.servicePDF.generarReporteParam2(stream,response,consultaRepo.totalConsultasMedicosyEspecialidad(idMedicoParam,idEspecialidadParam));

    }


}