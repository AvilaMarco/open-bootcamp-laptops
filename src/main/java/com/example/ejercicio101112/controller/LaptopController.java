package com.example.ejercicio101112.controller;

import com.example.ejercicio101112.entity.Laptop;
import com.example.ejercicio101112.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
public class LaptopController {

    @Value("${user-api}")
    private String user;

    LaptopRepository repository;

    public LaptopController ( LaptopRepository repository ) {
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<?> helloUser () {
        String helloWorld = "Hello " + user;
        return new ResponseEntity<>(helloWorld, OK);
    }

    @GetMapping("/api/laptops")
    public ResponseEntity<List<Laptop>> getAll () {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<?> getById ( @PathVariable Long id ) {
        Optional<Laptop> laptop = repository.findById(id);

        if (laptop.isEmpty()) {
            return new ResponseEntity<>("Laptop's id not found", NOT_FOUND);
        }

        return new ResponseEntity<>(laptop.get(), OK);
    }

    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create ( @RequestBody Laptop laptop ) {
        return ResponseEntity.status(CREATED)
          .body(repository.save(laptop));
    }

    @PutMapping("/api/laptops/{id}")
    public ResponseEntity<?> update ( @PathVariable Long id, @RequestBody Laptop laptop ) {
        Optional<Laptop> laptopOp = repository.findById(id);

        if (laptopOp.isEmpty()) {
            return new ResponseEntity<>("Laptop's id not found", NOT_FOUND);
        }

        Laptop laptopDb = laptopOp.get();
        laptopDb.setName(laptop.getName());

        return ResponseEntity.ok(repository.save(laptopDb));
    }

    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<String> delete ( @PathVariable Long id ) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Laptop's id not found", NOT_FOUND);
        }

        repository.deleteById(id);
        return ResponseEntity.noContent()
          .build();
    }

    @DeleteMapping("/api/laptops")
    public ResponseEntity<String> deleteAll () {
        repository.deleteAll();

        return ResponseEntity.noContent()
          .build();

    }
}
