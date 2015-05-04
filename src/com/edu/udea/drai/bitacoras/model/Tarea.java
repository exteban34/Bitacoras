package com.edu.udea.drai.bitacoras.model;

import java.io.Serializable;

/**
 * Clase para el tranporte de datos de Tareas en el sistema
 * @author Heinner Esteban Alvarez Rivas <exteban34@gmail.com>
 * @version 1.0 25/02/2015
 *
 */
public class Tarea implements Serializable{
	
	/**
	 * Id de identificacion de la tarea
	 */
	private String id;
	
	/**
	 * Auxiliar que realiza la tarea
	 */
	//private Auxiliar auxiliar;
	
	/**
	 * Fecha de realizacion de la tarea
	 */
	private String fecha;
	
	/**
	 * Hora de inicio de la tarea
	 */
	private String horario;
	
	
	/**
	 * Perfil que ejerce el auxiliar en la tarea
	 */
	private String perfil;
	
	/**
	 * Tarea que se realizara
	 */
	private String tarea;
	
	/**
	 * Observacion que lleva la tarea
	 */
	private String observacion;
	
	/**
	 * Cedula del auxiliar con quien se realizo un cambio
	 */
	private String CedulaCambio;
	/**
	 * cedula del auxiliar
	 */
	private String cedulaAux;
	/**
	 * nombre del auxiliar
	 */
	private String nombreAux;
	
	
	public Tarea(String id,String nombreaux,String ceduladAux, String fecha,String horario,  
			String tarea,String perfil, String observacion) {
		super();
		this.id = id;
		this.horario = horario;
		//this.horaFin = horaFin;
		this.perfil = perfil;
		this.tarea = tarea;
		this.observacion = observacion;
		this.fecha = fecha;
		this.cedulaAux=ceduladAux;
		this.nombreAux=nombreaux;		
		
	}
	
	public String getCedulaAux() {
		return cedulaAux;
	}

	public void setCedulaAux(String cedulaAux) {
		this.cedulaAux = cedulaAux;
	}

	public String getNombreAux() {
		return nombreAux;
	}

	public void setNombreAux(String nombreAux) {
		this.nombreAux = nombreAux;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	/*	
	public Auxiliar getAuxiliar() {
		return auxiliar;
	}
	public void setAuxiliar(Auxiliar auxiliar) {
		this.auxiliar = auxiliar;		
	}
	*/
	public String getCedulaCambio() {
		return CedulaCambio;
	}
	public void setCedulaCambio(String cedulaCambio) {
		CedulaCambio = cedulaCambio;
	}
	public String getHorario() {
		return horario;
	}
	public void setHoraInicio(String horario) {
		this.horario = horario;
	}
	/*public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}*/
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getTarea() {
		return tarea;
	}
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	

	
	
	
}
