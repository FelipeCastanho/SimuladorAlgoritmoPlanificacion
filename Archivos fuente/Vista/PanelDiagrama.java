package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Controlador.ControladorDiagrama;
import Modelo.Proceso;

public class PanelDiagrama extends JPanel {

	private static final long serialVersionUID = 8866696742805343809L;
	VentanaPrincipal area;
	int alto;
	int ancho;
	int altoCelda;
	int tamanhoTiempo;
	boolean actualizar = false;
	boolean inciarVentanita = false;
	ArrayList<Proceso> terminados;
	ArrayList<ArrayList<JLabel>> ventanitas;
	ControladorDiagrama control;
	int k = 0;
	int l = 0;
	
	public PanelDiagrama(VentanaPrincipal area) {
		this.area = area;
		alto = area.getAlto();
		ancho = area.getAncho();
		terminados = new ArrayList<Proceso>();
		altoCelda = 35;
		tamanhoTiempo = 0;
		ventanitas = new ArrayList<ArrayList<JLabel>>();
		control = new ControladorDiagrama(this);
		addMouseListener(control);
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Font f = new Font("Source Sans Pro", Font.BOLD, 16);
		g.setFont(f);
		g.fillRect(50, 50, 3, alto-190);
		g.fillRect(50, alto-140, ancho-160, 3);
		g.drawString("Procesos", 25, 40);
		g.drawString("Tiempo", ancho-100, alto-135);
		g.setColor(Color.BLACK);
		g.drawString("E/S", ancho-270, 40);
		g.drawString("Listo", ancho-375, 40);
		g.drawString("Ejecutando", ancho-180, 40);
		g.setColor(new Color(102, 255, 51));
		g.fillRect(ancho-205, 25, 20, 20);
		g.setColor(new Color(255,37,37));
		g.fillRect(ancho-295, 25, 20, 20);
		g.setColor(new Color(0, 102, 255));
		g.fillRect(ancho-400, 25, 20, 20);
		g.setColor(Color.black);
				
		if(actualizar)
		{
			int mayor = sacarMayor();
			tamanhoTiempo = (ancho-160)/mayor;
			int posX = 45;
			int posY = alto-165;
			for (int i = 1; i <= terminados.size(); i++) {
				g.drawString("P"+i, 15, posY);
				posY -= altoCelda;
			}
			
			for (int i = 0; i <= mayor; i++) {
				g.drawLine(50+(tamanhoTiempo*i+1), alto-135,50+(tamanhoTiempo*i+1), alto-145);
			}
			
			for (int i = 0; i <= mayor; i+=10) {
				g.drawString(i+"", posX, alto-120);
				posX += (tamanhoTiempo*10);
			}
			
			for (int i = 0; i < terminados.size(); i++) {
				for (int j = 0; j < terminados.get(i).getEstados().size(); j++) {
					if(terminados.get(i).getEstados().get(j) == 0 || terminados.get(i).getEstados().get(j) == 4){
						g.setColor(Color.white);
					}
					else if(terminados.get(i).getEstados().get(j) == 1){
						g.setColor(new Color(0, 102, 255));
					}
					else if(terminados.get(i).getEstados().get(j) == 2){
						g.setColor(new Color(102, 255, 51));
					}
					else{
						g.setColor(new Color(255,37,37));
					}
					g.fillRect(55+(tamanhoTiempo*(j)), alto-182-(altoCelda*i), tamanhoTiempo-5, 25);
					g.setColor(Color.black);
				}
			}
			g.setColor(Color.BLACK);
		}
		if(inciarVentanita){
			Proceso proceso = terminados.get(k);
			g.setColor(Color.WHITE);
			g.fillRect(55+(tamanhoTiempo*(l)), alto-242-(altoCelda*k), 130, 70);
			//g.fillOval((tamanhoTiempo*(l)), alto-202-(altoCelda*k), 130, 130);
			g.setColor(Color.BLACK);
			g.drawString("Tiempo "+ l, 60+(tamanhoTiempo*(l)), alto-222-(altoCelda*k));
			g.drawString("Proceso "+(k+1), 60+(tamanhoTiempo*(l)), alto-202-(altoCelda*k));
			String cola = "";
			if(proceso.getColasHabitadas().get(l) == 4){
				cola = "Bloqueos";
			}
			else{
				cola = proceso.getColasHabitadas().get(l)+"";
			}
			g.drawString("Cola: " + cola , 60+(tamanhoTiempo*(l)), alto-182-(altoCelda*k));
			
		}
		inciarVentanita = false;
	}
	private int sacarMayor() {
		int resultado = Integer.MIN_VALUE;
		for(int i = 0; i < terminados.size(); i++) {
			if(terminados.get(i).getEstados().size() > resultado){
				resultado = terminados.get(i).getEstados().size();
			}
		}
		return resultado;
	}


	public void actualizar(){
		ArrayList<Proceso> aux = area.getTerminados();
		terminados = organizarProcesos(aux);
		actualizar = true;
		repaint();
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
	
	public boolean getActualizar(){
		return actualizar;
	}
	public int getTamanhoTiempo(){
		return tamanhoTiempo;
	}
	public int getAltoCelda(){
		return altoCelda;
	}
	public int getAlto(){
		return alto;
	}
	public ArrayList<Proceso> getTerminados(){
		return terminados;
	}
	
	public void setInicarVentanita(boolean cambio, int i, int j){
		inciarVentanita = cambio;
		k = i;
		l = j;
	}
}
