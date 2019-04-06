package Modele;

import java.util.Comparator;

public class ComparateurPolarite implements Comparator<Critere>{
	@Override
	public int compare(Critere o1, Critere o2) {
		if(o1.getPolarite() == o2.getPolarite()) {
			return 0;
		}else if(o1.getPolarite() == Polarite.PRESENT) {
			return -1;
		}else {
			return 1;
		}
	}
}
