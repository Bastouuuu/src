package Modele;

public class Resultat<S, N> {
	private S nom;
	private N nombre;
	
	public Resultat() {
		nom = null;
		nombre = null;
	}
	
	public Resultat(S n, N no) {
		nom = n;
		nombre = no;
	}
	
	public S getNom() {
		return nom;
	}
	
	public N getNombre() {
		return nombre;
	}
	
	public void setNombre(N nb) {
		this.nombre = nb;
	}

	public String toString(){
		if(this.nombre instanceof Float){
			Float f = (Float) this.nombre;
			return (" " + this.nom + " -- "+ f.intValue());
		}
		return (" " + this.nom + " -- "+ this.nombre);
	}
  
}
