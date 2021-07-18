package trabalhofinal;

import org.junit.jupiter.api.*;
import org.junit.Rule;
import utilizador.Utilizador;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;

public class PresencaTest {

	Presenca presenca;

	@Mock
	Utilizador user;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@BeforeEach
	public void setUp() {
		presenca = new Presenca(null);
	}

	@AfterEach
	public void tearDown() {
		presenca = null;
	}

	@Test
	public void setUtilizadorr() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		presenca.setUtilizador(user);

		assertEquals("docente", user.getEmail());
		assertEquals("James", user.getNome());
		assertEquals("007", user.getId());

	}

	@Test
	public void setFalta() {
		presenca.setFalta(false);
		assertEquals(false, presenca.isFalta());
	}

	@Test
	public void setJustificada() {
		presenca.setJustificada(true);
		assertEquals(true, presenca.isJustificada());
	}

	@Test
	public void getUtilizador() {
		user = mock(Utilizador.class);
		when(user.getId()).thenReturn("007");
		when(user.getNome()).thenReturn("James");
		when(user.getEmail()).thenReturn("docente");

		presenca.setUtilizador(user);
		presenca = new Presenca(user);

		assertEquals("docente", presenca.getUtilizador().getEmail());
		assertEquals("James", presenca.getUtilizador().getNome());
		assertEquals("007", presenca.getUtilizador().getId());

	}

	@Test
	public void isFalta() {
		assertEquals(true, presenca.isFalta());
	}

	@Test
	public void isJustificada() {
		assertEquals(false, presenca.isJustificada());
	}

}
