package Modele;

import Controleur.ControlleurIndexation;

public class ThreadIndexation extends Thread {

		private ControlleurIndexation control;
		
		public ThreadIndexation(String s, ControlleurIndexation c) {
			super(s);
			this.control =c;
		}
		
		public void run() {
			switch(this.getName()) {
			case "Texte" : control.lancerIndexationTexte(); break;
			case "Image" : control.lancerIndexationImage(); break;
			case "Son" : System.out.println("IndexSon"); break;
			default : System.out.println("Non reconnu");
			}
		}
}
