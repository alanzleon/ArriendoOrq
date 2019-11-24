package com.example.msarriendoorq.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "arriendos")
public class Arriendo implements Serializable {
    @Id
    private String id;

    private Date fechaInicio;
    private Date fechaFin;
    private String patenteAutomovil;
    private String rutCliente;
    private String rutColaborador;
    private int valorArriendo;
    private int totalDias;

}
