package Boundary;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
	
	public void ecrireHistoDansFichier() {
		controlleurSauvegardeHistorique.ecrireHistoDansFichier();
	}
	
	public void recupHisto() {
		controlleurSauvegardeHistorique.recupHisto();
	}
	
	
	
}

