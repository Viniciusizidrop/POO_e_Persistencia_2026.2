import time
class Carro():
    def __init__(self, marca, modelo, ano):
        self.marca = marca
        self.modelo = modelo
        self.ano = ano
        self.ligado = False

    def exibir_informacoes(self):
        return f"Marca: {self.marca}, Modelo: {self.modelo}, Ano: {self.ano}"

    def ligar(self):
        self.ligado = True
        print(f"ligando o {self.modelo}")
        time.sleep(2)
        print(f"O carro {self.modelo} esta ligado.")
    
    def desligar(self):
        self.ligado = False
        print(f"O carro {self.modelo} esta desligando.")

    def acelerar(self):
        velocidade = 0
        if self.ligado == True:
            while True:
                print(f"Velocidade atual: {velocidade} km/h")
                velocidade += 3
                time.sleep(1)
        else:
            print("O carro está desligado. Ligue o carro antes de acelerar.")

Carro1 = Carro("Toyota", "Corolla", 2020)

Carro1.ligar()

Carro1.acelerar()