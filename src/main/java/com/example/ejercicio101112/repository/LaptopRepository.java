package com.example.ejercicio101112.repository;

import com.example.ejercicio101112.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}
