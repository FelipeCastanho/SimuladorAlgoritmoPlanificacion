package Modelo;
import java.util.ArrayList;
public class RR extends Algoritmo{
	int procesoAEjecutar;
	
	public RR(String nombre, int permanencia, int cuanto){
		super(nombre,permanencia,cuanto);
		procesoAEjecutar = 0;
	}
	public Proceso ejecutarCPU(ArrayList<Proceso> procesos, int tiempo){		
		Proceso aux = procesos.get(0);
		int tiempoLlegada = aux.getTiempoLlegada();
		if(tiempo >= tiempoLlegada){
			aux.ejecutarRafagaCPU(tiempo);
		}
		if(aux.getCuanto() == cuanto){
			aux.reiniciarCuanto();
			procesos.remove(aux);
			procesos.add(aux);
		}
		return aux;
	}
	
	public int getPermanencia() {
		return permanencia;
	}

	public int getProcesoAEjecutar() {
		return procesoAEjecutar;
	}
	
}