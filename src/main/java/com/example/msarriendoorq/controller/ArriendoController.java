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

    public String mensajeError(String msjPersonalizado) {
        return "{\"Error\":\""+msjPersonalizado+"\"}";
    }

    public String mensaje(String msjPersonalizado) {
        return "{\"Mensaje\":\""+msjPersonalizado+"\"}";
    }
}
