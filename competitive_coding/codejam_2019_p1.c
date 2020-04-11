#include<stdio.h>
#include<string.h>

// get abc for a given digit
void get_abc(int digit, int *a, int *b, int *c) {
    if(digit <= 1) {
        digit += 10;
        *c = 1;
    } else {
        *c = 0;
    }
    if(digit == 6) {
        *a = 3;
    } else if(digit == 2) {
        *a = 1;
    } else {
        *a = 2;
    }
    *b = digit - *a;
}

// string reverse
void str_rev(char *S, int N) {
    int i;
    char t;
    for(i = 0; i < N / 2; i++) {
        t = S[i];
        S[i] = S[N - 1 - i];
        S[N - 1 - i] = t;
    }
}

int main() {
    int T, i, j, digit, a, b, c, l, tc;
    char input[105], A[105], B[105];
    
    scanf("%d", &T);
    tc = 1;
    while(tc <= T) {
        scanf("%s", input);
        l = strlen(input);
        i = l - 1, j = 0, c = 0;
        while(i >= 0) {
            digit = input[i];
            digit -= 48;
            digit -= c;
            if(digit == 0 && i == 0) {
                // last digit
                A[j] = 0;
                str_rev(A, j);
                break;
            } else if(digit == 1 && i == 0) {
                // if last digit is 1
                A[j] = 49;
                A[j + 1] = 0;
                str_rev(A, j + 1);
                break;
            } else {
                get_abc(digit, &a, &b, &c);
                A[j] = a + 48;
                B[j] = b + 48;
                j++;
                if(i == 0) {
                    A[j] = 0;
                    str_rev(A, j);
                }
            }
            i--;
        }
        B[j] = 0;
        str_rev(B, j);
        printf("Case #%d: %s %s\n", tc, A, B);
        tc++;
    }
    return 0;
}
