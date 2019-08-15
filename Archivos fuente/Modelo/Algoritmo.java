package Modelo;
import java.util.ArrayList;
public abstract class Algoritmo{
	String nombre;
	int permanencia;
	int cuanto;
	
	public Algoritmo(String nombre, int permanencia, int cuanto){
		this.nombre = nombre;
		this.permanencia = permanencia;
		this.cuanto = cuanto;
	}
	
	public abstract Proceso ejecutarCPU(ArrayList<Proceso> cola, int tiempo);
	
	public String getNombre(){
		return nombre;
	}
	public int getPermanencia(){
		return permanencia;
	}
	public int getCuanto(){
		return cuanto;
	}
}