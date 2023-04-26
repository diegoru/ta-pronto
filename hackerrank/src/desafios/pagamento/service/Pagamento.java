package desafios.pagamento.service;

import desafios.pagamento.model.Remunerado;

public class Pagamento {

    public double pagar(Remunerado remunerado) {
        return remunerado.calculaRemuneracao();
    }
}
