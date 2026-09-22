import java.util.Scanner;
import java.util.InputMismatchException;
public class DivisaoComThrow {
    public static int dividir(int numerador, int denominador){
        if(denominador==0){
            throw new arithmeticException("Não e permitido divisão por 0");
        }
        return numerador / denominador;
    }
    public static void main(String [] args){

        try{
            System.out.println("Informe o numerador: ");
            int n1 = entrada.nextLine();
            System.out.println("infome o denominador: ");
            int n2 = entrada.nextLine();
        }
    }
}
