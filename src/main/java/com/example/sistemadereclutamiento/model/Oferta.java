package com.example.sistemadereclutamiento.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(length = 120)
    private String ubicacion;

    @Column(nullable = false)
    private Double salario;

    @Column(length = 30, nullable = false)
    private String estado = "ACTIVA";

    @ManyToOne
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;
}