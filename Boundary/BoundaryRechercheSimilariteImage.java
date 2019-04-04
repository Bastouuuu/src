package Boundary;

import java.util.Scanner;
import java.util.TreeSet;

import Controleur.ControlleurIndexation;
import Controleur.ControlleurRechercheSimilariteImage;
import Modele.Resultat;
import Modele.ThreadIndexation;

public class BoundaryRechercheSimilariteImage {
	
	private ControlleurRechercheSimilariteImage control; 
	private ControlleurIndexation controlIndexation;
	
	public BoundaryRechercheSimilariteImage(ControlleurRechercheSimilariteImage c, ControlleurIndexation ci) {
		this.control = c;
		this.controlIndexation = ci;
	}
	
	public void rechercheSimilariteImage() {
		ThreadIndexation t = new ThreadIndexation("Image",controlIndexation);
		t.start();
		int pathvalide = 0;//variable pour savoir si le fichier existe
		int verifNbOk = 0 ; //variable permettant de tester si le fichier entré est bien au format bmp
		int verifCouleurOk = 0 ; //variable permettant de tester si le fichier entré est bien au format jpg
		
		String nomFichier ="";
		
		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>() ;
		
		System.out.println("Entrez le nom de l'image que vous souhaitez rechercher.\n" + 
				"Pour un fichier en noir et blanc, entrez son nom.bmp\n" +
				"Pour un fichier en couleurs, entrez son nom.jpg\n" + 
				"Précisez le chemin complet s'il n'est pas dans le dossier courant.");
		
		while(verifNbOk == 0 && verifCouleurOk == 0) {
			Scanner s = new Scanner(System.in);
			while(pathvalide == 0) {
				nomFichier = s.nextLine();
				pathvalide = control.fileExists(nomFichier) ;
				if(pathvalide == 0) {
					System.out.println("Le document est introuvable ! Réessayez.");
				}
				
			}
			verifNbOk = control.verif_format(nomFichier);
			verifCouleurOk = control.verif_format_rgb(nomFichier);
			
			if(verifNbOk == 0 && verifCouleurOk == 0) {
				System.out.println("Le format n'est pas respecté ! Réessayez.");
				nomFichier = s.nextLine();
			}
			else {
				if(verifNbOk == 1) {
					System.out.println("Requête noir et blanc");
					while(t.getState() != Thread.State.TERMINATED) {}
					hash = control.rechercheSimilariteImageNb(nomFichier) ; 
				}
				else {
					System.out.println("Requête couleurs");
					while(t.getState() != Thread.State.TERMINATED) {}
					hash = control.rechercheSimilariteImageCouleur(nomFichier) ; 
				}
			}
		}
		if(hash.isEmpty()) {
			System.out.println("Aucun document ne correspond au document soumis.");
		}
		else {
			System.out.println("Documents similaires trouvés :");
			for(Resultat<String,Float> r : hash ) {
				System.out.println("Fichier : " + r.getNom() + " match: " + r.getNombre() +"%");
			}
		}
		
	}

}