package model;

public class ChamadoSuporte {
    private int id;
    private String descricao;
    private String status;
    private AtivoTI ativo;

    public ChamadoSuporte() {
    }

    public ChamadoSuporte(int id, String descricao, String status, AtivoTI ativo) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AtivoTI getAtivo() {
        return ativo;
    }

    public void setAtivo(AtivoTI ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "ChamadoSuporte [id=" + id + ", descricao=" + descricao
            + ", status=" + status
            + ", ativo=" + (ativo != null ? ativo.getNome() : "null") + "]";
    }
}
