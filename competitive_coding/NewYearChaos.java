package cp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/new-year-chaos/problem
public class NewYearChaos {
    
    // below algorithm is to find inversion count
    
    // divide and conquer
    static int minbribes(int arr[], int left, int right) { 
        int mid, ic = 0; 
        if(right > left) {
            mid = (right + left) / 2; 
      
            // divide
            ic += minbribes(arr, left, mid); 
            ic += minbribes(arr, mid + 1, right); 
      
            // conquer
            ic += mergecount(arr, left, mid + 1, right); 
        } 
        return ic;
    }
    
    // merge
    static int mergecount(int arr[], int left, int mid, int right) { 
        int i = left;
        int j = mid;
        int k = 0;
        int ic = 0;
        int temp[] = new int[right -left + 1];
        while((i <= mid - 1) && (j <= right)) { 
            if(arr[i] <= arr[j]) { 
                temp[k++] = arr[i++]; 
            } else { 
                temp[k++] = arr[j++]; 
                ic += mid - i; 
            } 
        } 
      
        while(i <= mid - 1) { 
            temp[k++] = arr[i++];
        }
      
        while(j <= right) { 
            temp[k++] = arr[j++];
        }
      
        for(i = left; i <= right; i++) { 
            arr[i] = temp[i - left];
        }
      
        return ic;
    }
    
    // checks is chaotic or not
    static boolean ischaotic(int a[]) {
        int l = a.length;
        for(int i = 0; i < l; i++) {
            if(a[i] > i + 1) {
                // check position is valid position or not
                if(a[i] - i - 1 > 2) {
                    // chaotic
                    return true;
                }
            }
        }
        
        return false; // all positions are valid
    }
    
    public static void main(String args[]) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        int t = Integer.parseInt(reader.readLine());
        while(--t >= 0) {
            // each test-case
            int n = Integer.parseInt(reader.readLine());
            int a[] = new int[n];
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            for(int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(tokens.nextToken());
            }
            
            // first check validity
            if(ischaotic(a)) {
                System.out.println("Too chaotic");
            } else {
                // minimum bribes
                System.out.println(minbribes(a, 0, n - 1));
            }
        }
        
        reader.close();
    }
}