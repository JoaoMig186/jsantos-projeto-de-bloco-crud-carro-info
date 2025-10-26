package tests;

import org.jsantostp1.util.Rede;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RedeTimeoutTest {
    @Test
    void deveTentarVariasVezesAntesDeSucesso_Timeouts() throws IOException {
        Rede rede = Mockito.spy(new Rede());
        doThrow(new SocketTimeoutException("Timeout 1"))
                .doThrow(new SocketTimeoutException("Timeout 2"))
                .doNothing()
                .when(rede)
                .verificarConexao(anyString());

        rede.verificarConexaoVariasVezes("http://localhost:7000/");
        verify(rede, times(3)).verificarConexao(anyString());
    }

    @Test
    void deveLancarIOExceptionAposTresTimeouts() throws IOException {
        Rede rede = Mockito.spy(new Rede());

        doThrow(new SocketTimeoutException("Tempo limite excedido"))
                .when(rede)
                .verificarConexao(anyString());

        IOException exception = assertThrows(IOException.class, () -> {
            rede.verificarConexaoVariasVezes("http://localhost:7000/");
        });

        assertTrue(exception.getCause() instanceof SocketTimeoutException);

        verify(rede, times(3)).verificarConexao(anyString());
    }
}
