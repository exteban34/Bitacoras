package com.edu.udea.drai.bitacoras.model;

/**
 * Clase para el tranporte de datos de Auxiliares en el sistema
 * @author Heinner Esteban Alvarez Rivas <exteban34@gmail.com>
 * @version 1.0 25/02/2015
 *
 */
public class Auxiliar {
	
	/**
	 * cedula del auxiliar
	 */
	private String cedula;
	/**
	 * nombre del auxiliar
	 */
	private String nombre;	

	public Auxiliar(String cd, String nom){
		nombre=nom;
		cedula= cd;
		}
	       
	   
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	
	
	
}
