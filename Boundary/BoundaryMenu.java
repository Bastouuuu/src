package Boundary;

import java.util.Scanner;

public class BoundaryMenu {

	private BoundaryRechercheCritereTexte bRechCritText;
	private BoundaryRechercheCritereImage bRechCritIm;
	private BoundaryRechercheSimilariteTexte bRechSimiText;
	private BoundaryRechercheSimilariteImage bRechSimiImage;
	private BoundaryAdministrateur bAdmin;
	//ajouter les autres boundary
	
	//ajouter les autres boundary dans les parametres
	public BoundaryMenu(BoundaryRechercheCritereTexte bRechCritText, BoundaryRechercheCritereImage bRechCritIm,
			BoundaryRechercheSimilariteTexte bRechSimiText, BoundaryRechercheSimilariteImage bRechSimiImage, BoundaryAdministrateur bAdmin) {
		this.bRechCritText = bRechCritText;
		this.bRechCritIm = bRechCritIm;
		this.bRechSimiText = bRechSimiText;
		this.bRechSimiImage = bRechSimiImage;
		this.bAdmin = bAdmin;
		//ajouter les autres boundary
	}
	
	public void afficherMenu(){
		System.out.println("================MENU================");
		System.out.println("Recherche texte :\n\t1. Par critere\n\t2. Par similarite");
		System.out.println("Recherche image :\n\t3. Par critere\n\t4. Par similarite");
		System.out.println("Recherche son :\n\t5. Par frequence\n\t6. Par similarite");
		System.out.println("7. Mode administrateur");
		
		int choix;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Veuillez choisir l'action que vous voulez effectuer : ");
			choix = sc.nextInt();
		}while(choix != 1 && choix != 2 && choix != 3 && choix != 4 && choix != 5 && choix != 6 && choix != 7);
		
		// une fois les autres boundary ajoutes, de-commenter les lignes suivantes
		// et mettre les bons noms de fonctions (si ceux-ci ne sont pas bons)
		switch(choix){
		case 1:
			//bRechCritText.rechercheParCritereComplexe();
			break;
		case 2:
			//bRechSimiText.rechercheSimilariteTexte();
			break;
		case 3:
			bRechCritIm.rechercheParCritereComplexe();
			break;
		case 4:
			//bRechSimiImage.rechercheSimilariteImage();
			break;
		case 5:
			//bRechCritSon.rechercheCritSon();
			//break;
		case 6:
			//bRechSimiSon.rechercheSimilariteSon();
			//break;
		case 7:
			bAdmin.edit_settings();
			break;
		default:
			System.out.println("Ce type d'action n'est pas disponible.");
		}
		
		sc.close();
	}
	
}
