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

	@Override
	public boolean equals(Object o) {
		if (o instanceof Resultat){
			Resultat tmp = (Resultat) o;
			if(tmp.nom.equals(nom) && tmp.nombre == nombre){
				return true;
			}
		}
		return false;
	}

	public String toString(){
		if(this.nombre instanceof Float){
			Float f = (Float) this.nombre;
			return (" " + this.nom + "--"+ f.intValue());
		}
		return (" " + this.nom + "--"+ this.nombre);
	}
  
}
