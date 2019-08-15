package Modelo;
import java.util.ArrayList;
public class FCFS extends Algoritmo{
	public FCFS(String nombre){
		super(nombre,-1,0);
	}
	public Proceso ejecutarCPU(ArrayList<Proceso> procesos, int tiempo){
		int tiempoLlegada = procesos.get(0).getTiempoLlegada();
		if(tiempo >= tiempoLlegada){
			procesos.get(0).ejecutarRafagaCPU(tiempo);
		}
		return null;
	}
}