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
        if(arriendo.getFechaInicio() != null){
            if (arriendo.getTotalDias() > 0){
                if(arriendo.getPatenteAutomovil() != null && !arriendo.getPatenteAutomovil().isEmpty()) {
                    //Validar que exista patente
                    if(this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil())  != null){
                        Automovil automovil = this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil());
                        if(arriendo.getRutCliente() != null && !arriendo.getRutCliente().isEmpty()) {
                            //Validar que exista cliente
                            if(this.clienteClient.obtenerClientePorRut(arriendo.getRutCliente()) != null){
                                if(arriendo.getRutColaborador() != null && !arriendo.getRutColaborador().isEmpty()) {
                                    //Validar que exista colaborador
                                    if(this.colaboradorClient.obtenerColaboradorPorRut(arriendo.getRutColaborador()) != null){
                                        //Calculo de fecha fin en base a total dias
                                        arriendo.setFechaFin(arriendo.getFechaInicio().plusDays(arriendo.getTotalDias()));
                                        //Calculo valor arriendo en base a total dias
                                        arriendo.setValorArriendo((int) (automovil.getValorDiario()*arriendo.getTotalDias()));
                                        this.repository.save(arriendo);
                                        return "agregado";
                                    } else {
                                        return "colaboradorNoEncontrado";
                                    }
                                } else {
                                    return "faltaRutColaborador";
                                }
                            } else {
                                return "clienteNoEncontrado";
                            }
                        } else {
                            return "faltaRutCliente";
                        }
                    } else {
                        return "patenteNoEncontrada";
                    }
                } else {
                    return "faltaPatente";
                }
            } else {
                return "faltaTotalDias";
            }
        } else {
            return "faltaFechaInicio";
        }
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
        if(this.repository.findOneById(idArriendo) != null){
            Arriendo arriendoDB = this.repository.findOneById(idArriendo);
            if(arriendo.getFechaInicio() != null){
                arriendoDB.setFechaInicio(arriendo.getFechaInicio());
            }
            if(arriendo.getPatenteAutomovil() != null) {
                if(this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil()) != null){
                    arriendoDB.setPatenteAutomovil(arriendo.getPatenteAutomovil());
                } else {
                    return "patenteNoEncontrada";
                }
            }
            if(arriendo.getRutCliente() != null) {
                if(this.clienteClient.obtenerClientePorRut(arriendo.getRutCliente()) != null) {
                    arriendoDB.setRutCliente(arriendo.getRutCliente());
                } else {
                    return "clienteNoEncontrado";
                }
            }
            if(arriendo.getRutColaborador() != null) {
                if(this.colaboradorClient.obtenerColaboradorPorRut(arriendo.getRutColaborador()) != null) {
                    arriendoDB.setRutColaborador(arriendo.getRutColaborador());
                } else {
                    return "colaboradorNoEncontrado";
                }
            }
            if(arriendo.getTotalDias() > 0) {
                arriendoDB.setTotalDias(arriendo.getTotalDias());
            }
            //Si actualizan fecha inicio y total dias
            if(arriendo.getFechaInicio() != null && arriendo.getTotalDias() > 0){
                arriendoDB.setFechaFin(arriendo.getFechaInicio().plusDays(arriendo.getTotalDias()));
            }
            //Si actualizan solo fecha inicio
            if(arriendo.getFechaInicio() != null && arriendo.getTotalDias() == 0) {
                arriendoDB.setFechaFin(arriendo.getFechaInicio().plusDays(arriendoDB.getTotalDias()));
            }
            //Si actualizan solo total dias
            if(arriendo.getFechaInicio() == null && arriendo.getTotalDias() > 0) {
                arriendoDB.setFechaFin(arriendoDB.getFechaInicio().plusDays(arriendo.getTotalDias()));
            }
            // Si actualizan la patente y el total dias
            if(arriendo.getPatenteAutomovil() != null && arriendo.getTotalDias() > 0){
                if(this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil()) != null){
                    Automovil auto = this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil());
                    arriendoDB.setValorArriendo((int) (auto.getValorDiario()*arriendo.getTotalDias()));
                } else {
                    return "patenteNoEncontrada";
                }
            }
            // Si actualzan solo patente
            if(arriendo.getPatenteAutomovil() != null && arriendo.getTotalDias() == 0) {
                if(this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil()) != null){
                    Automovil auto = this.automovilClient.buscarAutomovilPorPatente(arriendo.getPatenteAutomovil());
                    arriendoDB.setValorArriendo((int) (auto.getValorDiario()*arriendoDB.getTotalDias()));
                } else {
                    return "patenteNoEncontrada";
                }
            }
            //Si actualizan solo total dias
            if(arriendo.getPatenteAutomovil() == null && arriendo.getTotalDias() > 0) {
                if(this.automovilClient.buscarAutomovilPorPatente(arriendoDB.getPatenteAutomovil()) != null){
                    Automovil auto = this.automovilClient.buscarAutomovilPorPatente(arriendoDB.getPatenteAutomovil());
                    arriendoDB.setValorArriendo((int) (auto.getValorDiario()*arriendo.getTotalDias()));
                }
            }
            this.repository.save(arriendoDB);
            return "arriendoActualizado";
        } else {
            return "arriendoNoEncontrado";
        }

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
