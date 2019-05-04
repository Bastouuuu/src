package Controleur;

import java.util.Scanner;


import Modele.commun;

public class ControlleurAdministrateur {

	public String get_password() {
		return commun.get_password();
	}

	

	public void edit_settings_texte(int lon, int occu, int save) {
		boolean configvalide = false;
		String s = commun.get_config_texte();
		System.out.println("Les parametres actuels sont :\nLongueur min d'un mot, nombre d'occurences pour etre mot-cle, nombre de mot-cles sauvegardes : " + s);
		System.out.println("Nouvelle longueur minimale d'un mot :");
		System.out.println(lon);
		System.out.println("Nouveau nombre d'occurences pour etre mot-cle :");
		System.out.println(occu);
		System.out.println("Nouveau nombre de mots sauvegardes :");
		System.out.println(save);
		configvalide = (lon > 0 && occu > 0 && save > 0);
		if(!configvalide) {
				System.out.println("Parametres invalides ! Ils doivent etre > 0 ! Reessayez.");
		} else {
			commun.edit_settings_text(lon, occu, save);
			System.out.println("Parametres mis a jour !");
		}
	}

	
	
	public void edit_settings_image(int bpf, int seuil) {
		boolean configvalide = false;
		String s = commun.get_config_image();
		System.out.println("Les parametres actuels sont :\nNombres de bits de poids forts, seuil d'affichage des resultats (%): " + s);
		System.out.println("Nouveau nombre de bits de poids forts:");
		System.out.println(bpf);
		System.out.println("Nouveau seuil d'affichage des resultats :");
		System.out.println(seuil);
		configvalide = (seuil > 0 && seuil <= 100 && (bpf == 2  || bpf == 8 || bpf == 4) && bpf > 0 && ((bpf & (bpf - 1)) == 0));
		if(!configvalide){
				System.out.println("Parametres invalides ! Ils doivent etre > 0 ! Reessayez.");
		}else {
			commun.edit_settings_image(bpf, seuil);
			System.out.println("Parametres mis a jour !");
		}
	}

	public String get_settings_texte(){
		return commun.get_config_texte();
	}

	public String get_settings_image(){
		return commun.get_config_image();
	}

	public String get_settings_sound(){
		return commun.get_config_son();
	}

	public void edit_settings_son(int sample, int inter) {
		boolean configvalide = false;
		String s = commun.get_config_son();
		System.out.println("Les parametres actuels sont :\nNombre d'echantillons, nombre d'intervalles : " + s);
		System.out.println("Nouveau nombre d'echantillons (1024 ou 2048 ou 4096)");
		System.out.println(sample);
		System.out.println("Nouveau nombre d'intervalles (entre 30 et 100) :");
		System.out.println(inter);
		configvalide = ((sample == 1024 || sample == 2048 || sample == 4096) && inter >= 30 && inter <= 100);
		if(!configvalide) {
				System.out.println("Parametres invalides ! Ils doivent etre > 0 ! Reessayez.");
		}else {
			commun.edit_settings_sound(sample, inter);
			System.out.println("Parametres mis a jour !");	
		}
	}

	public String get_path(){
		return commun.get_path();
	}

	public void edit_settings_path(String newpath) {
		String s = commun.get_path();
		System.out.println("Le chemin pour acceder aux fichiers est : " + s);
		System.out.print("Quel est le nouveau chemin : ");
		System.out.print(newpath);
		int test =	commun.fileExists(newpath);
		if(test!=1) {
			System.out.println("Chemin invalide. Reessayez.");
		}else {
			if(newpath.charAt(newpath.length()-1) != '/') {
				newpath = newpath.concat("/");
			}
			commun.edit_path(newpath);
			System.out.println("Parametre mis a jour !");
		}
	}

	public boolean isPathValide(String s){
		int a = commun.fileExists(s);
		if(a == 1){
			return true;
		}
		return false;
	}

	public void edit_setting_password(String newpass) {
		//boolean passvalide = false;
		System.out.println("Entrez le nouveau mot de passe : ");
		//passvalide = newpass.length() > 0;
		commun.new_password(newpass);
		System.out.println("Parametre mis a jour !");
	}
}
