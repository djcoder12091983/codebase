#include<stdio.h>
#include<stdlib.h>
#include<string.h>

// find actual index (in case of after rotations) using modulo arithmetic
// note: index is 0 based
int find_index(int rotations, int idx, int N) {
    int t = idx - rotations;
    if(t >= 0) {
        return t % N;
    } else {
        // negative case
        return (N - (-1 * t)) % N;
    }
}

int main() {
    
    int N, i, Q, v, idx, rotations;
    long long int *A;
    char op[10];
    
    scanf("%d", &N);
    
    A = (long long int *)malloc(N * sizeof(long long int));
    for(i = 0; i < N; i++) {
        scanf("%lu", A + i);
    }
    
    scanf("%d", &Q);
    
    // query
    rotations = 0;
    while(Q--) {
        scanf("%s", op);
        if(strcmp(op, "Left") == 0) {
            // left rotations
            rotations--;
        } else if(strcmp(op, "Right") == 0) {
            // right rotations
            rotations++;
        } else if(strcmp(op, "Update") == 0) {
            // update @ index by given value
            scanf("%d", &idx);
            idx = find_index(rotations, idx - 1, N);
            scanf("%lu", A + idx);
        } else if(strcmp(op, "Increment") == 0) {
            // increment @ index by 1
            scanf("%d", &idx);
            idx = find_index(rotations, idx - 1, N);
            A[idx]++;
        } else if(strcmp(op, "?") == 0) {
            // find @ index
            scanf("%d", &idx);
            idx = find_index(rotations, idx - 1, N);
            printf("%lu\n", A[idx]);
        }
    }
    
    // relases memory
    free(A);
    
    return 0;
}
