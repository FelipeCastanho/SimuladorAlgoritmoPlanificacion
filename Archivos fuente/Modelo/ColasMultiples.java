package Modelo;
import java.util.ArrayList;
public class ColasMultiples{
	Proceso procesoActual;
	boolean finEjecucion;
	int tiempo;
	ArrayList<Proceso> colaEspera;
	ArrayList<Proceso> colaUno;
	ArrayList<Proceso> colaDos;
	ArrayList<Proceso> colaTres;
	ArrayList<Proceso> colaBloqueados;
	ArrayList<Algoritmo> algoritmos;
	ArrayList<Proceso> terminados;

	public ColasMultiples(ArrayList<Proceso> colaEspera, ArrayList<Algoritmo> algoritmos){
		procesoActual = null;
		finEjecucion = false;
		tiempo = 0;
		this.colaEspera = colaEspera;
		this.algoritmos = algoritmos;
		colaUno = new ArrayList<Proceso>();
		colaDos = new ArrayList<Proceso>();
		colaTres = new ArrayList<Proceso>();
		colaBloqueados = new ArrayList<Proceso>();
		terminados = new ArrayList<Proceso>();
	}

	public void ejecutarPrograma(){
		while(!finEjecucion){
			ejecutarUnaRafaga();
		}
	}

	public void ejecutarUnaRafaga(){
		revisarEstados();
		if(!colaUno.isEmpty()){
			procesoActual = algoritmos.get(0).ejecutarCPU(colaUno,tiempo);
		}
		else if(!colaDos.isEmpty()){
			procesoActual = algoritmos.get(1).ejecutarCPU(colaDos, tiempo);
		}
		else if(!colaTres.isEmpty()){
			procesoActual = algoritmos.get(2).ejecutarCPU(colaTres, tiempo);
		}
		if(!colaBloqueados.isEmpty()){
			colaBloqueados.get(0).ejecutarRafagaES();
		}
		guardarEstados();
		tiempo++;
	}

	private void guardarEstados(){
		for (int i = 0; i < colaEspera.size(); i++){
			colaEspera.get(i).guardarEstado();
			colaEspera.get(i).addColasHabitadas(0);
		}
		for (int i = 0; i < colaUno.size(); i++){
			colaUno.get(i).guardarEstado();
			colaUno.get(i).addColasHabitadas(1);
		}
		for (int i = 0; i < colaDos.size(); i++){
			colaDos.get(i).guardarEstado();
			colaDos.get(i).addColasHabitadas(2);
		}
		for (int i = 0; i < colaTres.size(); i++) {
			colaTres.get(i).guardarEstado();
			colaTres.get(i).addColasHabitadas(3);
		}
		
		for (int i = 0; i < colaBloqueados.size(); i++){
			if(i > 0){
				colaBloqueados.get(i).guardarEstado();
			}
			else colaBloqueados.get(i).guardarEstado();
			colaBloqueados.get(i).addColasHabitadas(4);
		}
	}

	public void revisarEstados(){
		if(colaUno.isEmpty() && colaDos.isEmpty() &&
		   colaTres.isEmpty() && colaBloqueados.isEmpty() && colaEspera.isEmpty()) finEjecucion = true;
		
		if(!colaEspera.isEmpty()){
			Proceso proceso = null;
			
			for (int i = 0; i < colaEspera.size(); i++){
				proceso = colaEspera.get(i);
				if(proceso.getEstado() == 0 && tiempo >= proceso.getTiempoLlegada()){
					proceso.setEstado(1);
					colaEspera.remove(proceso);
					i = i-1;
					colaUno.add(proceso);
					int j = colaUno.indexOf(proceso);
					if(j > 0){
						if(colaUno.get(j-1).getEstado() == 2){
							colaUno.set(j, colaUno.get(j-1));
							colaUno.set(j-1, proceso);
						}
					}
				}
			}
		}
		if(!colaUno.isEmpty()){
			for (int i = 0; i < colaUno.size(); i++){
				revisarProcesoConDegradado(colaUno.get(i),colaUno, algoritmos.get(0));
			}
		}
		if(!colaDos.isEmpty()){
			for (int i = 0; i < colaDos.size(); i++){
				revisarProcesoConDegradado(colaDos.get(i),colaDos, algoritmos.get(1));
			}
		}
		if(!colaTres.isEmpty()){
			for (int i = 0; i < colaTres.size(); i++) {
				revisarProcesoConDegradado(colaTres.get(i), colaTres, algoritmos.get(2));
			}
		}
		if(!colaBloqueados.isEmpty()){
			for (int i = 0; i < colaBloqueados.size(); i++){
				revisarProcesoBloqueado(colaBloqueados.get(i));
			}
		}
	}

	private void revisarProcesoConDegradado(Proceso proceso, ArrayList<Proceso> cola, Algoritmo algoritmo){
		if(proceso.finProceso()){
			proceso.setEstado(4);
			proceso.guardarEstado();
			cola.remove(proceso);
			terminados.add(proceso);
		}
		else {
			if((proceso.getEstado() == 1 || proceso.getEstado() == 2) && proceso.getRafagaInicial() != 0){
				proceso.setEstado(1);
			}
			
			if(proceso.getEstado() == 2 && proceso.getRafagaInicial() == 0){
				proceso.setEstado(3);
				proceso.reiniciarCuanto();
				proceso.removerRafagaVacia();
				proceso.setCola(cola);
				cola.remove(proceso);
				colaBloqueados.add(proceso);
				if(algoritmo.getPermanencia() == proceso.getPermanencia()){
					if(cola == colaUno){
						proceso.setCola(colaDos);
					}
					else if(cola == colaDos){
						proceso.setCola(colaTres);
					}
					proceso.reiniciarCuanto();
					proceso.reiniciarCuantoPermanencia();
				}
			}
			
			else if(algoritmo.getPermanencia() == proceso.getPermanencia()){
				proceso.reiniciarCuanto();
				proceso.reiniciarCuantoPermanencia();
				cola.remove(proceso);
				if(cola == colaUno){
					colaDos.add(proceso);
				}
				else if(cola == colaDos){
					colaTres.add(proceso);
				}
			}
		}
	}

	private void revisarProcesoBloqueado(Proceso proceso){
		if(proceso.getEstado() == 3 && proceso.getRafagaInicialB() == 0){
			ArrayList<Proceso> aux = proceso.getCola();
			proceso.setEstado(1);
			proceso.removerRafagaVaciaB();
			colaBloqueados.remove(proceso);
			aux.add(proceso);
			if(aux.size()>1){
				if(aux.get(aux.size()-2) == procesoActual && procesoActual.getCuanto() == 0){
					aux.set(aux.size()-1, aux.get(aux.size()-2));
					aux.set(aux.size()-2, proceso);
				}
			}
		}
	}

	public ArrayList<Proceso> getTerminados(){
		return terminados;
	}
	
}
