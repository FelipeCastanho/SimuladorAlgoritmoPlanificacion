package Controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import Modelo.Proceso;
import Vista.PanelDiagrama;

public class ControladorDiagrama implements MouseListener {

	PanelDiagrama area;
	
	public ControladorDiagrama(PanelDiagrama area) {
		this.area = area;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		int ancho = 0;
		int alto = 0;
		ArrayList<Proceso> terminados = area.getTerminados();
		for (int i = 0; i < terminados.size(); i++) {
			for (int j = 0; j < terminados.get(i).getEstados().size(); j++) {
				if(terminados.get(0).getEstados().get(i) != 0 || terminados.get(0).getEstados().get(i) != 4){
					ancho = 55+(area.getTamanhoTiempo()*j);
					alto = area.getAlto()-182-area.getAltoCelda()*i;
					if(x >= ancho && x < ancho+area.getTamanhoTiempo()-5 && y >= alto && y < alto +25){
						area.setInicarVentanita(true,i,j);
					}
				}
			}
		}
		area.repaint();
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
