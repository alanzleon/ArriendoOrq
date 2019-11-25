package com.example.msarriendoorq.client;

import com.example.msarriendoorq.entity.Automovil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class AutomovilClient implements AutomovilClientService {

    private final RestTemplate restTemplate;

    @Override
    public Automovil buscarAutomovilPorPatente(String patente) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = "http://localhost:8093/automovil/getByPatente/"+patente;

        ResponseEntity<String> response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class);
        Automovil automovil = new Gson().fromJson(response.getBody(), Automovil.class);
        return automovil;

    }

    @Override
    public void actualizarAutomovilPorPatente(Automovil auto,String patente) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> parts = new HashMap<>();
        parts.put("patente",patente);
        auto.setEstadoArriendo("Arrendado");

        HttpEntity<Automovil> request = new HttpEntity<>(auto, headers);
        String url = "http://localhost:8093/automovil/actualizar/{patente}";

        this.restTemplate.exchange(url,HttpMethod.PUT,request,Void.class,parts);
    }
}
