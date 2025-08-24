package org.jsantostp1.controller;
import org.jsantostp1.service.CarroService;
import java.util.Scanner;
import org.jsantostp1.util.InputUtils;

public class CarroController {
    private final CarroService service;
    private final Scanner scanner;
    private final InputUtils input;

    public CarroController(CarroService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
        this.input = new InputUtils(scanner);
    }

    public void iniciar() {
        int opcao;

        do {
            System.out.println("\n===== MENU CARROS =====");
            System.out.println("1 - Cadastrar carro");
            System.out.println("2 - Listar carros");
            System.out.println("3 - Buscar carro por ID");
            System.out.println("4 - Atualizar carro");
            System.out.println("5 - Remover carro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> atualizar();
                case 5 -> remover();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        String marca = input.lerString("Marca: ");
        String modelo = input.lerString("Modelo: ");
        int ano = input.lerInt("Ano: ", 1886);
        int cavalos = input.lerInt("Cavalos de potência: ", 1);
        double cilindrada = input.lerDouble("Cilindrada (ex: 2.0): ", 1.0);

        service.cadastrarCarro(marca, modelo, ano, cavalos, cilindrada);
        System.out.println("Carro cadastrado com sucesso!");
    }

    private void listar() {
        System.out.println("\n--- Lista de Carros ---");
        service.listarCarros().forEach(System.out::println);
    }

    private void buscarPorId() {
        System.out.print("Informe o ID do carro: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        var carro = service.buscarCarroPorId(id);
        if (carro.isPresent()) {
            System.out.println(carro.get());
        } else {
            System.out.println("Carro não encontrado.");
        }
    }

    private void atualizar() {
        int id = input.lerInt("ID do carro a atualizar: ", 1);

        var carroExistente = service.buscarCarroPorId(id);
        if (carroExistente.isEmpty()) {
            System.out.println("Carro com ID " + id + " não encontrado.");
            return;
        }

        System.out.println("Carro atual: " + carroExistente.get());

        String marca = input.lerString("Nova marca: ");
        String modelo = input.lerString("Novo modelo: ");
        int ano = input.lerInt("Novo ano: ", 1886);
        int cavalos = input.lerInt("Novos cavalos de potência: ", 1);
        double cilindrada = input.lerDouble("Nova cilindrada (ex: 2.0): ", 1.0);

        boolean sucesso = service.atualizarCarro(id, marca, modelo, ano, cavalos, cilindrada);
        if (sucesso) {
            System.out.println("Carro atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar carro.");
        }
    }

    private void remover() {
        System.out.print("ID do carro a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean sucesso = service.removerCarro(id);
        if (sucesso) {
            System.out.println("Carro removido com sucesso.");
        } else {
            System.out.println("Carro não encontrado.");
        }
    }
}
