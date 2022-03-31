package Modelo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ConexionFirebase {

    static Firestore bd = null;

    public static void conectar() throws IOException {

        File json = createJson("cajero.json", "{\n"
                + "  \"type\": \"service_account\",\n"
                + "  \"project_id\": \"cajero-1e201\",\n"
                + "  \"private_key_id\": \"6365d463e0338f1456ba4e1d5c68d91969dc8823\",\n"
                + "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDqGTQMAVj2mXg7\\n9fiSZ6zBr31W2xidYql9YMTGC/i/Fzj0nes/Ble46m+drl/yzJw1EqtDPldOAuqW\\nUabUWmuGWsTw6mG/pQ1TZuO98qNYWBJta8hT9urC5A+8bCj1KX1abH+NBQdO/jw7\\n/yqXQ7gGyXl+FmEdjl/ZEk/BmvJVIOJbwjPntB6Cv4T/bcmY+3DWBBSpdMmKHxCN\\n6N7xTpJNzr07OEY/9BaNwiTSaKgjDDWwzNMD/khD47em4QlL4pEYkjVQI0UpuX7X\\ny4FEP7d2kqMrJLahGGol7/2SA5SzH0H498Y0ia09I9ygcaKOv0X17ePnoulAeV8Y\\nnE3k+DChAgMBAAECggEAHLPGYTUbwUXZK2Y3JKGNhESOAO46uwvzwqmbYWTqX2jr\\nf3ff9gmYYSsX+vn8TaVZ/wUw9metsKTu5PX8lyDBpObQKscHVnCtjOzv6MQtKZHI\\nK0kBy48ErayPZAd8O22WLwC1NYceTvMg/izaU1Lm51N15Uhn09s2/phzgEYNMZ6V\\n2n7zY1LcS/R57Dele+5tkVeod2VyZJhY7ERDIZZBKdmDHbirdB9xwpQzq/n9o7st\\nk5T2UgmW0TSBhzZPKJAVWtw1IaNhqcdmWMGhhTo4DfaJXWIe0EYdE9Jw5wMD1esZ\\nXcuwYZBibTpkDuoaRyRs0/+M5ZBESgtgEHGfyYPzGQKBgQD1E8xxix1d1eBZkk5T\\nLhAvqlsG2TeoGe+0afn5c6HAL0oJZBPImRMl5Z3AwxeKtQggi6JcWxpI6A1r3GDF\\nDX7qAC9dYdZtP+KyXcerwLb3Xuyc5H9USFs9aDYWHgVFNgcpySec4E9U6l5F+okD\\nlpOu3KsIqgoh19LZ1OwxS4PVAwKBgQD0iCQ2uWs+tLSsWW0lIE6r6A7yMJAT4tVn\\nlxPPBjjp/Tbp8M6ErZqu9lN4jN/bV1k7kIO3EY/XK2Pa+cOgZT1BGB+6ErA5Yxqn\\nr04/ntBkljIvL5M31XL8CnrdBUuuKnwtOlXmrpPkIly1jW+Eh24B1cVok02+kDAb\\nU9GT7ePYiwKBgQDOiavvOJ08GmXjNtC5ewV7Fid2gxNLoOgJdJKNbqyvaWsMtQGT\\nOaYQdw1WiVPKurwpqWPgT4q8TIwbDXOkPbZqgFNqcGykT4W+ahg/H79Sg/YGVu8T\\n2ZLDEpMaWpx4corg5Tx0Efnph0aB1R0pH/pAEIo3WA4H7XotyCpxdMGafwKBgDCU\\ni31DwQaoJ+zFp2Vk0bxdUfrTowwQWI1H+mt6tPvnjm1TpfO9+akgztx4QFD3PYcT\\n4tcP6h+5QxFR5YeMFWtFO6hf5hrAkZ8WuNjy2DvjivzM2taQ1VM1UctvLeXgEuN5\\nX2+A52i/WuPGwYz2yugabCmmeh+NzcDgGhblgAqZAoGBAMGqBrH/tZbV8iHQv8F8\\natKZtZLcXleDe9z0i35GcjSAt1cuHHve32eTzylcBncSFYjKkpoZ0cNuDR8cJu9C\\nGId2/KJPxHxedw7H0DtGmsni/xxibczbrGBRxoVtGfp2D82EfCiBfuy7pp4CmxHT\\nOu7eezxH/VBcAuQiHnqu5vbt\\n-----END PRIVATE KEY-----\\n\",\n"
                + "  \"client_email\": \"firebase-adminsdk-xdqlv@cajero-1e201.iam.gserviceaccount.com\",\n"
                + "  \"client_id\": \"101351829859115038538\",\n"
                + "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n"
                + "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n"
                + "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n"
                + "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-xdqlv%40cajero-1e201.iam.gserviceaccount.com\"\n"
                + "}");
        
        FileInputStream serviceAccount = new FileInputStream(json);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        if (bd == null) {
            bd = FirestoreClient.getFirestore();
        }
    }

    public static File createJson(String jsonName, String jsonData) {
        File file = new File(System.getProperty("user.dir"), jsonName);
        try {

            if (!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(jsonData);
                bw.close();
                return file;
            } else {
                return file;
            }
        } catch (IOException e) {
            return file;
        } catch (Exception e) {
            return file;
        }
    }

}
