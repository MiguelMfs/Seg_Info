import java.nio.charset.StandardCharsets; //Define o conjunto de caracteres UTF-8 (necessário para conversão de strings em bytes).
import java.security.InvalidKeyException;
import java.security.KeyPair; //Representa um par de chaves (pública e privada).
import java.security.KeyPairGenerator; //Usado para gerar o par de chaves RSA.
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64; //Codifica/decodifica bytes para String Base64 (um formato legível de dados binários).

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher; //Realiza operações criptográficas (encriptação/descriptação).
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
//Importacoes das bibliotecas necessarias 

public class RSA {

	public static void main(String[] args) {
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA"); //Instancia um gerador de par de chaves que usa o algoritmo "RSA"
			gen.initialize(2048); //Inicializa o gerador de chaves com o tamanho da chave de 2048 bits (um tamanho seguro para RSA).
			KeyPair pair = gen.generateKeyPair(); //Gera o par de chaves RSA (contém uma chave pública e uma chave privada).
			System.out.println(" ");
      
      // Armazena as chaves
			PrivateKey privateKey = pair.getPrivate(); //Obtém a chave privada do par gerado.
			PublicKey publicKey = pair.getPublic(); //Obtém a chave pública do par gerado.

			String secretMessage = "Bem vindos ao IFSC";

			Cipher encryptCipher = Cipher.getInstance("RSA"); //Cria um objeto Cipher que usará o algoritmo "RSA".
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey); //Inicializa o Cipher no modo de encriptação (ENCRYPT_MODE) usando a chave PÚBLICA.

			byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8); //Converte a mensagem secreta para um array de bytes usando o conjunto de caracteres UTF-8.
			byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes); //Encripta os bytes da mensagem utilizando a chave pública. O resultado é um array de bytes encriptado.

			String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes); //Converte os bytes encriptados para uma string codificada em Base64

			System.out.println(encodedMessage); //Imprime no console a versão criptografada e codificada da mensagem.

			Cipher decryptCipher = Cipher.getInstance("RSA"); //Cria outro objeto Cipher para descriptografar os dados.
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey); //Inicializa o Cipher no modo de descriptografia (DECRYPT_MODE) utilizando a chave privada.

			byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes); //Descriptografa os bytes encriptados usando a chave privada.
			String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8); //Converte os bytes descriptografados de volta para uma string utilizando o conjunto de caracteres UTF-8.

			System.out.println(decryptedMessage); //Imprime no console a mensagem original, agora descriptografada.

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
      //tratamento de excecoes 
      //Se o algoritmo especificado ("RSA") não for encontrado.
      //Se houver um problema com o padding (preenchimento) da criptografia.
      //Se uma chave inválida for usada
      //Se o tamanho do bloco de dados for incorreto.
      //Se houver um erro no padding ao descriptografar
      //Se qualquer uma dessas exceções ocorrer, o programa imprimirá o erro no console.
		}

	}

}
