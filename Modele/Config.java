/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package Modele;

public class Config {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Config(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Config obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        communJNI.delete_Config(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setNum(String value) {
    communJNI.Config_num_set(swigCPtr, value);
  }

  public String getNum() {
    return communJNI.Config_num_get(swigCPtr);
  }

  public Config() {
    this(communJNI.new_Config(), true);
  }

}
