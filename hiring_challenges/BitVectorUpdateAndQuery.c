#include<stdlib.h>
#include<stdio.h>

// global
int size;
int requiredSize;
unsigned long *vector;
int long_size = sizeof(unsigned long);

void load(int l) {
	int i;
	size = l;
	int requiredSize = (l / long_size) + 1;
	vector = (unsigned long*)malloc(requiredSize * long_size);
	for(i = 0; i < requiredSize; i++) {
		vector[i] = 0;
	}
}

unsigned long pow2(int x) {
	int i;
	int l = long_size - x; 
	unsigned long p = 1;
	for(i = 0; i <= l; i++) {
		p *= 2;
	}
	return p;
}

void update(int index) {
	int requiredIndex = (index / long_size) + 1;
	int internalIndex = index - (requiredIndex - 1)*long_size; // right most bit
	unsigned long orMask = pow2(internalIndex -1);
	
	vector[requiredIndex] |= orMask; // set to 1
}

int query(int index) {
	int requiredIndex = (index / long_size) + 1;
	int internalIndex = index - (requiredIndex - 1)*long_size; // right most bit
	unsigned long andMask = pow2(internalIndex - 1);
	
	unsigned long r = vector[requiredIndex] & andMask;
	return r == 0 ? 0 : 1;
}

// using bit vector
int main() {
	
	int l = 1000;
	load(l);
	
	// update and query
	update(10);
	printf("%d\n", query(9));
	printf("%d\n", query(10));
	printf("%d\n", query(11));	
}
