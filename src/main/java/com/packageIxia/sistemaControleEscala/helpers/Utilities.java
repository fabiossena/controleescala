package com.packageIxia.sistemaControleEscala.helpers;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.validation.BindingResult;

public class Utilities { 	
	
	public static <E> List<E> toList(Iterable<E> iterable) {		
		if(iterable instanceof List) {
			return (List<E>) iterable;
		}
		 
		ArrayList<E> list = new ArrayList<E>();
		if(iterable != null) {
			for(E e: iterable) {
				list.add(e);
			}
		}
		
		return list;
	}

	public static String getAllErrosBindingResult(BindingResult result) {
		return result.getFieldErrors().stream().map(x->replaceIdTag(x.getField())).collect(Collectors.joining(", "));
	}
	
	private static String replaceIdTag(String value) {
		value = (value +"-").replaceAll("Id-", "").replaceAll("-", "");
		value = String.join(" ", value.split("(?<=\\p{Ll})(?=\\p{Lu})|(?<=\\p{L})(?=\\p{Lu}\\p{Ll})"));
		return  value.toLowerCase();
	}
	
	public static String dateToString(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public static LocalDateTime stringToDateTime(String date) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date , f) ;
	}
	
	public static boolean validarHora(String hora) {
		String rex = "^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$";
		return hora == null || hora == "" ? false : hora.matches(rex);
	}

	
	public static boolean validarHoraPreenchida(String hora) {
		String rex = "^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$";
		return hora == null || hora == "" ? true : hora.matches(rex);
	}
	
	public static int horaToInt(String hora) {
		return Integer.parseInt(hora.replace(":", ""));
	}
	
	public static String horaDiff(String hora1, String hora2) {
		double diff = horaValueDiff(hora1, hora2);
		if (diff == 0) {
			return "";
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(diff) + (diff > 1 ? "hrs" : "hr");
	}
	
	public static double horaValueDiff(String hora1, String hora2) {
		if (!validarHora(hora1) || !validarHora(hora1)) {
			return 0;
		}
		
		String[] hrs1 = hora1.split(":");
		double hr1 = Integer.parseInt(hrs1[0]) + ((Integer.parseInt(hrs1[1])/0.6)/100);

		String[] hrs2 = hora2.split(":");
		double hr2 = Double.parseDouble(hrs2[0]) + ((Double.parseDouble(hrs2[1])/0.6)/100);
		double diff = hr2 - hr1;		
		return diff;
	}
	
	public static boolean dataEstaEntreDiasDaSemana(LocalDate dataInicio, int diaSemanaInicio, int diaSemanaFim) {
		return dataEstaEntreDiasDaSemana(dataInicio, diaSemanaInicio, diaSemanaFim, true);
	}
	
	public static boolean dataEstaEntreDiasDaSemana(LocalDate data, int diaSemanaInicio, int diaSemanaFim, boolean ajuste) {
		if (data == null) {
			return true;
		}
		
		List<Integer> diasSemanas = new ArrayList<Integer>();
//		if (ajuste) {
//			diaSemanaInicio = diaSemanaInicio == 1 ? 7 : diaSemanaInicio - 1;
//			diaSemanaFim = diaSemanaFim == 1 ? 7 : diaSemanaFim - 1;
//		}
		
		while (diaSemanaInicio <= diaSemanaFim) {
			diasSemanas.add(diaSemanaInicio);
			diaSemanaInicio = diaSemanaInicio+1;
		}

		if (diasSemanas.stream().anyMatch(x->x==data.getDayOfWeek().getValue())) {
			return true;
		}
		
		return false;
	}

	public static double Round(double value) {
		return Round(value, 2);
	}
	
	public static double Round(double value, int casas) {
		DecimalFormat formato = new DecimalFormat("#0." + String.join("", Collections.nCopies(casas, "#")));
		return Double.parseDouble(formato.format(value).replace(",", "."));
	}

	public static Iterable<Long> streamLongToIterable(Stream<Long> longValues) {
		return longValues == null ? null : longValues.map(Long::longValue).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> converterObjetoParaArrayTipo(Object list, T t){
	    return (List<T>)list;
	}

	@SuppressWarnings("unchecked")
	public static <T> T converterObjetoParaTipo(Object list, T t){
	    return (T)list;
	}

	public static String getExtension(String file) {
		String[] partes = file.split("\\.");        
		return partes[partes.length-1];
	}
	
	public static String converterToTime(int sec) {
		if (sec <= 0) {
			return "00:00:00";
		}
		
	    int seconds = sec % 60;
	    int minutes = sec / 60;
	    if (minutes >= 60) {
	        int hours = minutes / 60;
	        minutes %= 60;
	        if( hours >= 24) {
	            int days = hours / 24;
	            return String.format("%d days %02d:%02d:%02d", days,hours%24, minutes, seconds);
	        }
	        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    }
	    return String.format("00:%02d:%02d", minutes, seconds);
	}

	public static LocalDateTime now() {
		Calendar c= Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		Date dataNow= c.getTime();
		System.out.println("DATA AGORA " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataNow));

		Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"), new Locale("pt", "BR"));
		System.out.println("DATA AGORA2 " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(c2.getTime()));
		System.out.println("DATA real " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(LocalDateTime.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId())));
		System.out.println("DATA real2 " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(LocalDateTime.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()).minusHours(1)));
		
		return LocalDateTime.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()).minusHours(1);
	}

	public static LocalDate now2() {
		return LocalDate.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId());
	}
	
}
