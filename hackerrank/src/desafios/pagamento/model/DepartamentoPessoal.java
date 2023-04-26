package desafios.pagamento.model;

public class DepartamentoPessoal extends Fucionario implements Remunerado {
    public DepartamentoPessoal(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calculaRemuneracao() {
        return this.getSalario() * 0.93;
    }
}
