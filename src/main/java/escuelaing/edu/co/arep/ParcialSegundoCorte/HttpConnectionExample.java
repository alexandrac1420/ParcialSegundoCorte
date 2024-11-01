package escuelaing.edu.co.arep.ParcialSegundoCorte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class HttpConnectionExample {
    private final List<String> servers = Arrays.asList("http://localhost:8081", "http://localhost:8082");
    //private final List<String> servers = Arrays.asList("http://3.80.185.161:8080", "http://3.94.208.75:8080");
    int currentIndex = 0;

    public ResponseEntity<String> linearSearch (int[] list, int value){
        String url = servers.get(currentIndex) + "/linearsearch?list="+formatArray(list)+"&value="+value;
        System.out.println("LinearSearch: "+ url);
        currentIndex = (currentIndex +1) % servers.size();
        return new RestTemplate().getForEntity(url, String.class);
    }


    public ResponseEntity<String> binarySearch (int[] list, int value){
        String url = servers.get(currentIndex) + "/binarysearch?list="+formatArray(list)+"&value="+value;
        System.out.println("BinarySearch: "+ url);
        currentIndex = (currentIndex +1) % servers.size();
        return new RestTemplate().getForEntity(url, String.class);

    }

    
    private String formatArray(int[] list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.length; i++){
            sb.append(list[i]);
            if (i < list.length -1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
