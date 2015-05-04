package com.edu.udea.drai.bitacoras;

import java.util.ArrayList;
import com.edu.udea.drai.bitacoras.model.Tarea;
import com.edu.udea.drai.bitacoras.util.DataPass;

import com.edu.udea.drai.bitacoras.util.Lista_Adaptador;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Clase encargada de consultar y mostrar la lista de tareas asociadas a un auxiliar.
 * @author Heinner Esteban Alvarez <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */
public class Lista_tareas extends Activity {
	
	/**
	 * ProgressDialog que indica la carga de datos
	 */
	ProgressDialog pDialog;	
	/**
	 *Objeto ListView para deplegar la lista de tareas
	 */
	private ListView lista;
	
	
	/**
	 * ArrayList<Tarea> que contendra el listado de tareas
	 */
	ArrayList<Tarea> tareas;
	
	/**
	 * Objeto Tarea para controlar algunas funciones
	 */
	Tarea tar;
	
	
	
	/**
	 * TextView que contendra la bienvenida al usuario
	 */
	TextView tvBienvenida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layaout_lista_tareas);
		
		/**
		 * Obtengo la lista de tareas con el objeto dataPass
		 */
		DataPass dw = (DataPass) getIntent().getSerializableExtra("tareas");
		tareas = dw.getTareas();
		/**
		 * asocio la vista a su variable objeto
		 */
		tvBienvenida = (TextView) findViewById(R.id.textViewBienvenida);
		/**
		 * Relleno el mensaje de bienvenida con el nombre del auxiliar
		 */
		tvBienvenida.setText(tvBienvenida.getText()+"  "+tareas.get(0).getNombreAux());
		
		lista = (ListView) findViewById(R.id.listViewTareas);
		
		/**
		 * Agrego un adaptador de lista a la lista de tareas
		 */

	 	lista.setAdapter(new Lista_Adaptador(this ,R.layout.layaout_elemento_listado,
				 tareas) {
			/**
			 * Sobreescribo el metodo OnEntrada(), este llena las vistas de cada elemento de
			 * la lista con los valores de cada tarea
			 */
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					tar = (Tarea) entrada;
					TextView tvfecha = (TextView) view.findViewById(R.id.textViewFechaListado);
					if (tvfecha != null){
						tvfecha.setText(tar.getFecha());						
					}
					TextView tvtitulo = (TextView) view.findViewById(R.id.textViewTituloListado);
					if (tvtitulo != null){
						tvtitulo.setText(tar.getTarea());						
					}
					TextView tvhoras = (TextView) view.findViewById(R.id.textViewHorasListado);
					if (tvhoras != null){
						tvhoras.setText(tar.getHorario());					
					}								
				}
			}
		});

		/**
		 * Agrego un evento click en un item de la lista
		 */
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				
				tar = (Tarea) pariente
						.getItemAtPosition(posicion);
				String tareaId = tar.getId();
				Intent i = new Intent("com.edu.udea.drai.bitacoras.Detalle_tarea");
				i.putExtra("idTarea", tareaId);
				startActivity(i);

			}
		});
		
		

	}
	
	
}
