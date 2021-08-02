package br.com.temvaga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecuredController {

    @GetMapping
    public ResponseEntity reachSecureEndpoint(){
        ResponseEntity responseEntity = new ResponseEntity("Você esta autenticado! Parabens", HttpStatus.OK);
        return responseEntity;
    }
}