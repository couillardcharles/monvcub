package com.appspot.monvcub;

public class Station {

	public String getNom() {
		return nom;
	}

	public int getVélosDisponibles() {
		return vélosDisponibles;
	}

	public int getPlacesDisponibles() {
		return placesDisponibles;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setVélosDisponibles(int nombre) {
		vélosDisponibles = nombre;
	}

	public void setPlacesDisponibles(int nombre) {
		placesDisponibles = nombre;
	}

	public void setEnMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}

	public boolean estEnMaintenance() {
		return maintenance;
	}

	private String nom;
	private int vélosDisponibles;
	private int placesDisponibles;
	private boolean maintenance;
}
