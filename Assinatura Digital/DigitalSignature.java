import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class DigitalSignature {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public DigitalSignature() throws NoSuchAlgorithmException {
        // Gera as chaves pública e privada usando o algoritmo RSA
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Tamanho da chave
        KeyPair keyPair = keyGen.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // Gera o hash da mensagem usando SHA-256
    public byte[] generateHash(String message) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(message.getBytes());
    }

    // Assina a mensagem (criptografa o hash com a chave privada)
    public String signMessage(String message) throws Exception {
        byte[] messageHash = generateHash(message);
        
        // Criptografa o hash com a chave privada
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signature = cipher.doFinal(messageHash);

        // Anexa a assinatura à mensagem original (codificada em Base64 para facilitar a manipulação)
        return message + "||" + Base64.getEncoder().encodeToString(signature);
    }

    // Verifica a assinatura da mensagem
    public boolean verifySignature(String signedMessage) throws Exception {
        // Divide a mensagem e a assinatura
        String[] parts = signedMessage.split("\\|\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Mensagem assinada inválida.");
        }

        String message = parts[0];
        byte[] signature = Base64.getDecoder().decode(parts[1]);

        // Gera o hash da mensagem recebida
        byte[] messageHash = generateHash(message);

        // Descriptografa a assinatura usando a chave pública
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedSignatureHash = cipher.doFinal(signature);

        // Compara o hash descriptografado com o hash da mensagem
        return MessageDigest.isEqual(messageHash, decryptedSignatureHash);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public static void main(String[] args) {
        try {
            DigitalSignature ds = new DigitalSignature();

            // Exemplo de mensagem a ser assinada
            String message = "Este é um documento confidencial.";

            // Assina a mensagem
            String signedMessage = ds.signMessage(message);
            System.out.println("Mensagem Assinada: " + signedMessage);

            // Verifica a assinatura
            boolean isValid = ds.verifySignature(signedMessage);
            System.out.println("Assinatura Válida: " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
