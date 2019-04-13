package Boundary;


import java.util.TreeSet;

import Controleur.ControlleurSauvegardeHistorique;
import Modele.Resultat;

public class BoundarySauvegardeHistorique {
	
	ControlleurSauvegardeHistorique controlleurSauvegardeHistorique;
	
	public BoundarySauvegardeHistorique (ControlleurSauvegardeHistorique controlleurSauvegardeHistorique) {
		this.controlleurSauvegardeHistorique = controlleurSauvegardeHistorique;
	}
	
	public void ajoutHistorique(String liste , TreeSet<Resultat<String,Float>> tree) {
		controlleurSauvegardeHistorique.ajoutHistorique(liste , tree);
	}
	
	public static void ecrireHistoDansFichier() {
		ControlleurSauvegardeHistorique.ecrireHistoDansFichier();
	}
	
	public static void recupHisto() {
		ControlleurSauvegardeHistorique.recupHisto();
	}
	public void supprimerHisto() {
		controlleurSauvegardeHistorique.supprimerHisto();
	}
	
	
	
}

