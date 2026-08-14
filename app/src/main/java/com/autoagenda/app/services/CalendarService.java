package com.autoagenda.app.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.autoagenda.app.dto.calendar.CalendarDayForm;
import com.autoagenda.app.dto.calendar.CalendarForm;
import com.autoagenda.app.models.AvailableDays;
import com.autoagenda.app.models.Reservation;
import com.autoagenda.app.models.User;
import com.autoagenda.app.repositories.AvaliableDaysRepository;
import com.autoagenda.app.repositories.UserRepository;

@Service
public class CalendarService {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	private final AvaliableDaysRepository avaliableDaysRepository;
	private final UserRepository userRepository;

	public CalendarService(AvaliableDaysRepository avaliableDaysRepository, UserRepository userRepository) {
		this.avaliableDaysRepository = avaliableDaysRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public CalendarForm getCalendarForm(Long userId) {
		User user = getUserOrThrow(userId);
		ensureCalendarExists(user);

		var form = new CalendarForm();
		List<CalendarDayForm> days = new ArrayList<>();
		DayOfWeek[] orderedDays = getOrderedDays();
		for (DayOfWeek dayOfWeek : orderedDays) {
			Optional<AvailableDays> existingDay = this.avaliableDaysRepository.findByUser_IdAndWeekDay(userId, dayOfWeek);
			if (existingDay.isPresent()) {
				days.add(toForm(existingDay.get()));
			}
		}
		form.setDays(days);
		return form;
	}

	@Transactional
	public void saveCalendar(Long userId, CalendarForm form) {
		User user = getUserOrThrow(userId);

		if (form == null || form.getDays() == null) {
			return;
		}

		List<CalendarDayForm> days = form.getDays();
		for (int i = 0; i < days.size(); i++) {
			CalendarDayForm dayForm = days.get(i);
			if (dayForm == null) {
				continue;
			}

			String weekDayValue = dayForm.getWeekDay();
			if (weekDayValue == null || weekDayValue.isBlank()) {
				continue;
			}

			DayOfWeek dayOfWeek = DayOfWeek.valueOf(weekDayValue);
			AvailableDays day = findDay(userId, dayOfWeek);
			if (day == null) {
				day = new AvailableDays();
				day.setUser(user);
				day.setWeekDay(dayOfWeek);
			}

			day.setUser(user);
			day.setWeekDay(dayOfWeek);
			day.setOpening(parseTime(dayForm.getOpening(), defaultOpening()));
			day.setClosening(parseTime(dayForm.getClosening(), defaultClosing()));

			this.avaliableDaysRepository.save(day);
		}
	}

	@Transactional
	public void createReservation(Long userId, String date, String time) {
		User user = getUserOrThrow(userId);
		if (date == null || date.isBlank() || time == null || time.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data e horário são obrigatórios");
		}

		LocalDate selectedDate = LocalDate.parse(date);
		DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();
		AvailableDays availableDay = findDay(userId, dayOfWeek);
		if (availableDay == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dia sem disponibilidade cadastrada");
		}

		OffsetTime selectedTime;
		try {
			selectedTime = parseTime(time, null);
		} catch (RuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário inválido");
		}
		if (selectedTime == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário inválido");
		}

		if (!isTimeAllowed(availableDay, selectedTime)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário fora da disponibilidade do dia");
		}

		Reservation reservation = new Reservation();
		reservation.setHorario(OffsetDateTime.of(selectedDate, selectedTime.toLocalTime(), ZoneOffset.UTC));

		List<Reservation> reservations = availableDay.getReservedDay();
		if (reservations == null) {
			reservations = new ArrayList<>();
			availableDay.setReservedDay(reservations);
		}
		reservations.add(reservation);
		availableDay.setUser(user);
		this.avaliableDaysRepository.save(availableDay);
	}

	private void ensureCalendarExists(User user) {
		DayOfWeek[] orderedDays = getOrderedDays();
		for (int i = 0; i < orderedDays.length; i++) {
			DayOfWeek dayOfWeek = orderedDays[i];
			Optional<AvailableDays> existingDay = this.avaliableDaysRepository.findByUser_IdAndWeekDay(user.getId(), dayOfWeek);
			if (existingDay.isEmpty()) {
				AvailableDays createdDay = new AvailableDays();
				createdDay.setUser(user);
				createdDay.setWeekDay(dayOfWeek);
				createdDay.setOpening(defaultOpening());
				createdDay.setClosening(defaultClosing());
				this.avaliableDaysRepository.save(createdDay);
			}
		}
	}

	private CalendarDayForm toForm(AvailableDays availableDays) {
		CalendarDayForm form = new CalendarDayForm();
		form.setWeekDay(availableDays.getWeekDay().name());
		form.setLabel(availableDays.getWeekDay().getDisplayName(java.time.format.TextStyle.FULL, new Locale("pt", "BR")));
		form.setOpening(formatTime(availableDays.getOpening()));
		form.setClosening(formatTime(availableDays.getClosening()));
		form.setTimeOptions(buildTimeOptions(availableDays.getOpening(), availableDays.getClosening()));
		return form;
	}

	private List<String> buildTimeOptions(OffsetTime opening, OffsetTime closening) {
		List<String> timeOptions = new ArrayList<>();
		OffsetTime currentTime = opening.withSecond(0).withNano(0);
		OffsetTime endTime = closening.withSecond(0).withNano(0);

		while (!currentTime.isAfter(endTime)) {
			timeOptions.add(formatTime(currentTime));
			currentTime = currentTime.plusMinutes(15);
		}

		return timeOptions;
	}

	private String formatTime(OffsetTime time) {
		return time.withSecond(0).withNano(0).format(TIME_FORMATTER);
	}

	private OffsetTime parseTime(String time, OffsetTime fallback) {
		if (time == null || time.isBlank()) {
			return fallback;
		}

		try {
			return OffsetTime.of(LocalTime.parse(time), ZoneOffset.UTC);
		} catch (RuntimeException ex) {
			return fallback;
		}
	}

	private boolean isTimeAllowed(AvailableDays availableDay, OffsetTime selectedTime) {
		OffsetTime opening = availableDay.getOpening();
		OffsetTime closing = availableDay.getClosening();
		if (opening == null || closing == null || selectedTime == null) {
			return false;
		}

		OffsetTime currentTime = opening.withSecond(0).withNano(0);
		OffsetTime endTime = closing.withSecond(0).withNano(0);
		OffsetTime targetTime = selectedTime.withSecond(0).withNano(0);

		while (!currentTime.isAfter(endTime)) {
			if (currentTime.equals(targetTime)) {
				return true;
			}
			currentTime = currentTime.plusMinutes(15);
		}

		return false;
	}

	private AvailableDays findDay(Long userId, DayOfWeek dayOfWeek) {
		Optional<AvailableDays> existingDay = this.avaliableDaysRepository.findByUser_IdAndWeekDay(userId, dayOfWeek);
		if (existingDay.isPresent()) {
			return existingDay.get();
		}
		return null;
	}

	private DayOfWeek[] getOrderedDays() {
		return new DayOfWeek[] {
			DayOfWeek.SUNDAY,
			DayOfWeek.MONDAY,
			DayOfWeek.TUESDAY,
			DayOfWeek.WEDNESDAY,
			DayOfWeek.THURSDAY,
			DayOfWeek.FRIDAY,
			DayOfWeek.SATURDAY
		};
	}

	private OffsetTime defaultOpening() {
		return OffsetTime.of(LocalTime.of(8, 0), ZoneOffset.UTC);
	}

	private OffsetTime defaultClosing() {
		return OffsetTime.of(LocalTime.of(17, 0), ZoneOffset.UTC);
	}

	private User getUserOrThrow(Long userId) {
		Optional<User> user = this.userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
	}
}
