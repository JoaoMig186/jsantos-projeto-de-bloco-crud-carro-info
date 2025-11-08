package org.jsantostp1.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rede {

    public void verificarConexao(String url) throws IOException {
        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(1000);
        conexao.connect();

        if (conexao.getResponseCode() != 200) {
            throw new IOException("Código HTTP inesperado: " + conexao.getResponseCode());
        }
    }

    public void verificarConexaoVariasVezes(String url) throws IOException {
        int tentativas = 3;
        int tentativaAtual = 0;

        while (tentativaAtual < tentativas) {
            try {
                tentativaAtual++;
                verificarConexao(url);
                System.out.println("Conexão bem-sucedida na tentativa " + tentativaAtual);
                return;
            } catch (IOException e) {
                System.out.println("Tentativa " + tentativaAtual + " falhou: " + e.getMessage());
                if (tentativaAtual == tentativas) {
                    throw new IOException("Falha após " + tentativas + " tentativas", e);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
