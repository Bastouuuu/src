/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package Modele;

public class texte implements texteConstants {
  public static void ignore_balise(String str) {
    texteJNI.ignore_balise(str);
  }

  public static void ignore_commas(String str) {
    texteJNI.ignore_commas(str);
  }

  public static void traitement_file(String nom) {
    texteJNI.traitement_file(nom);
  }

  public static SWIGTYPE_p_Cellule init_PILE() {
    long cPtr = texteJNI.init_PILE();
    return (cPtr == 0) ? null : new SWIGTYPE_p_Cellule(cPtr, false);
  }

  public static int PILE_estVide(SWIGTYPE_p_Cellule p) {
    return texteJNI.PILE_estVide(SWIGTYPE_p_Cellule.getCPtr(p));
  }

  public static SWIGTYPE_p_Cellule emPILE(SWIGTYPE_p_Cellule p, String s, int i, int nb) {
    long cPtr = texteJNI.emPILE(SWIGTYPE_p_Cellule.getCPtr(p), s, i, nb);
    return (cPtr == 0) ? null : new SWIGTYPE_p_Cellule(cPtr, false);
  }

  public static void ignore_etoile(String str) {
    texteJNI.ignore_etoile(str);
  }

  public static void indexer() {
    texteJNI.indexer();
  }

  public static String recherchecrit(String arg0) {
    return texteJNI.recherchecrit(arg0);
  }

  public static String recherchesimi(String arg0) {
    return texteJNI.recherchesimi(arg0);
  }

  public static int pathFileExists(String arg0) {
    return texteJNI.pathFileExists(arg0);
  }

  public static SWIGTYPE_p_File init_FILE(SWIGTYPE_p_File f) {
    return new SWIGTYPE_p_File(texteJNI.init_FILE(SWIGTYPE_p_File.getCPtr(f)), true);
  }

  public static String affiche_FILE(SWIGTYPE_p_File f) {
    return texteJNI.affiche_FILE(SWIGTYPE_p_File.getCPtr(f));
  }

  public static int est_Vide_FILE(SWIGTYPE_p_File f) {
    return texteJNI.est_Vide_FILE(SWIGTYPE_p_File.getCPtr(f));
  }

  public static SWIGTYPE_p_File enfiler_FILE(int i, int n, SWIGTYPE_p_File f) {
    return new SWIGTYPE_p_File(texteJNI.enfiler_FILE(i, n, SWIGTYPE_p_File.getCPtr(f)), true);
  }

  public static void setNotempty1(SWIGTYPE_p_stat value) {
    texteJNI.notempty1_set(SWIGTYPE_p_stat.getCPtr(value));
  }

  public static SWIGTYPE_p_stat getNotempty1() {
    return new SWIGTYPE_p_stat(texteJNI.notempty1_get(), true);
  }

  public static void setNotempty2(SWIGTYPE_p_stat value) {
    texteJNI.notempty2_set(SWIGTYPE_p_stat.getCPtr(value));
  }

  public static SWIGTYPE_p_stat getNotempty2() {
    return new SWIGTYPE_p_stat(texteJNI.notempty2_get(), true);
  }

}
