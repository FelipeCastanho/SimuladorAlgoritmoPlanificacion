package Controlador;
import Modelo.Algoritmo;
import Modelo.ColasMultiples;
import Modelo.FCFS;
import Modelo.PrioridadApropiativa;
import Modelo.Proceso;
import Modelo.RR;
import Modelo.SRT;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import Vista.PanelDatos;


public class ControladorPanelDatos implements MouseListener,ActionListener
{
	PanelDatos area;
	JLabel comodin;
	Proceso comodin2;
	ArrayList<Proceso> procesos;
	ArrayList<Algoritmo> algoritmos;
	boolean tablaGenerada = false;
	int columnas = 0;
	int filas = 0;
	int algoritmo = 1;
	boolean error = false;
	ColasMultiples colitas;
	ArrayList<Proceso> datos;
	
	public ControladorPanelDatos(PanelDatos area){
		this.area = area;
		algoritmos = new ArrayList<Algoritmo>();
		colitas = null;
		datos = null;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		int aux = 0;
		int aux2 = 0;
		int x = e.getX();
		int y = e.getY();
		aux = 10;
		filas = Integer.parseInt(area.numeroProcesos.getText());
		aux2 = ((area.getAnchoTabla()/area.getAnchoCelda())-3)/2;
		columnas = Integer.parseInt(area.numeroBloqueos.getText());
		if(x>= 200 && x<= 290 && y >= 10 && y <= 35){
			if(filas > aux){
				JOptionPane.showMessageDialog(area, "Error, el número de procesos excede el tamaño de la tabla");
			}	
			else if(columnas > aux2){
				JOptionPane.showMessageDialog(area, "Error, el número de rafagas de CPU-Bloqueos excede el tamaño de la tabla");
			}
			else{
				reiniciarTabla();
				crearCabecera(filas, columnas);
				tablaGenerada = true;
			}
			
		}
		else if(x >= area.getAnchoTabla()-65 && x<= area.getAnchoTabla()+5 && y >= 25 && y <=50){
			for (int i =1; i <= filas && !error ; i++) {
				for (int j = 1; j <= ((columnas*2)+1) && !error; j++) {
					if(Integer.parseInt(area.tabla.get(i).get(j).getText()) <= 0){
						error = true;
						JOptionPane.showMessageDialog(area, "Error, ningun dato en la tabla debe ser igual o menor a cero");
					}
				}
			}
			algoritmo = area.colaDos.getSelectedIndex() +1;
			if((algoritmo == 1 && Integer.parseInt(area.cuantoC2.getText()) <= 0) || Integer.parseInt(area.cuantoC1.getText()) <= 0){
				JOptionPane.showMessageDialog(area, "Error, el cuanto para el algoritmo RR debe ser mayor a cero");
				error = true;
			}
			if(Integer.parseInt(area.permanenciaC1.getText()) <= 0 || Integer.parseInt(area.permanenciaC2.getText()) <= 0){
				JOptionPane.showMessageDialog(area, "Error, La permanencia de ser mayor a cero");
			}
			if(!error){
				algoritmo = area.colaDos.getSelectedIndex() +1;
				algoritmos.clear();
				int q1 = Integer.parseInt(area.cuantoC1.getText());
				int p1 = Integer.parseInt(area.permanenciaC1.getText());
				int q2 = Integer.parseInt(area.cuantoC2.getText());
				int p2 = Integer.parseInt(area.permanenciaC2.getText());
				RR rr = new RR("RR", p1, q1);
				RR rr2 = new RR("RR", p2, q2);
				SRT srt = new SRT("SRT", p2);
				PrioridadApropiativa p = new PrioridadApropiativa("Prioridad", p2);
				FCFS fcfs = new FCFS("FCFS");
				algoritmos.add(rr);
				algoritmos.add(rr2);
				algoritmos.add(srt);
				algoritmos.add(p);
				algoritmos.add(fcfs);
				if(algoritmo == 1){
					algoritmos.remove(srt);
					algoritmos.remove(p);
				}
				else if(algoritmo == 2){
					algoritmos.remove(rr2);
					algoritmos.remove(p);
				}
				else if(algoritmo == 3){
					algoritmos.remove(srt);
					algoritmos.remove(rr2);
				}
				procesos = new ArrayList<Proceso>();
				iniciarProcesos();
				for (int i =1; i <= filas ; i++) {
					for (int j = 1; j <= ((columnas*2)+3); j++) {
						if(j == ((columnas*2)+3)){
							procesos.get(i-1).setPrioridad(Integer.parseInt((area.tabla.get(i).get(j).getText())));
						}
						else if(j == ((columnas*2)+2)){
							procesos.get(i-1).setTiempoLlegada(Integer.parseInt((area.tabla.get(i).get(j).getText())));
						}
						else if(j%2 == 1){
							procesos.get(i-1).setRafagaCPU((int)Math.floor(j/2),Integer.parseInt((area.tabla.get(i).get(j).getText())));
						}
						else if(j%2 == 0){
							procesos.get(i-1).setRafagaES((int)Math.floor(j/2)-1,Integer.parseInt((area.tabla.get(i).get(j).getText())));
						}
					}
				}
				colitas = new ColasMultiples(procesos, algoritmos);
				colitas.ejecutarPrograma();
				datos = colitas.getTerminados();
				area.enviarDatos(datos);
				area.perdirActualizacion();
				JOptionPane.showMessageDialog(area, "Operacion exitosa");
			}
			error = false;
		}
		else if(x> 200 && x<= 290 && y >= 40 && y <= 65){
			if(filas > aux){
				JOptionPane.showMessageDialog(area, "Error, el número de procesos excede el tamaño de la tabla");
			}	
			else if(columnas > aux2){
				JOptionPane.showMessageDialog(area, "Error, el número de procesos excede el tamaño de la tabla");
			}
			else{
				reiniciarTabla();
				crearCabecera(filas, columnas);
				tablaGenerada = true;
				generarDatos();
			}
		}
		
		validarclicGrilla(x, y, area.getAltoTabla(), area.getAnchoTabla(), area.getAltoCelda(), area.getAnchoCelda(), filas, columnas);
		
	}

	private void generarDatos() {
		for (int i =1; i <= filas ; i++) {
			for (int j = 1; j <= ((columnas*2)+3); j++) {
				Random r = new Random();
				int n = (int)(r.nextDouble()*5)+1;
				area.tabla.get(i).get(j).setText(n+"");
			}
		}
	}

	private void iniciarProcesos() {
		for (int i = 0; i < filas; i++) {
			Proceso p = new Proceso();
			p.setNumeroProceso(i);
			for (int j = 0; j <= columnas*+1; j++) {
				p.agregarRafagaCPU(0);
			}
			for (int j = 0; j < columnas; j++) {
				p.agregarRafagaES(0);
			}
			procesos.add(p);
		}
	}

	private void validarclicGrilla(int x, int y, int altoTabla, int anchoTabla, int altoCelda, int anchoCelda, int filas, int columnas) {
		if(tablaGenerada){
			int posX = 30;
			int posY = 100;
			
			for (int i = 1; i <= filas; i++) {
				if(x >= (posX + anchoCelda) && x< posX + (anchoCelda*2) && y >= posY + (altoCelda*(i)) && y < posY + (altoCelda*(i+1))){
					String cpu = JOptionPane.showInputDialog("Ingrese una rafaga de CPU");
					area.tabla.get(i).get(1).setText(cpu);
				}
				if(x >= posX + (anchoCelda*((columnas*2)+3)) && x <= posX + (anchoCelda*((columnas*2)+4)) &&
				   y >= posY + (altoCelda*(i)) && y < posY + (altoCelda*(i+1))){
					String prioridad = JOptionPane.showInputDialog("Ingrese la priodidad del proceso");
					area.tabla.get(i).get((columnas*2)+3).setText(prioridad);
				}
				if(x >= posX + (anchoCelda*((columnas*2)+2)) && x <= posX + (anchoCelda*((columnas*2)+3)) &&
						   y >= posY + (altoCelda*(i)) && y < posY + (altoCelda*(i+1))){
							String tLlegada = JOptionPane.showInputDialog("Ingrese el tiempo de llegada del proceso");
							area.tabla.get(i).get((columnas*2)+2).setText(tLlegada);
						}
				for (int j = 1; j < columnas+1; j++) {
					if(x >= (posX + anchoCelda*((2*j)+1)) && x< posX + (anchoCelda*((2*j)+2)) && y >= posY + (altoCelda*(i)) && y < posY + (altoCelda*(i+1))){
						String cpu = JOptionPane.showInputDialog("Ingrese una rafaga de CPU");
						area.tabla.get(i).get(j*2+1).setText(cpu);
					}
				}
				
				for (int j = 0; j < columnas; j++) {
					if(x >= (posX + anchoCelda*((2*j)+2)) && x< posX + (anchoCelda*((2*j)+3)) && y >= posY + (altoCelda*(i)) && y < posY + (altoCelda*(i+1))){
						String bloqueo = JOptionPane.showInputDialog("Ingrese una rafaga de E/S");
						area.tabla.get(i).get(j*2+2).setText(bloqueo);
					}
				}
				
			}
			
		}
	}
	
	private void reiniciarTabla() {
		for (int i = 0; i < area.tabla.size(); i++) {
			for (int j = 0; j < area.tabla.get(0).size(); j++) {
				area.tabla.get(i).get(j).setBorder(null);
				area.tabla.get(i).get(j).setText("");
			}
		}
		
	}

	private void crearCabecera(int filas, int bloqueos) {
		for (int i = 1; i <= filas; i++) {
			area.tabla.get(i).get(0).setText("Proceso"+i);
			area.tabla.get(i).get(0).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));		
		}
		
		for (int i = 1; i < (bloqueos*2); i+=2) {
			area.tabla.get(0).get(i).setText("CPU");
			area.tabla.get(0).get(i).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));
			area.tabla.get(0).get(i+1).setText("E/S");
			area.tabla.get(0).get(i+1).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));
		}
		area.tabla.get(0).get(0).setText("Procesos");
		area.tabla.get(0).get(0).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));
		area.tabla.get(0).get((bloqueos*2)+3).setText("Prioridad");
		area.tabla.get(0).get((bloqueos*2)+3).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));
		area.tabla.get(0).get((bloqueos*2)+2).setText("T. Llegada");
		area.tabla.get(0).get((bloqueos*2)+2).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));
		area.tabla.get(0).get((bloqueos*2)+1).setText("CPU");
		area.tabla.get(0).get((bloqueos*2)+1).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.black, null));
		
		for (int i = 1; i <= filas; i++){
			for (int j = 1; j <= (bloqueos*2)+3; j++){
				area.tabla.get(i).get(j).setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				area.tabla.get(i).get(j).setText("0");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){
		
	}

	@Override
	public void mouseExited(MouseEvent e){
		
	}

	@Override
	public void mousePressed(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		if(x>= 200 && x<= 290 && y >= 10 && y <= 35){
			area.generarTabla.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));					
		}
		else if(x >= area.anchoTabla-65 && x<= area.anchoTabla+5 && y >= 25 && y <=50){
			area.ejecutar.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		else if(x> 200 && x<= 290 && y >= 40 && y <= 65){ 
			area.datosAutomaticos.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));					
		}
	}

	@Override
	public void mouseReleased(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		if(x>= 200 && x<= 290 && y >= 10 && y <= 35){
			area.generarTabla.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));				
		}
		else if(x >= area.anchoTabla-65 && x<= area.anchoTabla+5 && y >= 25 && y <=50){
			area.ejecutar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}
		else if(x> 200 && x<= 290 && y >= 40 && y <= 65){ 
			area.datosAutomaticos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));				
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent accion){
	}	
}
