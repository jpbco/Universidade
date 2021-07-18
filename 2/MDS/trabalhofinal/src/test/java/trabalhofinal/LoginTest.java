package trabalhofinal;

import org.junit.jupiter.api.*;
import org.junit.Rule;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import login.*;

public class LoginTest {
	Login login;

	@Mock
	User user;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@BeforeEach
	public void setUp() {
		login = new Login();
	}

	@AfterEach
	public void tearDown() {
		login = null;
	}

    @Test
    public void testValidar(){
        user = mock(User.class);
        when(user.getNome()).thenReturn("Frank");
        when(user.getId()).thenReturn("001");
        when(user.getPassword()).thenReturn("Super&Secure&Password");
        login.getTable().put("001", user);

        assertEquals(true, login.validar(user.getId(),user.getPassword()));
    }

    @Test
    public void testValidarFalha(){
        user = mock(User.class);
        when(user.getNome()).thenReturn("Frank");
        when(user.getId()).thenReturn("001");
        when(user.getPassword()).thenReturn("Super&Secure&Password");
        login.getTable().put("001", user);

        assertNotEquals(true, login.validar(user.getId(),"Alohomora"));
    }

    @Test
    public void testGetTable(){
        user = mock(User.class);
        when(user.getNome()).thenReturn("Frank");
        when(user.getId()).thenReturn("001");
        when(user.getPassword()).thenReturn("Super&Secure&Password");
        login.getTable().put(user.getId(), user);
        HashMap<String, User> tabela = new HashMap<String, User>();
        tabela.put(user.getId(),user);

        assertEquals(tabela,login.getTable());
    }

}
