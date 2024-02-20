package com.crni99.qrcodegenerator.repository;

import com.crni99.qrcodegenerator.models.Usuaris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarisRepository extends JpaRepository<Usuaris, Long> {
    Usuaris findByEmailAndPassword(String email, String password);
}
