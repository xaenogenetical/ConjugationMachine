package conjmachine;

public class Tense {

	public static final String[] povs = new String[] { "yo", "tu", "el", "nosotros", "ellos" };

	private String name;

	private String[] conjugations;

	public Tense(String name, String[] conj) {
		this.name = name;
		conjugations = conj;
	}

	public String getConj(String conj) {
		for (int i = 0; i < povs.length; i++) {
			if (povs[i].equals(conj)) {
				return conjugations[i];
			}
		}
		return "CONJUGATION NOT FOUND";
	}

	public String toString() {
		return name;
	}

}
