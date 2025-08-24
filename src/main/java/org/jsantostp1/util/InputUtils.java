package org.jsantostp1.util;
import java.util.Scanner;

public class InputUtils {
    private final Scanner scanner;

    public InputUtils(Scanner scanner) {
        this.scanner = scanner;
    }

    public int lerInt(String mensagem, int minimo) {
        int valor;
        while (true) {
            System.out.print(mensagem);
            try {
                valor = Integer.parseInt(scanner.nextLine());
                if (valor < minimo) {
                    System.out.println("O valor deve ser maior ou igual a " + minimo + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public double lerDouble(String mensagem, double minimo) {
        double valor;
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().replace(",", ".");

            if (!entrada.matches("^-?\\d+(\\.\\d+)?$")) {
                System.out.println("Entrada inválida. Use apenas números reais, como 2.0 ou 1.6.");
                continue;
            }

            try {
                valor = Double.parseDouble(entrada);
                if (valor < minimo) {
                    System.out.println("O valor deve ser maior ou igual a " + minimo + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número decimal válido.");
            }
        }
    }

    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}
