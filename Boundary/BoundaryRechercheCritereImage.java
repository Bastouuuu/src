package Boundary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

import Controleur.ControlleurCommun;
import Controleur.ControlleurIndexation;
import Controleur.ControlleurRechercheCritereImage;
import Modele.ComparateurResultat;
import Modele.CritereImage;
import Modele.Polarite;
import Modele.Resultat;
import Modele.ThreadIndexation;
import Modele.ComparateurPolarite;

public class BoundaryRechercheCritereImage {

	private ControlleurRechercheCritereImage control;
	private ControlleurIndexation controlIndexation;
	private ControlleurCommun controlCommun;
	
	public BoundaryRechercheCritereImage(ControlleurRechercheCritereImage c, ControlleurIndexation ct, ControlleurCommun cc) {
		this.control = c;
		this.controlIndexation = ct;
		this.controlCommun = cc;
	}
	
		//Recherche simple par critere. Une seule plage de couleurs prise en compte.
		public void rechercheParCritere(int valMin, int valMax) {
			TreeSet<Resultat<String,Float>> hash = control.rechercheCritereImage(valMin, valMax);
			if(hash.isEmpty()) {
				System.out.println("Plage de couleurs " + valMin + "-" + valMax + " non trouvee !");
			}else {
			System.out.println("Plage de couleurs " + valMin + "-" + valMax + " trouvee :");
				for(Resultat<String,Float> r : hash) {
					System.out.println("Fichier : " + r.getNom()  + " nb occu : " + r.getNombre().intValue());
				}
			}
		}
		
		
		//Requete complexe utilisant plusieurs requetes simples
		//La premiere plage saisie DOIT etre PRESENT dans les resultats pour creer une base de comparaison initiale.
		public void rechercheParCritereComplexe() {
			ThreadIndexation t = new ThreadIndexation("Image",controlIndexation);
			t.start();
			ArrayList<CritereImage> listeCrit = new ArrayList<>();
			boolean newplage = true;
			Scanner sc = new Scanner(System.in);
			//Saisie des criteres soit une polarite + un mot par critere.
			while(newplage) {
			boolean plagevalide = false;
			Polarite po = Polarite.PRESENT;
				boolean povalide = false;
				while (!povalide) {
					System.out.println("Quelle est la polarite de la plage que vous souhaitez rechercher ? 1. + 2. -");
					int polarite = sc.nextInt();
					if(polarite == 1 || polarite == 2) {
						if(polarite == 1) {
							po = Polarite.PRESENT;
						}else {
							po = Polarite.ABSENT;
						}
					povalide = true;
					}else {
						System.out.println("Polarite invalide. Reessayez.");
					}
				}
				System.out.println("Quelle est la plage a rechercher ?");
				int valMin = -1; // initialisation a une valeur fausse
				int valMax = -1; // initialisation a une valeur fausse
				while(!plagevalide) {
					System.out.println("Veuillez entrer la valeur minimale de la plage de recherche : ");
					Scanner sc1 = new Scanner(System.in);
					valMin = sc1.nextInt();
					System.out.println("Veuillez entrer la valeur maximale de la plage de recherche : ");
					valMax = sc1.nextInt();
					// verifier la validite des criteres
					plagevalide = (valMin >= 0 && valMin <= 255 && valMax >= 0 && valMax <= 255);
					if(!plagevalide) {
						System.out.println("La plage saisie est invalide. Reessayez.");
					}
				}
				CritereImage crit = new CritereImage(po,valMin,valMax);
				listeCrit.add(crit);
				System.out.println("Voulez-vous saisir une nouvelle plage ? 1.Oui 2.Non");
				int reponse = sc.nextInt();
				if (reponse == 2 ) {
					newplage = false;
				}
			}
			sc.close();
			Collections.sort(listeCrit, new ComparateurPolarite());
			if(listeCrit.get(0).getPolarite() != Polarite.PRESENT) {
				System.out.println("ERREUR : au moins UNE plage doit etre presente dans les resultats (polarite +).");
				System.exit(3);
			}
			while(t.getState() != Thread.State.TERMINATED) {}
			TreeSet<Resultat<String,Float>> result = new TreeSet<>(new ComparateurResultat());
			if (!listeCrit.isEmpty()) {
				if(listeCrit.size() == 1) {
					rechercheParCritere(listeCrit.get(0).getValMin(), listeCrit.get(0).getValMax());
				}else {
				//Permet de savoir s'il faut faire un union entre deux polarites + ou entre une + et une -
				Polarite PolariteCritCourant;
				//TreeSet contenant les resultats temporaires des recherches simples
				TreeSet<Resultat<String,Float>> result1 = new TreeSet<>();
				TreeSet<Resultat<String,Float>> result2 = new TreeSet<>();
				for(int i = 0; i < listeCrit.size(); i++) {
					if(i == 0) {
						//Creation de la base initiale des documents qui va pouvoir ensuite etre affine grace aux autres criteres
						result1 = control.rechercheCritereImage(listeCrit.get(i).getValMin(), listeCrit.get(i).getValMax());
						//Si la plage devant etre PRESENT n'est pas trouvee, �a ne sert a rien de poursuivre le traitement
						if(result1.isEmpty()) {
							System.out.println("Aucun document ne correspond a ces criteres !");
							System.exit(1);
						}
						i++;
					}else {
						//La base initiale est deja cree, on ne fait plus que comparer le resultat de l'union precedente avec le nouveau critere.
						//-> result1 est donc une copie de result.
						result1.clear();
						result1.addAll(result);
					}
					result2.clear();
					result2=control.rechercheCritereImage(listeCrit.get(i).getValMin(), listeCrit.get(i).getValMin());
					PolariteCritCourant = listeCrit.get(i).getPolarite();
					result.clear();
					//Si result2 ne donne rien et que result1 a donne un resultat, on ignore simplement ce critere et on poursuit le traitement
					if(!result2.isEmpty()) {
						//Choix de la fonction a utiliser en fonction de la polarite du critere courant a comparer
						if(PolariteCritCourant == Polarite.PRESENT) {
							result.addAll(controlCommun.rechercheCritereComplexePP(result1,result2));
						}else {
							result.addAll(controlCommun.rechercheCritereComplexePM(result1, result2));
						}
					}else {
						if(PolariteCritCourant == Polarite.ABSENT) {
							result.addAll(result1);
						}
					}
			 }
			 if (!result.isEmpty()) {
				 for(Resultat<String,Float> r : result){
					 System.out.println("Result : " + r.getNom() + " " + r.getNombre().intValue());
				 }
			 }else {
				 System.out.println("Aucun document ne correspond a ces criteres !");
			 }
			}
			}else {
				System.out.println("Liste de critere vide.");
			}
		}	
}
