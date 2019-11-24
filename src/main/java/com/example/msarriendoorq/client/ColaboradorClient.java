package com.example.msarriendoorq.client;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ColaboradorClient implements ColaboradorClientService {

    private final RestTemplate restTemplate;

    @Override
    public String obtenerColaboradorPorRut(String rut) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = "http://localhost:8092/colaborador/colaborador/"+rut;

        return this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class).getBody();
    }

}
