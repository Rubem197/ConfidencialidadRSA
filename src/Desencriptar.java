import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

public class Desencriptar {

    public static void main(String[] args) throws Exception {
        String ficheroEncriptado = "encriptado.txt";
        String ficheroDesencriptado = "desencriptado.txt";

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("privateKey.txt"));
        PrivateKey privateKey = (PrivateKey) inputStream.readObject();
        inputStream.close();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptFile(ficheroEncriptado, ficheroDesencriptado, cipher);

        System.out.println("Archivo descifrado con éxito.");
    }

    private static void decryptFile(String inputFile, String outputFile, Cipher cipher) throws Exception {
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
