package org.jsantostp1.model;

public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private int cavalos;
    private Double cilindrada;

    public Carro(String marca, String modelo, int ano, int cavalos, Double cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cavalos = cavalos;
        this.cilindrada = cilindrada;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public int getCavalos(){
        return cavalos;
    }

    public Double getCilindrada(){
        return cilindrada;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setCavalos(int cavalos){
        this.cavalos = cavalos;
    }

    public void setCilindrada(Double cilindrada){
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return "Carro {" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano + '\'' +
                ", cavalos=" + cavalos + '\'' +
                ", cilindrada=" + cilindrada + '\'' +
                '}';
    }
}
