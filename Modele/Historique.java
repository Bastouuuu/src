package Modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** Singleton contenant la HashMap (qui contient les couples requête/résultats) et les méthodes pour l'historique.
 * @author Omar Hilmi
 *
 */

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
	
	/**
    Ajouter un couple requête/résultats dans la HashMap.
    @param liste La requête en chaîne de caractères.
    @param tree Les résultats de la requête en TreeSet.
	 */
	public void ajoutHistorique(String liste, TreeSet<Resultat<String, Float>> tree) {
		
		if(!tree.isEmpty()) {
			String s = "";
			for (Resultat<String, Float> resultat : tree) {
				s+=resultat.toString() ;
			}
			hashReqRes.put(liste, s);
		}
	}


	/**Convertit la HashMap contenant les couples requête/résultats en une chaîne de caractère.
	 * @param map La HashMap contenant les couples requête/résultats. 
	 * @return Une chaîne de caractères contenant tous les résultats selon un certain formatage.
	 * @author Bastien Corrge
	 */
	public static <K, V> String mapToString(Map<K, V> map) {
		return map.entrySet()
				.stream()
				.map(
						entry -> (entry.getKey() == map ? "(this Map)" : entry.getKey())
								+ "="
								+ (entry.getValue() == map ? "(this Map)" : entry.getValue()))
				.collect(Collectors.joining("; ", "{", "}"));
	}


	/**
	 * Ecrit dans le fichier historique.txt l'ensemble de la HashMap convertie en String dans le but de sauvegarder l'historique.
	 * @author Omar Hilmi
	 */
	public void ecrireHistoDansFichier() {

		
		if(!hashReqRes.isEmpty()) {
			
			try {
				FileWriter writer = new FileWriter("historique.txt", false);
				writer.write(mapToString(hashReqRes)+"\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 Lit dans le fichier historique.txt l'ensemble de l'historique et le stocke dans la HashMap.
	 @author Omar Hilmi 
	 */
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
				    String[] parts = line.split(";");
				    for(String tmp : parts){
				    	String[] result = tmp.split("=");
				    	hashReqRes.put(result[0].trim(),result[1].trim());
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
	
	/**
	 Vide la HashMap contenant l'historique et vide le fichier historique.txt.
	 @author Omar Hilmi
	 */
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
	
	/**
	 * Supprime un seul couple requête/resultats identifié par sa clef.
	 * @param clef est la requête du couple à supprimer. 
	 * @author Omar Hilmi
	 */
	public void removeUneReq(String clef) {
		hashReqRes.remove(clef);
	}
	
	public HashMap<String, String> getHash(){
		return hashReqRes;
	}

	

}
