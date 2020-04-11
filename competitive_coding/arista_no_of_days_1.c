#include<stdio.h>
#include<stdlib.h>

// find far position for given key
int find_far(int *A, int l, int r, int key) {
    int mid, p;
    p = -1;
    while(l <= r) {
        mid = (l + r) / 2;
        if(A[mid] <= key) {
            // take right
            if(A[mid] == key) {
                // found last position
                p = mid;
            }
            l = mid + 1;
        } else {
            // take left
            r = mid - 1;
        }
    }
    return p; 
}

int main() {
    
    int t, n, i, max, req, p, c;
    char *A;
    int *C0, *C1;
    
    scanf("%d", &t);
    while(t--) {
        scanf("%d", &n);
        
        A = (char *)malloc((n + 1) * sizeof(char));
        scanf("%s", A);
        
        C0 = (int *)malloc((n + 1) * sizeof(int));
        C1 = (int *)malloc((n + 1) * sizeof(int));
        C0[0] = 0;
        C1[0] = 0;
        
        // first pass: pre-process count(0/1)
        for(i = 0; i < n; i++) {
            if(A[i] == '0') {
                C0[i + 1] = C0[i] + 1;
                C1[i + 1] = C1[i];
            } else {
                C1[i + 1] = C1[i] + 1;
                C0[i + 1] = C0[i];
            }
        }
        
        max = 0;
        // second pass: count maximum result
        for(i = 1; i <= n; i++) {
            req = C0[i - 1] + 1;
            p = find_far(C0, i, n, req);
            if(p == -1) {
                // no 0 found
                req = C0[i - 1];
                p = find_far(C0, i, n, req);
                c = C1[p] - C1[i - 1];
            } else {
                // inlcude 0
                c = C1[p] - C1[i - 1] + 1;
            }
            if(c > max) {
                // new max
                max = c;
            }
        }
        
        printf("%d\n", max);
        
        free(A);
        free(C0);
        free(C1);
    }
    
    return 0;
}
