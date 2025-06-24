import java.util.Random;

public class GeradorDados {
    public static Registro[] gerarRegistros(int quantidade, long seed) {
        Registro[] registros = new Registro[quantidade];
        Random random = new Random(seed);

        int min = 100000000;
        int max = 999999999;

        for (int i = 0; i < quantidade; i++) {
            int codigo = random.nextInt(max - min + 1) + min;
            registros[i] = new Registro(codigo);
        }

        return registros;
    }
}
