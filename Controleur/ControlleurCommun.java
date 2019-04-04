package Controleur;

import java.util.TreeSet;

import Modele.ComparateurResultat;
import Modele.Resultat;

public class ControlleurCommun {
	
	//Requete complexe "+ +", on souhaite garder les fichiers présents dans les deux Set.
			// Parcours des deux Set s'il y a égalité entre les noms alors on ajoute le fichier à un troisieme set qui sera retourne.
			public TreeSet<Resultat<String, Float>> rechercheCritereComplexePP(TreeSet<Resultat<String, Float>> result1, TreeSet<Resultat<String, Float>> result2) {
			TreeSet<Resultat<String,Float>> setResultat = new TreeSet<>(new ComparateurResultat());
				for(Resultat<String,Float> r : result1) {
					for(Resultat<String,Float> r2 : result2) {
						if(r.getNom().equals(r2.getNom())) {
							float tmp = r.getNombre() + r2.getNombre();
							setResultat.add(new Resultat<String,Float>(r.getNom(),tmp));
							break;
						}
					}
				}
				return setResultat;
			}
			
			//Requete complexe "+ -", on souhaite garder les fichiers presents dans le Set1 et absents dans le Set2. 
			//Parcours des deux Set avec un booleen permettant de savoir si le fichier est present dans les deux sets ou non.
			public TreeSet<Resultat<String, Float>> rechercheCritereComplexePM(TreeSet<Resultat<String, Float>> result1, TreeSet<Resultat<String, Float>> result2) {
				TreeSet<Resultat<String,Float>> setResultat = new TreeSet<>(new ComparateurResultat());
					for(Resultat<String,Float> r : result1) {
						boolean trouve = false;
						for(Resultat<String,Float> r2 : result2) {
							if(r.getNom().equals(r2.getNom())) {
								trouve = true;
								break;
							}
						}
						if(!trouve) {
							setResultat.add(new Resultat<String,Float>(r.getNom(),r.getNombre()));
						}
					}
					return setResultat;
				}

}
