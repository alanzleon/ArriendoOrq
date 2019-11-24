package com.example.msarriendoorq.service;


import com.example.msarriendoorq.entity.Arriendo;

import java.util.List;

public interface IArriendoService {
    String agregaArriendo(Arriendo arriendo);
    List<Arriendo> buscarArriendos();
    Arriendo buscarArriendoPorId(String idArriendo);
    String actualizarArriendo(Arriendo arriendo, String idArriendo);
    String buscar(String rut);
}
