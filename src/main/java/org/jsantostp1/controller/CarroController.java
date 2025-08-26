package org.jsantostp1.controller;

import org.jsantostp1.model.Combustivel;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.util.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarroController {
    private final CarroService service;
    private final Scanner scanner;
    private final InputUtils input;

    public CarroController(CarroService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
        this.input = new InputUtils(scanner);
    }

    public CarroController(CarroService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
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
            scanner.nextLine();

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

    private List<Combustivel> escolherCombustiveis() {
        List<Combustivel> combustiveis = new ArrayList<>();

        while (true) {
            if (combustiveis.size() == 2) {
                System.out.println("Já foram escolhidos 2 combustíveis (máximo permitido).");
                break;
            }

            System.out.println("\nEscolha um combustível:");
            int i = 1;
            for (Combustivel c : Combustivel.values()) {
                System.out.println(i + " - " + c);
                i++;
            }
            System.out.println("0 - Finalizar escolha");

            int opcao = input.lerInt("Opção: ", 0);

            switch (opcao) {
                case 0 -> {
                    if (combustiveis.isEmpty()) {
                        System.out.println("É obrigatório escolher pelo menos 1 combustível.");
                    } else {
                        return combustiveis;
                    }
                }
                case 1, 2, 3 -> {
                    Combustivel escolhido = Combustivel.values()[opcao - 1];
                    if (combustiveis.contains(escolhido)) {
                        System.out.println("Esse combustível já foi escolhido.");
                    } else {
                        combustiveis.add(escolhido);
                        System.out.println(escolhido + " adicionado!");
                    }
                }
                default -> System.out.println("Opção inválida!");
            }
        }
        return combustiveis;
    }

    void cadastrar() {
        String marca = input.lerString("Marca: ");
        String modelo = input.lerString("Modelo: ");
        int ano = input.lerInt("Ano: ", 1886);
        List<Combustivel> combustiveis = escolherCombustiveis();
        int cavalos = input.lerInt("Cavalos de potência: ", 1);
        double cilindrada = input.lerDouble("Cilindrada (ex: 2.0): ", 1.0);

        service.cadastrarCarro(marca, modelo, ano, combustiveis, cavalos, cilindrada);
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

    void atualizar() {
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
        List<Combustivel> combustiveis = escolherCombustiveis();
        int cavalos = input.lerInt("Novos cavalos de potência: ", 1);
        double cilindrada = input.lerDouble("Nova cilindrada (ex: 2.0): ", 1.0);

        boolean sucesso = service.atualizarCarro(id, marca, modelo, ano, combustiveis, cavalos, cilindrada);
        if (sucesso) {
            System.out.println("Carro atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar carro.");
        }
    }

    void remover() {
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
