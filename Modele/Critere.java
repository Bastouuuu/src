package Modele;
public abstract class Critere {
	
	private Polarite polarite;

	public Critere(Polarite polarite) {
		this.polarite = polarite;
	}

	public Polarite getPolarite() {
		return this.polarite;
	}

	@Override
	public String toString() {
		return "polarite=" + this.polarite;
	}
}
