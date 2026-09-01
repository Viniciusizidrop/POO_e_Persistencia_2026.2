//package ChamadoTI;
//import AtivoTI.java;
//aula2
/*public class AtivoTI {
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
}*/

//aula4
public class AtivoTI{
    private int id;
    private String patrimonio;
    private String modelo;
    private String status;

    //construtor completo
    public AtivoTI(int id, String patrimonio, String modelo, String status){
        this.id = id;
        this.patrimonio = patrimonio;
        this.modelo = modelo;
        this.status = status;
    }

    //construtor sobrecarregado
    //se o status não for informado, assume "Ativo" por padrão
    public AtivoTI(int id, String patrimonio, String modelo){
        this(id, pratrimonio, modelo, status: "Ativo");

    }

    //getters e setters
    public String getStatus() {return this.status;}
    public void setStatus(String status){this.status = status;}
    public String getModelo() {return this.modelo;}

    //representação textual do objeto AtivoTI
    @Override
    public String toString(){
        return "Ativo TI [ID: "+this.id+" | Pat: "+this.patrimonio+" | Modelo: "+this.modelo+" | Setstatus: "+this.status+"]";
    }
}

