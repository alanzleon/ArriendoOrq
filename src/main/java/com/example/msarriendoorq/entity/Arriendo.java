package com.example.msarriendoorq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "arriendos")
public class Arriendo implements Serializable {
    @Id
    private String id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String patenteAutomovil;
    private String rutCliente;
    private String rutColaborador;
    private int valorArriendo;
    private int totalDias;

}
