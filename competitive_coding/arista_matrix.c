#include<stdio.h>
#include<stdlib.h>

int main() {
    
    int t, n, m, k, i, j, c, max;
    int **DP;
    char *A;
    
    scanf("%d", &t);
    while(t--) {
        scanf("%d%d%d", &n, &m, &k);
        
        DP = (int **)malloc((n + 1) * sizeof(int *));
        for(i = 0; i <= n; i++) {
            DP[i] = (int *)malloc((m + 1) * sizeof(int));
        }
        
        // base cases
        for(i = 0; i <= n; i++) {
            DP[i][0] = 0;
        }
        for(i = 0; i <= m; i++) {
            DP[0][i] = 0;
        }
        
        // pre-process
        A = (char *)malloc((m + 1) * sizeof(char));
        for(i = 1; i <= n; i++) {
            scanf("%s", A);
            for(j = 1; j <= m; j++) {
                DP[i][j] = (A[j - 1] == '*') + DP[i - 1][j] + DP[i][j - 1] - DP[i - 1][j - 1];
            }
        }
        // solution
        max = 0;
        for(i = 0; i <= n - k; i++) {
            for(j = 0; j <= m - k; j++) {
                c = DP[i + k][j + k] - DP[i][j + k] - DP[i + k][j] + DP[i][j];
                if(c > max) {
                    // new max
                    max = c;
                }
            }
        }
        printf("%d\n", max);
        
        // free memory
        for(i = 0; i <= n; i++) {
            free(DP[i]);
        }
        free(DP);
        free(A);
    }
    
    return 0;
}
