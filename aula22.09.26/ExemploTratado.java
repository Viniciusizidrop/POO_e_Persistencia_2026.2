import java.util.Scanner;
import java.util.InputMismatchException;

public class ExemploTratado {    
public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);//instanciando a entrada
    Boolean entradaValida = false;//definindo a variavel como false

    while(!entradaValida){//!enquanto a variavel for falsa execute
        try{//tentativa
    System.out.println("Informe o ID do produto: ");//o input
    int idProduto = entrada.nextInt();//o leitor do input
    System.out.println("Código registrado com sucesso: " +idProduto);//um print
    entradaValida = true;//sai do loop se der certo, transformando a variavel em verdadeira
}catch (InputMismatchException e) {//captura do erro
    System.err.println("Erro: Você deve digitar um número inteiro");//print do erro 
    entrada.nextLine();//descarta a entrada incorreta da memoria para tentar novamente
}
}   
entrada.close();//fechamento do input
}
}
