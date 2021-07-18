package login;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Scanner;
import java.util.HashMap;
import trabalhofinal.GestaoSistema;

public class Login {


	private HashMap<String, User> table;
	static boolean flag;


	public Login() {
		this.table = new HashMap<String, User>();
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Login l = new Login();
		Scanner scan = new Scanner(System.in);
		JSONArray listLogin = GestaoSistema.importData("src/main/resources/login_data.json");

		listLogin.forEach(user -> l.parseUserObject((JSONObject) user));
		String id = "";
		String pw = "";
		flag = false;
		while (!flag) {
			System.out.print("Insira o ID: ");
			id = scan.nextLine();
			System.out.print("Insira a Password: ");
			pw = scan.nextLine();
			flag = l.validar(id, pw);

		}
		scan.close();
	}

	public void parseUserObject(JSONObject user) {
		table.put((String) user.get("id"),
				new User((String) user.get("nome"), (String) user.get("id"), (String) user.get("password")));
	}

	public boolean validar(String id, String password) {
		if (table.containsKey(id) && !table.get(id).getPassword().equals(password)) {
			System.out.println("Password errada.\nTente outra vez.\n");
		} else if (table.containsKey(id) && table.get(id).getPassword().equals(password)) {
			System.out.println("Bem vindo.");
			return true;
		} else {
			System.out.println("ID invalido\nTente outra vez.\n");
		}

		return false;
	}

	public HashMap<String, User> getTable(){
		return table;
	}
}
