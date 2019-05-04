package Controleur;

import java.util.TreeSet;

import Modele.Historique;
import Modele.Resultat;

/**Controleur du cas Sauvegarde Historique permettant d'ajouter dans l'historique, de supprimer l'ensemble de l'historique et de récupérer l'historique.
 * @author Omar Hilmi
 *
 */
public class ControlleurSauvegardeHistorique {
	
	

	public void ajoutHistorique(String liste, TreeSet<Resultat<String, Float>> tree) {
		Historique.getInstance().ajoutHistorique(liste, tree);
	}
	
	public static void ecrireHistoDansFichier() {
		Historique.getInstance().ecrireHistoDansFichier();
	}
	
	public static void recupHisto() {
		Historique.getInstance().recupHisto();
	}

	public void supprimerHisto() {
		Historique.getInstance().supprimerHisto();
		
	}

	
}
