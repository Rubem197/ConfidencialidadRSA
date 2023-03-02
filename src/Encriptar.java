import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

public class Encriptar {

    public static void main(String[] args) throws Exception {
        String ficheroEntrada = "ficheroEntrada.txt";
        String ficheroEncriptado = "encriptado.txt";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptFile(ficheroEntrada, ficheroEncriptado, cipher);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("privateKey.txt"));
        outputStream.writeObject(privateKey);
        outputStream.close();

        System.out.println("Archivo cifrado con éxito.");
    }

    private static void encryptFile(String inputFile, String outputFile, Cipher cipher) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = inputStream.read(buffer)) > 0) {
                cipherOutputStream.write(buffer, 0, count);
            }
        }
    }
}
