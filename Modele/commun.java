/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package Modele;

public class commun {
  public static int file_exists(String inode, String nomfic, String nombase) {
    return communJNI.file_exists(inode, nomfic, nombase);
  }

  public static int fileExists(String ptrFile) {
    return communJNI.fileExists(ptrFile);
  }

  public static void init_config(SWIGTYPE_p_Config tab) {
    communJNI.init_config(SWIGTYPE_p_Config.getCPtr(tab));
  }

  public static String init_path() {
    return communJNI.init_path();
  }

  public static String get_password() {
    return communJNI.get_password();
  }

  public static String get_config_texte() {
    return communJNI.get_config_texte();
  }

  public static void edit_path(String arg0) {
    communJNI.edit_path(arg0);
  }

  public static void new_password(String arg0) {
    communJNI.new_password(arg0);
  }

  public static String config(int a) {
    return communJNI.config(a);
  }

  public static void encrypt2(String s) {
    communJNI.encrypt2(s);
  }

  public static void decrypt(String s) {
    communJNI.decrypt(s);
  }

  public static void edit_settings_text(int arg0, int arg1, int arg2) {
    communJNI.edit_settings_text(arg0, arg1, arg2);
  }

  public static void edit_settings_image(int arg0, int arg1) {
    communJNI.edit_settings_image(arg0, arg1);
  }

  public static void edit_settings_sound(int arg0, int arg1) {
    communJNI.edit_settings_sound(arg0, arg1);
  }

  public static void test_setup() {
    communJNI.test_setup();
  }

  public static String get_config_image() {
    return communJNI.get_config_image();
  }

  public static String get_config_son() {
    return communJNI.get_config_son();
  }

  public static String get_path() {
    return communJNI.get_path();
  }

}
