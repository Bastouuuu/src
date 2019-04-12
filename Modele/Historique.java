package Modele;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.io.File;

public class Historique {
	
	
	private HashMap<String, String> hashReqRes = new HashMap<>();
	
	/* Constructeur privé */
	private Historique(){}
	
	/* Holder */
	private static class HistoriqueHolder{
		/* Instance unique non préinitialisée */
		private final static Historique instance = new Historique() ; 
	}
	
	/* Point d'accès pour l'instance unique du singleton */
	public static Historique getInstance() {
		
		return HistoriqueHolder.instance ; 
	}

	public void ajoutHistorique(String liste, TreeSet<Resultat<String, Float>> tree) {
		
		if(!tree.isEmpty()) {
			String s = "";
			for (Resultat<String, Float> resultat : tree) {
				s+=resultat.toString() ;
			}
			hashReqRes.put(liste, s);
		}
	}
	
	public void ecrireHistoDansFichier() {

		
		if(!hashReqRes.isEmpty()) {
			
			try {
				FileWriter writer = new FileWriter("historique.txt", false);
				writer.write(hashReqRes.toString()+"\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void recupHisto() {

	    String line="";
	    BufferedReader reader;
	    
	    File file = new File("historique.txt");
	    try {
			boolean b = file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    
		try {
			reader = new BufferedReader(new FileReader("historique.txt"));
			try {
				while ((line = reader.readLine()) != null)
				{	
					line = new StringBuilder(line).deleteCharAt(0).toString();
					line = new StringBuilder(line).deleteCharAt(line.length()-1).toString();
				    String[] parts = line.split(",");
				    for(String tmp : parts){
				    	String[] result = tmp.split("=");
				    	hashReqRes.put(result[0],result[1]);
					}
				}
			    reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	}
	
	public void supprimerHisto() {
		hashReqRes.clear();
		try {
			PrintWriter writer = new PrintWriter("historique.txt");
			writer.print("");
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void removeUneReq(String clef) {
		hashReqRes.remove(clef);
	}
	
	public HashMap<String, String> getHash(){
		return hashReqRes;
	}

	

}
