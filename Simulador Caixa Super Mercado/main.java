import java.util.List;
import java.util.ArrayList;

public class main {

    public static void main(String... args) {

        final int NUMERO_SIMULACOES = 1000;
        final List<Double> mediasAtendimento = new ArrayList<>();

        SimulacaoCaixaSupermercado simulador = new SimulacaoCaixaSupermercado();

        for (int i = 0; i < NUMERO_SIMULACOES; i++) {
            simulador.setNumeroCaixas(1);
            simulador.setMediaAtendimentos(100);
            simulador.setMediaTempoAtendimentoPorCliente(5.00);
            simulador.setDesvioPadraoTempoAtendimentoPorCliente(2);

            double mediaAtendimento = simulador.simular();
            mediasAtendimento.add(mediaAtendimento);
        }

        double media = media(mediasAtendimento);
        double dp = desvioPadrao(mediasAtendimento, media);

        System.out.printf("Média dos tempos de atendimento (%.0f simulações): %.3f min%n",
                (double) NUMERO_SIMULACOES, media);
        System.out.printf("Desvio-padrão das médias: %.3f min%n", dp);
    }

    private static double media(List<Double> xs) {
        double s = 0.0;
        for (double x : xs)
            s += x;
        return s / xs.size();
    }

    private static double desvioPadrao(List<Double> xs, double m) {
        double s2 = 0.0;
        for (double x : xs) {
            double d = x - m;
            s2 += d * d;
        }
        return Math.sqrt(s2 / (xs.size() - 1));
    }
}