package com.example.msarriendoorq.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class Automovil implements Serializable {

    private String id;
    private String patente;
    private String marca;
    private String modelo;
    private String color;
    private int fabricado;
    private String tipo;//{Citycar, Descapotable, SUV, AltaGama}
    private int numAsientos;
    private String numRevisionTecnica;
    private Date fechaVctoRevision;
    private String estadoArriendo;//{Disponible, Mantencion, Arrendado}
    private float valorDiario;
}
