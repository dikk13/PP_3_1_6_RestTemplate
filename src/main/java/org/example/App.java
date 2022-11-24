package org.example;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class App
{
    public static void main( String[] args ) {
        String url = "http://94.198.50.185:7081/api/users";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);
        Map<String,String> headers = responseEntity.getHeaders().toSingleValueMap();
        String cookie = headers.get("Set-Cookie");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add(HttpHeaders.COOKIE, cookie);

        HttpEntity<User> request = new HttpEntity<>(new User (3L, "James", "Brown", (byte) 10),
                httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request,String.class);
        String s = response.getBody();

        request = new HttpEntity<>(new User (3L, "Thomas", "Shelby", (byte) 10),
                httpHeaders);
        response = restTemplate.exchange(url, HttpMethod.PUT, request,String.class);
        s = s + response.getBody();

        httpHeaders.add("id", "3");
        request = new HttpEntity<>(httpHeaders);
        response = restTemplate.exchange(url + "/3", HttpMethod.DELETE, request,String.class);
        s = s + response.getBody();

        System.out.println(s);
        System.out.println( "Hello World!" );
    }
}
