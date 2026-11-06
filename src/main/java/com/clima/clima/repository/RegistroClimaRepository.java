package com.clima.clima.repository;

import com.clima.clima.entity.RegistroClima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroClimaRepository extends JpaRepository<RegistroClima, Long> {

}