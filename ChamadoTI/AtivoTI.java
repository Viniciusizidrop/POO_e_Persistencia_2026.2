
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
/*public class AtivoTI{
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
        this(id, patrimonio, modelo,"Ativo");

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
}*/

//aula5
/*public class AtivoTI{
    //atributos
    protected  int id;
    protected String patrimonio;
    protected  String modelo;
    protected String status;


    //Construtor
    public AtivoTI(int id, String patrimonio, String modelo, String Status){
        this.id = id;
        this.patrimonio = patrimonio;
        this.modelo = modelo;
        this.status = status;
    }

    //getters e setters
    public int getId(){return id;}
    public String getPatrimonio(){return patrimonio;}
    public String getModelo(){return modelo;}
    public String getStatus(){return status;}
    public void setPatrimonio(String patrimonio){this.patrimonio = patrimonio;}
    public void setModelo(String modelo){this.modelo = modelo;}
    public void setStatus(String status){this.status = status;}

    //Representação
    @Override 
    public String toString(){
        return "Id: "+id+"  | Pat: " +patrimonio+"  | Modelo:  "+modelo+"  | status:  "+status;
    }
}*/

//aula6
/* 
public abstract class AtivoTI{
    //atributos
    protected  int id;
    protected String patrimonio;
    protected  String modelo;
    protected String status;


    //Construtor
    public AtivoTI(int id, String patrimonio, String modelo, String Status){
        this.id = id;
        this.patrimonio = patrimonio;
        this.modelo = modelo;
        this.status = status;
    }

    //getters e setters
    public int getId(){return id;}
    public String getPatrimonio(){return patrimonio;}
    public String getModelo(){return modelo;}
    public String getStatus(){return status;}
    public void setPatrimonio(String patrimonio){this.patrimonio = patrimonio;}
    public void setModelo(String modelo){this.modelo = modelo;}
    public void setStatus(String status){this.status = status;}

    //Representação
    @Override 
    public String toString(){
        return "Id: "+id+"  | Pat: " +patrimonio+"  | Modelo:  "+modelo+"  | status:  "+status;
    }

    //aula6
    //Método abstrato define oque fazer, mais não como.
    public abstract double calcularCustoManutencaoMensal();
    }
*/
//aula8 
public class AtivoTI {     
    private int id;     
    private String patrimonio;     
    private String nome;     
    private String categoria;      
    public AtivoTI(String patrimonio, String nome, String categoria) {         
        this.patrimonio = patrimonio;         
        this.nome = nome;         
        this.categoria = categoria;     }      
        public AtivoTI(int id, String patrimonio, String nome, String categoria) {         
            this(patrimonio, nome, categoria);         
            this.id = id;     }      
            public int getId() {return this.id;}      
            public String getPatrimonio() {return this.patrimonio;}      
            public String getNome() {return this.nome;}      
            public String getCategoria() { return this.categoria; } 
            @Override 
            public String toString() { 
                return String.format("ID: %-3d | Patrimônio: %-10s | Nome: %-30s | Categoria: %-15s", this.id, this.patrimonio, this.nome, this.categoria); } } 