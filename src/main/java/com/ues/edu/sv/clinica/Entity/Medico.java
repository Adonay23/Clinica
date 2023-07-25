package com.ues.edu.sv.clinica.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="medico")
public class Medico {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idMedico;
    private String nombreMedico;
    private String apellidoMedico;
    private String jvpm;

    public String getNombreCompletoMedico(){
        return this.nombreMedico !=null && this.apellidoMedico!=null ?
                this.nombreMedico+" "+this.apellidoMedico:"----------";
    }

}