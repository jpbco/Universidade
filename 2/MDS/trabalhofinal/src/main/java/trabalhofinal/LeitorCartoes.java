package trabalhofinal;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import utilizador.Utilizador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class LeitorCartoes {

	HashMap<String, Utilizador> table;
	Aula aula;
	ArrayList<Presenca> lidos;
	static boolean check = false;
	public LeitorCartoes(Aula aula) {
		this.aula = aula;
		this.lidos = new ArrayList<Presenca>();
	}

	public static void main(String[] args) {

		// Cria nova aula
		Scanner scan = new Scanner(System.in);
		JSONArray listAulas = importData("src/main/resources/aulas.json");
		System.out.println("Aulas marcadas no calendário:");
		int c = 1;
		for (int i = 0; i < listAulas.size(); i++) {
			System.out.println(c+ ": " + listAulas.get(i).toString());
			c++;
		}
		System.out.println("\nEscolha a aula para a qual o leitor ira registar as presencas.");
		System.out.println("\"exit\" para terminar a execução do leitor.");
		System.out.print("-> ");
		int aulan = scan.nextInt() - 1;
		scan.nextLine();
		while(aulan>9 || aulan <0) {
			System.out.println("Por favor escolha uma aula da lista.");
			aulan = scan.nextInt() - 1;
			scan.nextLine();
		}
		JSONObject objAula = (JSONObject) listAulas.get(aulan);
		Aula aula = new Aula(objAula.get("data").toString(), objAula.get("hora").toString(),
				objAula.get("disciplina").toString());
		// Cria o leitor 
		LeitorCartoes leitor = new LeitorCartoes(aula);
		leitor.table = new HashMap<String, Utilizador>();
		leitor.importarAlunos(importData("src/main/resources/dados.json")); // carrega ficheiro json dos alunos num

		// Leitura dos cartoes
		String temp = "";

		while (!temp.equals("exit")) {
			System.out.println("Insira o id do utilizador: ");
			temp = scan.nextLine();
			leitor.validar(temp, aula); // valida o cartao
		}
		leitor.atualizarDados(aula);
		scan.close();
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

	public void parseUserObject(JSONObject user) {

		Utilizador temp = new Utilizador((String) user.get("nome"), (String) user.get("papel"),
				(String) user.get("cartao"));
		table.put((String) user.get("cartao"), temp);

	}

	@SuppressWarnings("unchecked")
	public void importarAlunos(JSONArray userList) {
		userList.forEach(user -> parseUserObject((JSONObject) user));
	}

	public void validar(String string, Aula aula) {
		if (table.containsKey(string)) { // se o aluno estiver no ficheiro json
			Utilizador temp = table.get(string);
			System.out.println("leitura: " + temp.getNome() +" " +temp.getEmail() + " " +temp.getId());
			Presenca ptemp = new Presenca(new Utilizador(table.get(string).getNome(), table.get(string).getEmail(),
					table.get(string).getId()));
			if(!lidos.contains(ptemp))
				lidos.add(ptemp);
		} else {
			if (string.equals("exit"))
				System.out.println("Leitor terminou");
			else
				System.out.println("cartao invalido");
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void atualizarDados(Aula aula) {

		JSONArray listaAulas = importData("src/main/resources/presencas.json");
		JSONArray listaPresencas = new JSONArray();
		JSONObject aulaDetails = new JSONObject();
		aulaDetails.put("data", aula.getData());
		aulaDetails.put("hora", aula.getHora());
		aulaDetails.put("disciplina", aula.getDisciplina());

		for (Presenca p : lidos) {
			JSONObject presencatemp = new JSONObject();
			JSONObject userTemp = new JSONObject();
			userTemp.put("nome", p.getUtilizador().getNome());
			userTemp.put("tipo", p.getUtilizador().getEmail());
			userTemp.put("id", p.getUtilizador().getId());

			presencatemp.put("utilizador", userTemp);
			presencatemp.put("falta", false);
			presencatemp.put("justificada", false);
			if(!listaPresencas.contains(presencatemp))
				listaPresencas.add(presencatemp);
		}

		ArrayList<Utilizador> users = new ArrayList<Utilizador>(table.values());
		for (Utilizador user : users) {
			JSONObject presencatemp = new JSONObject();
			JSONObject userTemp = new JSONObject();
			Presenca ptemp = new Presenca(user);

			userTemp.put("nome", user.getNome());
			userTemp.put("tipo", user.getEmail());
			userTemp.put("id", user.getId());

			presencatemp.put("utilizador", userTemp);
			presencatemp.put("falta", true);
			presencatemp.put("justificada", ptemp.isJustificada());

			JSONObject presencatemp1 = new JSONObject(presencatemp);
			presencatemp1.replace("falta", false);
			if (!listaPresencas.contains(presencatemp1)) {
				listaPresencas.add(presencatemp);
			}

		}

		aulaDetails.put("presencas", listaPresencas);
		if(!listacontem(aula, listaAulas)) {
			listaAulas.add(aulaDetails);
			check = false;
		}

		

		// Write JSON file
		try (FileWriter file = new FileWriter("src/main/resources/presencas.json")) {

			file.write(listaAulas.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean listacontem(Aula aula, JSONArray lista) {
		lista.forEach(aula1 -> parseListaAulas((Aula) aula, (JSONObject) aula1));
		return check;
		
	}

	private static void parseListaAulas(Aula aula, JSONObject aula2) {
		String data = (String )aula2.get(("data"));
		String hora = (String )aula2.get(("hora"));
		if(aula.getData().equals(data) && aula.getHora().equals(hora) )
		check = true;
		else
			check = false;
	}
}
