package com.nes.tireso.boundedContext.tire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nes.tireso.boundedContext.tire.entity.Tire;

public interface TireRepository extends JpaRepository<Tire, Long> {
}
