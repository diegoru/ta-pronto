package desafios.pagamento.model;

public abstract class Fucionario {

    private String nome;
    private double salario;

    public Fucionario(String nome, double salario) {
        this.nome = nome;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }


    public double getSalario() {
        return salario;
    }

}
