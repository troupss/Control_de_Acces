package com.crni99.qrcodegenerator.repository;

import com.crni99.qrcodegenerator.models.Partits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface PartitsRepository extends JpaRepository<Partits, Integer> {
}
