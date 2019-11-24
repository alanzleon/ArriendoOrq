package com.example.msarriendoorq.service;

import com.example.msarriendoorq.client.ClienteClient;
import com.example.msarriendoorq.entity.Arriendo;
import com.example.msarriendoorq.repository.IArriendoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArriendoServiceImpl implements IArriendoService {
    @Autowired
    private IArriendoRepository repository;
    @Autowired
    private ClienteClient clienteClient;


    @Override
    public String agregaArriendo(Arriendo arriendo) {
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
        String cliente = this.clienteClient.obtenerClientePorRut(rut);
        if(cliente == null) {
            return "terrible nulo";

        } else {
        return cliente;
        }
    }

}
