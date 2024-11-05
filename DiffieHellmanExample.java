public class DiffieHellmanExample {

    // Método para calcular (base^expoente) de forma manual, usando exponenciação modular
    private static int powerMod(int base, int expoente, int mod) {
        int resultado = 1;  
        base = base % mod;

        while (expoente > 0) {
            // Se expoente for ímpar, multiplica o resultado pelo base
            if ((expoente & 1) == 1) {
                resultado = (resultado * base) % mod;
            }
            //deslocamento de bits à direita
            expoente = expoente >> 1;
            base = (base * base) % mod;
        }
        return resultado;
    }
    public static void main(String[] args) {
        //Conhecidos publicamente
        int p = 13; // Número primo
        int g = 5;  // Gerador

        int a = 6; // Chave privada da Alice 
        int A = powerMod(g, a, p);

        int b = 15; // Chave privada do Bob 
        int B = powerMod(g, b, p);

        // Alice e Bob agora trocam suas chaves públicas A e B
        System.out.println("Chave pública de Alice (A): " + A);
        System.out.println("Chave pública de Bob (B): " + B);

        int chaveSecretaAlice = powerMod(B, a, p);
        int chaveSecretaBob = powerMod(A, b, p);

        // As duas chaves secretas calculadas devem ser iguais
        System.out.println("Chave secreta calculada por Alice: " + chaveSecretaAlice);
        System.out.println("Chave secreta calculada por Bob: " + chaveSecretaBob);

        if (chaveSecretaAlice == chaveSecretaBob) {
            System.out.println("A chave secreta foi compartilhada com sucesso!");
        } else {
            System.out.println("Erro na troca de chaves!");
        }
    }
}
