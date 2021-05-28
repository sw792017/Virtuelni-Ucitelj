package com.javatechie.spring.drools.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Raspored {
	private int brTehnika;
	private List<String> zadaci;

	public Raspored(int brTehnika, List<String> zadaci) {
		this.brTehnika = brTehnika;
		this.zadaci = zadaci;
	}

	public Raspored(int brTehnika) {
		this.brTehnika = brTehnika;
		this.zadaci = new ArrayList<String>();
	}

	public Raspored(String str) {
		this.brTehnika = Integer.parseInt(str.split("@")[0]);
		this.zadaci = new ArrayList<String>();
		this.zadaci.addAll(Arrays.asList(str.split("@")[1].split("\\|")));
	}

	@Override
	public String toString() {
		String s = brTehnika + "@";
		for (String str : this.zadaci){
			s += str + "|";
		}
		return s.substring(0, s.length() - 1);
	}

	public int getBrTehnika() {
		return brTehnika;
	}
	
	
	public void setBrTehnika(int brTehnika) {
		this.brTehnika = brTehnika;
	}
	
	
	public List<String> getZadaci() {
		return zadaci;
	}
	
	
	public void setZadaci(List<String> zadaci) {
		this.zadaci = zadaci;
	}

	public void addZadatak(int i, String s){
		this.zadaci.set(i, s);
	}
}
