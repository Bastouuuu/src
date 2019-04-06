package Modele;
public class CritereTexte extends Critere {

	private String motCle;

	public CritereTexte(Polarite polarite, String motCle) {
		super(polarite);
		this.motCle = motCle;
	}

	public String getMotCle() {
		return motCle;
	}

	@Override
	public String toString() {
		return "CritereTexte [" + super.toString() + ", motCle=" + this.motCle + "]";
	}
}
