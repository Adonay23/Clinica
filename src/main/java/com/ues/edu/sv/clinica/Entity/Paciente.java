package com.ues.edu.sv.clinica.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;
    @Column(name = "nombre_paciente", nullable = false, length = 70)
    private String nombrePaciente;
    @Column(name = "apellido_paciente", nullable = false, length = 70)
    private String apellidoPaciente;
    @Column(name = "ident_paciente", nullable = true, length = 20)
    private String identPaciente;
    @Column(name = "direccion_paciente", nullable = false, length = 100)
    private String direccionPaciente;

    @Column(name = "telefono_paciente", nullable = false, length = 20)
    private String telefonoPaciente;

    @Column(name = "email_paciente", nullable = false, length = 50)
    private String emailPaciente;

    @Column(name = "fecha_nacimiento_paciente", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "tiene_expediente_paciente", nullable = false)
    private boolean tieneExpediente;

    @Column(name = "fecha_vencimiento_dui_paciente", nullable = false)
    private LocalDate fechaVencimientoDui;

    public String getNombreCompletoPaciente(){
        return this.nombrePaciente !=null && this.apellidoPaciente!=null ?
                this.nombrePaciente+" "+this.apellidoPaciente:"----------";
    }

}
