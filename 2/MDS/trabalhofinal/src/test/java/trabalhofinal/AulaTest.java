package trabalhofinal;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.*;

public class AulaTest {

	Aula aula;

	@BeforeEach
	public void setUp() {
		aula = new Aula(null, null, null);
	}

	@AfterEach
	public void tearDown() {
		aula = null;
	}

	@Test
	public void testSetData() {
		aula.setData("2020-06-01");
		assertEquals("2020-06-01", aula.getData());
	}

	@Test
	public void testSetHora() {
		aula.setHora("10:00");
		assertEquals("10:00", aula.getHora());
	}

	@Test
	public void testSetDisciplina() {
		aula.setDisciplina("Estudo do Meio");
		assertEquals("Estudo do Meio", aula.getDisciplina());
	}
	
	@Test
	public void testGetData() {
		aula = new Aula("2020-06-01",null,null);
		assertEquals("2020-06-01", aula.getData());
	}

	@Test
	public void testGetHora() {
		aula = new Aula(null,"10:00",null);
		assertEquals("10:00", aula.getHora());
	}

	@Test
	public void testGetDisciplina() {
		aula = new Aula(null,null,"Estudo do Meio");
		assertEquals("Estudo do Meio", aula.getDisciplina());
	}



}
