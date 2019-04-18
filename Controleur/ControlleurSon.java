package Controleur;

import java.util.Iterator;
import java.util.TreeSet;

import Modele.ComparateurResultat;
import Modele.Resultat;
import Modele.son;

public class ControlleurSon {
	
	public ControlleurSon() {
	}
	
	public void indexSon() {
		//son.indexSon();
	}
	
	
	public TreeSet<Resultat<String,Float>> rechercheSon(String chemin) {
		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat());
		String result = son.rechercheSonBis(chemin);
		if(!result.equals("empty")) {
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
