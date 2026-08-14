package com.autoagenda.app.models;

import java.time.DayOfWeek;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "dias_disponiveis")
public class AvailableDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia_semana")
    private DayOfWeek weekDay;
    @Column(name = "horario_abertura")
    public OffsetTime opening;
    @Column(name = "horario_fechamento")
    public OffsetTime closening;
    @ManyToOne ()
    @JoinColumn(name = "user_id")
    private User user; 

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "day_id")
    public List<Reservation> reservedDay = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(DayOfWeek weekDay) {
        this.weekDay = weekDay;
    }

    public OffsetTime getOpening() {
        return opening;
    }

    public void setOpening(OffsetTime opening) {
        this.opening = opening;
    }

    public OffsetTime getClosening() {
        return closening;
    }

    public void setClosening(OffsetTime closening) {
        this.closening = closening;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reservation> getReservedDay() {
        return reservedDay;
    }

    public void setReservedDay(List<Reservation> reservedDay) {
        this.reservedDay = reservedDay;
    }
}
