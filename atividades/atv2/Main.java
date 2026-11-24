//Plataforma A POO Guia Prático de Laboratório: Módulo 01
public class Main {
    public static void main(String[] args) {
        // 1. Instanciar conta com saldo inicial valido
        Account myAccount = new Account("Victor", 1000.0);
        System.out.print("Titular: " + myAccount.getOwner() + " | Saldo: " + myAccount.getBalance() + "\n");

        // 2. Realizar deposito valido
        myAccount.deposit(500.0);
        System.out.print("Saldo apos deposito: " + myAccount.getBalance() + "\n");

        // 3. Tentar deposito negativo (Deve falhar)
        myAccount.deposit(-200.0);

        // 4. Realizar saque valido
        myAccount.withdraw(300.0);
        System.out.print("Saldo apos saque: " + myAccount.getBalance() + "\n");

        // 5. Tentar saque superior ao saldo (Deve falhar e manter o estado)
        myAccount.withdraw(5000.0);
        System.out.print("Saldo final mantido: " + myAccount.getBalance() + "\n");
    }
}
