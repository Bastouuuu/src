package Boundary;

import Controleur.*;
import Modele.Resultat;

import java.util.*;

public class BoundaryRechercheSon {

	private ControlleurSon control;
	
	public BoundaryRechercheSon(ControlleurSon c) {
		control = c;
	}
	
	public TreeSet<Resultat<String,Float>> rechercheSon(String chemin) {
		control.indexSon();
		return control.rechercheSon(chemin);
		
	}
	
	public void indexSon() {
		control.indexSon();
	}
	
	
}
