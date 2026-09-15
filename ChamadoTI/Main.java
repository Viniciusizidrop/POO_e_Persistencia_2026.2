//package ChamadoTI;
//import java.util.Scanner;
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
/*public class Main{
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
}*/

//aula5
/*public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   TECHLAB INVENTORY - TESTE DE HERANÇA (AULA 5)  ");
        System.out.println("==================================================\n");

        System.out.println("[RAM] Instanciando objetos especializados no Heap...");
        
        // 1. Instanciamos apenas objetos da subclasse Servidor (com dados diferentes) [cite: 64, 211]
        Servidor servidorWeb = new Servidor(
            1, 
            "PAT-2026-001", 
            "Dell PowerEdge R740", 
            "Ativo", 
            "192.168.1.100", 
            "Ubuntu Server 22.04 LTS"
        );

        Servidor servidorBanco = new Servidor(
            2, 
            "PAT-2026-003", 
            "HP ProLiant DL360", 
            "Em Manutenção", 
            "192.168.1.150", 
            "Windows Server 2022"
        );

        System.out.println(servidorWeb);
        System.out.println(servidorBanco);
    }
        2. Polimorfismo com Arrays Nativos (Tamanho Fixo)
        // Criamos um array do tipo da superclasse 'AtivoTI' com capacidade para 2 elementos.
        // O Java permite armazenar referências de 'Servidor' nele porque Servidor "é um" AtivoTI.
        AtivoTI[] inventario = new AtivoTI[1]; [cite: 16, 80]
        inventario = servidorWeb;   // Posição 0 do vetor recebe o servidor Web
        inventario[2] = servidorBanco; // Posição 1 do vetor recebe o servidor de Banco

        System.out.println("\n==================================================");
        System.out.println("       RELATÓRIO POLIMÓRFICO DE ATIVOS (ARRAY)    ");
        System.out.println("==================================================");

        // 3. Iteração polimórfica com o laço 'for' aprimorado (foreach)
        // O Java percorre o array e chama implicitamente o toString() especializado 
        // de cada objeto em tempo de execução (vinculação tardia).
        for (AtivoTI ativo : inventario) { [cite: 17, 107]
            // Boa prática: Evitamos NullPointerException garantindo que a posição não está vazia
            if (ativo != null) {
                System.out.println(ativo); // Chamada implícita ao toString() especializado
            }
        }
        System.out.println("--------------------------------------------------");

        System.out.println("\n==================================================");
        System.out.println("        TESTANDO DOWNCASTING SEGURO (ARRAY)       ");
        System.out.println("==================================================");

        // 4. Varredura do array para Downcasting seguro usando 'instanceof'
        // Permite recuperar os atributos exclusivos do herdeiro a partir da referência genérica.
        for (AtivoTI ativo : inventario) {
            if (ativo instanceof Servidor) { // Se a referência apontar de fato para um Servidor
                Servidor srv = (Servidor) ativo; // Realiza o downcast explícito
                System.out.printf("Ativo ID %d é um Servidor. IP: %s | SO: %s%n", 
                                  srv.getId(), srv.getIpEstatico(), srv.getSistemaOperacional());
            }
        }
        System.out.println("==================================================");
    }
}*/

//aula6
public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" TECHLAB INVENTORY - ABSTRAÇÃO E INTERFACES (AULA 6) ");
        System.out.println("==================================================\n");

        // ----------------------------------------------------------------- 
        // 1. DEMONSTRAÇÃO DE ABSTRAÇÃO (Tentativa de Instanciação Direta) 
        // ----------------------------------------------------------------- 
        // A linha abaixo causará um ERRO DE COMPILAÇÃO se descomprometida: 
        // AtivoTI ativoGenerico = new AtivoTI(0, "PAT-000", "Generico", "Ativo"); 
        // Motivo: 'AtivoTI' é abstrata e não pode ser instanciada diretamente no Heap! 
        System.out.println("[REGRA DE NEGÓCIO] 'AtivoTI' é uma classe abstrata e não pode usar 'new'.\n");

        // ----------------------------------------------------------------- 
        // 2. INSTANCIAÇÃO DE SUBCLASSES CONCRETAS 
        // ----------------------------------------------------------------- 
        System.out.println("[RAM] Instanciando objetos concretos especializados no Heap...");

        // Instanciamos objetos da subclasse concreta 'Servidor' 
        Servidor servidorWeb = new Servidor(
            1, 
            "PAT-2026-001", 
            "Dell PowerEdge R740", 
            "Ativo", 
            "192.168.1.100", 
            "Ubuntu Server 22.04 LTS",
            128
        );

        Servidor servidorBanco = new Servidor(
            2, 
            "PAT-2026-003", 
            "HP ProLiant DL360", 
            "Em Manutenção", 
            "192.168.1.150", 
            "Windows Server 2022",
            64
        );

        // ----------------------------------------------------------------- 
        // 3. RELATÓRIO POLIMÓRFICO DE CUSTOS (Usando Classe Abstrata) 
        // ----------------------------------------------------------------- 
        System.out.println("\n==================================================");
        System.out.println(" RELATÓRIO DE CUSTOS DE MANUTENÇÃO (ABSTRAÇÃO) ");
        System.out.println("==================================================");

        // Correção da inicialização do array para evitar erros de compilação
        AtivoTI[] inventario = new AtivoTI[2]; 
        inventario[0] = servidorWeb; 
        inventario[1] = servidorBanco;

        double custoTotalEmpresa = 0.0;

        for (AtivoTI ativo : inventario) {
            if (ativo != null) {
                // Impressão polimórfica (toString) 
                System.out.println(ativo);

                // Invocação do MÉTODOS ABSTRATO implementado obrigatoriamente na subclasse 
                double custoAtivo = ativo.calcularCustoManutencaoMensal();
                custoTotalEmpresa += custoAtivo;

                System.out.printf(" └─> Custo Estimado de Manutenção: R$ %.2f%n%n", custoAtivo);
            }
        }

        System.out.printf("CUSTO TOTAL DE MANUTENÇÃO MENSAL: R$ %.2f%n", custoTotalEmpresa);
        System.out.println("--------------------------------------------------");

        // ----------------------------------------------------------------- 
        // 4. USO DE INTERFACES E CONTRATOS (INotificavel) 
        // ----------------------------------------------------------------- 
        System.out.println("\n==================================================");
        System.out.println(" DISPARO DE ALERTAS (INTERFACE INotificavel) ");
        System.out.println("==================================================");

        // Instanciamos um ChamadoSuporte que se associa a um ativo 
        ChamadoSuporte chamadoCritico = new ChamadoSuporte(
            101, 
            "Falha de Hardware no Servidor de Banco de Dados", 
            "Alta",
            servidorBanco
        );

        // Polimorfismo de Interface: Tratamos o objeto pela capacidade da Interface INotificavel 
        INotificavel canalNotificacao = chamadoCritico;

        // Disparamos a mensagem garantida pelo contrato da interface 
        canalNotificacao.enviarAlerta("Superaquecimento detectado no processador do Servidor!");
        System.out.println("================================================--");
    }
}