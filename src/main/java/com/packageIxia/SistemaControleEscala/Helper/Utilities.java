package com.packageIxia.SistemaControleEscala.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class Utilities { 	
	
	public static <E> List<E> toList(Iterable<E> iterable) {		
		if(iterable instanceof List) {
			return (List<E>) iterable;
		}
		
		 //StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
		 
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
		System.out.println(date);
		System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(date));
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public static boolean validarHora(String data) {
		String rex="^(([0-1][0-9])||([2][0-3])):[0-5][0-9]$";
		return data.matches(rex);
	}
}
