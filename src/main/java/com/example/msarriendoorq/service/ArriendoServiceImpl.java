package com.example.msarriendoorq.service;

import com.example.msarriendoorq.client.AutomovilClient;
import com.example.msarriendoorq.client.ClienteClient;
import com.example.msarriendoorq.client.ColaboradorClient;
import com.example.msarriendoorq.entity.Arriendo;
import com.example.msarriendoorq.entity.Automovil;
import com.example.msarriendoorq.repository.IArriendoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArriendoServiceImpl implements IArriendoService {
    @Autowired
    private IArriendoRepository repository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ColaboradorClient colaboradorClient;

    @Autowired
    private AutomovilClient automovilClient;


    @Override
    public String agregaArriendo(Arriendo arriendo) {
        Automovil automovil = this.automovilClient.buscarAutomovilPorPatente("PEPE20");
        //Calculo de fecha fin en base a total dias
        arriendo.setFechaFin(arriendo.getFechaInicio().plusDays(arriendo.getTotalDias()));
        //Calculo valor arriendo en base a total dias
        arriendo.setValorArriendo((int) (automovil.getValorDiario()*arriendo.getTotalDias()));

        this.repository.save(arriendo);
        return "agregado";
    }

    @Override
    public List<Arriendo> buscarArriendos() {
        return this.repository.findAll();
    }

    @Override
    public Arriendo buscarArriendoPorId(String idArriendo) {
        return this.repository.findById(idArriendo).get();
    }

    @Override
    public String actualizarArriendo(Arriendo arriendo, String idArriendo) {

        return null;
    }

    @Override
    public String buscar(String rut) {
        Automovil automovil = this.automovilClient.buscarAutomovilPorPatente(rut);
        String valor = String.valueOf(automovil.getValorDiario());
        if(automovil == null) {
            return "terrible nulo";

        } else {
        return valor;
        }
    }

}
