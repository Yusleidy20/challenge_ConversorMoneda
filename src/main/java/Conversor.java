import Conversor_rate.Conversor_de_moneda;

public class Conversor {
    double valorDeMoneda;

    public Conversor(Conversor_de_moneda conversionMoneda) {
        this.valorDeMoneda = conversionMoneda.conversion_rate();
    }
}
