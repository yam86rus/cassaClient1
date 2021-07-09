import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CassaClient1 {

    static int errorsCount = 0;

    public static void main(String[] args) {

        while (true) {
            URL url = null;
            String ipAdress = null;
            String hostName = null;

            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ipAdress = inetAddress.getHostAddress();
                hostName = inetAddress.getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            try {
                url = new URL("http://169.168.9.136:8077/api/cassaClient");
//                url = new URL("http://31.40.15.2:40002/api/cassaClient");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(hostName, ipAdress);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                try {
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                postData.append('=');
                try {
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            byte[] postDataBytes = new byte[0];
            try {
                postDataBytes = postData.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {

                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            try {
                conn.getOutputStream().write(postDataBytes);
            } catch (IOException e) {
            }

            Reader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (IOException e) {
            }

            try {

                for (int c; (c = in.read()) >= 0; )
                    System.out.print((char) c);
                errorsCount = 0;
            } catch (Exception e) {

            }

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
