package Controleur;

import java.util.TreeSet;

import Modele.ComparateurResultat;
import Modele.Resultat;
import Modele.texte;

public class ControlleurRechercheCritereTexte {
	
	
	public TreeSet<Resultat<String,Float>> rechercheCritereTexte(String s) {
			TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat());
			String result = texte.recherchecrit(s);
			if(!result.equals("empty")) {
				String[] x = result.split("\n");
				String[] y = null;
				for(String i : x) {
					y = i.split("--");
					Resultat<String,Float> tmp = new Resultat<String, Float>(y[0],Float.parseFloat(y[1].trim().replace(",", ".")));
					hash.add(tmp);
				}
			}
			return hash;
		}
}

