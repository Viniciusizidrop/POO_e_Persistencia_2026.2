//package ChamadoTI;
import java.util.Scanner;
//aula2
/*public class Main {
    public static void main(String[]args){
        AtivoTI servidor = new AtivoTI("1", "PAT-2026-001","Dell PowerEdge R740","Ativo");

        System.out.println("=== Ativo Cadastrado ===");
        System.out.println("Patrimonio: " + servidor.getCodigoPatrimonio());
        System.out.println("Modelo: " + servidor.getModelo());
        System.out.println("Status Inicial: " + servidor.getStatus());

        servidor.setStatus("Em Manutenção");
        System.out.println("Novo Status:" + servidor.getStatus());
    }
}*/
//aula3
/*public class Main{
    public static void main(String[]args){
        Scanner leitor = new Scanner(System.in);

        System.out.println("=== Cadastro de AtivoTI ===");
        System.out.print("Digite o ID do Ativo: ");
        int idAtivo = leitor.nextInt();
        leitor.nextLine(); // Limpar o buffer
        System.out.print("Digite o código de patrimônio: ");
        String codigoPratimonio = leitor.nextLine();
        System.out.print("Informe o modelo do ativo: ");
        String modelo = leitor.nextLine();
        System.out.print("Informe o status do ativo: ");
        String status = leitor.nextLine();

        AtivoTI equipamento = new AtivoTI(idAtivo, codigoPratimonio, modelo, status);

        System.out.println("\n=== Ativo Cadastrado ===");
        System.out.print("ID do Chamado de Suporte: ");
        int idChamado = leitor.nextInt();
        leitor.nextLine(); //Limpar o buffer
        System.out.print("Descrição do Chamado: ");
        String descricaoChamado = leitor.nextLine();

        ChamadoSuporte chamado = new ChamadoSuporte(idChamado, descricaoChamado, equipamento);
        
        System.out.println("\n === Status inicial do equipamento ===");
        System.out.println("Status do Equipamento: " + chamado.getAtivoRelacionado().getStatus());

        System.out.println("Digite o novo status para o equipamento (ex: Em Manutencao):  ");
        String novostatus = leitor.nextLine();

        chamado.getAtivoRelacionado().setStatus(novostatus);

        System.out.println("\n=== Verificação de Integridade em RAM ===");
        System.out.println("Status no objeto 'equipamento': " + equipamento.getStatus());
        System.out.println("Status via objeto 'chamado': " + chamado.getAtivoRelacionado().getStatus()
    
    );

        leitor.close();

    }
}*/

//aula4
public class Main{
    public static void main(String[]args){
        Scanner leitor = new Scanner(System.in);

        System.out.println("=== Cadastro de AtivoTI Simplificado ===");
        System.out.print("Digite o ID do Ativo: ");
        int idAtivo = leitor.nextInt();
        leitor.nextLine(); // Limpar o buffer
        System.out.print("Digite o código de patrimônio: ");
        String codigoPratimonio = leitor.nextLine();
        System.out.print("Informe o modelo do ativo: ");
        String modelo = leitor.nextLine();
        System.out.print("Informe o status do ativo: ");
        String status = leitor.nextLine();

        AtivoTI equipamento = new AtivoTI(idAtivo, codigoPratimonio, modelo);

        System.out.println("\n=== Ativo Cadastrado ===");
        System.out.print("ID do Chamado de Suporte: ");
        int idChamado = leitor.nextInt();
        leitor.nextLine(); //Limpar o buffer
        System.out.print("Descrição do Chamado: ");
        String descricaoChamado = leitor.nextLine();
        System.out.print("Prioridade(Alta/Media/Baixa): ");
        String prioridade = leitor.nextLine();

        ChamadoSuporte chamado = new ChamadoSuporte(idChamado, descricaoChamado, prioridade, equipamento);

        System.out.println("\n=== Relatorio do Sistema ===");
        System.out.println(chamado);

        leitor.close();

        

    }