package co.themafia.service;

import java.io.Serializable;

public class CampanaWS implements Serializable{


	private static final long serialVersionUID = 1L;
	private Integer idCampana;
	private String nombreCampana;
	
	public CampanaWS(Integer idCampana, String nombre) {
		this.idCampana = idCampana;
		this.nombreCampana = nombre;
	}
	
	public Integer getIdCampana() {
		return idCampana;
	}
	public void setIdCampana(Integer idCampana) {
		this.idCampana = idCampana;
	}
	public String getNombreCampana() {
		return nombreCampana;
	}
	public void setNombreCampana(String nombreCampana) {
		this.nombreCampana = nombreCampana;
	}
	
	
}
