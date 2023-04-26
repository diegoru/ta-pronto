package desafios.pagamento.teste;

import desafios.pagamento.model.*;
import desafios.pagamento.service.Pagamento;

import java.text.NumberFormat;
import java.util.Locale;

public class FuncionarioTeste {

    public static void main(String[] args) {
        Pagamento pagamento = new Pagamento();

        Desenvolvedor diego = new Desenvolvedor("Diego", 5000);
        AnalistaSistema danilo = new AnalistaSistema("Danilo", 20_000);
        DepartamentoPessoal kleberton = new DepartamentoPessoal("Kleberton", 3_500);

        imprimePagamento(pagamento, diego.getNome(), diego);
        imprimePagamento(pagamento, danilo.getNome(), danilo);
        imprimePagamento(pagamento, kleberton.getNome(), kleberton);
    }

    private static void imprimePagamento(Pagamento pagamento, String nome, Remunerado remunerado) {
        System.out.printf("O salário do funcionário %s com os descontos é de %s%n", nome,
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                        .format(pagamento.pagar(remunerado)));
    }
}
