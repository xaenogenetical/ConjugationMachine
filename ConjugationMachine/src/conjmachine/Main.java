package conjmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		source = new File(System.getProperty("user.dir") + "\\src\\conjmachine\\Resources\\Vebz.txt");
		input = new Scanner(System.in);
		vz = new Scanner(source);
		initVerbs();
		boolean loop = true;
		while (loop) {
			System.out.println("Welcome to the Conjugation Machine!!");
			System.out.println("You have three options:");
			System.out.println("1: Quiz Mode");
			System.out.println("2: Manual Mode");
			System.out.println("3: Exit");
			System.out.println("What is your choice? (type \"INFO\" to learn what the options mean)");
			String resp = input.nextLine();
			switch (resp.trim().toLowerCase()) {
			case "3" -> {
				input.close();
				return;
			}
			case "info" -> {
				System.out.println(
						"Quiz Mode uses the pre-determined list of verbs and \nasks you to provide a random conjugation of a verb randomly \nselected from the list,");
				System.out.println(
						"\nManual Mode allows you to pick a specific verb and conjugation \nto check if you know it.");
				System.out.println("\n");
				enter();
				System.out.println();
			}
			case "1" -> quizMode(input);
			case "2" -> manualMode(input);
			default -> System.out.println("Please enter only 1, 2, 3, or INFO\n");
			}
		}
		input.close();
	}

	private static void enter() {
		System.out.println("Press enter to continue");
		@SuppressWarnings("unused")
		String waiter = input.nextLine();
	}

	private static void manualMode(Scanner input) {
		System.out.println("Beginning Manual Mode");
		while (true) {
			System.out.println("Enter the verb you want, or enter EXIT to return to the main menu.");
			String verb = input.nextLine();
			if (verb.trim().toLowerCase().equals("exit")) {
				System.out.println();
				break;
			}
			Verb selection = null;
			for (Verb v : verbs) {
				if (v.toString().toLowerCase().equals(verb.toLowerCase().trim())) {
					selection = v;
					break;
				}
			}
			if (selection == null) {
				System.out.println("That verb is not in the list. \nPlease choose one from the preexisting list.\n");
			} else {
				System.out.println(
						"Enter the tense and perspective you want to test, \nseparated by a space. (Ex. pasado ellos)");
				String t = input.next();
				if (t.trim().toLowerCase().equals("exit")) {
					break;
				}
				String p = input.next();
				if (p.trim().toLowerCase().equals("exit")) {
					break;
				}
				input.nextLine();
				String key = selection.getConj(t, p);
				while (true) {
					if (key.equals("TENSE NOT FOUND") || key.equals("CONJUGATION NOT FOUND")) {
						System.out.println(
								"There is no conjugation in this quiz that exists \nfor those parameters, try again.");
						break;
					}
					System.out.println("What is the proper conjugation for " + selection + " in the tense \n" + t
							+ " when used in the perspective of \"" + p + "\"?");
					if (input.nextLine().trim().toLowerCase().equals(key)) {
						System.out.println("Correct! Good Job!");
						enter();
						break;
					} else {
						System.out.println("Incorrect. Would you like to try again? (y/n)");
						if (input.nextLine().equals("n")) {
							System.out.println("The correct answer was " + key);
							enter();
							break;
						}
					}
				}
			}
		}
	}

	private static void quizMode(Scanner input) {
		System.out.println("Beginning Quiz Mode");
		System.out.println(
				"At any point, enter EXIT to end quiz mode. \nYour score will be saved until you end the program.");
		System.out.println("Press enter to begin!");
		@SuppressWarnings("unused")
		String waiter = input.nextLine();
		System.out.println();
		while (true) {
			// I know you're looking at this section second-guessing if I know what I'm
			// doing,
			// But the computer refuses to give me an actually random distribution of
			// numbers unless I do it like this
			// I think it hates me
			double num = Math.random() * verbs.length;
			// System.out.println(num);
			int verbNum = (int) num;
			// System.out.println(verbNum);
			Verb q = verbs[verbNum];
			String[] key = q.getRand();
			String ans = key[2];
			System.out.println("Current score: " + quizScore + "/" + quizQs);
			quizQs++;
			System.out.println("What is the proper conjugation for " + q + "\nin the tense " + key[0]
					+ "\nin the form of " + key[1] + "?");
			String resp = input.nextLine().trim().toLowerCase();
			if (resp.equals("exit")) {
				System.out.println();
				break;
			} else if (resp.equals(ans)) {
				quizScore++;
				System.out.println("Correct! Good Job!");
				System.out.println("Press enter to continue");
				String e = input.nextLine();
				if (e.trim().toLowerCase().equals("exit")) {
					break;
				}
				System.out.println();
			} else {
				System.out.println("Womp womp");
				System.out.println("The correct answer was " + ans);
				String e = input.nextLine();
				if (e.trim().toLowerCase().equals("exit")) {
					break;
				}
				System.out.println();
			}
		}
	}

	public static int quizScore = 0;
	public static int quizQs = 0;
	public static File source;
	public static Scanner vz;
	public static Scanner input;
	public static Verb[] verbs = new Verb[0];

	@SuppressWarnings("ManualArrayToCollectionCopy")
	public static void addVerb(Verb v) {
		Verb[] newVerbs = new Verb[verbs.length + 1];
		for (int i = 0; i < verbs.length; i++) {
			newVerbs[i] = verbs[i];
		}
		newVerbs[newVerbs.length - 1] = v;
		verbs = newVerbs;
	}

	public static Verb grabNextVerb(Scanner vz) {
		String name = vz.next();
		String token = vz.next();
		while (token.charAt(token.length() - 1) != '"') {
			token += " " + vz.next();
		}
		String def = token;
		vz.next();
		String[] pres = new String[] { vz.next(), vz.next(), vz.next(), vz.next(), vz.next() };
		vz.next();
		String[] pas = new String[] { vz.next(), vz.next(), vz.next(), vz.next(), vz.next() };
		vz.next();
		String[] imp = new String[] { vz.next(), vz.next(), vz.next(), vz.next(), vz.next() };
		vz.next();
		String[] fut = new String[] { vz.next(), vz.next(), vz.next(), vz.next(), vz.next() };
		vz.next();
		String[] subj = new String[] { vz.next(), vz.next(), vz.next(), vz.next(), vz.next() };
		Tense presente = new Tense("presente", pres);
		Tense pasado = new Tense("pasado", pas);
		Tense imperf = new Tense("imperfecto", imp);
		Tense futuro = new Tense("futuro", fut);
		Tense sub = new Tense("subjunctivo", subj);
		Tense[] tenses = new Tense[] { presente, pasado, imperf, futuro, sub };
		return new Verb(name, def, tenses);
	}

	public static void initVerbs() throws FileNotFoundException {

		while (vz.hasNext()) {
			addVerb(grabNextVerb(vz));
		}
		vz.close();
	}

}
