package com.autoagenda.app.models;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    public Long id;

    @Column(name = "horario_inicio")
    public OffsetDateTime horario;

    @Column(name = "horario_fim")
    public OffsetDateTime horarioFim;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getHorario() {
        return horario;
    }

    public void setHorario(OffsetDateTime horario) {
        this.horario = horario;
    }

    public OffsetDateTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(OffsetDateTime horarioFim) {
        this.horarioFim = horarioFim;
    }
}
