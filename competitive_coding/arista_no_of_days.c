#include<stdio.h>
#include<stdlib.h>

int main() {

	int t, n, i, c1, max1, j, *T, c2, c3, c;
	char *A;

	scanf("%d", &t);
	while(t--) {

		scanf("%d", &n);
		A = (char *)malloc((n + 1) * sizeof(char));
		T = (int *)malloc(n * sizeof(int)); // helper
		scanf("%s", A);

		// solution
		c1 = 0;
		j = 0;
		for(i = 0; i < n; i++) {
			if(A[i] == '0') {
				if(c1 > 0) {
					T[j++] = c1;
				}
				c1 = 0;
				T[j++] = c1;
			} else {
				c1++;
			}
		}
		if(c1 > 0) {
			T[j++] = c1;
		}
		if(j == 1) {
			// all 1's
			printf("%d\n", T[0]);
		} else {
			// second pass
			max1 = 0;
			for(i = 0; i < j - 1; i++) {
				c1 = T[i];
				c2 = T[i + 1];
				if(c1 == 0 && c2 == 0) {
					c = 0;
				} else if(c1 == 0) {
					c = c2 + 1;
				} else {
					if(i + 2 < j) {
						c3 = T[i + 2];
						if(c3 > 0) {
							c = c1 + c3 + 1;
						} else {
							c = c1 + 1;
						}
					} else {
						c = c1 + 1;
					}
				}

				if(c > max1) {
					max1 = c;
				}
			}
			printf("%d\n", max1);
		}
	}

	return 0;
}
