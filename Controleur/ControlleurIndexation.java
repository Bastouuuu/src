package Controleur;

import Modele.texte;
import Modele.image_nb;


public class ControlleurIndexation {

	public void lancerIndexationTexte() {
		texte.indexer();
	}
	
	public void lancerIndexationImage() {
		image_nb.indexation_image_nb(); // indexation images nb
		image_nb.open_image_c(); 		// indexation images rgb
	}
}
