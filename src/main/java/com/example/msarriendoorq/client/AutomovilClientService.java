package com.example.msarriendoorq.client;

import com.example.msarriendoorq.entity.Automovil;

public interface AutomovilClientService {
    Automovil buscarAutomovilPorPatente(String patente);
    void actualizarAutomovilPorPatente(Automovil auto,String patente);
}
