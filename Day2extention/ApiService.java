import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class ApiService {

    private static final String API_URL =
            "http://localhost:8080/patients";

    public static String addPatient(
            Patient patient) {

        try {

            Gson gson = new Gson();

            String json =
                    gson.toJson(patient);

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(new URI(API_URL))
                            .header(
                                    "Content-Type",
                                    "application/json")
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(json))
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers
                                    .ofString());

            return response.body();

        } catch (Exception e) {

            e.printStackTrace();

            return "API Error";
        }
    }

    public static List<Patient> getPatients() {

        try {

            HttpClient client =
                HttpClient.newHttpClient();

            HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(new URI(API_URL))
                        .GET()
                        .build();

            HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers
                                .ofString());

            Gson gson = new Gson();

            Patient[] patients =
                gson.fromJson(
                        response.body(),
                        Patient[].class);

            return Arrays.asList(patients);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    public static String deletePatient(
        int id) {

    try {

        HttpClient client =
                HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                new URI(
                                        API_URL + "/" + id))
                        .DELETE()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers
                                .ofString());

        return response.body();

    } catch (Exception e) {

        e.printStackTrace();

        return "Delete Failed";
    }
}
}

