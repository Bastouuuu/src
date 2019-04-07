package Controleur;

import java.util.TreeSet;

import Modele.Historique;
import Modele.Resultat;

public class ControlleurSauvegardeHistorique {
	
	

	public void ajoutHistorique(String liste, TreeSet<Resultat<String, Float>> tree) {
		Historique.getInstance().ajoutHistorique(liste, tree);
	}
	
	public void ecrireHistoDansFichier() {
		Historique.getInstance().ecrireHistoDansFichier();
	}
	
	public void recupHisto() {
		Historique.getInstance().recupHisto();
	}

	
}
