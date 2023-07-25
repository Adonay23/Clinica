package com.ues.edu.sv.clinica.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private Integer code;
    private String message;
    private T response;
}
