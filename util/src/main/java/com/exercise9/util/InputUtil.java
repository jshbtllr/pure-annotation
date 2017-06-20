package com.exercise9.util;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class InputUtil {
	public static Boolean checkDate(String input) {
		Date date = null;
		Boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);

			try {
				date = format.parse(input);
			} catch(ParseException pe) {
				flag = false;
			}
		return flag;
	}

	public static Date getDate(String input) {
		Date date = null;
		Boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);

			try {
				date = format.parse(input);
			} catch(ParseException pe) {
				flag = false;
				System.out.print("Date or format incorrect. Input another: ");
			}
		return date;
	}

	public static Boolean checkGrade(Float grade) {
		
		if ((grade < 1) || grade > 5) {
			return false;
		} else {
			return true;
		}
	}	
}