//aula5
public class Servidor extends AtivoTI{
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
 
 
}
