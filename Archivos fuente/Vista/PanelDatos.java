package Vista;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import Controlador.ControladorPanelDatos;
import Modelo.Proceso;

public class PanelDatos extends JPanel {

	private static final long serialVersionUID = 7976742814514208407L;
	public JTextField numeroProcesos;
	public JTextField numeroBloqueos;
	public JTextField permanenciaC1;
	public JTextField permanenciaC2;
	public JTextField cuantoC1;
	public JTextField cuantoC2;
	public JLabel q1;
	public JLabel q2;
	public JLabel p1;
	public JLabel p2;
	public JLabel datosAutomaticos;
	public JLabel generarTabla;
	public JLabel ejecutar;
	public JLabel aux;
	public JLabel colaUno;
	public JLabel colaDos1;
	@SuppressWarnings("rawtypes")
	public JComboBox colaDos;
	public JLabel colaTres;
	public int anchoTabla;
	public int altoTabla;
	public int altoCelda = 30;
	public int anchoCelda = 90;
	public ArrayList<ArrayList<JLabel>> tabla;
	VentanaPrincipal area;
	ControladorPanelDatos control;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelDatos(VentanaPrincipal area) {	
		this.area = area;
		anchoTabla = area.getAncho()-55;
		altoTabla = area.getAlto()-230;
		aux = null;
		tabla = new ArrayList<ArrayList<JLabel>>();
		
		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		JLabel labelCantidad = new JLabel("Cantidad de procesos: ");
		labelCantidad.setBounds(10, 10, 130, 20);
		add(labelCantidad);
		JLabel labelBloqueos = new JLabel("Cantidad de E/S: ");
		labelBloqueos.setBounds(10, 40, 130, 20);
		add(labelBloqueos);
		
		generarTabla = new JLabel("Generar tabla");
		generarTabla.setHorizontalAlignment(JLabel.CENTER);
		generarTabla.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		generarTabla.setBounds(200, 10, 90, 25);
		add(generarTabla);
		
		datosAutomaticos = new JLabel("Generar datos");
		datosAutomaticos.setHorizontalAlignment(JLabel.CENTER);
		datosAutomaticos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		datosAutomaticos.setBounds(200, 40, 90, 25);
		add(datosAutomaticos);
	
		int anchoLabels =(area.getAncho()-500)/2;
		colaUno = new JLabel("Cola uno:   RR");
		colaUno.setBounds(300,10,anchoLabels,20);
		colaUno.setHorizontalAlignment(JLabel.CENTER);
		add(colaUno);
		
		q1 = new JLabel("Q: ");
		q1.setBounds(350, 40, 30, 20);
		add(q1);
		
		cuantoC1 = new JTextField("1");
		cuantoC1.setBounds(375,40,30,20);
		add(cuantoC1);
		
		p1 = new JLabel("P: ");
		p1.setBounds(190+anchoLabels,40,30,20);
		add(p1);
		
		permanenciaC1 = new JTextField("1");
		permanenciaC1.setBounds(210+anchoLabels, 40, 30, 20);
		add(permanenciaC1);
		
		colaDos1 = new JLabel("Cola dos: ");
		colaDos1.setBounds(300+anchoLabels, 10, 70,20);
		colaDos1.setHorizontalAlignment(JLabel.CENTER);
		add(colaDos1);
		
		colaDos = new JComboBox();
		colaDos.setModel(new DefaultComboBoxModel(new String[] {"RR", "SRT", "Prioridad"}));
		colaDos.setBounds(370+anchoLabels,10,anchoLabels-160,20);
		add(colaDos);
		
		q2 = new JLabel("Q: ");
		q2.setBounds(330+anchoLabels, 40, 30, 20);
		add(q2);
		
		cuantoC2 = new JTextField("1");
		cuantoC2.setBounds(355+anchoLabels,40,30,20);
		add(cuantoC2);
		
		p2 = new JLabel("P: ");
		p2.setBounds(170+(anchoLabels*2),40,30,20);
		add(p2);
		
		permanenciaC2 = new JTextField("1");
		permanenciaC2.setBounds(190+(anchoLabels*2), 40, 30, 20);
		add(permanenciaC2);
		
		colaTres = new JLabel("Cola tres:   FCFS");
		colaTres.setBounds(250+(anchoLabels*2),10,100,20);
		colaTres.setHorizontalAlignment(JLabel.CENTER);
		add(colaTres);
		
		ejecutar = new JLabel("Ejecutar");
		ejecutar.setHorizontalAlignment(JLabel.CENTER);
		ejecutar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		ejecutar.setBounds(area.getAncho()-120, 25, 70, 25);
		add(ejecutar);
		
		int aux = 0;
		numeroProcesos = new JTextField(10+"");
		numeroProcesos.setBounds(145, 12, 30, 20);
		add(numeroProcesos);
		numeroProcesos.setColumns(10);
		
		aux = ((anchoTabla/anchoCelda)-3)/2;
		numeroBloqueos = new JTextField(aux+"");
		numeroBloqueos.setBounds(145, 42, 30, 20);
		add(numeroBloqueos);
		numeroBloqueos.setColumns(10);
		llenarTabla();
		control = new ControladorPanelDatos(this);
		addMouseListener(control);
	}
	
	private void llenarTabla() {
		int posX = 30;
		int posY = 100;
		ArrayList<JLabel> fila = null;
		for (int i = 0; i < (altoTabla/altoCelda); i++) {
			fila = new ArrayList<JLabel>();
			for (int j = 0; j < (anchoTabla/anchoCelda) ; j++) {
				aux = new JLabel();
				aux.setBounds(posX, posY, anchoCelda, altoCelda);
				aux.setHorizontalAlignment(JLabel.CENTER);
				posX += anchoCelda;
				fila.add(aux);
				add(aux);
			}
			tabla.add(fila);
			posX = 30;
			posY += altoCelda;
		}
		
	}

	public void enviarDatos(ArrayList<Proceso> datos){
		area.setTerminados(datos);
	}
	public void perdirActualizacion(){
		area.actualizar();
	}
	
	public int getAnchoTabla(){
		return anchoTabla;
	}
	public int getAltoTabla(){
		return altoTabla;
	}
	public int getAnchoCelda(){
		return anchoCelda;
	}
	public int getAltoCelda(){
		return altoCelda;
	}
}
