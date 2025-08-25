package org.jsantostp1.model;
import java.util.List;

public final class Carro {
    private final int id;
    private final String marca;
    private final String modelo;
    private final int ano;
    private final List<Combustivel> combustiveis;
    private final int cavalos;
    private final double cilindrada;

    public Carro(int id, String marca, String modelo, int ano, List<Combustivel> combustiveis, int cavalos, double cilindrada) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.combustiveis = List.copyOf(combustiveis);
        this.cavalos = cavalos;
        this.cilindrada = cilindrada;
    }

    public Carro(String marca, String modelo, int ano, List<Combustivel> combustiveis, int cavalos, double cilindrada) {
        this(0, marca, modelo, ano, combustiveis, cavalos, cilindrada);
    }

    // Getters
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public List<Combustivel> getCombustiveis() { return combustiveis; }
    public int getCavalos() { return cavalos; }
    public double getCilindrada() { return cilindrada; }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", combustiveis=" + combustiveis +
                ", cavalos=" + cavalos +
                ", cilindrada=" + cilindrada +
                '}';
    }
}
