package desafios.pagamento.model;

public class Desenvolvedor extends Fucionario implements Remunerado {

    public Desenvolvedor(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calculaRemuneracao() {
        return this.getSalario() * 0.95;
    }
}
