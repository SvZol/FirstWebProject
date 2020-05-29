import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MyPost {
    public static void main() throws IOException {
        URL url = new URL("https://localhost:9091");
        URLConnection connection = url.openConnection();
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(1);
        outputStream.flush();

    }
}
