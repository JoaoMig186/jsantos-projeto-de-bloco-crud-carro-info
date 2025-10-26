package tests;

import org.jsantostp1.service.CarroService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecisaoIfElseTest {
    CarroService service = new CarroService(null);

    @Test
    void testVerificaAno(){
        assertEquals("Ano inválido", service.verificaAno(1800));
        assertEquals("Ano válido", service.verificaAno(2020));
    }

    @Test
    void testVerificarCavalos(){
        assertEquals("O número de cavalos deve ser maior que 0", service.vereficarCavalos(0));
        assertEquals("Cavalos corretos", service.vereficarCavalos(100));
    }

    @Test
    void testVerificarCilindrada(){
        assertEquals("O número de cilindradas deve ser maior que 0", service.vereficarCilindrada(0));
        assertEquals("Cilindradas corretas", service.vereficarCilindrada(1.0));
    }
}
