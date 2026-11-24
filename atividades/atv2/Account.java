//javac Account.java Main.java; java Main
public class Account {
    private String owner;
    private double balance;
    protected String accountType;

    public Account(String owner, double initialBalance) {
        this.owner = owner;
        this.accountType = "Standard";
        // Garantindo a Invariante de Classe: Saldo nunca inicia negativo
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
            System.out.print("Aviso: Saldo inicial negativo invalido. Definido como 0.0.\n");
        }
    }

    public String getOwner() {
        return this.owner;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double value) {
        // Validação manual obrigatória (Proibido Math.abs)
        if (value > 0) {
            this.balance += value;
        } else {
            System.out.print("Erro: O valor do deposito deve ser positivo.\n");
        }
    }

    public void withdraw(double value) {
        if (value > this.balance) {
            System.out.print("Erro: Saldo insuficiente para realizar a operacao.\n");
        } else if (value <= 0) {
            System.out.print("Erro: O valor de saque deve ser positivo.\n");
        } else {
            this.balance -= value;
        }
    }
}
