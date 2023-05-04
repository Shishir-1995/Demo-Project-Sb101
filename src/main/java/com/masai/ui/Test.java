package com.masai.ui;

import java.time.LocalDate;

public class Test {
	public static void main(String[] args) {
		System.out.println(LocalDate.parse("2023-04-30").minusDays(30));
		System.out.println(LocalDate.now().plusYears(1));
		System.out.println(LocalDate.parse("2022-05-03").plusYears(1));
	}
}
