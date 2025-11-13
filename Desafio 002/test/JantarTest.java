package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.Filosofo;
import main.Garfo;
import main.Jantar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes TDD para Jantar dos Filósofos")
class JantarTest {

    private Jantar jantar;

    @ParameterizedTest
    @ValueSource(ints = { 5, 8 })
    @DisplayName("Deve criar o número correto de filósofos e garfos únicos")
    void testarContagemEUnicidade(int numeroFilosofos) {
        jantar = new Jantar(numeroFilosofos);

        jantar.iniciar();

        List<Filosofo> filosofos = jantar.getFilosofos();
        List<Garfo> garfos = jantar.getGarfos();

        assertNotNull(filosofos, "Lista de filósofos não pode ser nula");
        assertNotNull(garfos, "Lista de garfos não pode ser nula");

        assertEquals(numeroFilosofos, filosofos.size(), "Deve haver " + numeroFilosofos + " filósofos.");
        assertEquals(numeroFilosofos, garfos.size(), "Deve haver " + numeroFilosofos + " garfos.");

        assertEquals(numeroFilosofos, new HashSet<>(filosofos).size(), "Filósofos devem ser instâncias únicas.");
        assertEquals(numeroFilosofos, new HashSet<>(garfos).size(), "Garfos devem ser instâncias únicas.");
    }

    @ParameterizedTest
    @ValueSource(ints = { 5, 10 })
    @DisplayName("Deve configurar a topologia de compartilhamento corretamente")
    void testarTopologiaDeCompartilhamento(int numeroFilosofos) {
        jantar = new Jantar(numeroFilosofos);

        jantar.iniciar();

        List<Filosofo> filosofos = jantar.getFilosofos();
        Map<Garfo, Integer> contagemUsoGarfos = new HashMap<>();

        for (Filosofo f : filosofos) {
            Garfo esquerdo = f.getGarfoEsquerdo();
            Garfo direito = f.getGarfoDireito();

            assertNotNull(esquerdo, "Garfo esquerdo não pode ser nulo.");
            assertNotNull(direito, "Garfo direito não pode ser nulo.");
            assertNotSame(esquerdo, direito, "Filósofo " + f.getId() + " tem garfos idênticos.");

            contagemUsoGarfos.merge(esquerdo, 1, Integer::sum);
            contagemUsoGarfos.merge(direito, 1, Integer::sum);
        }

        assertEquals(numeroFilosofos, contagemUsoGarfos.size(), "O número de garfos únicos usados deve ser N.");
        for (Map.Entry<Garfo, Integer> entry : contagemUsoGarfos.entrySet()) {
            assertEquals(2, entry.getValue(),
                    "O Garfo (ID: " + entry.getKey().getId() + ") deve ser compartilhado por exatamente 2 filósofos.");
        }
    }

    @Test
    @DisplayName("Deve executar sem Deadlock e garantir Liveness (todos comem)")
    void testarExecucaoLivenessSemDeadlock() {
        int N = 5;
        jantar = new Jantar(N);
        jantar.iniciar();
        List<Filosofo> filosofos = jantar.getFilosofos();

        try {
            jantar.executarPor(2000);
        } catch (InterruptedException e) {
            fail("O teste de execução foi interrompido.", e);
        }

        for (Filosofo f : filosofos) {
            assertTrue(f.getVezesComeu() > 0,
                    "Filósofo " + f.getId()
                            + " deveria ter comido ao menos uma vez. (Possível Starvation ou Deadlock)");
        }
    }
}