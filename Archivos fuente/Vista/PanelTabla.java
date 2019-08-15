package Vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import Modelo.Proceso;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.EtchedBorder;

public class PanelTabla extends JPanel {

	private static final long serialVersionUID = -4287050789703524430L;
	int ancho;
	int alto;
	int cantidadProcesos;
	int altoEtiqueta;
	int anchoEtiqueta;
	VentanaPrincipal area;
	ArrayList<Proceso> listaProcesos;
	ArrayList<ArrayList <JLabel> > tabla;
	ArrayList<Proceso> resultado;
	ArrayList<ArrayList<Double>> tabular;
	ArrayList<Double> promediosDatos;
	
	/**
	 * Create the panel.
	 */
	public PanelTabla(VentanaPrincipal area) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.WHITE);
		setLayout(null);
		this.area = area;
		listaProcesos = new ArrayList<Proceso>();
		ancho = 1000;
		alto = 700;
		cantidadProcesos = listaProcesos.size();
		altoEtiqueta = (int)(alto/(cantidadProcesos+7.5));
		anchoEtiqueta = (int) (ancho/7.0);
		tabla = new ArrayList<ArrayList <JLabel> >();
		promediosDatos = new ArrayList<Double>();
		JLabel label = null;
		EtchedBorder e = new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, null);
		Font f = new Font("Source Sans Pro", Font.PLAIN, 16);
		tabla.add(new ArrayList<JLabel>());
		for (int j = 0; j < 5; j++) {
			label = new JLabel();
			label.setFont(f);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(anchoEtiqueta*(j+1), altoEtiqueta, anchoEtiqueta, altoEtiqueta);
			label.setBorder(e);
			add(label);
			tabla.get(0).add(label);
		}
		tabla.get(0).get(0).setText("Procesos");
		tabla.get(0).get(1).setText("Tiempo final");
		tabla.get(0).get(2).setText("Tiempo de servicio");
		tabla.get(0).get(3).setText("Tiempo de espera");
		tabla.get(0).get(4).setText("Indice de servicio");
	}
	
	public void actualizar(){
		for (int i = 0; i < tabla.size(); i++) {
			for (int j = 0; j < tabla.get(i).size(); j++) {
				this.remove(tabla.get(i).get(j));
			}
		}
		tabla.clear();
		listaProcesos = area.getTerminados();
		cantidadProcesos = listaProcesos.size();
		altoEtiqueta = (int)(alto/(cantidadProcesos+7.5));
		anchoEtiqueta = (int) (ancho/7.0);
		cantidadProcesos = listaProcesos.size();
		resultado = organizarProcesos(listaProcesos);
		tabular = tiempos(resultado);
		tabla = new ArrayList<ArrayList <JLabel> >();
		promediosDatos = new ArrayList<Double>();
		JLabel label = null;
		EtchedBorder e = new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, null);
		EtchedBorder e1 = new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, null);
		Font f = new Font("Source Sans Pro", Font.PLAIN, 16);
		for (int i = 0; i < cantidadProcesos+2; i++) {
			tabla.add(new ArrayList<JLabel>());
			for (int j = 0; j < 5; j++) {
				label = new JLabel("");
				label.setFont(f);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBounds(anchoEtiqueta*(j+1), altoEtiqueta*(i+1), anchoEtiqueta, altoEtiqueta);
				if(i == 0 || j == 0){
					label.setBorder(e1);
				}
				else{
					label.setBorder(e);
				}
				add(label);
				tabla.get(i).add(label);
			}
		}
		
		tabla.get(0).get(0).setText("Procesos");
		tabla.get(0).get(1).setText("Tiempo final");
		tabla.get(0).get(2).setText("Tiempo de servicio");
		tabla.get(0).get(3).setText("Tiempo de espera");
		tabla.get(0).get(4).setText("Indice de servicio");
		tabla.get(cantidadProcesos+1).get(0).setText("Promedios: ");
		
		for (int i = 1; i <= cantidadProcesos; i++) {
			tabla.get(i).get(0).setText("Proceso "+ i);
		}
		
		for (int i = 1; i < cantidadProcesos+1; i++) {
			for (int j = 1; j < 5; j++) {
				tabla.get(i).get(j).setText(""+tabular.get(i-1).get(j-1));
			}
		}
		
		promediosDatos = promedios(tabular);
		for (int i = 0; i < promediosDatos.size(); i++) {
			tabla.get(cantidadProcesos+1).get(i+1).setText(""+promediosDatos.get(i));
		}
	}
	public ArrayList<Proceso> organizarProcesos(ArrayList<Proceso> procesos){
		for (int i = 0; i < procesos.size(); i++) {
			for (int j = i+1; j < procesos.size(); j++) {
				if(procesos.get(i).getNumeroProceso() > procesos.get(j).getNumeroProceso()){
					Proceso aux = procesos.get(i);
					procesos.set(i, procesos.get(j));
					procesos.set(j, aux);
				}
			}
		}
		return procesos;
	}
	
	public ArrayList<ArrayList<Double>> tiempos(ArrayList<Proceso> procesos){
		
		ArrayList<ArrayList<Double>> datosProceso = new ArrayList<ArrayList<Double>>();
		Double tiempoEspera = 0.0;
		Double tiempoServicio = 0.0;
		Double tiempoFinal = 0.0;
		Double indiceServicio = 0.0;
		Double tiempoEjecucion = 0.0;
		for (int i = 0; i < procesos.size(); i++) {
			ArrayList<Double> datosTabla = new ArrayList<Double>();
			for (int j = 0; j < procesos.get(i).getEstados().size(); j++) {
				if(procesos.get(i).getEstados().get(j) == 1){
					tiempoEspera = tiempoEspera+1;
				}
				if(procesos.get(i).getEstados().get(j) == 2){
					tiempoEjecucion = tiempoEjecucion+1;
				}
				if(procesos.get(i).getEstados().get(j) != 0 && procesos.get(i).getEstados().get(j) != 4){
					tiempoServicio = tiempoServicio+1;
				}
				tiempoFinal = tiempoFinal+1;
			}
			indiceServicio = tiempoEjecucion/tiempoServicio;
			datosTabla.add(tiempoFinal-1);
			datosTabla.add(tiempoServicio);
			datosTabla.add(tiempoEspera);
			datosTabla.add((double)Math.rint(indiceServicio*100)/100);
			datosProceso.add(datosTabla);
			tiempoEspera = 0.0;
			tiempoServicio= 0.0;
			tiempoFinal = 0.0;
			tiempoEjecucion = 0.0;
			indiceServicio = 0.0;
		}
		
		return datosProceso;
	}
	

	public ArrayList<Double> promedios(ArrayList<ArrayList <Double>> datos){
		ArrayList<Double> promedio = new ArrayList<Double>();
		double promedioTiempoFinal = 0;
		double promedioServicio = 0;
		double promedioEspera = 0;
		double promedioIndice = 0;
		for (int i = 0; i < datos.size(); i++) {
			for (int j = 0; j < datos.get(i).size(); j++) {
				if(j==0){
					promedioTiempoFinal = promedioTiempoFinal + datos.get(i).get(j);
				}else if(j==1){
					promedioServicio = promedioServicio + datos.get(i).get(j);
				}else if(j==2){
					promedioEspera = promedioEspera + datos.get(i).get(j);
				}else if(j==3){
					promedioIndice = promedioIndice + datos.get(i).get(j);
				}
			}
		}
		promedio.add((double)Math.rint(promedioTiempoFinal/cantidadProcesos*100)/100);
		promedio.add((double)Math.rint(promedioServicio/cantidadProcesos*100)/100);
		promedio.add((double)Math.rint(promedioEspera/cantidadProcesos*100)/100);
		promedio.add((double)Math.rint(promedioIndice/cantidadProcesos*100)/100);
		return promedio;
	}
	
}
