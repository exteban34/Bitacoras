package com.edu.udea.drai.bitacoras.util;

import java.io.Serializable;
import java.util.ArrayList;

import com.edu.udea.drai.bitacoras.model.Tarea;

public class DataPass implements Serializable {

	private ArrayList<Tarea> tareas;

	   public DataPass(ArrayList<Tarea> data) {
	      this.tareas = data;
	   }

	   public ArrayList<Tarea> getTareas() {
	      return this.tareas;
	   }
}
