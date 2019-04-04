package Modele;
public class CritereImage extends Critere {

	private int valMin;
	private int valMax;
	
	public CritereImage(Polarite polarite, int valMin, int valMax) {
		super(polarite);
		this.valMin = valMin;
		this.valMax = valMax;
	}

	public int getValMin() {
		return valMin;
	}

	public int getValMax() {
		return valMax;
	}

	@Override
	public String toString() {
		return "CritereImage [" + super.toString() + ", valMin=" + this.valMin + ", valMax=" + this.valMax + "]";
	}
	
	
}
