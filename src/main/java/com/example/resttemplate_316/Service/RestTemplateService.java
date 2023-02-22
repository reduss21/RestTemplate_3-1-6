package com.example.resttemplate_316.Service;


import com.example.resttemplate_316.model.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class RestTemplateService {

    RestTemplate restTemplate;
    String urlAPI = "http://94.198.50.185:7081/api/users";

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void getCookieAndUser() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List> responseEntity = getUsersList(httpEntity);
        System.out.println(httpHeaders.get("Cookie"));
        httpHeaders.set("Cookie", String.join(";",
                Objects.requireNonNull(responseEntity.getHeaders()
                        .get("Set-Cookie"))));

        User newUser = new User();
        newUser.setId(3L);
        newUser.setName("name");
        newUser.setLastName("surname");
        newUser.setAge((byte) 35);
        httpEntity = new HttpEntity<>(newUser, httpHeaders);
        String part1 = saveNewUser(httpEntity);

        newUser.setName("billy");
        newUser.setLastName("herington");
        httpEntity = new HttpEntity<>(newUser, httpHeaders);
        String part2 = updateUser(httpEntity);

        String part3 = deleteUser(httpEntity, newUser);

        System.out.println(part1 + part2 + part3);

    }

    public ResponseEntity<List> getUsersList(HttpEntity<Object> httpEntity) {
        ResponseEntity<List> responseEntity = restTemplate.exchange(urlAPI, HttpMethod.GET, httpEntity, List.class);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        return responseEntity;
    }

    public String saveNewUser(HttpEntity<Object> httpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlAPI, HttpMethod.POST, httpEntity, String.class);
        String currentString = responseEntity.getBody();
        return currentString;
    }

    public String updateUser(HttpEntity<Object> httpEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlAPI, HttpMethod.PUT, httpEntity, String.class);
        String currentString = responseEntity.getBody();
        return currentString;
    }

    public String deleteUser(HttpEntity<Object> httpEntity, User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlAPI + "/" + user.getId(), HttpMethod.DELETE, httpEntity, String.class);
        String currentString = responseEntity.getBody();
        return currentString;
    }
}
