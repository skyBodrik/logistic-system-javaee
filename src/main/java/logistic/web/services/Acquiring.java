package logistic.web.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Acquiring {

    /**
     * Создаёт запрос на оплату к системе приёма платежей
     * @return
     */
    public boolean toPay() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost/payment.php");
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            String strJson = "";
            while ((line = rd.readLine()) != null) {
                strJson += line;
            }
            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(strJson).getAsJsonObject();
            JsonElement statusCode = mainObject.get("code");
            JsonElement message = mainObject.get("message");
            if (statusCode.getAsInt() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
