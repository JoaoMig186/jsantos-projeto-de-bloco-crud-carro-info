package tests;

import org.jsantostp1.model.Combustivel;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.repository.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class CarroServiceStressTest {

    private CarroService service;

    @BeforeEach
    void setup() {
        service = new CarroService(new CarroRepository());
    }

    @Test
    void deveSuportarInsercaoEmMassa() {
        int total = 10_000;

        IntStream.range(0, total).forEach(i ->
                service.cadastrarCarro(
                        "Marca" + i,
                        "Modelo" + i,
                        2020,
                        List.of(Combustivel.ETANOL),
                        100,
                        1.8
                )
        );

        assertEquals(total, service.listarCarros().size(), "Todos os carros devem ser adicionados");
    }

    @Test
    void deveSuportarConcorrencia() throws InterruptedException, ExecutionException {
        int threads = 50;
        int carrosPorThread = 200;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        Callable<Void> task = () -> {
            for (int i = 0; i < carrosPorThread; i++) {
                service.cadastrarCarro(
                        "MarcaConcurrent",
                        "ModeloConcurrent",
                        2021,
                        List.of(Combustivel.GASOLINA),
                        120,
                        2.0
                );
            }
            return null;
        };

        List<Future<Void>> futures = new CopyOnWriteArrayList<>();
        for (int i = 0; i < threads; i++) {
            futures.add(executor.submit(task));
        }

        for (Future<Void> f : futures) f.get();
        executor.shutdown();

        int esperado = threads * carrosPorThread;
        assertEquals(esperado, service.listarCarros().size(), "Todos os carros concorrentes devem ser adicionados");
    }

}
