package com.eduardo.AdoptAPetAPI.controllers;

import com.eduardo.AdoptAPetAPI.services.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("animals")
public class AnimalController {

    private final AnimalService service;
}
