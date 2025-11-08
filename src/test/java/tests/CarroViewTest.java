package tests;

import org.jsantostp1.model.Carro;
import org.jsantostp1.model.Combustivel;
import org.jsantostp1.view.CarroView;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarroViewTest {
    @Test
    void deveRenderizarListaDeCarrosCorretamente() {
        List<Carro> carros = List.of(
                new Carro("Ford", "Ka", 2020, List.of(Combustivel.GASOLINA), 100, 1.0),
                new Carro("VW", "Golf", 2019, List.of(Combustivel.DIESEL), 150, 2.0)
        );

        String html = CarroView.renderList(carros);

        assertTrue(html.contains("Ford"));
        assertTrue(html.contains("Ka"));
        assertTrue(html.contains("VW"));
        assertTrue(html.contains("Golf"));
    }

    @Test
    void deveRenderizarFormularioComValoresExistentes() {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("marca", "Toyota");
        modelo.put("modelo", "Corolla");
        modelo.put("ano", 2022);

        String html = CarroView.renderForm(modelo);

        assertTrue(html.contains("value=\"Toyota\""));
        assertTrue(html.contains("value=\"Corolla\""));
        assertTrue(html.contains("value=\"2022\""));
    }
}
