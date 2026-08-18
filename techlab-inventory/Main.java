//package techlab-inventory;

public class Main {
    public static void main(String[]args){
        AtivoTI servidor = new AtivoTI("1", "PAT-2026-001","Dell PowerEdge R740","Ativo");

        System.out.println("=== Ativo Cadastrado ===");
        System.out.println("Patrimonio: " + servidor.getCodigoPatrimonio());
        System.out.println("Modelo: " + servidor.getModelo());
        System.out.println("Status Inicial: " + servidor.getStatus());

        servidor.setStatus("Em Manutenção");
        System.out.println("Novo Status:" + servidor.getStatus());
    }
}