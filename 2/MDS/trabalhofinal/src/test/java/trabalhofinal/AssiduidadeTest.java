package trabalhofinal;

import org.junit.jupiter.api.*;
import org.junit.Rule;
import utilizador.Utilizador;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

public class AssiduidadeTest {

	Assiduidade a;

	@Mock
	Utilizador user;
	Presenca presenca;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@BeforeEach
	public void setUp() {
		a = new Assiduidade(null, null);
	}

	@AfterEach
	public void tearDown() {
		a = null;
	}

	@Test
	public void getUser() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		a.setUser(user);
		a = new Assiduidade(user, null);

		assertEquals("docente", a.getUser().getEmail());
		assertEquals("James", a.getUser().getNome());
		assertEquals("007", a.getUser().getId());

	}

	@Test
	public void setUser() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		a.setUser(user);

		assertEquals("docente", user.getEmail());
		assertEquals("James", user.getNome());
		assertEquals("007", user.getId());

	}

	@Test
	public void setPresencas() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		presenca = mock(Presenca.class);
		when(presenca.getUtilizador()).thenReturn(user);

		ArrayList<Presenca> lista = new ArrayList<Presenca>();
		lista.add(presenca);
		a.setPresencas(lista);

		assertEquals(lista, a.getPresencas());
	}

	@Test
	public void getPresencas() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		presenca = mock(Presenca.class);
		when(presenca.getUtilizador()).thenReturn(user);

		ArrayList<Presenca> lista = new ArrayList<Presenca>();
		lista.add(presenca);

		a = new Assiduidade(user, lista);
		assertEquals(lista, a.getPresencas());
	}

	@Test
	public void addPresencas() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		presenca = mock(Presenca.class);
		when(presenca.getUtilizador()).thenReturn(user);

		a = new Assiduidade(user, new ArrayList<Presenca>());
		a.addPresencas(presenca);
		assertEquals(presenca, a.getPresencas().get(0));
	}

}
