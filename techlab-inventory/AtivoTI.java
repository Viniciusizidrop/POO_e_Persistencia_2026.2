//package techlib-inventory;
//import AtivoTI.java;

public class AtivoTI {
    private int id;
    private String codigoPatrimonio;
    private String modelo;
    private String status;

    //construtor
    public AtivoTI(int id, String codigoPatrimonio, String modelo, String status){
        this.id = id;
        this.codigoPatrimonio = codigoPatrimonio;
        this.modelo = modelo;
        this.status = status;
    }
    //Getter e Setters
    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getCodigoPatrimonio(){
        return codigoPatrimonio;
    }
    public void getCodigoPatrimonio(String codigoPatrimonio){
        this.codigoPatrimonio = codigoPatrimonio;
    }

    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}

