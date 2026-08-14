package com.autoagenda.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoagenda.app.models.AvailableDays;

public interface AvaliableDaysRepository extends JpaRepository<AvailableDays, Long>{
    
}
