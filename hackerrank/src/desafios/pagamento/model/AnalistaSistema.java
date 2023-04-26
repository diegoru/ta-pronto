package desafios.pagamento.model;

public class AnalistaSistema extends Fucionario implements Remunerado {
    public AnalistaSistema(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calculaRemuneracao() {
        return this.getSalario() * 0.9;
    }
}
