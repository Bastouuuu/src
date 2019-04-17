package sample;
import Boundary.BoundarySauvegardeHistorique;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    Controller c = new Controller();



    @Override
    public void start(Stage primaryStage) throws Exception{
    	System.load("/Users/bast/Downloads/FilRougeV3/commun.dylib");
    	System.load("/Users/bast/Downloads/FilRougeV3/texte.dylib");
    	System.load("/Users/bast/Downloads/FilRougeV3/setup.dylib");
    	System.load("/Users/bast/Downloads/FilRougeV3/son.dylib");
    	System.load("/Users/bast/Downloads/FilRougeV3/image_nb.dylib");
        Parent root = FXMLLoader.load(getClass().getResource("Ariane'sThread.fxml"));
        c.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Ariane's Thread");
        Scene s = new Scene(root,640,680);
        primaryStage.setScene(s);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
    	BoundarySauvegardeHistorique.recupHisto();
        launch(args);
        BoundarySauvegardeHistorique.ecrireHistoDansFichier();
    }
    
    
}
