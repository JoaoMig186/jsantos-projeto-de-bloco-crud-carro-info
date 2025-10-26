package tests;

import org.jsantostp1.util.Rede;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RedeTest {

    @Test
    void deveTentarVariasVezesAntesDeFalhar() throws IOException {
        Rede rede = Mockito.spy(new Rede());

        doThrow(new IOException("Falha 1"))
                .doThrow(new IOException("Falha 2"))
                .doNothing()
                .when(rede)
                .verificarConexao(anyString());

        rede.verificarConexaoVariasVezes("http://localhost:7000/");

        verify(rede, times(3)).verificarConexao(anyString());
    }

    @Test
    void deveLancarExcecaoAposTresFalhas() throws IOException {
        Rede rede = Mockito.spy(new Rede());

        doThrow(new IOException("Falha de rede"))
                .when(rede)
                .verificarConexao(anyString());

        assertThrows(IOException.class, () -> {
            rede.verificarConexaoVariasVezes("http://localhost:7000/");
        });

        verify(rede, times(3)).verificarConexao(anyString());
    }
}
