package Controleur;

import java.util.TreeSet;

import Modele.ComparateurResultat;
import Modele.Resultat;
import Modele.commun;
import Modele.image_nb;

public class ControlleurRechercheSimilariteImage {

	
	public int fileExists(String s) {
		return commun.fileExists(s);
	}
	
	public int verif_format(String s) {
		return image_nb.verif_format(s);
	}
	
	public int verif_format_rgb(String s) {
		return image_nb.verif_format_rgb(s);
	}
	
	public TreeSet<Resultat<String,Float>> rechercheSimilariteImageNb(String s){
		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat());
		
		String result = image_nb.recherche_similarite_image_nb(s);
		if(!result.contains("Aucun") && !result.contains("seuil") && !result.isEmpty()) {
			String[] x = result.split("\n");
			String[] y = null;
			for(String i : x) {
				y = i.split("--");
				Resultat<String,Float> tmp = new Resultat<String, Float>(y[0],Float.parseFloat(y[1].trim().replace(",", ".")));
				hash.add(tmp);
			}
		}else {
			System.out.println("Aucun résultat");
		}
		return hash;
		
	}
	
	public TreeSet<Resultat<String,Float>> rechercheSimilariteImageCouleur(String s){
		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat());
		
		String result = image_nb.recherche_similarite_image_couleurs(s);
		if(!result.isEmpty()) {
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