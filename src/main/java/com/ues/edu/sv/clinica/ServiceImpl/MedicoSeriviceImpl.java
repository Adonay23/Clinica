package com.ues.edu.sv.clinica.ServiceImpl;

import com.ues.edu.sv.clinica.Entity.Medico;
import com.ues.edu.sv.clinica.Repository.IMedicoRepo;
import com.ues.edu.sv.clinica.Service.IMedicoService;
import com.ues.edu.sv.clinica.Service.IReporteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RequiredArgsConstructor
@Service
public class MedicoSeriviceImpl implements IMedicoService {
    private final IMedicoRepo servicioMedico;
    private final IReporteService servicePDF;

    @Override
    public Medico registrar(Medico obj) {
        // TODO Auto-generated method stub
        return this.servicioMedico.save(obj);
    }

    @Override
    public Medico modificar(Medico obj) {
        // TODO Auto-generated method stub
        //return null;
        return this.servicioMedico.save(obj);
    }

    @Override
    public List<Medico> listar() {
        List<Medico> listMedicos= this.servicioMedico.findAll();
        return listMedicos;
    }

    @Override
    public Medico leerPorId(Integer id) {
        // TODO Auto-generated method stub
        return this.servicioMedico.findById(id).get();
    }

    @Override
    public boolean eliminar(Medico obj) {
        // TODO Auto-generated method stub
        try {
            this.servicioMedico.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception

            return false;
        }

    }
    @Override
    public List<Medico> buscarMedico(String filtro) {
        // TODO Auto-generated method stub
        return  this.servicioMedico.buscarMedico(filtro) ;
    }

    @Override
    public void generarReporteMedicoPorEspecialidad(HttpServletResponse response) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/medicosPorEspecialidad.jrxml");
        this.servicePDF.generarReporte(stream,response,servicioMedico.totalconsultaspormedico());

    }

    @Override
    public void generarReporteMedico(HttpServletResponse response) throws IOException {
        final InputStream stream=this.getClass().getResourceAsStream("/reports/todoslosMedicos.jrxml");
        this.servicePDF.generarReporte(stream,response,listar());
    }
}
