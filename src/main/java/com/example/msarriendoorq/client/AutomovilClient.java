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
    public void actualizarAutomovilPorPatente(Automovil auto, String patente) {
        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        parts.add("estadoArriendo",auto.getEstadoArriendo());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parts, headers);
        String url = "http://localhost:8093/automovil/actualizar/"+patente;

        this.restTemplate.exchange(url,HttpMethod.PUT,request,String.class);
    }
}
