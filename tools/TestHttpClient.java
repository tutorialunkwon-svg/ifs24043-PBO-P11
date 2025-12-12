import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;

public class TestHttpClient {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        // 1) Public: POST /api/temperature/convert
        String tempJson = "{\"temperature\":25,\"unit\":\"CELSIUS\"}";
        HttpRequest tempReq = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/api/temperature/convert"))
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(tempJson))
            .build();

        System.out.println("Sending temperature request: " + tempJson);
        HttpResponse<String> tempResp = client.send(tempReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + tempResp.statusCode());
        System.out.println("Response body:\n" + tempResp.body());

        // 2) Protected: GET /api/todos without token (should be 401/403)
        HttpRequest todosReq = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/api/todos"))
            .timeout(Duration.ofSeconds(10))
            .GET()
            .build();
        HttpResponse<String> todosResp = client.send(todosReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nGET /api/todos without token -> Status: " + todosResp.statusCode());
        System.out.println(todosResp.body());

        // 3) Register a user
        String regJson = "{\"name\":\"tester\",\"email\":\"tester@example.com\",\"password\":\"secret123\"}";
        HttpRequest regReq = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/api/auth/register"))
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(regJson))
            .build();
        HttpResponse<String> regResp = client.send(regReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nRegister status: " + regResp.statusCode());
        System.out.println(regResp.body());

        // 4) Login to obtain token
        String loginJson = "{\"email\":\"tester@example.com\",\"password\":\"secret123\"}";
        HttpRequest loginReq = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/api/auth/login"))
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(loginJson))
            .build();
        HttpResponse<String> loginResp = client.send(loginReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nLogin status: " + loginResp.statusCode());
        System.out.println(loginResp.body());

        // extract token (naive parse)
        String token = null;
        String body = loginResp.body();
        int idx = body.indexOf("authToken");
        if (idx != -1) {
            int start = body.indexOf(':', idx) + 1;
            int q1 = body.indexOf('"', start);
            int q2 = body.indexOf('"', q1 + 1);
            if (q1 != -1 && q2 != -1) token = body.substring(q1 + 1, q2);
        }
        System.out.println("Token: " + token);

        // 5) Call protected endpoint with Bearer token
        if (token != null) {
            HttpRequest todosAuthReq = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/todos"))
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
            HttpResponse<String> todosAuthResp = client.send(todosAuthReq, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nGET /api/todos with token -> Status: " + todosAuthResp.statusCode());
            System.out.println(todosAuthResp.body());
        }
    }
}
