package login;

public class User {
    private String nome;
	private String id;
	private String password;

		public User(String nome, String id, String password){
			this.nome = nome;
			this.id = id;
			this.password = password;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
        
}
