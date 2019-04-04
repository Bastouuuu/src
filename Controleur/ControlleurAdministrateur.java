package Controleur;

import java.util.Scanner;

import Modele.commun;

public class ControlleurAdministrateur {

	public String get_password() {
		return commun.get_password();
	}

	

	public void edit_settings_texte() {
		boolean configvalide = false;
		String s = commun.get_config_texte();
		System.out.println("Les parametres actuels sont :\nLongueur min d'un mot, nombre d'occurences pour etre mot-cle, nombre de mot-cles sauvegardes : " + s);
		Scanner sx = new Scanner(System.in);
		int lon,occu,save;
		do {
			System.out.println("Nouvelle longueur minimale d'un mot :");
			lon = sx.nextInt();
			sx.nextLine();
			System.out.println("Nouveau nombre d'occurences pour etre mot-cle :");
			occu = sx.nextInt();
			sx.nextLine();
			System.out.println("Nouveau nombre de mots sauvegardes :");
			save = sx.nextInt();
			sx.nextLine();
			configvalide = (lon > 0 && occu > 0 && save > 0);
			if(!configvalide) {
				System.out.println("Parametres invalides ! Ils doivent etre > 0 ! Reessayez.");
			}
		}while(!configvalide);
		sx.close();
		commun.edit_settings_text(lon, occu, save);
		System.out.println("Parametres mis a jour !");
	}



	public void edit_settings_image() {
		boolean configvalide = false;
		String s = commun.get_config_image();
		System.out.println("Les parametres actuels sont :\nNombres de bits de poids forts, seuil d'affichage des resultats (%): " + s);
		Scanner sx = new Scanner(System.in);
		int occu,save;
		do {
			System.out.println("Nouveau nombre de bits de poids forts:");
			occu = sx.nextInt();
			sx.nextLine();
			System.out.println("Nouveau seuil d'affichage des resultats :");
			save = sx.nextInt();
			sx.nextLine();
			configvalide = (save > 0 && save <= 100 && (occu == 2  || occu == 8 || occu == 4) && occu > 0 && ((occu & (occu - 1)) == 0));
			if(!configvalide){
				System.out.println("Parametres invalides ! Ils doivent etre > 0 ! Reessayez.");
			}
		}while(!configvalide);
		sx.close();
		commun.edit_settings_image(occu, save);
		System.out.println("Parametres mis a jour !");		
	}



	public void edit_settings_son() {
		boolean configvalide = false;
		String s = commun.get_config_son();
		System.out.println("Les parametres actuels sont :\nNombre d'echantillons, nombre d'intervalles : " + s);
		Scanner sx = new Scanner(System.in);
		int occu,save;
		do {
			System.out.println("Nouveau nombre d'echantillons (1024 ou 2048 ou 4096)");
			occu = sx.nextInt();
			sx.nextLine();
			System.out.println("Nouveau nombre d'intervalles (entre 30 et 100) :");
			save = sx.nextInt();
			sx.nextLine();
			configvalide = ((occu == 1024 || occu == 2048 || occu == 4096) && save >= 30 && save <= 100);
			if(!configvalide) {
				System.out.println("Parametres invalides ! Ils doivent etre > 0 ! Reessayez.");
			}
		}while(!configvalide);
		sx.close();
		commun.edit_settings_sound(occu, save);
		System.out.println("Parametres mis a jour !");			
	}



	public void edit_settings_path() {
		boolean pathvalide = false;
		String s = commun.get_path();
		System.out.println("Le chemin pour acceder aux fichiers est : " + s);
		Scanner ss = new Scanner(System.in);
		String newpath="";
		do {
			System.out.print("Quel est le nouveau chemin : ");
			newpath = ss.nextLine();
		int test =	commun.fileExists(newpath);
		if(test==1) {
			pathvalide = true;
		}else {
			System.out.println("Chemin invalide. Reessayez.");
		}
		}while(!pathvalide);
		if(newpath.charAt(newpath.length()-1) != '/') {
			newpath = newpath.concat("/");
		}
		ss.close();
		commun.edit_path(newpath);
		System.out.println("Parametre mis a jour !");
	}



	public void edit_setting_password() {
		boolean passvalide = false;
		Scanner ss = new Scanner(System.in);
		String newpass = "";
		do {
			System.out.println("Entrez le nouveau mot de passe : ");
			newpass = ss.nextLine();
			passvalide = newpass.length() > 0;
		}while(!passvalide);
		ss.close();
		commun.new_password(newpass);
		System.out.println("Parametre mis a jour !");
	}
}
