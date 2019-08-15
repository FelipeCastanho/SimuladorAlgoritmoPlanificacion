package Modelo;
import java.util.ArrayList;
public class Proceso{
	int tiempoLlegada;
	int tiempoSalida;
	int tiempoEjecucion;
	int cuantoEjecutado;
	int cuantoPermanencia;
	int estado;
	int numeroProceso;
	int prioridad;
	ArrayList<Proceso> cola;
	ArrayList<Integer> rafagasCPU;
	ArrayList<Integer> rafagasES;
	ArrayList<Integer> estados;
	ArrayList<Integer> colasHabitadas;

	public Proceso(){
		tiempoLlegada = 0;
		tiempoSalida = 0;
		cuantoEjecutado = 0;
		cuantoPermanencia = 0;
		estado = 0;//0 Para nulo, 1 para listo, 2 para ejecutado, 3 para bloqueado y 4 para terminado.
		numeroProceso = 0;
		prioridad = 0;
		colasHabitadas = new ArrayList<Integer>();
		cola = new ArrayList<Proceso>();
		rafagasCPU = new ArrayList<Integer>();
		rafagasES = new ArrayList<Integer>();
		estados = new ArrayList<Integer>();
		tiempoEjecucion = getTiempoEjecucionRestante();
	}

	public void setCola(ArrayList<Proceso> cambio){
		cola = cambio;
	}

	public void setEstado(int cambio){
		estado = cambio;
	}

	public void setTiempoLlegada(int tiempo){
		tiempoLlegada = tiempo;
	}

	public void setPrioridad(int prioridad){
		this.prioridad = prioridad;
	}
	
	public void setPermanencia(int cambio){
		cuantoPermanencia = 0;
	}
	
	public void setNumeroProceso(int cambio){
		numeroProceso = cambio;
	}
	
	public int getNumeroProceso(){
		return numeroProceso;
	}
	
	public int getPermanencia(){
		return cuantoPermanencia;
	}
	
	public ArrayList<Proceso> getCola(){
		return cola;
	}

	public int getEstado(){
		return estado;
	}

	public int getPrioridad(){
		return prioridad;
	}

	public int getCuanto(){
		return cuantoEjecutado;
	}
	
	public int gerCuantoPermanencia(){
		return cuantoPermanencia;
	}

	public int getTiempoLlegada(){
		return tiempoLlegada;
	}

	public int getTiempoSalida(){
		return tiempoSalida;
	}

	public int getTiempoEjecucion(){
		return tiempoEjecucion;
	}
	
	public int getRafagaInicial(){
		return rafagasCPU.get(0);
	}

	public int getRafagaInicialB(){
		return rafagasES.get(0);
	}
	
	public int getTiempoEjecucionRestante(){
		int respuesta = 0;
		for (int i: rafagasCPU) {
			respuesta += i;
		}
		return respuesta;
	}
	
	public int getTiempoBloqueoRestante(){
		int respuesta = 0;
		for (int i: rafagasES) {
			respuesta += i;
		}
		return respuesta;
	}
	
	public ArrayList<Integer> getEstados(){
		return estados;
	}
	
	public void removerRafagaVacia(){
		rafagasCPU.remove(0);
	}
	
	public void removerRafagaVaciaB(){
		rafagasES.remove(0);
	}
	
	public void agregarRafagaCPU(int rafaga){
		rafagasCPU.add(rafaga);
	}

	public void agregarRafagaES(int rafaga){
		rafagasES.add(rafaga);
	}

	public void reiniciarCuanto(){
		cuantoEjecutado = 0;
	}
	
	public void reiniciarCuantoPermanencia(){
		cuantoPermanencia = 0;
	}
	
	public boolean finProceso(){
		if((rafagasCPU.isEmpty() || (getRafagaInicial() == 0 && rafagasCPU.size() == 1)) && rafagasES.isEmpty()) return true;
		else return false;
	}

	public void ejecutarRafagaCPU(int tiempoTotal){
		if(estado == 1 || estado == 2){
			rafagasCPU.set(0,rafagasCPU.get(0)-1);
			cuantoEjecutado++;
			cuantoPermanencia++;
			estado = 2;
		}	
	}

	public void ejecutarRafagaES(){
		if(estado == 2 || estado == 3){
			rafagasES.set(0,rafagasES.get(0)-1);
			estado = 3;
		}	
	}

	public void guardarEstado(){
		estados.add(estado);
	}

	public boolean hayBloqueos() {
		return !rafagasES.isEmpty();
	}
	
	public ArrayList<Integer> getRafagasCPU(){
		return rafagasCPU;
	}
	
	public ArrayList<Integer> getRafagasES(){
		return rafagasES;
	}
	
	public void setRafagaCPU(int i, int cambio){
		rafagasCPU.set(i, cambio);
	}
	
	public void setRafagaES(int i, int cambio){
		rafagasES.set(i, cambio);
	}
	
	public ArrayList<Integer> getColasHabitadas(){
		return colasHabitadas;
	}
	
	public void addColasHabitadas(int n){
		colasHabitadas.add(n);
	}
}