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
	
	public TreeSet<Resultat<String,Float>> rechercheSimilariteImage(String s) {
		ThreadIndexation t = new ThreadIndexation("Image",controlIndexation);
		t.start();
		int pathvalide = 0;//variable pour savoir si le fichier existe
		int verifNbOk = 0 ; //variable permettant de tester si le fichier entré est bien au format bmp
		int verifCouleurOk = 0 ; //variable permettant de tester si le fichier entré est bien au format jpg

		TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>() ;
		
	/*	System.out.println("Entrez le nom de l'image que vous souhaitez rechercher.\n" +
				"Pour un fichier en noir et blanc, entrez son nom.bmp\n" +
				"Pour un fichier en couleurs, entrez son nom.jpg\n" + 
				"Précisez le chemin complet s'il n'est pas dans le dossier courant.");*/
		
		while(verifNbOk == 0 && verifCouleurOk == 0) {
			//Scanner s = new Scanner(System.in);
			while(pathvalide == 0) {
				//nomFichier = s.nextLine();
				pathvalide = control.fileExists(s) ;
				if(pathvalide == 0) {
					System.out.println("Le document est introuvable ! Réessayez.");
				}
				
			}
			verifNbOk = control.verif_format(s);
			verifCouleurOk = control.verif_format_rgb(s);

			if(verifNbOk == 0 && verifCouleurOk == 0) {
				System.out.println("Le format n'est pas respecté ! Réessayez.");
			//	nomFichier = s.nextLine();
			}
			else {
				if(verifNbOk == 1) {
					System.out.println("Requête noir et blanc");
					while(t.getState() != Thread.State.TERMINATED) {}
					hash = control.rechercheSimilariteImageNb(s) ;
				}
				else {
					System.out.println("Requête couleurs");
					while(t.getState() != Thread.State.TERMINATED) {}
					hash = control.rechercheSimilariteImageCouleur(s) ;
				}
			}
		}
		return hash;
	}
		
	}
