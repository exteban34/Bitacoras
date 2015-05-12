package com.edu.udea.drai.bitacoras;

import java.util.Calendar;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import com.edu.udea.drai.bitacoras.util.LeerJSON;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Actividad encargada de desplegar la vista del detalle de una tarea
 * 
 * @author Heinner Esteban Alvarez Rivas <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */

public class Detalle_tarea extends Activity {

	/**
	 * TextView's de la vista
	 */
	TextView tvfecha;
	TextView tvhoras;
	TextView tvtarea;
	TextView tvperfil;
	TextView tvobservacion;

	/**
	 * String's para capturar los datos desde el objeto JSON
	 */
	String titulo;
	String fecha;
	String horario;
	String perfil;
	String observacion;

	/**
	 * Int's que se usan para añadir el evento al calendario
	 */
	int diaIni;
	int mesIni;
	int anoIni;
	int horaIni;
	int minIni;
	int horaFin;
	int minFin;

	/**
	 * String's que se usan para capturar los datos que se requieren para añadir
	 * al calendario
	 */
	String horaInicio;
	String minInicio;
	String horaFinal;
	String minFinal;
	String diaInicio;
	String mesInicio;
	String anoInicio;

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
		 * capturo el id de la tarea
		 */
		idTarea = getIntent().getStringExtra("idTarea");
		/**
		 * Asocio las vistas a sus variables objeto
		 */
		tvfecha = (TextView) findViewById(R.id.textViewFecha);
		tvhoras = (TextView) findViewById(R.id.textViewHoras);
		tvtarea = (TextView) findViewById(R.id.textViewTarea);
		tvperfil = (TextView) findViewById(R.id.TextViewPerfil);
		tvobservacion = (TextView) findViewById(R.id.textViewObservacion);
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

				JSONArray tareas = new JSONArray(result);

				for (int i = 0; i < tareas.length(); i++) {
					JSONObject tareaJSON = tareas.getJSONObject(i);
					if (tareaJSON.getString("id").equalsIgnoreCase(idTarea)) {
						tvfecha.setText("  " + tareaJSON.getString("fecha"));
						fecha = tareaJSON.getString("fecha");
						tvhoras.setText("  " + tareaJSON.getString("horario"));
						horario = tareaJSON.getString("horario");
						tvtarea.setText("  " + tareaJSON.getString("tarea"));
						titulo = tareaJSON.getString("tarea");
						tvperfil.setText("  " + tareaJSON.getString("perfil"));
						perfil = tareaJSON.getString("perfil");
						tvobservacion.setText("  "
								+ tareaJSON.getString("observacion"));
						observacion = tareaJSON.getString("observacion");
						obtenerDatosParaCalendario();
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
	 * 
	 * @param view
	 */
	public void regresar(View view) {

		onBackPressed();
	}

	/**
	 * Evento click del boton Añadir
	 * 
	 * @param view
	 */
	public void anadir(View view) {
		try {
			Calendar beginTime = Calendar.getInstance();
			beginTime.set(anoIni, mesIni, diaIni, horaIni, minIni);
			Calendar endTime = Calendar.getInstance();
			endTime.set(anoIni, mesIni, diaIni, horaFin, minFin);
			TimeZone timeZone = TimeZone.getDefault();
			Intent intent = new Intent(Intent.ACTION_INSERT)
					.setData(Events.CONTENT_URI)
					.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
							beginTime.getTimeInMillis())
					.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
							endTime.getTimeInMillis())
					.putExtra(Events.TITLE, titulo)
					.putExtra(
							Events.DESCRIPTION,
							"perfil: " + perfil + "\n" + "observacion: "
									+ observacion)
					.putExtra(Events.EVENT_TIMEZONE, timeZone.getID());

			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"El formato para añadir al calendario no es valido",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Metodo encargado de desglosar los datos obtenidos en el objeto JSON y
	 * capturarlos en Int's para añadir al calendario
	 */
	public void obtenerDatosParaCalendario() {

		/**
		 * Desglosar String's que contienen Fechas y Horas
		 */
		horaInicio = horario.substring(0, horario.indexOf(":"));
		minInicio = horario.substring(horario.indexOf(":") + 1,
				horario.indexOf("-"));
		horaFinal = horario.substring(horario.indexOf("-") + 1,
				horario.lastIndexOf(":"));
		minFinal = horario.substring(horario.lastIndexOf(":") + 1,
				horario.length());
		mesInicio = fecha.substring(0, fecha.indexOf("/"));
		diaInicio = fecha.substring(fecha.indexOf("/") + 1,
				fecha.lastIndexOf("/"));
		anoInicio = fecha.substring(fecha.lastIndexOf("/") + 1, fecha.length());
		/**
		 * Capturar en enteros los datos en los substring's resultantes
		 */
		horaIni = Integer.valueOf(horaInicio);
		minIni = Integer.valueOf(minInicio);
		diaIni = Integer.valueOf(diaInicio);
		anoIni = (Integer.valueOf(anoInicio) + 2000);
		mesIni = Integer.valueOf(mesInicio);
		horaFin = Integer.valueOf(horaFinal);
		minFin = Integer.valueOf(minFinal);

		/**
		 * Ciclo para chequear el mes de la Fecha Inicio
		 */
		/*
		 * if(mesInicio.equalsIgnoreCase("Enero")){ mesIni=0; }else{
		 * if(mesInicio.equalsIgnoreCase("Febrero")){ mesIni=1; }else{
		 * if(mesInicio.equalsIgnoreCase("Marzo")){ mesIni=2; }else{
		 * if(mesInicio.equalsIgnoreCase("Abril")){ mesIni=3; }else{
		 * if(mesInicio.equalsIgnoreCase("Mayo")){ mesIni=4; }else{
		 * if(mesInicio.equalsIgnoreCase("Junio")){ mesIni=5; }else{
		 * if(mesInicio.equalsIgnoreCase("Julio")){ mesIni=6; }else{
		 * if(mesInicio.equalsIgnoreCase("Agosto")){ mesIni=7; }else{
		 * if(mesInicio.equalsIgnoreCase("Septiembre")){ mesIni=8; }else{
		 * if(mesInicio.equalsIgnoreCase("Octubre")){ mesIni=9; }else{
		 * if(mesInicio.equalsIgnoreCase("Noviembre")){ mesIni=10; }else{
		 * if(mesInicio.equalsIgnoreCase("Diciembre")){ mesIni=11; } } } } } } }
		 * } } } } }
		 */

	}
}
