import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    private BigInteger N;  // Modulo
    private BigInteger G;  // Gerador, numero que sera elevado para gerar chaves 
    private BigInteger S;  // Numero privado do usuario 
    private BigInteger publicValue;  // Valor publico gerado, a partir do numero privado 
    private BigInteger sharedKey;    // chave compartilhada calculada com base no valor publico do outro usuario
    
    // Construtor que define o modulo (N) e o gerador (G)
    public DiffieHellman(BigInteger N, BigInteger G) {
        this.N = N;
        this.G = G;
        this.S = generatePrivateNumber();  // Gera o numero privado S
        this.publicValue = G.modPow(S, N);  // Calcula o valor publico
    }

    // Gera o numero privado (S) aleatorio
    private BigInteger generatePrivateNumber() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(N.bitLength() - 1, random);
    }

    // Obtem o valor publico calculado
    public BigInteger getPublicValue() {
        return publicValue;
    }

    // Calcula a chave privada (K) com base no valor publico do outro lado
    public void calculateSharedKey(BigInteger otherPublicValue) { // formula K=(otherPublicValueS)mod  NK=(otherPublicValueS)modN
        this.sharedKey = otherPublicValue.modPow(S, N);  // K = (outroValorPublico ^ S) % N
    }

    // Obtem a chave privada compartilhada
    public BigInteger getSharedKey() {
        return sharedKey;
    }
}
