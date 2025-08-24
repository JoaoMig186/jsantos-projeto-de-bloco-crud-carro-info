package org.jsantostp1;

import org.jsantostp1.controller.CarroController;
import org.jsantostp1.repository.CarroRepository;
import org.jsantostp1.service.CarroService;

public class Main {
    public static void main(String[] args) {
        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);
        CarroController controller = new CarroController(service);

        controller.iniciar();
    }
}
