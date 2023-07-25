package com.ues.edu.sv.clinica.Service;

import java.util.List;

public interface ICRUD<T> {

    T registrar(T obj);
    T modificar(T obj);
    List<T> listar();
    T leerPorId(Integer id);
    boolean eliminar(T obj);

}
