package Modele;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

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
				FileWriter writer = new FileWriter("historique.txt", true);
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
		hashReqRes.clear();

	    String line="";
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("historique.txt"));
			try {
				while ((line = reader.readLine()) != null)
				{	
					line = new StringBuilder(line).deleteCharAt(0).toString();
					line = new StringBuilder(line).deleteCharAt(line.length()-1).toString();
				    String[] parts = line.split("=", 2);
				    if (parts.length == 2)
				    {
				        String key = parts[0];
				        String value = parts[1];
				        hashReqRes.put(key, value);
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
		
		//System.out.println(hashReqRes.toString());
	    
	}
	
	public void removeUneReq(String clef) {
		hashReqRes.remove(clef);
	}
	
	public HashMap<String, String> getHash(){
		return hashReqRes;
	}
	

}
