import Conversor_rate.Conversor_de_moneda;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorDeMoneda {

    double valorAConvertir = 0;
    int opcionDelUsuario = 0;

    String menu = """
                ********************************************************
                *                                                      *
                *       Bienvenido al Conversor de Moneda =)           *
                *                                                      *
                ********************************************************
                *                                                      *
                *    1) Dólar (USD) =>> Peso Argentino (ARS)           *
                *    2) Peso Argentino (ARS) =>> Dólar (USD)           *
                *    3) Dólar (USD) =>> Real Brasileño (BRL)           *
                *    4) Real Brasileño (BRL) =>> Dólar (USD)           *
                *    5) Dólar (USD) =>> Peso Colombiano (COP)          *
                *    6) Peso Colombiano (COP) =>> Dólar (USD)          *
                *    7) Salir                                          *
                *                                                      *
                *  Digite una opción del 1 al 6 para continuar, si     *
                *  desea finalizar la aplicación, digite la opción 7.  *
                *                                                      *
                ********************************************************
                """;

    public void opcionesElegibles() {
        Scanner lectura = new Scanner(System.in);

        while (opcionDelUsuario != 7) {
            System.out.println(menu);
            System.out.print("Seleccione una opción: ");

            try {
                opcionDelUsuario = lectura.nextInt();
                if (opcionDelUsuario != 7) {
                    System.out.print("Ingrese el valor a convertir: ");
                    valorAConvertir = lectura.nextDouble();
                }
            } catch (Exception e) {
                System.out.println("Opción inválida. Por favor, ingrese una opción del 1 al 6 o si desea finalizar la aplicación, digite la opción 7");
                lectura.next(); // Limpiar el scanner
                continue;
            }

            switch (opcionDelUsuario) {
                case 1:
                    obtenerConversion("USD", "ARS", valorAConvertir);
                    break;
                case 2:
                    obtenerConversion("ARS", "USD", valorAConvertir);
                    break;
                case 3:
                    obtenerConversion("USD", "BRL", valorAConvertir);
                    break;
                case 4:
                    obtenerConversion("BRL", "USD", valorAConvertir);
                    break;
                case 5:
                    obtenerConversion("USD", "COP", valorAConvertir);
                    break;
                case 6:
                    obtenerConversion("COP", "USD", valorAConvertir);
                    break;
                case 7:
                    System.out.println("Finalizando aplicación. Gracias por utilizar nuestros servicios");
                    break;
                default:
                    System.out.println("Esta opción no está disponible. Por favor, ingrese una opción del 1 al 6 o si desea finalizar la aplicación, digite la opción 7");
                    break;
            }
        }
    }

    public void obtenerConversion(String monedaSinConvertir, String monedaConvertida, double valorAConvertir) {
        Gson gson = new Gson();

        try {
            String direccion = "https://v6.exchangerate-api.com/v6/87a9fe6a962c5f3f9504b1de/pair/" + monedaSinConvertir + "/" + monedaConvertida;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            Conversor_de_moneda conversorMoneda = gson.fromJson(json, Conversor_de_moneda.class);
            Conversor conversion = new Conversor(conversorMoneda);
            double multiplicacion = conversion.valorDeMoneda * valorAConvertir;
            System.out.println("El valor de " + valorAConvertir + " [" + monedaSinConvertir + "] es igual a " + multiplicacion + " [" + monedaConvertida + "]");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
