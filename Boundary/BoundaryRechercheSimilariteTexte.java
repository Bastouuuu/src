package Boundary;

import java.util.Scanner;
import java.util.TreeSet;

import Controleur.ControlleurIndexation;
import Controleur.ControlleurRechercheSimilariteTexte;
import Modele.Resultat;
import Modele.ThreadIndexation;

public class BoundaryRechercheSimilariteTexte {
	
	private ControlleurRechercheSimilariteTexte control;
	private ControlleurIndexation controlIndexation;
	
	public BoundaryRechercheSimilariteTexte(ControlleurRechercheSimilariteTexte c, ControlleurIndexation ct) {
		this.control = c;
		this.controlIndexation = ct;
	}
	
	public TreeSet<Resultat<String,Float>> rechercheSimilariteTexte(String s) {
		ThreadIndexation t = new ThreadIndexation("Texte",controlIndexation);
		t.start();
		/*System.out.println("Veuillez saisir le nom du fichier a comparer. Precisez le chemin complet s'il n'est pas dans le dossier courant.");
		int pathvalide = 0;
		String s ="";
		String tmp="";
		Scanner sc = new Scanner(System.in);
		while(pathvalide == 0 || !tmp.equals(".xml")) {
			s = sc.nextLine();
			tmp = (""+s.charAt(s.length()-4) + s.charAt(s.length()-3) +s.charAt(s.length()-2) + s.charAt(s.length()-1));
			pathvalide = control.pathFileExists(s);
			if(pathvalide ==0) {
				System.out.println("Le document est introuvable ! Reessayez.");
			}
			if(!tmp.equals(".xml")) {
				System.out.println("Format de document incorrect !");
			}
		}
		sc.close();*/
		while(t.getState() != Thread.State.TERMINATED) {}
		TreeSet<Resultat<String,Float>> hash = control.rechercheSimilariteTexte(s);
		return hash;
	}
}