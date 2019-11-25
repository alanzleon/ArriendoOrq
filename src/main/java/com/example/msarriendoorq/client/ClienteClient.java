package com.example.msarriendoorq.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class ClienteClient implements ClienteClientService{

    private final RestTemplate restTemplate;

    @Override
    public String obtenerClientePorRut(String rut) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList((MediaType.APPLICATION_JSON)));
        HttpEntity <String> request = new HttpEntity<>(headers);
        String url = "http://localhost:8091/cliente/"+rut;

        return this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class).getBody();
    }
}
