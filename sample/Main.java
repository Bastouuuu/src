package sample;

import Boundary.BoundaryRechercheCritereTexte;


import Boundary.BoundaryRechercheSimilariteTexte;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
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
    
    //Partie sauvegarde historique
    @FXML
    private Button ButtonSauvegarder;
    @FXML
    private ListView ListHisto;
    @FXML
    private Tab TabHisto;
    
    
    
    

    private ControlleurRechercheCritereTexte control = new ControlleurRechercheCritereTexte();
    private ControlleurRechercheSimilariteTexte controlSimi = new ControlleurRechercheSimilariteTexte();
    private ControlleurSauvegardeHistorique controlSauv = new ControlleurSauvegardeHistorique();
    private BoundarySauvegardeHistorique boundSauv = new BoundarySauvegardeHistorique(controlSauv);
    private ControlleurIndexation index = new ControlleurIndexation();
    private ControlleurCommun commun = new ControlleurCommun();
    private BoundaryRechercheCritereTexte bound = new BoundaryRechercheCritereTexte(control,index,commun);
    private BoundaryRechercheSimilariteTexte boundSimi = new BoundaryRechercheSimilariteTexte(controlSimi,index);
    private TreeSet<Resultat<String,Float>> lastresult = new TreeSet<>(new ComparateurResultat());
    
    private String requete="";
    boolean sauvegarderPressed = false;
    Historique historique = Historique.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
    	System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/commun.dylib");
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/texte.dylib");
        System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/setup.dylib");
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/son.dylib");
		System.load("/Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/FilRougeV3/image_nb.dylib");
        Parent root = FXMLLoader.load(getClass().getResource("Ariane'sThread.fxml"));
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Ariane's Thread");
        Scene s = new Scene(root,640,400);
        primaryStage.setScene(s);
        primaryStage.show();
        
        boundSauv.recupHisto();
    }


    public void disableFieldsText(){
        if(TextFieldMotCle.getText().length() <= 0){
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
                    new FileChooser.ExtensionFilter("Fichier image", "*.jpg")
            );
        }else{
            browser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Fichier son", "*.txt")
            );
        }
        File f = browser.showOpenDialog(primaryStage);
        if(f != null){
            TextFieldSimi.setText(f.getAbsolutePath());
        }
    }

    public void parcourirSon(){
        FileChooser browser = new FileChooser();
        browser.setTitle("Ouvrir un document...");
        browser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier son", "*.txt")
        );
        File f = browser.showOpenDialog(primaryStage);
        if(f != null){
            ColorPickerImage.setDisable(true);
            TextFieldMotCle.setDisable(true);
            TextFieldSon.setText(f.getName());
        }
    }

    public void login(){
        if (TextFieldLogin.getText().equals("Password")) {
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

    public void logoff(){
        TextFieldLogin.setPromptText("login admin");
        TextFieldLogin.setText("");
    }

    public void rechercher(){
      if(TextFieldMotCle.getLength() > 0) {
          new Thread(() -> {
              ButtonRechercher.setDisable(true);
              ProgressIndex.setVisible(true);
              ArrayList<CritereTexte> list = new ArrayList<>();
              String s = TextFieldMotCle.getText();
              ProgressIndex.setProgress(0.1);
              String[] ss = s.split(",");
              ProgressIndex.setProgress(0.3);
              if(ss.length > 1){
                  for(int i= 0;i <ss.length;i++){
                      if(ss[i].charAt(0) == '+'){
                          list.add(new CritereTexte(Polarite.PRESENT,ss[i].substring(1).trim()));
                      }else if(ss[i].charAt(0) == '-'){
                          list.add(new CritereTexte(Polarite.ABSENT,ss[i].substring(1).trim()));
                      }
                  }
                  ProgressIndex.setProgress(0.6);
              }else{
                  if(ss[0].charAt(0) == '+'){
                      list.add(new CritereTexte(Polarite.PRESENT,ss[0].substring(1).trim()));
                  }else if(ss[0].charAt(0) == '-'){
                      list.add(new CritereTexte(Polarite.ABSENT,ss[0].substring(1).trim()));
                  }
              }
              ProgressIndex.setProgress(0.7);
              lastresult.clear();
              TreeSet<Resultat<String,Float>>  tmp = bound.rechercheParCritereComplexe(list);
              ProgressIndex.setProgress(1.0);
              try{
                  Thread.sleep(500);
              }catch(InterruptedException e){
                  System.out.println(e);
              }
              if(tmp == null){
                  lastresult.add(new Resultat<String,Float>("Aucun document trouvé !", 0F));
              }else {
                  lastresult.addAll(tmp);
              }
              ProgressIndex.setVisible(false);
              ButtonRechercher.setDisable(false);
          }).start();
      }
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
    }

    public void saveHisto() {
    	boundSauv.recupHisto();
    	sauvegarderPressed = true;
    	System.out.println("test saveHisto");
    	if(!TextFieldMotCle.getText().isEmpty() || !TextFieldSon.getText().isEmpty() || !TextFieldSimi.getText().isEmpty()) {
    		if(!TextFieldMotCle.getText().isEmpty()) {
    			requete = TextFieldMotCle.getText();
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
    	boundSauv.ajoutHistorique(requete, lastresult);
    }
    
    public void afficheHisto() {
    	ObservableList<String> list = FXCollections.observableArrayList();
    	for(Map.Entry<String, String> entry : historique.getHash().entrySet()) {
    	    list.add(entry.getKey());
    	    list.add(entry.getValue());
    	}
		if(sauvegarderPressed) {
	    	System.out.println("test afficheHisto");
	        if(lastresult.isEmpty()) {
	        	list.add("");
	        }
	        else {
	            for (Resultat<String, Float> r : lastresult) {
	                list.add(r.toString());
	            }
	        }	        
    	}
		else {
			list.add("");
		}
		ListHisto.setItems(list);
		
    	

    }
    
    
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /* POUR TEST OMAR 
     /Users/o/Documents/TRAVAIL/1A_UPSSI/Fil_rouge/partie2/DATA_FIL_ROUGE_DEV/TexteTest/03-Des_chercheurs_parviennent_à_régénérer.xml
     
     */
    
    
}
