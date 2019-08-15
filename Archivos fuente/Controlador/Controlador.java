package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import Vista.VentanaPrincipal;


public class Controlador implements MouseListener,ActionListener
{
	VentanaPrincipal area;
	
	public Controlador(VentanaPrincipal area)
	{
		this.area = area;
	}

	public void mouseClicked(MouseEvent e)
	{
		int posInicioX2 = area.anchoMenu;
		int posInicioY2 = area.altoMenu+10;
		int x = e.getX();
		int y = e.getY();
		if(x>= 20 && x<= posInicioX2+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.panelDatos.setVisible(true);
			area.panelDiagrama.setVisible(false);
			area.panelTabla.setVisible(false);
		}
		
		else if(x>= posInicioX2+17 && x<= (posInicioX2*2)+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.panelDatos.setVisible(false);
			area.panelDiagrama.setVisible(true);
			area.panelTabla.setVisible(false);
		}
		else if(x>= (posInicioX2*2)+17 && x<= (posInicioX2*3)+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.panelDatos.setVisible(false);
			area.panelDiagrama.setVisible(false);
			area.panelTabla.setVisible(true);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e) 
	{}

	@Override
	public void mousePressed(MouseEvent e){
		int posInicioX2 = area.anchoMenu;
		int posInicioY2 = area.altoMenu+10;
		int x = e.getX();
		int y = e.getY();
		if(x>= 20 && x<= posInicioX2+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.ingresarProcesos.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));					
		}
		else if(x>= posInicioX2+17 && x<= (posInicioX2*2)+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.diagrama.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		else if(x>= (posInicioX2*2)+17 && x<= (posInicioX2*3)+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.tabla.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));		
		}
	}

	@Override
	public void mouseReleased(MouseEvent e){
		int posInicioX2 = area.anchoMenu;
		int posInicioY2 = area.altoMenu+10;
		int x = e.getX();
		int y = e.getY();
		if(x>= 20 && x<= posInicioX2+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.ingresarProcesos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
					
		}
		else if(x>= posInicioX2+17 && x<= (posInicioX2*2)+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.diagrama.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		}
		else if(x>= (posInicioX2*2)+17 && x<= (posInicioX2*3)+15 && y >= posInicioY2 && y <= posInicioY2+30){
			area.tabla.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));		
		}
	}

	@Override
	public void actionPerformed(ActionEvent accion)
	{
	
	}	
}