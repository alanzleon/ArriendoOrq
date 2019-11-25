package com.example.msarriendoorq.controller;

import com.example.msarriendoorq.entity.Arriendo;
import com.example.msarriendoorq.service.ArriendoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/arriendo")
@CrossOrigin(value = {})
public class ArriendoController {
    @Autowired
    private ArriendoServiceImpl service;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        ResponseEntity<?> response;
        try {
            List<Arriendo> arriendos = this.service.buscarArriendos();
            response = new ResponseEntity<>(arriendos, HttpStatus.OK);

        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
    @GetMapping("/prueba")
    public String getPrueba(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        HttpEntity <String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8091/cliente/getByRut/9.995.775-1";
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

    }
    @GetMapping("/prueba2/{rut}")
    public ResponseEntity<?> getPrueba2(@PathVariable(value = "rut") String rut){
        ResponseEntity<?> response;
        try {
            String cliente = this.service.buscar(rut);
            response = new ResponseEntity<>(cliente, HttpStatus.OK);

        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }


    @GetMapping("/{idArriendo}")
    public ResponseEntity<?> getByArriendoId(@PathVariable(value = "idArriendo") String idArriendo) {
        ResponseEntity<?> response;
        try {
            Arriendo arriendo = this.service.buscarArriendoPorId(idArriendo);
            response = new ResponseEntity<>(arriendo, HttpStatus.OK);
        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping()
    public ResponseEntity<?> agregarArriendo(@RequestBody Arriendo arriendo) {
        ResponseEntity<?> response;
        String respuestaService = this.service.agregaArriendo(arriendo);
        try {
           switch(respuestaService){
               case "agregado":
                   response = new ResponseEntity<>(mensaje("Arriendo Agregado"),HttpStatus.OK);
                   break;
               case "faltaFechaInicio":
                   response = new ResponseEntity<>(mensajeError("Falta fecha de inicio de arriendo."),HttpStatus.BAD_REQUEST);
                   break;
               case "faltaTotalDias":
                   response = new ResponseEntity<>(mensajeError("Debe especificar el total de dias."),HttpStatus.BAD_REQUEST);
                   break;
               case "faltaPatente":
                   response = new ResponseEntity<>(mensajeError("Falta una patente"),HttpStatus.BAD_REQUEST);
                   break;
               case "patenteNoEncontrada":
                   response = new ResponseEntity<>(mensajeError("Patente ingresa no existe"),HttpStatus.NOT_FOUND);
                   break;
               case "faltaRutCliente":
                   response = new ResponseEntity<>(mensajeError("Falta rut del cliente"),HttpStatus.BAD_REQUEST);
                   break;
               case "clienteNoEncontrado":
                   response = new ResponseEntity<>(mensajeError("Cliente ingresado no existe"),HttpStatus.NOT_FOUND);
                   break;
               case "faltaRutColaborador":
                   response = new ResponseEntity<>(mensajeError("Falta rut del colaborador"),HttpStatus.BAD_REQUEST);
                   break;
               case "colaboradorNoEncontrado":
                   response = new ResponseEntity<>(mensajeError("Colaborador ingresado no existe"),HttpStatus.NOT_FOUND);
                   break;
               case "autoNoDisponible":
                   response = new ResponseEntity<>(mensajeError("Automovil ingresado no disponible"),HttpStatus.BAD_REQUEST);
                   break;
               default:
                   response = new ResponseEntity<>(mensajeError("Defalut error x("),HttpStatus.INTERNAL_SERVER_ERROR);
                   break;
           }
        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Arriendo arriendo, @PathVariable(value = "id") String id){
        ResponseEntity<?> response;
        String respuestaService = this.service.actualizarArriendo(arriendo,id);
        try {
            switch (respuestaService) {
                case "arriendoActualizado":
                    response = new ResponseEntity<>(mensaje("Arriendo Actualizado"),HttpStatus.OK);
                    break;
                case "patenteNoEncontrada":
                    response = new ResponseEntity<>(mensajeError("Patente No existe"),HttpStatus.NOT_FOUND);
                    break;
                case "clienteNoEncontrado":
                    response = new ResponseEntity<>(mensaje("Cliente No existe"),HttpStatus.NOT_FOUND);
                    break;
                case "colaboradorNoEncontrado":
                    response = new ResponseEntity<>(mensaje("Colaborador No existe"),HttpStatus.NOT_FOUND);
                    break;
                case "arriendoNoEncontrado":
                    response = new ResponseEntity<>(mensaje("Arriendo No existe"),HttpStatus.NOT_FOUND);
                    break;
                default:
                    response = new ResponseEntity<>(mensajeError("Defalut error x("),HttpStatus.INTERNAL_SERVER_ERROR);
                    break;
            }
        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    public String mensajeError(String msjPersonalizado) {
        return "{\"Error\":\""+msjPersonalizado+"\"}";
    }

    public String mensaje(String msjPersonalizado) {
        return "{\"Mensaje\":\""+msjPersonalizado+"\"}";
    }
}
