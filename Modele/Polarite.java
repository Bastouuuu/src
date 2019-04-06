package Modele;
public enum Polarite {
	ABSENT("-"), PRESENT("+");
	
	private String sign = "";
	
	private Polarite(String sign){
		this.sign = sign;
	}
	
	@Override
	public String toString(){
		return sign;
	}
}