package Modele;

import java.util.Comparator;

public class ComparateurResultat implements Comparator<Resultat<String,Float>>{
	@Override
	public int compare(Resultat<String, Float> o1, Resultat<String, Float> o2) {
		if(o2.getNombre().compareTo(o1.getNombre()) == 0) {
			return (o2.getNom().compareTo(o1.getNom()));
		}
			return (o2.getNombre().compareTo(o1.getNombre()));
	}
}