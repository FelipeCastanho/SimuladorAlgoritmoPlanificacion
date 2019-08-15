package Vista;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

import Controlador.Controlador;
import Modelo.Proceso;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = -61573413305830026L;
	private JPanel contentPane;
	public int ancho = 1000;
	public int alto = 700;
	public int anchoMenu;
	public int altoMenu;
	Controlador control;
	public JLabel ingresarProcesos;
	public JLabel diagrama;
	public JLabel tabla;
	public PanelDatos panelDatos;
	public PanelDiagrama panelDiagrama;
	public PanelTabla panelTabla;
	ArrayList<Proceso> terminados;
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ancho, alto);
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(243, 243, 243));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, ancho-30, 30);
		contentPane.add(panel);
		panel.setBackground(new Color(243, 243, 243));
		panel.setLayout(null);
		
		ingresarProcesos = new JLabel("Ingresar procesos");
		ingresarProcesos.setHorizontalAlignment(JLabel.CENTER);
		ingresarProcesos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		altoMenu = 30;
		anchoMenu = (ancho-40)/7;
		ingresarProcesos.setBounds(0, 0, anchoMenu, altoMenu);
		panel.add(ingresarProcesos);
		
		diagrama = new JLabel("Diagrama de Gantt");
		diagrama.setHorizontalAlignment(SwingConstants.CENTER);
		diagrama.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		diagrama.setBounds(anchoMenu, 0, anchoMenu, altoMenu);
		panel.add(diagrama);
		
		tabla = new JLabel("Tabla de datos");
		tabla.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabla.setBounds(anchoMenu*2, 0, anchoMenu, altoMenu);
		panel.add(tabla);
		
		panelDatos = new PanelDatos(this);
		panelDatos.setBounds(10, 40,ancho-30, alto-90);
		panelDatos.setBackground(Color.WHITE);
		contentPane.add(panelDatos);
		
		panelDiagrama = new PanelDiagrama(this);
		panelDiagrama.setBackground(Color.white);
		panelDiagrama.setBounds(10, 40, ancho-30, alto-90);
		contentPane.add(panelDiagrama);
		panelDiagrama.setVisible(false);
		
		panelTabla = new PanelTabla(this);
		panelTabla.setBounds(10, 40, ancho-30, alto-90);
		contentPane.add(panelTabla);
		panelTabla.setVisible(false);
		
		control = new Controlador(this);
		addMouseListener(control);
	}
	
	public ArrayList<Proceso> getTerminados(){
		return terminados;
	}
	
	public void setTerminados(ArrayList<Proceso> cambio){
		terminados = cambio;
	}
	
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void actualizar(){
		panelTabla.actualizar();
		panelDiagrama.actualizar();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
