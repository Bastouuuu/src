package Controleur;

import java.util.TreeSet;

import Modele.ComparateurResultat;
import Modele.Resultat;
import Modele.image_nb;

public class ControlleurRechercheCritereImage {

	public TreeSet<Resultat<String,Float>> rechercheCritereImage(int valMin, int valMax) {
		// pour simuler une plage de couleurs, on fait la moyenne des 2 valeurs
		int val = (int)((valMin + valMax)/2);
		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat());
		String result = image_nb.recherche_niveau_image_nb(val);
		if(!result.contains("Aucun")&&!result.contains("seuil")) {
			String[] x = result.split("\n");
			String[] y = null;
			for(String i : x) {
				y = i.split("--");
				Resultat<String,Float> tmp = new Resultat<String, Float>(y[0],Float.parseFloat(y[1].trim()));
				hash.add(tmp);
			}
		}
		return hash;
	}
}
