package estrolldomezxtestencryption.encryptiondecryptiom.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estrolldomezxtestencryption.encryptiondecryptiom.services.FileEncryptDecryptService;

@RestController
@RequestMapping(path = "/api")
public class FileEncryptionController {

    @Autowired
    private final FileEncryptDecryptService encryptDecryptService;

    private String encryptedData = "";

    public FileEncryptionController(FileEncryptDecryptService encryptDecryptService) {
        this.encryptDecryptService = encryptDecryptService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptData() {
        String data = "";
        try {
            String filePath = "src/main/java/estrolldomezxtestencryption/encryptiondecryptiom/services/test.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    data += line.toString() + "\n";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            encryptedData = encryptDecryptService.encryptData(data);
            return ResponseEntity.ok(encryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Encryption failed: " + e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptData() {
        try {
            String decryptedData = encryptDecryptService.decryptData(encryptedData);
            System.out.println("decryptedData");
            System.out.println(decryptedData);
            return ResponseEntity.ok(decryptedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Decryption failed: " + e.getMessage());
        }
    }
}
