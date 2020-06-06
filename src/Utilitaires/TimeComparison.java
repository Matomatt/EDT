package Utilitaires;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Compare 2 temps et renvoie true si le temps 1 est plus petit que le temps 2
 */
public class TimeComparison {
	public static boolean Compare(Time time1, Time time2) 
	{
		LocalDate today = LocalDate.now();
		String startTimeStrT = today + " " + time1;
		String endTimeStrT = today + " " + time2;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		try {

			LocalDateTime startTime = LocalDateTime.parse(startTimeStrT, formatter);
			LocalDateTime endTime = LocalDateTime.parse(endTimeStrT, formatter);

			Duration d = Duration.between(startTime, endTime);
			
			return (d.getSeconds() < 0);
		} catch (DateTimeParseException e) {
			System.out.println("Invalid Input" + e.getMessage());
			return false;
		}
	}
}
