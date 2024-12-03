package conjmachine;

public class Verb {

	public static final String[] tenseNames = new String[] { "presente", "pasado", "imperfecto", "futuro",
			"subjuntivo" };

	private String name, transl;

	private Tense[] tenses;

	public Verb(String name, String transl, Tense[] tense) {
		this.name = name;
		this.transl = transl;
		tenses = tense;

	}

	public String getMeaning() {
		return transl;
	}

	public String getConj(String tense, String pov) {
		for (int i = 0; i < tenseNames.length; i++) {
			if (tenseNames[i].equals(tense)) {
				return tenses[i].getConj(pov);
			}
		}
		return "TENSE NOT FOUND";
	}

	public String[] getRand() {
		int rand1 = (int) (Math.random() * tenseNames.length);
		int rand2 = (int) (Math.random() * Tense.povs.length);
		return new String[] { tenseNames[rand1], Tense.povs[rand2], getConj(tenseNames[rand1], Tense.povs[rand2]) };
	}

	public String toString() {
		return name;
	}

}
