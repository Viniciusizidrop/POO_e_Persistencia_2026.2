//aula5
/*public class Servidor extends AtivoTI{
    //Atributos novos da classe filho
    private String ipEstatico;
    private String sistemaOperacional;

    //Construtor
    public Servidor(int id, String patrimonio, String modelo, String status, String ipEstatico, String sistemaOperacional){ super(id,patrimonio,modelo,status);//pegando os atributos da classe mãe
                         this.ipEstatico = ipEstatico;//adicionando os da nova classe
                         this.sistemaOperacional = sistemaOperacional;                           
    }

    //getters e setters
    public String getIpEstatico(){return ipEstatico;}
    public String getSistemaOperacional(){return sistemaOperacional;}

    //Representação
    @Override 
    public String toString(){
        return "[Servidor] " +super.toString()+ " | IP: " +ipEstatico+ " | SO: " +sistemaOperacional;
    }
 
 
}*/

//aula6
public class Servidor extends AtivoTI{
    private int quantidadeCores;
 //Atributos novos da classe filho
    private String ipEstatico;
    private String sistemaOperacional;
    private int memoriaRam;

    //Construtor
    public Servidor(int id, String patrimonio, String modelo, String status, String ipEstatico, String sistemaOperacional, int memoriaRam){ super(id,patrimonio,modelo,status);//pegando os atributos da classe mãe
                         this.ipEstatico = ipEstatico;//adicionando os da nova classe
                         this.sistemaOperacional = sistemaOperacional;
                         this.memoriaRam = memoriaRam;                           
    }
    //Representação
    @Override 
    public String toString(){
        return "[Servidor] " +super.toString()+ " | IP: " +ipEstatico+ " | SO: " +sistemaOperacional+ "| Me: " + memoriaRam;
    }
//getters e setters
public String getquantidadeCores(){
    return this.getquantidadeCores();
}
@Override 
public double calcularCustoManutencaoMensal(){
//regra de negocio: custo base + (cores * 50)
return 200.0 + (this.quantidadeCores * 50.0);    
}
}

