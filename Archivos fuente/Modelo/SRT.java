package Modelo;
import java.util.ArrayList;
public class SRT extends Algoritmo{
	int procesoAEjecutar;
	
	public SRT(String nombre, int permanencia){
		super(nombre, permanencia,0);
		procesoAEjecutar = 0;
	}
	
	public Proceso ejecutarCPU(ArrayList<Proceso> procesos, int tiempo) {
		esccogerProceso(procesos, tiempo);
		Proceso aux = procesos.get(procesoAEjecutar);
		int tiempoLlegada = aux.getTiempoLlegada();
		if(tiempo >= tiempoLlegada){
			aux.ejecutarRafagaCPU(tiempo);
		}
		return aux;
	}
	
	public void esccogerProceso(ArrayList<Proceso> procesos, int tiempo){
		int procesoEscogido = 0;
		int rafagaProcesoEscogido = Integer.MAX_VALUE;
		int rafagaActual = 0;
		for (int i = 0; i < procesos.size(); i++) {
			rafagaActual = procesos.get(i).getTiempoEjecucionRestante();
			if(rafagaActual < rafagaProcesoEscogido &&  (tiempo >= procesos.get(i).getTiempoLlegada())){
				procesoEscogido = i;
				rafagaProcesoEscogido = rafagaActual;
			}
		}
		procesoAEjecutar = procesoEscogido;
	}
}