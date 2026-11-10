package model;

public class AtivoTI {
    private int id;
    private String nome;
    private String patrimonio;
    private double valor;

    public AtivoTI() {
    }

    public AtivoTI(int id, String nome, String patrimonio, double valor) {
        this.id = id;
        this.nome = nome;
        this.patrimonio = patrimonio;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "AtivoTI [id=" + id + ", nome=" + nome
            + ", patrimonio=" + patrimonio + ", valor=" + valor + "]";
    }
}
