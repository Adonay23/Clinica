package com.ues.edu.sv.clinica.Dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteValidDTO {

    @NotBlank(message = "El nombre del paciente no puede estar vacio")
    @NotNull(message = "El nombre del paciente no puede ser nulo")
    @Size(min = 3, max = 30, message = "El nombre del paciente debe tener entre 3 y 30 caracteres")
    private String nombrePaciente;

    @NotBlank(message = "El apellido del paciente no puede estar vacio")
    @NotNull(message = "El apellido del paciente no puede ser nulo")
    private String apellidoPaciente;

    @Size(max = 10, message = "No DUI del paciente debe tener como maximo 10 caracteres")
    private String identPaciente;

    private String direccionPaciente;

    @NotBlank(message = "El telefono del paciente no puede estar vacio")
    private String telefonoPaciente;

    @NotEmpty
    @Email
    private String emailPaciente;

    @AssertTrue
    private boolean tieneExpediente;

    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "La fecha debe ser anterior a la actual")
    private LocalDate fechaNacimiento;

    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "La fecha debe ser actual o posterior a la actual")
    private LocalDate fechaVencimientoDui;

}
