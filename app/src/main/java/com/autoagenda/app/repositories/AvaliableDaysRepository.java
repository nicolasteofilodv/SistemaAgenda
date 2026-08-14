package com.autoagenda.app.repositories;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoagenda.app.models.AvailableDays;

public interface AvaliableDaysRepository extends JpaRepository<AvailableDays, Long>{

	List<AvailableDays> findAllByUser_IdOrderByWeekDayAsc(Long userId);

	Optional<AvailableDays> findByUser_IdAndWeekDay(Long userId, DayOfWeek weekDay);
}
