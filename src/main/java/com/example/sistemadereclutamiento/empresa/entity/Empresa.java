package com.example.sistemadereclutamiento.empresa.entity;
import com.example.sistemadereclutamiento.usuario.entity.Usuario;
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

    private String ruc;

    private String descripcion;

    private String direccion;

    @Column(name = "pagina_web", length = 150)
    private String paginaWeb;

    @Column(name = "estado_validacion", length = 30)
    private String estadoValidacion = "PENDIENTE";

   
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}