package com.example.sistemadereclutamiento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empresa", length = 150)
    private String nombreEmpresa;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    private String ruc;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(length = 200)
    private String direccion;

    @Column(name = "pagina_web", length = 150)
    private String paginaWeb;

    @Column(name = "estado_validacion", length = 30)
    private String estadoValidacion = "PENDIENTE";

   
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}