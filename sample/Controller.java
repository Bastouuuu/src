package sample;

import Boundary.*;
import Controleur.*;
import Modele.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.UnaryOperator;



public class Controller {

    //Partie recherche critère
    @FXML
    private Button ButtonRechercher;

    @FXML
    private TextField TextFieldMotCle;

    @FXML
    private ColorPicker ColorPickerImage;
    @FXML
    private Button ButtonRechercherSimi;
    @FXML
    private Button ButtonReset;
    @FXML
    private ListView MiniListResult;

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
    private ListView ListResultSimi;
    @FXML
    private Button ButtonResetSimi;
    @FXML
    private DialogPane SimiErrorBox;
    @FXML
    private AnchorPane TextErrorSimi;
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
    @FXML
    private Button ButtonClearConfig;
    @FXML
    private Button ParcourirConfig;

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

    //Initialisation des formatteurs de saisie
    private boolean TextFiltersConfigSet = false;
    private boolean TextFilterCritereSet = false;

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

    private ControlleurRechercheCritereImage controlCritImage = new ControlleurRechercheCritereImage();
    private BoundaryRechercheCritereImage boundCritImage = new BoundaryRechercheCritereImage(controlCritImage, index, commun);

    private ControlleurSon controlSon = new ControlleurSon();
    private BoundaryRechercheSon boundSon = new BoundaryRechercheSon(controlSon);

    private String requete="";
    boolean sauvegarderPressed = false;
    Historique historique = Historique.getInstance();
    private File simi;
    private File son;
    private boolean fromHisto=false;

    public void setPrimaryStage(Stage s){
        this.primaryStage = s;
    }

    public void setTextFilters(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextFieldConfigImageSeuil.setTextFormatter(textFormatter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        TextFieldConfigSonInter.setTextFormatter(textFormatter2);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
        TextFieldConfigSonSample.setTextFormatter(textFormatter3);
        TextFormatter<String> textFormatter4 = new TextFormatter<>(filter);
        TextFieldConfigTexteLon.setTextFormatter(textFormatter4);
        TextFormatter<String> textFormatter5 = new TextFormatter<>(filter);
        TextFieldConfigTexteOcc.setTextFormatter(textFormatter5);
        TextFormatter<String> textFormatter6 = new TextFormatter<>(filter);
        TextFieldConfigTexteSave.setTextFormatter(textFormatter6);
    }

    public void disableFieldsText(){
        if(TextFieldMotCle.getText().length() < 1){
            ColorPickerImage.setDisable(false);
        }else {
            ColorPickerImage.setDisable(true);
        }
    }

    public void setTextFilterCritere(){
        if(!TextFilterCritereSet){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")|| text.matches("[a-z]*") || text.matches("[,]*") || text.matches("[+]*") || text.matches("[-]*") || text.matches("[A-Z]*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextFieldMotCle.setTextFormatter(textFormatter);
        TextFilterCritereSet=true;
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

    public void parcourirConfig(){
        DirectoryChooser browser = new DirectoryChooser();
        browser.setTitle("Sélectionner le nouveau répertoire.");
        simi = browser.showDialog(this.primaryStage);
        if(simi != null && simi.isDirectory()){
            TextFieldConfigPath.setText(simi.getAbsolutePath());
        }
    }

    public void clear2(){
        RadioTexte.setDisable(false);
        RadioImage.setDisable(false);
        RadioSon.setDisable(false);
        TextFieldSimi.clear();
        simi = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        ListResultSimi.setItems(list);
    }

    public void reloadResultFromHisto(){
        String result = ListHisto.getSelectionModel().getSelectedItem().toString().trim();
        if(!result.contains("+") && result.length() > 0 && !result.contains("/") && result.contains("--")) {
            String[] tmp = result.split(" ");
            lastresult.clear();
            for (String s : tmp) {
                String[] resultat = s.split("--");
                lastresult.add(new Resultat<String, Float>(resultat[0], Float.parseFloat(resultat[1])));
            }
            fromHisto = true;
        }
    }


    public void clear(){
        son = null;
        TextFieldMotCle.clear();
        ColorPickerImage.setValue(Color.WHITE);
        TextFieldMotCle.setDisable(false);
        ColorPickerImage.setDisable(false);
        ObservableList<String> list = FXCollections.observableArrayList();
        MiniListResult.setItems(list);
    }

    public void login(){
        if(!TextFiltersConfigSet){
            setTextFilters();
            TextFiltersConfigSet=true;
        }
        if (TextFieldLogin.getText().equals(controlAdmin.get_password())) {
            TextFieldLogin.setVisible(false);
            AnchorParam.setVisible(true);
            setCurrentConfig();
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
                list.add(r.toString().replaceAll("[^a-zA-Z0-9_.,/-]+",""));
                r.setNom(r.getNom().replaceAll("[^a-zA-Z0-9_.,/-]+",""));
            }
        }
        ListResult.setItems(list);
    }

    public void afficheResultMini(){
        Platform.runLater(
                () -> {
                    // Update UI here.
                    int maxsize;
                    ObservableList<String> list = FXCollections.observableArrayList();
                    if (lastresult.isEmpty()) {
                        list.add("Aucune recherche récente");
                    } else {
                        list.clear();
                        if (lastresult.size() > 5) {
                            maxsize = 5;
                        } else {
                            maxsize = lastresult.size();
                        }
                        int i = 0;
                        for (Resultat<String, Float> r : lastresult) {
                            if (i < maxsize) {
                                list.add(r.toString().replaceAll("[^a-zA-Z0-9_.,/-]+",""));
                                i++;
                            } else {
                                break;
                            }
                        }
                    }
                    MiniListResult.setItems(list);
                });
    }

    public void afficheResultSimi(){
        Platform.runLater(
                () -> {
                    // Update UI here.
                    int maxsize;
                    ObservableList<String> list = FXCollections.observableArrayList();
                    if (lastresult.isEmpty()) {
                        list.add("Aucune recherche récente");
                    } else {
                        list.clear();
                        if (lastresult.size() > 5) {
                            maxsize = 5;
                        } else {
                            maxsize = lastresult.size();
                        }
                        int i = 0;
                        for (Resultat<String, Float> r : lastresult) {
                            if (i < maxsize) {
                                list.add(r.toString().replaceAll("[^a-zA-Z0-9_.,/-]+",""));
                                i++;
                            } else {
                                break;
                            }
                        }
                    }
                    ListResultSimi.setItems(list);
                });
    }

    public void openResult() throws IOException {
        if (ListResult.getSelectionModel().getSelectedItem() != null) {
            if (!ListResult.getSelectionModel().getSelectedItem().toString().contains("Aucun")) {
                String path = controlAdmin.get_path();
                String fic = ListResult.getSelectionModel().getSelectedItem().toString();
                if (fic.contains(".xml")) {
                    path = path.concat("TexteTest/");
                } else if (fic.contains(".jpg")) {
                    path = path.concat("TEST_RGB/");
                } else if (fic.contains(".bmp")) {
                    path = path.concat("image_nb/");
                } else if (fic.contains(".wav")) {
                    path = path.concat("TEST_SON/");
                }
                String[] tmp = fic.split("--");
                path = path.concat(tmp[0].trim());
                Desktop desktop = Desktop.getDesktop();
                File f = new File(path);
                if (f.exists()) {
                    desktop.open(f);
                } else {
                    Label x = new Label("Une erreur s'est produite, le fichier est introuvable.\n Veuillez relancer une recherche et re-essayer." +
                            "\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
                    x.setTextFill(javafx.scene.paint.Color.PURPLE);
                    TextErrorOpenResult.getChildren().clear();
                    TextErrorOpenResult.getChildren().add(x);
                    ErrorOpenResult.setVisible(true);
                    ErrorOpenResult.setExpandableContent(null);
                }
            } else {
                Label x = new Label("Aucun fichier à ouvrir !\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
                x.setTextFill(javafx.scene.paint.Color.PURPLE);
                TextErrorOpenResult.getChildren().clear();
                TextErrorOpenResult.getChildren().add(x);
                ErrorOpenResult.setVisible(true);
                ErrorOpenResult.setExpandableContent(null);
            }
        }
    }

    public void openResult2() throws IOException {
        if (MiniListResult.getSelectionModel().getSelectedItem() != null) {
            if (!MiniListResult.getSelectionModel().getSelectedItem().toString().contains("Aucun")) {
                String path = controlAdmin.get_path();
                String fic = MiniListResult.getSelectionModel().getSelectedItem().toString();
                if (fic.contains(".xml")) {
                    path = path.concat("TexteTest/");
                } else if (fic.contains(".jpg")) {
                    path = path.concat("TEST_RGB/");
                } else if (fic.contains(".bmp")) {
                    path = path.concat("image_nb/");
                } else if (fic.contains(".wav")) {
                    path = path.concat("TEST_SON/");
                }
                String[] tmp = fic.split("--");
                path = path.concat(tmp[0].trim());
                Desktop desktop = Desktop.getDesktop();
                File f = new File(path);
                if (f.exists()) {
                    desktop.open(f);
                } else {
                    Label x = new Label("Une erreur s'est produite, le fichier est introuvable.\n Veuillez relancer une recherche et re-essayer." +
                            "\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
                    x.setTextFill(javafx.scene.paint.Color.PURPLE);
                    TextError.getChildren().clear();
                    TextError.getChildren().add(x);
                    ErrorCritere.setVisible(true);
                    ErrorCritere.setExpandableContent(null);
                }
            } else {
                Label x = new Label("Aucun fichier à ouvrir !\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
                x.setTextFill(javafx.scene.paint.Color.PURPLE);
                TextError.getChildren().clear();
                TextError.getChildren().add(x);
                ErrorCritere.setVisible(true);
                ErrorCritere.setExpandableContent(null);
            }
        }
    }
    public void openResult3() throws IOException {
        if (ListResultSimi.getSelectionModel().getSelectedItem() != null) {
            if(!ListResultSimi.getSelectionModel().getSelectedItem().toString().contains("Aucun")){
            String path = controlAdmin.get_path();
            String fic = ListResultSimi.getSelectionModel().getSelectedItem().toString();
            if (fic.contains(".xml")) {
                path = path.concat("TexteTest/");
            }else if(fic.contains(".jpg")){
                path = path.concat("TEST_RGB/");
            }else if(fic.contains(".bmp")){
                path=path.concat("image_nb/");
            }else if(fic.contains(".wav")){
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
                x.setTextFill(javafx.scene.paint.Color.PURPLE);
                TextErrorSimi.getChildren().clear();
                TextErrorSimi.getChildren().add(x);
                SimiErrorBox.setVisible(true);
                SimiErrorBox.setExpandableContent(null);
            }
        }else{
            Label x = new Label("Aucun fichier à ouvrir !\n\n\n Cliquez n'importe ou dans la fenetre pour fermer.");
            x.setTextFill(javafx.scene.paint.Color.PURPLE);
            TextErrorSimi.getChildren().clear();
            TextErrorSimi.getChildren().add(x);
            SimiErrorBox.setVisible(true);
            SimiErrorBox.setExpandableContent(null);
        }
        }
    }

    public void closeErrorResult(){
        this.ErrorOpenResult.setVisible(false);
    }

    public void closeErrorSimi(){
        this.SimiErrorBox.setVisible(false);
    }


    public void logoff(){
        TextFieldLogin.setPromptText("login admin");
        TextFieldLogin.setText("");
        TextFieldLogin.setVisible(true);
        AnchorParam.setVisible(false);
    }

    public void rechercher(){
        Thread th = null;
        if(TextFieldMotCle.getLength() > 0) {
            if((TextFieldMotCle.getText().contains("-") &&  TextFieldMotCle.getText().contains(",") || (!TextFieldMotCle.getText().contains("-")))) {
                clear2();
                    th = new Thread() {
                       public void run() {
                           ButtonRechercher.setDisable(true);
                           ButtonReset.setDisable(true);
                           ProgressIndex.setVisible(true);
                           ArrayList<CritereTexte> list = new ArrayList<>();
                           String s = TextFieldMotCle.getText();
                           ProgressIndex.setProgress(0.1);
                           String[] ss = s.split(",");
                           ProgressIndex.setProgress(0.3);
                           for (int i = 0; i < ss.length; i++) {
                               if (ss[i].charAt(0) == '+') {
                                   list.add(new CritereTexte(Polarite.PRESENT, ss[i].substring(1).trim()));
                               } else if (ss[i].charAt(0) == '-') {
                                   list.add(new CritereTexte(Polarite.ABSENT, ss[i].substring(1).trim()));
                               } else {
                                   list.add(new CritereTexte(Polarite.PRESENT, ss[i].trim()));
                               }
                           }
                           boolean critvalides = false;
                           for (CritereTexte t : list) {
                               if (t.getPolarite() == Polarite.PRESENT) {
                                   critvalides = true;
                                   break;
                               }
                           }
                           ProgressIndex.setProgress(0.6);
                           ProgressIndex.setProgress(0.7);
                           if (critvalides) {
                               lastresult.clear();
                               TreeSet<Resultat<String, Float>> tmp = bound.rechercheParCritereComplexe(list);
                               ProgressIndex.setProgress(1.0);
                               try {
                                   Thread.sleep(500);
                               } catch (InterruptedException e) {
                                   System.out.println(e);
                               }
                               if (tmp == null) {
                                   lastresult.add(new Resultat<String, Float>("Aucun_document_trouve", 0F));
                               } else {
                                   lastresult.addAll(tmp);
                                   afficheResultMini();
                               }
                               fromHisto=false;
                           } else {
                               ErrorCritere.setVisible(true);
                           }
                           ProgressIndex.setVisible(false);
                           ButtonRechercher.setDisable(false);
                           ButtonReset.setDisable(false);

                       }
                   };
                   th.start();
                Label x = new Label("ERREUR : la recherche doit au moins comporter un mot-clé PRESENT. \n\n\n" +
                        "Cliquez n'importe où dans la fenêtre pour la fermer.");
                x.setTextFill(javafx.scene.paint.Color.PURPLE);
                TextError.getChildren().clear();
                TextError.getChildren().add(x);
                ErrorCritere.setExpandableContent(null);
            }else{
                Label x = new Label("Le formatage de la recherche est incorrect.\n La saisie doit être de la forme suivante :\n " +
                        "Polarité (+ ou -) Mot\n Plusieurs critères peuvent être saisis, séparés d'une virgule.\n" +
                        "Au moins un mot-clé doit être PRESENT.\n\n\n" +
                        "Cliquez n'importe où dans la fenêtre pour la fermer.");
                x.setTextFill(javafx.scene.paint.Color.PURPLE);
                TextError.getChildren().clear();
                TextError.getChildren().add(x);
                ErrorCritere.setVisible(true);
                ErrorCritere.setExpandableContent(null);
            }
        }
        else{
            clear2();
        	//Partie Image
            new Thread(() -> {
                ButtonRechercher.setDisable(true);
                ButtonReset.setDisable(true);
                ProgressIndex.setVisible(true);
                ProgressIndex.setProgress(0.6);
                // on recupere les valeurs
                int red = (int)(255*ColorPickerImage.getValue().getRed());
                int green = (int)(255*ColorPickerImage.getValue().getGreen());
                int blue = (int)(255*ColorPickerImage.getValue().getBlue());
                //convertir en 1 valeur pour les images noir et blanc
                int val = -1;
                if(red == green && red == blue) {
                    val = red;
                }
                //apeler la recherche du boundary en passant 2 fois la meme variable
                TreeSet<Resultat<String, Float>> tmp = simuRechercheImage(val,val);
                lastresult.clear();
                if (!tmp.isEmpty()) {
                    lastresult.addAll(tmp);
                    afficheResultMini();
                } else {
                    lastresult.add(new Resultat<String, Float>("Aucun document trouvé !", 0F));
                }
                ProgressIndex.setProgress(1.0);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    System.out.println(e);
                }
                ProgressIndex.setVisible(false);
                ButtonReset.setDisable(false);
                ButtonRechercher.setDisable(false);
            }).start();
            fromHisto=false;            
        }
    }

    public void closeDialog(){
        ErrorCritere.setVisible(false);
    }

    public void rechercherSimi(){
        if(GroupRadio.getSelectedToggle() == RadioTexte && TextFieldSimi.getLength() > 0){
            clear();
            new Thread(() -> {
                ButtonRechercherSimi.setDisable(true);
                ProgressSimi.setVisible(true);
                ButtonResetSimi.setDisable(true);
                ProgressSimi.setProgress(0.6);
                TreeSet<Resultat<String, Float>> tmp = boundSimi.rechercheSimilariteTexte(TextFieldSimi.getText());
                lastresult.clear();
                if (!tmp.isEmpty()) {
                    lastresult.addAll(tmp);
                    afficheResultSimi();
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
                ButtonResetSimi.setDisable(false);
            }).start();
            fromHisto=false;
        }
        if(GroupRadio.getSelectedToggle() == RadioImage && TextFieldSimi.getLength() > 0){
            clear();
            new Thread(() -> {
                ButtonRechercherSimi.setDisable(true);
                ButtonResetSimi.setDisable(true);
                ProgressSimi.setVisible(true);
                ProgressSimi.setProgress(0.6);
                TreeSet<Resultat<String, Float>> tmp = boundSimiImage.rechercheSimilariteImage(TextFieldSimi.getText());
                lastresult.clear();
                if (!tmp.isEmpty()) {
                    lastresult.addAll(tmp);
                    afficheResultSimi();
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
                ButtonResetSimi.setDisable(false);
            }).start();
            fromHisto=false;
        }
        if(GroupRadio.getSelectedToggle() == RadioSon && TextFieldSimi.getLength() > 0){
            clear();
                new Thread(() -> {
                    ButtonRechercherSimi.setDisable(true);
                    ButtonResetSimi.setDisable(true);
                    ProgressSimi.setVisible(true);
                    ProgressSimi.setProgress(0.6);
                    TreeSet<Resultat<String, Float>> tmp = boundSon.rechercheSon(TextFieldSimi.getText());
                    lastresult.clear();
                    if (!tmp.isEmpty()) {
                        lastresult.addAll(tmp);
                        afficheResultSimi();
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
                    ButtonResetSimi.setDisable(false);
                    ButtonRechercherSimi.setDisable(false);
                }).start();
                fromHisto=false;
        }
    }

    public void setCurrentConfig(){
        String s = controlAdmin.get_settings_texte();
        String[] tab = s.split(" ");
        TextFieldConfigTexteLon.setText(tab[0]);
        TextFieldConfigTexteOcc.setText(tab[1]);
        TextFieldConfigTexteSave.setText(tab[2]);
        String i = controlAdmin.get_settings_image();
        String[] tab2 = i.split(" ");
        TextFieldConfigImageBPF.setText(tab2[0]);
        TextFieldConfigImageSeuil.setText(tab2[1]);
        String n = controlAdmin.get_settings_sound();
        String[] tab3 = n.split(" ");
        TextFieldConfigSonSample.setText(tab3[0]);
        TextFieldConfigSonInter.setText(tab3[1]);
        TextFieldConfigPath.setText(controlAdmin.get_path());
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

    public void clearConfig() {
        TextFieldConfigTexteLon.clear();
        TextFieldConfigTexteOcc.clear();
        TextFieldConfigTexteSave.clear();
        TextFieldConfigImageBPF.clear();
        TextFieldConfigImageSeuil.clear();
        TextFieldConfigSonInter.clear();
        TextFieldConfigSonSample.clear();
        TextFieldConfigMdp.clear();
        TextFieldConfigPath.clear();
        setCurrentConfig();
    }

    public void closeConfigDialog(){
        ErrorConfiguration.setVisible(false);
    }

    public void saveHisto() {
        boundSauv.recupHisto();
        sauvegarderPressed = true;

        if((!TextFieldMotCle.getText().isEmpty() || !ColorPickerImage.getPromptText().isEmpty() || !TextFieldSimi.getText().isEmpty()) && !fromHisto) {
            if(!TextFieldMotCle.getText().isEmpty()) {
                requete = TextFieldMotCle.getText();
                requete = requete.replaceAll(",","/");
            }
            else if(!TextFieldSimi.getText().isEmpty()){
                requete = TextFieldSimi.getText();
            } else {
            	requete = String.valueOf((int)(ColorPickerImage.getValue().getRed()*255)) + " " + String.valueOf((int)(ColorPickerImage.getValue().getGreen()*255)) + " " + String.valueOf((int)(ColorPickerImage.getValue().getBlue()*255));
            }
        }
        if(!lastresult.contains(new Resultat<String, Float>("Aucun_document_trouve", 0F)) && !fromHisto) {
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

    public TreeSet<Resultat<String,Float>> simuRechercheImage(int valMin, int valMax) {
    	TreeSet<Resultat<String,Float>> hash = new TreeSet<Resultat<String,Float>>(new ComparateurResultat()) ;
    	Resultat<String, Float> r1 = new Resultat<String, Float>();
    	Resultat<String, Float> r2 = new Resultat<String, Float>();
    	Resultat<String, Float> r3 = new Resultat<String, Float>();
    	Resultat<String, Float> r4 = new Resultat<String, Float>();
    	Resultat<String, Float> r5 = new Resultat<String, Float>();
    	Resultat<String, Float> r6 = new Resultat<String, Float>();
    	//Set pour la recherche de blanc
    	if (valMin == 255) {
    		r1 = new Resultat<String, Float>("51.bmp", 75.0F);
        	r2 = new Resultat<String, Float>("52.bmp", 75.0F);
        	r3 = new Resultat<String, Float>("53.bmp", 70.0F);
        	hash.add(r1);
        	hash.add(r2);
        	hash.add(r3);
    	}
    	//Set pour la recherche de noir
    	else if(valMin == 0) {
    		r1 = new Resultat<String, Float>("56.bmp", 78.0F);
        	r2 = new Resultat<String, Float>("56.bmp", 75.0F);
        	r3 = new Resultat<String, Float>("54.bmp", 72.0F);
        	r4 = new Resultat<String, Float>("57.bmp", 52.0F);
        	r5 = new Resultat<String, Float>("58.bmp", 49.0F);
        	r6 = new Resultat<String, Float>("62.bmp", 45.0F);
        	hash.add(r1);
        	hash.add(r2);
        	hash.add(r3);
        	hash.add(r4);
        	hash.add(r5);
        	hash.add(r6);
    	}
    	//Set pour la recherche de rouge (et toutes les autres recherches)
    	else {
    		r1 = new Resultat<String, Float>("16.jpg", 95.0F);
        	r2 = new Resultat<String, Float>("17.jpg", 80.0F);
        	r3 = new Resultat<String, Float>("36.jpg", 75.0F);
        	r4 = new Resultat<String, Float>("38.jpg", 50.0F);
        	r5 = new Resultat<String, Float>("42.jpg", 41.0F);
        	r6 = new Resultat<String, Float>("43.jpg", 40.0F);
        	hash.add(r1);
        	hash.add(r2);
        	hash.add(r3);
        	hash.add(r4);
        	hash.add(r5);
        	hash.add(r6);
    	}
		return hash;
    }
}
