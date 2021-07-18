package trabalhofinal;

import static org.junit.Assert.assertEquals;
import login.User;
import org.junit.jupiter.api.*;

public class UserTest {

	User user;

	@BeforeEach
	public void setUp() {
		user = new User(null, null, null);
	}

	@AfterEach
	public void tearDown() {
		user = null;
	}

	@Test
	public void testSetPassword() {
		user.setPassword("Super&Secure&Password");
		assertEquals("Super&Secure&Password", user.getPassword());
	}

	@Test
	public void testSetId() {
		user.setId("001");
		assertEquals("001", user.getId());
	}

	@Test
	public void testSetNome() {
		user.setNome("Frank");
		assertEquals("Frank", user.getNome());
	}
	
	@Test
	public void testGetPassword() {
		user = new User(null,null,"Super&Secure&Password");
		assertEquals("Super&Secure&Password", user.getPassword());
	}

	@Test
	public void testGetId() {
		user = new User(null,"001",null);
		assertEquals("001", user.getId());
	}

	@Test
	public void testGetNome() {
		user = new User("Frank",null,null);
		assertEquals("Frank", user.getNome());
	}



}
