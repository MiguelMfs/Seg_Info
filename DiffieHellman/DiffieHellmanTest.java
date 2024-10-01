import java.math.BigInteger;

public class DiffieHellmanTest {
    public static void main(String[] args) {
        // Define o modulo (N) e o gerador (G)
        BigInteger N = new BigInteger("23");  // Exemplo de pequeno modulo
        BigInteger G = new BigInteger("5");   // Exemplo de gerador

        // Cria dois objetos DiffieHellman representando duas partes (A e B)
        DiffieHellman Miguel = new DiffieHellman(N, G);
        DiffieHellman Isa = new DiffieHellman(N, G);

        // Cada um calcula sua chave privada usando o valor publico do outro
        Miguel.calculateSharedKey(Isa.getPublicValue());
        Isa.calculateSharedKey(Miguel.getPublicValue());

        // Mostra a chave privada compartilhada
        System.out.println("Chave privada calculada por Miguel: " + Miguel.getSharedKey());
        System.out.println("Chave privada calculada por Isa: " + Isa.getSharedKey());

        // Verifica se as chaves sao iguais (devem ser)
        if (Miguel.getSharedKey().equals(Isa.getSharedKey())) {
            System.out.println("A troca de chaves Diffie-Hellman foi bem-sucedida!");
        } else {
            System.out.println("Falha na troca de chaves.");
        }
    }
}
