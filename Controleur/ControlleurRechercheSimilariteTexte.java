package Controleur;

import java.util.TreeSet;
import Modele.ComparateurResultat;
import Modele.Resultat;
import Modele.commun;
import Modele.texte;

public class ControlleurRechercheSimilariteTexte {

	public TreeSet<Resultat<String,Float>> rechercheSimilariteTexte(String s) {
		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat()); 
		String result = texte.recherchesimi(s);
		//Si la string recuperee n'est pas vide -> il y a des resultats
		//Mise en forme de la String en un TreeSet par decoupage successifs pour permettre de trier les resultats. 
		if(!result.isEmpty()) {
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
  
	public int pathFileExists(String s) {
		return commun.fileExists(s);
	}

}

 