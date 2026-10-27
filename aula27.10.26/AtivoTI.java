public class AtivoTI {

    private int id;
    private String patrimonio;
    private String nome;
    private String categoria;

    public AtivoTI(String patrimonio, String nome, String categoria) {
        this.patrimonio = patrimonio;
        this.nome = nome;
        this.categoria = categoria;
    }

    public AtivoTI(int id, String patrimonio, String nome, String categoria) {
        this(patrimonio, nome, categoria);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getPatrimonio() {
        return this.patrimonio;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCategoria() {
        return this.categoria;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-3d | Patrimônio: %-10s | Nome: %-30s | Categoria: %-15s",
            this.id, this.patrimonio, this.nome, this.categoria
        );
    }
}
