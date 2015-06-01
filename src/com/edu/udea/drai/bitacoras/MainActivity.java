package com.edu.udea.drai.bitacoras;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.edu.udea.drai.bitacoras.model.Tarea;
import com.edu.udea.drai.bitacoras.util.DataPass;
import com.edu.udea.drai.bitacoras.util.LeerJSON;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * MainActivity, provee un espacio para ingresar la cedula de la cual se quiere
 * consultar las tareas registradas en el sistema
 * 
 * @author Heinner Esteban Alvarez <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */
public class MainActivity extends Activity {

	/**
	 * Elementos de la vista
	 */
	Button boton_consulta;
	EditText edCedula;
	/**
	 * String con la cedula digitada
	 */
	String cedAux;

	/**
	 * ProgressDialog que indica la carga de datos
	 */
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		boton_consulta = (Button) findViewById(R.id.botonConsulta);
		edCedula = (EditText) findViewById(R.id.editTextCedula);

	}

	public void onClickConsultar(View view) {
		cedAux = edCedula.getText().toString();
		edCedula.setText("");
		if (estaConectado()) {
			new LeerAuxiliarCedula()
					.execute("http://172.21.35.139:1350/calendario/");
		}

	}

	private class LeerAuxiliarCedula extends AsyncTask<String, Void, String> {
		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
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
				/**
				 * Obtengo listado de tareas del auxiliar
				 */
				ArrayList<Tarea> tareasObj = new ArrayList<Tarea>();

				JSONArray tareas = new JSONArray(result);

				for (int i = 0; i < tareas.length(); i++) {
					JSONObject tareaJSON = tareas.getJSONObject(i);
					if (tareaJSON.getString("cedulaaux").equalsIgnoreCase(
							cedAux)) {
						Tarea tarea = new Tarea(tareaJSON.getString("id"),
								tareaJSON.getString("auxiliar"),
								tareaJSON.getString("cedulaaux"),
								tareaJSON.getString("fecha"),
								tareaJSON.getString("horario"),
								tareaJSON.getString("tarea"),
								tareaJSON.getString("perfil"),
								tareaJSON.getString("observacion"));
						tareasObj.add(tarea);

					}

				}

				if (tareasObj.isEmpty()) {
					Toast.makeText(
							getApplicationContext(),
							"No existe el usuario.  \n"
									+ "Por favor verifique la informacion ingresada",
							Toast.LENGTH_LONG).show();

				} else {
					Intent e = new Intent(
							"com.edu.udea.drai.bitacoras.Lista_tareas");
					e.putExtra("tareas", new DataPass(tareasObj));
					startActivity(e);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected Boolean estaConectado() {
		if (conectadoWifi()) {
			return true;
		} else {
			if (conectadoRedMovil()) {
				return true;
			} else {
				showAlertDialog(MainActivity.this, "Conexion a Internet",
						"Tu Dispositivo no tiene Conexion a Internet.");			
				
				return false;
			}
		}
	}

	protected Boolean conectadoWifi() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (info != null) {
				if (info.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}

	protected Boolean conectadoRedMovil() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (info != null) {
				if (info.isConnected()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void showAlertDialog(Context context, String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		 
		alertDialog.setTitle(title);
		 
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
			});

		alertDialog.show();
		}


		

}
