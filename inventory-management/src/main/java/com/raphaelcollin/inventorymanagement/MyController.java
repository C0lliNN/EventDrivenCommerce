package com.raphaelcollin.inventorymanagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    @GetMapping("/")
    public Map<String, String> helloWorld() {
        return Map.of("status", "OK");
    }
}
