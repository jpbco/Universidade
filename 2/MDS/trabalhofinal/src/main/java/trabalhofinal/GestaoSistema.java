package trabalhofinal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utilizador.Utilizador;

public class GestaoSistema {

	static HashMap<String, Utilizador> tableUsers;
	static HashMap<String, Assiduidade> tableAssiduidade;
	static ArrayList<Aula> listaAulas;
	static ArrayList<Aula> listaAulas2;
	static boolean quit = true;

	public static void main(String[] args) {
		tableUsers = new HashMap<String, Utilizador>();
		tableAssiduidade = new HashMap<String, Assiduidade>();
		listaAulas = new ArrayList<Aula>();
		listaAulas2 = new ArrayList<Aula>();

		importarAlunos(importData("src/main/resources/dados.json"), true);
		importarPresencas(importData("src/main/resources/presencas.json"));
		importarAulas(importData("src/main/resources/aulas.json"));

		menu();
	}

	@SuppressWarnings("resource")
	public static void menu() {
		Scanner input = new Scanner(System.in);

		int choice = 0;
		while (true) {
			System.out.println("\n\tMenu principal\n");
			System.out.print("1.) Consultar faltas por aluno\n");
			System.out.print("2.) Justificar Falta\n");
			System.out.print("3.) Importar dados dos utilizadores\n");
			System.out.print("4.) Mostrar Relatorio de Faltas\n");
			System.out.print("5.) Mostrar aulas registadas\n");
			System.out.println("6.) Exit.\n");
			System.out.print("\nIntroduza um numero->: ");

			if (input.hasNextInt())
				choice = input.nextInt();
			else
				menu();

			input.nextLine();
			switch (choice) {

			case 1:
				System.out.println("aluno id: ");
				String cartao = input.nextLine();
				System.out.println("Faltas registadas: " + (tableAssiduidade.get("001").getPresencas().size() -  ConsultarFaltasAluno(cartao, true)) );
				break;

			case 2:
				System.out.println("Inserir id de utilizador e a data e hora da falta a justificar.");
				System.out.println("Exemplo -> 001,2020-06-01,10:00");
				String s = input.nextLine();
				String[] s2 = s.split(",");
				while (s2.length < 3) {
					System.out.println("insira todos os valores");
					s = input.nextLine();
					s2 = s.split(",");
				}
				JustificarFalta(s2[0], s2[1], s2[2]);
				break;

			case 3:
				importarAlunos(importData("src/main/resources/dados.json"), false);
				System.out.println("Utilizadores importados: ");
				tableUsers.forEach((k, v) -> System.out.println(v.toString()));
				break;

			case 4:
				mostrarRelatorio();
				break;
			case 5:
				listarAulas();
				break;
			case 6:
				System.out.println("Exiting Program...");
				System.exit(0);
				break;
			default:
				System.out.println("escolha invalida!");
				break;

			}

		}

	}

	private static void mostrarRelatorio() {
		System.out.println("\n\t Relatório de Faltas:");
		System.out.println("| Utilizador | Faltas | Aulas |");
		tableUsers.forEach(
				(k, v) -> System.out.println("     " + k + "          " + (tableAssiduidade.get("001").getPresencas().size()-ConsultarFaltasAluno(k, false))
						+ "      " + tableAssiduidade.get("001").getPresencas().size()));

		
	}

	public static JSONArray importData(String local) {
		JSONParser jsonParser = new JSONParser();
		JSONArray objArray = null;
		try (FileReader reader = new FileReader(local)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			objArray = (JSONArray) obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return objArray;
	}

	@SuppressWarnings("unchecked")
	public static void importarAlunos(JSONArray userList, boolean flag) {
		userList.forEach(user -> parseUserObject((JSONObject) user, flag));
	}

	public static void parseUserObject(JSONObject user, boolean flag) {
		Utilizador temp = new Utilizador((String) user.get("nome"), (String) user.get("papel"),
				(String) user.get("cartao"));
		tableUsers.put((String) user.get("cartao"), temp);
		if (flag)
			tableAssiduidade.put((String) user.get("cartao"), new Assiduidade(temp, new ArrayList<Presenca>()));

	}

	@SuppressWarnings("unchecked")
	public static void importarPresencas(JSONArray aulaList) {
		aulaList.forEach(aula -> parsePresenca((JSONObject) aula));
	}

	@SuppressWarnings("unchecked")
	public static void importarAulas(JSONArray aulaList) {
		aulaList.forEach(aula -> parseAula((JSONObject) aula));
	}

	public static void parseAula(JSONObject aula) {
		Aula atemp = new Aula((String) aula.get("data"), (String) aula.get("hora"), (String) aula.get("disciplina"));
		listaAulas.add(atemp);
	}

	@SuppressWarnings("unchecked")
	public static void parsePresenca(JSONObject user) {
		JSONArray listaP = (JSONArray) user.get("presencas");
		listaP.forEach(presenca -> parseListaPresencas((JSONObject) presenca));
	}

	public static void parseListaPresencas(JSONObject presenca) {

		JSONObject objtemp = (JSONObject) presenca.get("utilizador");
		Presenca ptemp = new Presenca(
				new Utilizador((String) objtemp.get("nome"), (String) objtemp.get("tipo"), (String) objtemp.get("id")));
		ptemp.setFalta((Boolean) presenca.get("falta"));
		ptemp.setJustificada((Boolean) presenca.get("justificada"));
		if (tableAssiduidade.containsKey(((String) objtemp.get("id")))) {
			tableAssiduidade.get((String) objtemp.get("id")).addPresencas(ptemp);
		}

	}

	public static void parseAtualizarListaPresencas(JSONObject presenca) {

		JSONObject objtemp = (JSONObject) presenca.get("utilizador");
		Presenca ptemp = new Presenca(
				new Utilizador((String) objtemp.get("nome"), (String) objtemp.get("tipo"), (String) objtemp.get("id")));
		ptemp.setFalta((Boolean) presenca.get("falta"));
		ptemp.setJustificada((Boolean) presenca.get("justificada"));
		if (tableAssiduidade.containsKey(((String) objtemp.get("id")))) {
			tableAssiduidade.get((String) objtemp.get("id")).addPresencas(ptemp);
		}

	}

	public static int ConsultarFaltasAluno(String cartao, boolean detailed) {
		int npresencas = 0;
		int naulas = 1;

		if (tableUsers.containsKey(cartao)) {
			JSONArray arrayAulas = importData("src/main/resources/presencas.json");
			Assiduidade temp = tableAssiduidade.get(cartao);
			for (Presenca p : temp.getPresencas()) {
				JSONObject obtemp = (JSONObject) arrayAulas.get(naulas - 1);
				if (p.isFalta() && p.isJustificada()) {
					npresencas++;
					if (detailed)
						System.out.println("\tEsteve presente -> Aula: " + obtemp.get("data").toString() + ","
								+ obtemp.get("data").toString());
				}

				else if (!p.isFalta()) {
					npresencas++;

					if (detailed)
						System.out.println("\tEsteve presente -> Aula: " + obtemp.get("data").toString() + ","
								+ obtemp.get("data").toString());

				} else if (p.isFalta() && !p.isJustificada()) {
					if (detailed)
						System.out.println("\tFaltou -> Aula: " + obtemp.get("data").toString() + ","
								+ obtemp.get("data").toString());
				} else if (p.isFalta()) {
					if (detailed)
						System.out.println("\tFaltou ->Aula: " + obtemp.get("data").toString() + ","
								+ obtemp.get("data").toString());
				}

				naulas++;
			}
		} else {
			System.out.println("id nao encontrado");
		}
		if (detailed)
			System.out.println();
		return npresencas;
	}

	@SuppressWarnings("unchecked")
	public static void listarAulas() {
		JSONArray arrayAulas = importData("src/main/resources/presencas.json");
		System.out.println("Aulas registadas: ");
		arrayAulas.forEach(aula -> System.out.println("\tAula: " + ((JSONObject) aula).get("data").toString() + ","
				+ ((JSONObject) aula).get("hora").toString()));
	}

	@SuppressWarnings("unchecked")
	public static void JustificarFalta(String cartao, String data, String hora) {
		if (tableUsers.containsKey(cartao)) {
			JSONArray arrayAulas = importData("src/main/resources/presencas.json");
			for (int i = 0; i < arrayAulas.size(); i++) {
				JSONObject temp = (JSONObject) arrayAulas.get(i);
				if (temp.get("data").equals(data) && temp.get("hora").equals(hora)) {
					JSONArray arrayPresencas = (JSONArray) temp.get("presencas");
					for (int j = 0; j < arrayPresencas.size(); j++) {
						JSONObject ptemp = (JSONObject) arrayPresencas.get(j);
						JSONObject utemp = (JSONObject) ptemp.get("utilizador");
						if (utemp.get("id").equals(cartao) && (Boolean) ptemp.get("justificada") == false) {
							ptemp.replace("justificada", true);
						}
					}

				}
			}
			try (FileWriter file = new FileWriter("src/main/resources/presencas.json")) {

				file.write(arrayAulas.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
			arrayAulas = importData("src/main/resources/presencas.json");
			tableAssiduidade.forEach((k, v) -> v.setPresencas(new ArrayList<Presenca>()));
			importarPresencas(importData("src/main/resources/presencas.json"));
		} else {
			System.out.println("Utilizador nao esta registado");
		}

	}

	public static void AceitarJustificacoes(String cartao, String data, String hora) {

	}

	public static void MostrarRelatorio() {

	}
}
