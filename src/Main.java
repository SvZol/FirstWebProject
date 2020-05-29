import java.io.*;
import java.net.*;

public class Main {
    static ServerSocket ss;
    static String ip = "localhost";
    static String string;

    public static void main(String[] args) throws Throwable {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ss = new ServerSocket(9091, 0, InetAddress.getByName(ip));
        while(!"stop".equals(string)) {
            string = reader.readLine();
                Socket s = ss.accept();
                //System.err.println("Client accepted");
                new Thread(new SocketProcessor(s)).start();
        }
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                readInputHeaders();
                writeResponse("<html><body><h1>"+ string +"</h1></body></html> ");
            } catch (Throwable t) {
            }
            //System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
                String response = "HTTP/1.1 200 OK\r\n" +
                        "Server: YarServer/2009-09-09\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + s.length()+ "\r\n" +
                        "Connection: open\r\n\r\n";

                String result = response + s;
                os.write(result.getBytes());
                os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}