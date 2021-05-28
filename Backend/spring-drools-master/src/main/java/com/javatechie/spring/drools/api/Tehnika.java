package com.javatechie.spring.drools.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Tehnika {
	private String naziv;
	private List<PodTehnika> podtehnike;
	private double prioritet;
	private Date presao;
	
	
	@Override
	public String toString() {
		//Test Tehnika|60|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0
		String str = naziv + "|" + prioritet + "|" + presao + "|";
		for (PodTehnika pt : podtehnike) {
			str += pt.getNaziv() + "@" + pt.getKorak() + "@" + pt.getSavadao() + "%";
		}
		return str.substring(0, str.length() - 1);
	}

	public int comparedTo(Tehnika o1) {
		return this.getPodtehnika().getSavadao() > o1.getPodtehnika().getSavadao() ? -1 : 1;
	}

	public Tehnika(String naziv, List<PodTehnika> podtehnike, double prioritet, Date presao) {
		this.naziv = naziv;
		this.podtehnike = podtehnike;
		this.prioritet = prioritet;
		this.presao = presao;
	}
	
	public Tehnika(String json) {

		String[] niz = json.split("\\|");
		
		this.naziv = niz[0];
		this.prioritet = Double.parseDouble(niz[1]);

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateInString = niz[2];
			Date date = formatter.parse(dateInString);
			
			this.presao = date;
		}catch (Exception e) {
			this.presao = new Date(System.currentTimeMillis());
		}
		
		this.podtehnike = new ArrayList<PodTehnika>();

		for (String linija : niz[3].split("\\\"")[0].split("\\%"))
		{
		    String[] pt_str = linija.split("\\@");
		    PodTehnika pt = new PodTehnika(pt_str[0], Double.parseDouble(pt_str[1]), Double.parseDouble(pt_str[2]));
		    this.podtehnike.add(pt);
		}
		/*
			Test Tehnika|60|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0
		*/
	}


	public double getProcenat(){
		for(PodTehnika pt : this.getPodtehnike()){
			if (pt.getSavadao() < 100)
				return pt.getSavadao();
		}
		return 100;
	}

	public PodTehnika getPodtehnika(){
		for(PodTehnika pt : this.getPodtehnike()){
			if (pt.getSavadao() < 100)
				return pt;
		}
		return null;
	}

	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public List<PodTehnika> getPodtehnike() {
		return podtehnike;
	}


	public void setPodtehnike(List<PodTehnika> podtehnike) {
		this.podtehnike = podtehnike;
	}


	public double getPrioritet() {
		return prioritet;
	}


	public void setPrioritet(double prioritet) {
		this.prioritet = prioritet;
	}


	public Date getPresao() {
		return presao;
	}


	public void setPresao(Date presao) {
		this.presao = presao;
	}

	static void main(String[] args) {
		String s="Test Tehnika|60|01/01/2021|pt1@50@100%pt2@50@100%pt3@50@50%pt4@50@0%pt5@50@0";/*"{\n" + 
				"				\"naziv\":\"Test Tehnika\",\n" + 
				"				\"podtehnike\":[\r\n" + 
				"					{ \"naziv\":\"pt1\", \"korak\":\"50\", \"savadao\":\"100\" },\n" + 
				"					{ \"naziv\":\"pt2\", \"korak\":\"50\", \"savadao\":\"100\" },\n" + 
				"					{ \"naziv\":\"pt3\", \"korak\":\"50\", \"savadao\":\"100\" },\n" + 
				"					{ \"naziv\":\"pt4\", \"korak\":\"50\", \"savadao\":\"100\" },\n" + 
				"					{ \"naziv\":\"pt5\", \"korak\":\"50\", \"savadao\":\"100\" },\n" + 
				"					{ \"naziv\":\"pt6\", \"korak\":\"50\", \"savadao\":\"100\" }\n" + 
				"				],\n" + 
				"				\"prioritet\":\"60\"\n" + 
				"			}";*/
		Tehnika t = new Tehnika(s);
		
		System.out.println(t.toString());
	}

}