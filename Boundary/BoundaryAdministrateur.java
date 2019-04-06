package Boundary;

import java.util.Scanner;

import Controleur.ControlleurAdministrateur;

public class BoundaryAdministrateur {

	private ControlleurAdministrateur controlAdmin;
	
	public BoundaryAdministrateur(ControlleurAdministrateur c) {
		this.controlAdmin = c;
	}
	
	public void edit_settings() {
	int tentative =0;
	String s="";
	boolean logged = false;
	Scanner sc = new Scanner(System.in);
	do {
		System.out.println("Entrez le mot de passe administrateur");
		s = sc.nextLine();
		logged = s.equals(controlAdmin.get_password());
		if(!logged && tentative !=2) {
			System.out.println("Login incorrect. Reessayez.");
		}
		tentative++;
	}while(tentative < 3 && !logged);
	if(tentative == 3 && !logged) {
		System.out.println("Login incorrect. Deconnexion du mode administrateur.");
		System.exit(1);
	}
    
	System.out.println("Quel type de parametres souhaitez-vous modifier ?\n1. Texte 2. Image 3.Son 4. Chemin des fichiers 5. Mot de passe administrateur");

	int reponse = sc.nextInt();
	switch(reponse) {
	case 1 : controlAdmin.edit_settings_texte(3, 3, 100); break;
	case 2 : controlAdmin.edit_settings_image(2, 75); break;
	case 3 : controlAdmin.edit_settings_son(2048, 35); break;
	case 4 : controlAdmin.edit_settings_path("cheminblabla"); break;
	case 5 : controlAdmin.edit_setting_password("mdp"); break;
	default : System.out.println("Choix non reconnu. Fermeture."); System.exit(2);
	}
	sc.close();
	
	}
}
