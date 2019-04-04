    
package Boundary;

import java.util.ArrayList;
import java.util.Collections;


import java.util.Scanner;
import java.util.TreeSet;
import Modele.ComparateurPolarite;
import Controleur.ControlleurCommun;
import Controleur.ControlleurIndexation;
import Controleur.ControlleurRechercheCritereTexte;
import Modele.ComparateurResultat;
import Modele.CritereTexte;
import Modele.Polarite;
import Modele.Resultat;
import Modele.ThreadIndexation;

public class BoundaryRechercheCritereTexte {
	
	//Tableau des principaux caraceteres interdits
	char CharInterdit[]= {'$','*','!','?','&','�','�','%','/',':',',','�','\\','�','+','-'};
	
	private ControlleurRechercheCritereTexte control;
	private ControlleurIndexation controlIndexation;
	private ControlleurCommun controlCommun;
	
	public BoundaryRechercheCritereTexte(ControlleurRechercheCritereTexte c, ControlleurIndexation ct, ControlleurCommun cc) {
		this.control = c;
		this.controlIndexation = ct;
		this.controlCommun = cc;
	}
	
	//Recherche simple par critere. Un seul mot pris en compte.
	public TreeSet<Resultat<String,Float>> rechercheParCritere(String s) {
		TreeSet<Resultat<String,Float>> hash = new TreeSet<>(control.rechercheCritereTexte(s));
		if(hash.isEmpty()) {
			System.out.println("Mot " + s + " non trouve !");
			return hash;
		}else {
			System.out.println("Mot " + s +" trouve :");
				return hash;
		}
	}
	
	//Requete complexe utilisant plusieurs requetes simples
	//Le premier mot saisi DOIT etre PRESENT dans les resultats pour creer une base de comparaison initiale.
	public TreeSet<Resultat<String, Float>> rechercheParCritereComplexe(ArrayList<CritereTexte> listeCrit ) {
		ThreadIndexation t = new ThreadIndexation("Texte", controlIndexation);
		t.start();
		//ArrayList<CritereTexte> listeCrit = new ArrayList<>();
	/*	boolean newmot = true;
		String s = "";
		Scanner sc = new Scanner(System.in);
		//Saisie des criteres soit une polarite + un mot par critere.
		while(newmot) {
		boolean motvalide = false;
		Polarite po = Polarite.PRESENT;
			boolean povalide = false;
			while (!povalide) {
				System.out.println("Quelle est la polarite du mot que vous souhaitez rechercher ? 1. + 2. -");
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
			System.out.println("Quel est le mot a rechercher ?");
			while(!motvalide) {
				Scanner sc2 = new Scanner(System.in);
				s = sc2.nextLine();
				String tmp = new String(s);
				for(char c : CharInterdit) {
					tmp = tmp.replace(c, '#');
				}
				motvalide = (s.equals(tmp) && s.length() > 0 && !s.contains("#"));
				if(!motvalide) {
					System.out.println("Le mot saisi est invalide. Reessayez.");
				}
			}
			CritereTexte crit = new CritereTexte(po,s);
			listeCrit.add(crit);
			System.out.println("Voulez-vous saisir un nouveau mot ? 1.Oui 2.Non");
			int reponse = sc.nextInt();
			if (reponse == 2 ) {
				newmot = false;
			}
		}
		sc.close();*/
		Collections.sort(listeCrit, new ComparateurPolarite());
		if(listeCrit.get(0).getPolarite() != Polarite.PRESENT) {
			System.out.println("ERREUR : au moins UN mot-cle doit etre present dans les resultats (polarite +).");
			System.exit(3);
		}
		while(t.getState() != Thread.State.TERMINATED) {}
		TreeSet<Resultat<String,Float>> result = new TreeSet<>(new ComparateurResultat());
		if (!listeCrit.isEmpty()) {
			if(listeCrit.size() == 1) {
					result.addAll(rechercheParCritere(listeCrit.get(0).getMotCle()));
			}else {
			//Permet de savoir s'il faut faire un union entre deux polarites + ou entre une + et une -
			Polarite PolariteCritCourant;
			//TreeSet contenant les resultats temporaires des recherches simples
			TreeSet<Resultat<String,Float>> result1 = new TreeSet<>();
			TreeSet<Resultat<String,Float>> result2 = new TreeSet<>();
			for(int i = 0; i < listeCrit.size();i++) {
				if(i==0) {
					//Creation de la base initiale des documents qui va pouvoir ensuite etre affine grace aux autres criteres
					result1 = control.rechercheCritereTexte(listeCrit.get(i).getMotCle());
					//Si le mot devant etre PRESENT n'est pas trouve, ca ne sert a rien de poursuivre le traitement
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
				result2=control.rechercheCritereTexte(listeCrit.get(i).getMotCle());
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
		}
			if (!result.isEmpty()) {
				return result;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
	
}
