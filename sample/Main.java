package sample;

import Boundary.BoundaryRechercheCritereTexte;
import Boundary.BoundaryRechercheSimilariteImage;
import Boundary.BoundaryRechercheSimilariteTexte;
import Controleur.*;


import Boundary.BoundarySauvegardeHistorique;
import Controleur.ControlleurCommun;
import Controleur.ControlleurIndexation;
import Controleur.ControlleurRechercheCritereTexte;
import Controleur.ControlleurRechercheSimilariteTexte;
import Controleur.ControlleurSauvegardeHistorique;
import Modele.ComparateurResultat;
import Modele.CritereTexte;
import Modele.Historique;
import Modele.Polarite;
import Modele.Resultat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;



public class Main extends Application {

    //Partie recherche critère
    @FXML
    private Button ButtonRechercher;
    @FXML
    private Button ButtonParcourir;
    @FXML
    private TextField TextFieldSon;

    @FXML
    private TextField TextFieldMotCle;

    @FXML
    private ColorPicker ColorPickerImage;
    @FXML
    private Button ButtonRechercherSimi;


    //Partie recherche similarité
    @FXML
    private ToggleGroup GroupRadio;
    @FXML
    private RadioButton RadioTexte;
    @FXML
    private RadioButton RadioImage;
    @FXML
    private RadioButton RadioSon;
    @FXML
    private TextField TextFieldSimi;
    //Partie admin
    @FXML
    private Tab PaneAdministrator;
    @FXML
    private AnchorPane AnchorAdministrator;
    @FXML
    private AnchorPane AnchorParam;
    @FXML
    private TabPane TabPane;
    @FXML
    private TextField TextFieldLogin;
    //Partie result
    @FXML
    private ListView ListResult;
    @FXML
    private Tab TabResult;
    @FXML
    protected ProgressBar ProgressIndex;
    @FXML
    private VBox Layout;
    @FXML
    private ProgressBar ProgressSimi;

    private Stage primaryStage;
   
    //Partie config
    @FXML
    private TextField TextFieldConfigTexteLon;
    @FXML
    private TextField TextFieldConfigTexteOcc;
    @FXML
    private TextField TextFieldConfigTexteSave;
    @FXML
    private TextField TextFieldConfigImageBPF;
    @FXML
    private TextField TextFieldConfigImageSeuil;
    @FXML
    private TextField TextFieldConfigSonSample;
    @FXML
    private TextField TextFieldConfigSonInter;
    @FXML
    private TextField TextFieldConfigPath;
    @FXML
    private TextField TextFieldConfigMdp;
    @FXML 
    private Button ButtonConfigurer;
    
    //Partie sauvegarde historique
    @FXML
    private Button ButtonSauvegarder;
    @FXML
    private ListView ListHisto;
    @FXML
    private Tab TabHisto;
    @FXML
    private DialogPane ErrorOpenResult;
    @FXML
    private AnchorPane TextErrorOpenResult;
    @FXML
    private DialogPane ErrorCritere;
    @FXML
    private DialogPane ErrorConfiguration;
    @FXML
    private AnchorPane TextErrorConfig;
    @FXML
    private AnchorPane TextError;
    @FXML
    private Button ButtonSupprimer;
    
    //Partie dark mode
    @FXML
    private CheckBox checkBoxDarkMode;
    


    private ControlleurRechercheCritereTexte control = new ControlleurRechercheCritereTexte();
    private ControlleurRechercheSimilariteTexte controlSimi = new ControlleurRechercheSimilariteTexte();
    private ControlleurSauvegardeHistorique controlSauv = new ControlleurSauvegardeHistorique();
    private BoundarySauvegardeHistorique boundSauv = new BoundarySauvegardeHistorique(controlSauv);
    private ControlleurIndexation index = new ControlleurIndexation();
    private ControlleurCommun commun = new ControlleurCommun();
    private BoundaryRechercheCritereTexte bound = new BoundaryRechercheCritereTexte(control,index,commun);
    private BoundaryRechercheSimilariteTexte boundSimi = new BoundaryRechercheSimilariteTexte(controlSimi,index);
    private TreeSet<Resultat<String,Float>> lastresult = new TreeSet<>(new ComparateurResultat());

    private ControlleurRechercheSimilariteImage controlSimiImage = new ControlleurRechercheSimilariteImage();
    private BoundaryRechercheSimilariteImage boundSimiImage = new BoundaryRechercheSimilariteImage(controlSimiImage,index);
    private ControlleurAdministrateur controlAdmin = new ControlleurAdministrateur();

    
    private String requete="";
    boolean sauvegarderPressed = false;
    Historique historique = Historique.getInstance();
    private File simi;
    private File son;

    @Override
    public void start(Stage primaryStage) throws Exception{
//<<<<<< HEAD
    	//pour git
    	/*System.load("/Users/bast/Downloads/FilRougeV3/commun.dylib");
		System.load("/Users/bast/Downloads/FilRougeV3/texte.dylib");
        System.load("/Users/bast/Downloads/FilRougeV3/setup.dylib");
		System.load("/Users/bast/Downloads/FilRougeV3/son.dylib");
		System.load("/Users/bast/Downloads/FilRougeV3/image_nb.dylib");*/

		
    	//pour Omar
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/commun.dylib");
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/texte.dylib");
        System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/setup.dylib");
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/son.dylib");
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/image_nb.dylib");
//=======

		
		/*System.load("/Users/bast/Downloads/FilRougeV3/commun.dylib");
        System.load("/Users/bast/Downloads/FilRougeV3/texte.dylib");
        System.load("/Users/bast/Downloads/FilRougeV3/image_nb.dylib");
        System.load("/Users/bast/Downloads/FilRougeV3/setup.dylib");
		System.load("/Users/bast/Downloads/FilRougeV3/son.dylib");*/
		///Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/image_nb.dylib
//>>>>>>> master
        Parent root = FXMLLoader.load(getClass().getResource("Ariane'sThread.fxml"));
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Ariane's Thread");
        Scene s = new Scene(root,640,680);
        primaryStage.setScene(s);
        primaryStage.show();
        //s.getRoot().setStyle("-fx-base:black");
        //primaryStage.getScene().getRoot().setStyle("-fx-base:black");

    }


    public void disableFieldsText(){
        if(TextFieldMotCle.getText().length() < 1){
            ColorPickerImage.setDisable(false);
            ButtonParcourir.setDisable(false);
        }else {
            ColorPickerImage.setDisable(true);
            ButtonParcourir.setDisable(true);
        }
    }


    public void parcourir(){
        FileChooser browser = new FileChooser();
        browser.setTitle("Ouvrir un document...");
        if(GroupRadio.getSelectedToggle() == RadioTexte ){
            browser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Fichier texte", "*.xml")
            );
        }else if(GroupRadio.getSelectedToggle() == RadioImage){
            browser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Fichier image", "*.jpg", "*.bmp")
            );
        }else{
            browser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Fichier son", "*.txt")
            );
        }
        simi = browser.showOpenDialog(primaryStage);
        if(simi != null){
            RadioImage.setDisable(true);
            RadioSon.setDisable(true);
            RadioTexte.setDisable(true);
            TextFieldSimi.setText(simi.getAbsolutePath());
        }
    }

    public void clear2(){
        RadioTexte.setDisable(false);
        RadioImage.setDisable(false);
        RadioSon.setDisable(false);
        TextFieldSimi.clear();
        simi = null;
    }

    public void parcourirSon() {
        FileChooser browser = new FileChooser();
        browser.setTitle("Ouvrir un document...");
        browser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier son", "*.txt")
        );
        son = browser.showOpenDialog(primaryStage);
        if(son != null){
            ColorPickerImage.setDisable(true);
            TextFieldMotCle.setDisable(true);
            TextFieldSon.setText(son.getName());
        }
    }

    public void clear(){
        son = null;
        TextFieldMotCle.clear();
        ColorPickerImage.setValue(Color.WHITE);
        TextFieldSon.clear();
        TextFieldMotCle.setDisable(false);
        ColorPickerImage.setDisable(false);
        ButtonParcourir.setDisable(false);
    }

    public void login(){
        if (TextFieldLogin.getText().equals(controlAdmin.get_password())) {
            TextFieldLogin.setVisible(false);
            AnchorParam.setVisible(true);
        }else{
            TextFieldLogin.setVisible(true);
            AnchorParam.setVisible(false);
        }
    }

    public void afficheResult(){
        ObservableList<String> list = FXCollections.observableArrayList();
        if(lastresult.isEmpty()) {
            list.add("Aucune recherche récente");
        }else{
            list.clear();
            for (Resultat<String, Float> r : lastresult) {
                list.add(r.toString());
            }
        }
        ListResult.setItems(list);
    }

    public void openResult() throws IOException {
        if(!ListResult.getSelectionModel().getSelectedItem().toString().contains("Aucun")) {
            String path = controlAdmin.get_path();
            String fic = ListResult.getSelectionModel().getSelectedItem().toString();
            if (fic.contains(".xml")) {
                path = path.concat("TexteTest/");
            }else if(fic.contains(".jpg")){
                path = path.concat("TEST_RGB/");
            }else if(fic.contains(".bmp")){
                path=path.concat("image_nb/");
            }else if(fic.contains(".txt")){
                path=path.concat("TEST_SON/");
            }
            String[] tmp = fic.split("--");
            path = path.concat(tmp[0].trim());
            Desktop desktop = Desktop.getDesktop();
            File f = new File(path);
            if(f.exists()) {
                desktop.open(f);
            }else{
                Label x = new Label("Une erreur s'est produite, le fichier est introuvable.\n Veuillez relancer une recherche et re-essayer."+
                        "\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
                x.setTextFill(Color.PURPLE);
                TextErrorOpenResult.getChildren().clear();
                TextErrorOpenResult.getChildren().add(x);
                ErrorOpenResult.setVisible(true);
                ErrorOpenResult.setExpandableContent(null);
            }
        }else{
            Label x = new Label("Aucun fichier à ouvrir !\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
            x.setTextFill(Color.PURPLE);
            TextErrorOpenResult.getChildren().clear();
            TextErrorOpenResult.getChildren().add(x);
            ErrorOpenResult.setVisible(true);
            ErrorOpenResult.setExpandableContent(null);
        }
    }

    public void closeErrorResult(){
        this.ErrorOpenResult.setVisible(false);
    }

    public void logoff(){
        TextFieldLogin.setPromptText("login admin");
        TextFieldLogin.setText("");
    }

    public void rechercher(){
      if(TextFieldMotCle.getLength() > 0) {
          if(TextFieldMotCle.getText().charAt(0) == '+' || TextFieldMotCle.getText().charAt(0) == '-') {
              new Thread(() -> {
                  ButtonRechercher.setDisable(true);
                  ProgressIndex.setVisible(true);
                  ArrayList<CritereTexte> list = new ArrayList<>();
                  String s = TextFieldMotCle.getText();
                  ProgressIndex.setProgress(0.1);
                  String[] ss = s.split(",");
                  ProgressIndex.setProgress(0.3);
                  if (ss.length > 1) {
                      for (int i = 0; i < ss.length; i++) {
                          if (ss[i].charAt(0) == '+') {
                              list.add(new CritereTexte(Polarite.PRESENT, ss[i].substring(1).trim()));
                          } else if (ss[i].charAt(0) == '-') {
                              list.add(new CritereTexte(Polarite.ABSENT, ss[i].substring(1).trim()));
                          }
                      }
                      ProgressIndex.setProgress(0.6);
                  } else {
                      if (ss[0].charAt(0) == '+') {
                          list.add(new CritereTexte(Polarite.PRESENT, ss[0].substring(1).trim()));
                      } else if (ss[0].charAt(0) == '-') {
                          list.add(new CritereTexte(Polarite.ABSENT, ss[0].substring(1).trim()));
                      }
                  }
                  ProgressIndex.setProgress(0.7);
                  lastresult.clear();
                  TreeSet<Resultat<String, Float>> tmp = bound.rechercheParCritereComplexe(list);
                  ProgressIndex.setProgress(1.0);
                  try {
                      Thread.sleep(500);
                  } catch (InterruptedException e) {
                      System.out.println(e);
                  }
                  if (tmp == null) {
                      lastresult.add(new Resultat<String, Float>("Aucun document trouvé !", 0F));
                  } else {
                      lastresult.addAll(tmp);
                  }
                  ProgressIndex.setVisible(false);
                  ButtonRechercher.setDisable(false);
              }).start();
          }else{

                  Label x = new Label("Le formatage de la recherche est incorrect.\n La saisie doit être de la forme suivante :\n " +
                          "Polarité (+ ou -) Mot\n Plusieurs critères peuvent être saisis, séparés d'une virgule.\n\n\n" +
                          "Cliquez n'importe où dans la fenêtre pour la fermer.");
                  x.setTextFill(Color.PURPLE);
                  TextError.getChildren().clear();
                  TextError.getChildren().add(x);
                  ErrorCritere.setVisible(true);
                  ErrorCritere.setExpandableContent(null);
      }
    }
    }

    public void closeDialog(){
          ErrorCritere.setVisible(false);
        }

    public void rechercherSimi(){
        if(GroupRadio.getSelectedToggle() == RadioTexte && TextFieldSimi.getLength() > 0){
            new Thread(() -> {
                ButtonRechercherSimi.setDisable(true);
                ProgressSimi.setVisible(true);
                ProgressSimi.setProgress(0.6);
                TreeSet<Resultat<String, Float>> tmp = boundSimi.rechercheSimilariteTexte(TextFieldSimi.getText());
                lastresult.clear();
                if (!tmp.isEmpty()) {
                    lastresult.addAll(tmp);
                } else {
                    lastresult.add(new Resultat<String, Float>("Aucun document trouvé !", 0F));
                }
                ProgressSimi.setProgress(1.0);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    System.out.println(e);
                }
                ProgressSimi.setVisible(false);
                ButtonRechercherSimi.setDisable(false);
            }).start();
        }
        if(GroupRadio.getSelectedToggle() == RadioImage && TextFieldSimi.getLength() > 0){
            new Thread(() -> {
                ButtonRechercherSimi.setDisable(true);
                ProgressSimi.setVisible(true);
                ProgressSimi.setProgress(0.6);
                TreeSet<Resultat<String, Float>> tmp = boundSimiImage.rechercheSimilariteImage(TextFieldSimi.getText());
                lastresult.clear();
                if (!tmp.isEmpty()) {
                    lastresult.addAll(tmp);
                } else {
                    lastresult.add(new Resultat<String, Float>("Aucun document trouvé !", 0F));
                }
                ProgressSimi.setProgress(1.0);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    System.out.println(e);
                }
                ProgressSimi.setVisible(false);
                ButtonRechercherSimi.setDisable(false);
            }).start();
        }
    }
    
    public void configurer() {
    	String textLon = TextFieldConfigTexteLon.getText();
    	String textOcc = TextFieldConfigTexteOcc.getText();
    	String textSave = TextFieldConfigTexteSave.getText();
    	String imBPF = TextFieldConfigImageBPF.getText();
    	String imSeuil = TextFieldConfigImageSeuil.getText();
    	String sonSample = TextFieldConfigSonSample.getText();
    	String sonInter = TextFieldConfigSonInter.getText();
    	String path = TextFieldConfigPath.getText();
    	String newMdp = TextFieldConfigMdp.getText();
    	String x= new String();
    	if(textLon.length() != 0 && textOcc.length() != 0 && textSave.length() != 0) {
           if(Integer.parseInt(textLon) > 0 && Integer.parseInt(textOcc) > 0 && Integer.parseInt(textSave) >0) {
               controlAdmin.edit_settings_texte(Integer.parseInt(textLon), Integer.parseInt(textOcc), Integer.parseInt(textSave));
               x = x.concat("Texte mis a jour !\n");
           }else{
               x = x.concat("Texte invalide ! Les parametres doivent etre > 0.\n");
           }
    	} 
    	if(imBPF.length() != 0 && imSeuil.length() != 0) {
          if(Integer.parseInt(imBPF) > 0 && Integer.parseInt(imSeuil) > 0 && Integer.parseInt(imSeuil) <101) {
              controlAdmin.edit_settings_image(Integer.parseInt(imBPF), Integer.parseInt(imSeuil));
              x = x.concat("Image mis a jour !\n");
          }else{
              x = x.concat("Image invalide ! Bit poids fort doit etre > 0 et 0 < Seuil <= 100.\n");
          }
    	}
    	if(sonSample.length() != 0 && sonInter.length() != 0) {
    	    if(((Integer.parseInt(sonSample) == 1024 || Integer.parseInt(sonSample) == 2048) && Integer.parseInt(sonInter) >= 40 && Integer.parseInt(sonInter) <= 100))
            {
                controlAdmin.edit_settings_son(Integer.parseInt(sonSample), Integer.parseInt(sonInter));
                x = x.concat("Son mis a jour !\n");
            }else{
    	        x = x.concat("Son invalide ! Nb bits doit etre 1024 ou 2048 et 40 <= nb intervalles <= 100\n");
            }
        }
    	if(path.length() != 0) {
    	    if(controlAdmin.isPathValide(path)) {
                controlAdmin.edit_settings_path(path);
                x = x.concat("Chemin des dossiers mis a jour !\n");
            }else{
    	        x = x.concat(("Chemin invalide ! \n"));
            }
        }
    	if(newMdp.length() != 0) {
    		controlAdmin.edit_setting_password(newMdp);
          x=  x.concat("Mot de passe mis a jour !\n");
        }
        if(x.isEmpty()){
           x= x.concat("Tous les champs d'un type de document doivent etre remplis pour valider\n les modifications.");
        }
        x = x.concat("\n\n\n Cliquer n'importe ou dans la fenetre pour fermer.");
        Label y = new Label(x);
        y.setTextFill(Color.PURPLE);
        TextErrorConfig.getChildren().clear();
        TextErrorConfig.getChildren().add(y);
        ErrorConfiguration.setVisible(true);
        ErrorConfiguration.setExpandableContent(null);
    }

    public void closeConfigDialog(){
        ErrorConfiguration.setVisible(false);
    }

    public void saveHisto() {
    	boundSauv.recupHisto();
    	sauvegarderPressed = true;

    	if(!TextFieldMotCle.getText().isEmpty() || !TextFieldSon.getText().isEmpty() || !TextFieldSimi.getText().isEmpty()) {
    		if(!TextFieldMotCle.getText().isEmpty()) {
    			requete = TextFieldMotCle.getText();
    			requete = requete.replaceAll(",","/");
    		}
    		else {
    			if(!TextFieldSon.getText().isEmpty()) {
    				requete = TextFieldSon.getText();
    			}
    			else {
    				requete = TextFieldSimi.getText();
    			}
    		}
    	}
    	if(!lastresult.contains(new Resultat<String, Float>("Aucun document trouvé !", 0F))) {
    		//System.out.println("condition nulle");
    		boundSauv.ajoutHistorique(requete, lastresult);
    	}

    	
    }
    
    public void afficheHisto() {
    	ObservableList<String> list = FXCollections.observableArrayList();
    		for(Map.Entry<String, String> entry : historique.getHash().entrySet()) {
        	    list.add(entry.getKey());
        	    list.add(entry.getValue());
        	}
    	list.add("");
    	sauvegarderPressed = false;
		ListHisto.setItems(list);
    }
    
    public void supprimerHisto() {
    	boundSauv.supprimerHisto();
    	afficheHisto();
    }
    
    public void setDarkMode() {
    	if(checkBoxDarkMode.isSelected()) {
    		Layout.setStyle("-fx-base:black");
    	}
    	else {
    		Layout.setStyle("");
    	}
    }
    
    
    
    public static void main(String[] args) {
    	BoundarySauvegardeHistorique.recupHisto();
        launch(args);
        BoundarySauvegardeHistorique.ecrireHistoDansFichier();
    }
    
    
}
