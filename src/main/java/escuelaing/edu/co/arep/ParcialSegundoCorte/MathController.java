package escuelaing.edu.co.arep.ParcialSegundoCorte;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {


    @GetMapping("/linearsearch")
    public ResponseEntity<Map<String, Object>> linearSearch (@RequestParam int[] list, @RequestParam int value){
        int result = -1;
        result = linearSearchImplementation(list, value);
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "linearSearch");
        response.put("list", list);
        response.put("value", value);
        response.put("output", result);

        return ResponseEntity.ok(response); 
    }


    @GetMapping("/binarysearch")
    public ResponseEntity<Map<String, Object>> binarySearch (@RequestParam int[] list, @RequestParam int value){
        int result = -1;
        Arrays.sort(list);
        result = binarySearchImplementation(list, value);

        Map<String, Object> response = new HashMap<>();
        response.put("operation", "BinarySearch");
        response.put("list", list);
        response.put("value", value);
        response.put("output", result);

        return ResponseEntity.ok(response);

    }

    private int binarySearchImplementation(int[] array, int target) {
        int left = 0;
        int rigth = array.length -1;

        while (left <= rigth){
            int mid = left + (rigth - left)/2;

            if (array[mid] == target){
                return mid;
            }else if (array[mid] < target){
                left = mid + 1;
            }else{
                rigth = mid -1;
            }
        }

        return -1;
        
    }

    private int linearSearchImplementation(int[] array, int target) {
        for (int i = 0; i < array.length; i++){
            if(array[i]==target){
                return i;
            }
        }
        return -1;
    }

    
}
