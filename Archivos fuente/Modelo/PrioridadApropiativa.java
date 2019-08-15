package Modelo;
import java.util.ArrayList;
public class PrioridadApropiativa extends Algoritmo{
	int procesoAEjecutar;
	public PrioridadApropiativa(String nombre, int permanencia){
		super(nombre, permanencia,0);
		procesoAEjecutar = 0;
	}

	public Proceso ejecutarCPU(ArrayList<Proceso> cola, int tiempo){
		escogerProceso(cola);
		Proceso aux = cola.get(procesoAEjecutar);
		int tiempoLlegada = aux.getTiempoLlegada();
		if(tiempo >= tiempoLlegada){
			aux.ejecutarRafagaCPU(tiempo);
		}
		return aux;
	}

	public void escogerProceso(ArrayList<Proceso> cola){
		int procesoEscogido = 0;
		int prioridadProcesoEscogido = Integer.MIN_VALUE;
		int prioridadActual = 0;
		for (int i = 0; i < cola.size(); i++) {
			prioridadActual = cola.get(i).getPrioridad();
			if(prioridadActual > prioridadProcesoEscogido){
				procesoEscogido = i;
				prioridadProcesoEscogido = prioridadActual;
			}
		}
		procesoAEjecutar = procesoEscogido;
	}
}