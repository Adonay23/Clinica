package com.ues.edu.sv.clinica.Dtos;

import lombok.Getter;

import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EspecialidadDTO implements Serializable {
    private Integer idEspecialidad;
    private String nombreEspeciadad;
}
