package com.edu.udea.drai.bitacoras;


import org.json.JSONArray;
import org.json.JSONObject;

import com.edu.udea.drai.bitacoras.util.LeerJSON;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * Actividad encargada de desplegar la vista del detalle de una tarea
 * @author Heinner Esteban Alvarez Rivas <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */

public class Detalle_tarea extends Activity{

	/**
	 * TextView's de la vista
	 */
	TextView tvfecha;
	TextView tvhoras;
	TextView tvtarea;
	TextView tvperfil;
	TextView tvobservacion;
	
	/**
	 * ProgressDialog que indica la carga de datos
	 */
	ProgressDialog pDialog;
	
	/**
	 * Int que contendra id de la tarea a consultar
	 */
	String idTarea;
			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layaout_detalle_tarea);	
		/**
		 *capturo el id de la tarea
		 */
		idTarea = getIntent().getStringExtra("idTarea");
		/**
		 * Asocio las vistas a sus variables objeto
		 */
		tvfecha=(TextView) findViewById(R.id.textViewFecha);
		tvhoras=(TextView) findViewById(R.id.textViewHoras);
		tvtarea=(TextView) findViewById(R.id.textViewTarea);
		tvperfil=(TextView) findViewById(R.id.TextViewPerfil);
		tvobservacion=(TextView) findViewById(R.id.textViewObservacion);
		
		new LeerDetallesTarea().execute("http://172.21.32.77:1350/calendario/");		
	}
	
	private class LeerDetallesTarea extends AsyncTask<String, Void, String> {
		protected void onPreExecute() {
			pDialog = new ProgressDialog(Detalle_tarea.this);
			pDialog.setMessage(getString(R.string.carga_datos));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... urls) {
			return LeerJSON.leerJSON(urls[0]);
		}

		protected void onPostExecute(String result) {
		 try {		 			 	
			 	pDialog.dismiss();
			 	
			 	JSONArray tareas= new JSONArray(result);
			 	
			 	for (int i = 0; i < tareas.length(); i++) {
			 		JSONObject tareaJSON = tareas.getJSONObject(i);
			 		if(tareaJSON.getString("id").equalsIgnoreCase(idTarea)){			 			
						tvfecha.setText("  "+tareaJSON.getString("fecha"));
						tvhoras.setText("  "+tareaJSON.getString("horario"));
						tvtarea.setText("  "+tareaJSON.getString("tarea"));
						tvperfil.setText("  "+tareaJSON.getString("perfil"));
						tvobservacion.setText("  "+tareaJSON.getString("observacion"));
						break;
			 			}
			 			 		
			 				 		
			 		} 
			 				
			 	
			 			 		
			 
			 	} catch (Exception e) {
			 		e.printStackTrace();
			 	}
			 }
	}
	
	
	
	/**
	 * Evento click del boton Regresar
	 * @param view
	 */
	public void regresar(View view){
		
		onBackPressed();
	}
}
