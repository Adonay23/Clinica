package com.ues.edu.sv.clinica.ServiceImpl;

import com.ues.edu.sv.clinica.Entity.Especialidad;

import com.ues.edu.sv.clinica.Repository.IEspecialidadRepo;
import com.ues.edu.sv.clinica.Service.IEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EspecialidadServiceImpl implements IEspecialidadService {

    private final IEspecialidadRepo repoEspecialidad;

    @Override
    public Especialidad registrar(Especialidad obj) {
        return this.repoEspecialidad.save(obj);
    }

    @Override
    public Especialidad modificar(Especialidad obj) {
        return this.repoEspecialidad.save(obj);
    }

    @Override
    public List<Especialidad> listar() {
        List<Especialidad> listEspecialidad= this.repoEspecialidad.findAll();
        return listEspecialidad;
    }

    @Override
    public Especialidad leerPorId(Integer id) {
        return this.repoEspecialidad.findById(id).get();
    }

    @Override
    public boolean eliminar(Especialidad obj) {
        try {
            this.repoEspecialidad.delete(obj);
            return true;
        } catch (Exception e) {
            // TODO: handle exception

            return false;
        }
    }
}
