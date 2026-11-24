//Plataforma A POO Introdução à Programação Java: Variáveis, Operações e Estruturas de Controle
//Compila de qualquer forma
//javac LaboratorioIntroducao.java; java LaboratorioIntroducao
//Se tiver erros não compila
//javac LaboratorioIntroducao.java; if ($?) { java LaboratorioIntroducao }
import java.util.InputMismatchException;
import java.util.Scanner;  
    public class LaboratorioIntroducao {     
        public static void main(String[] args) {         
            Scanner leitor = new Scanner(System.in);         
            int opcao = -1;        
            do {             
                System.out.println("\n--- SISTEMA DE GESTÃO TECH ---");    
                System.out.println("1. Validar Estoque");     
                System.out.println("2. Garantia dos produtos");
                System.out.println("3. Relatorio de itens");        
                System.out.println("0. Sair");             
                System.out.print("Escolha uma opção: ");      
                try {
                opcao = leitor.nextInt();                          
                /*               
                * PRO-TIP DIDÁTICO: Limpeza de Buffer              
                * O método nextInt() lê apenas os dígitos, mas o caractere 'Enter' (\n)               
                * continua no buffer. Se não usarmos o nextLine() abaixo, o próximo               
                * input de texto seria "atropelado" pelo Enter residual.              */             
               leitor.nextLine();               
               switch(opcao) {
                case 0:
                // Caso vazio para o 0 não cair no default antes de sair
                    break;
               case 1:                 
                System.out.print("\nQuantidade atual em estoque: ");                 
                int estoque = leitor.nextInt();                 
                System.out.print("Quantidade para baixa: ");                 
                int pedido = leitor.nextInt();                                  
                //Validação manual de regra de negócio                 
                 if (pedido > 0 && pedido <= estoque) {                    
                 estoque -= pedido;                     
                 System.out.println("Baixa concluída. Novo estoque: " + estoque);                 
                } else {                     
                    System.out.println("\nFalha: Quantidade inválida ou insuficiente");                 
                }        
                break;     
            
                case 2:   
                System.out.println("\ncódigo do produto (1 para Eletrônicos, 2 para Vestuário, 3 para Móveis).");
                int codigoProduto = leitor.nextInt();
                leitor.nextLine();
                switch (codigoProduto) {
                    case 1:
                        System.out.println("Eletrônicos tem 24 Meses de garantia");
                        break;
                    case 2:
                        System.out.println("Roupas tem 3 Meses de garantia");
                        break;
                    case 3: 
                        System.out.println("Móveis tem 12 Meses de garantia");
                        break;

                default:
                System.out.println("Entrada Invalida insira um numero que esteja nos comandos");  
                    break;
            }        
            break;

            case 3:
                float precototalProd = 0;
                System.out.println("\nDigite a quantidade de Produtos que será calculada.");
                int quantProd = leitor.nextInt();
                for(int i = 1; i <= quantProd; i++) {
                    System.out.println("Digite o preço do produto N°" + i);
                    float precodoprod = leitor.nextInt();
                    precototalProd +=precodoprod;

                }
                System.out.println("A soma total dos produtos e: " +precototalProd);
                System.out.println("A media de preço dos produtos e: "+precototalProd/quantProd);
                break;



            default:
                System.out.println("Entrada Invalida insira um numero que esteja nos comandos");  
                break;
                }   
            }   catch (InputMismatchException e) {
                System.out.println("Erro: Digite apenas números inteiros!");
                leitor.nextLine(); // Limpa o texto inválido do buffer para não travar em loop infinito
                opcao = -1; // Garante que o programa continue rodando
        }
    }   
        while (opcao != 0);          
        System.out.println("\nEncerrando sistema...");         
        leitor.close();     
    } 
}
