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
		for(int i = 0 ; i < brTehnika ; ++i){
			zadaci.add("Zadatak" + (i+1));
		}
		while(zadaci.size() != 5)
			zadaci.add(" ");
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
		try {
			this.zadaci.set(i, s);
		}catch (Exception e){
			for (int j = 0 ; j < i ; ++j)
				try {
					if (this.getZadatak(j).equals(""))
						this.addZadatak(j, "Zadatak" + (j + 1));
				}catch (java.lang.IndexOutOfBoundsException e2){
					List<String> rr = this.getZadaci();
					rr.add("Zadatak" + (j + 1));
					this.setZadaci(rr);
				}
		}
	}

	public String getZadatak(int i){
		return this.zadaci.get(i);
	}
}
