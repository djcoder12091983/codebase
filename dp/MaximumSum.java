class MaximumSum {

	int[] input;
	
	MaximumSum(int[] input) {
		this.input = input;
	}
	
	class Result {
		int sum;
		int left;
		int right;
	}
	
	Result findSum() {
	
		int l = input.length;
		if(l == 1) {
			Result r = new Result();
			r.sum = input[0];
			r.left = 0;
			r.right = 0;
			
			return r;
		}
		
		Result maxEnds = new Result();
		maxEnds.sum = 0;
		maxEnds.left = 0;
		maxEnds.right = 0;
		
		Result maxSoFar = new Result();
		maxSoFar.sum = 0;
		maxSoFar.left = 0;
		maxSoFar.right = 0;
		
		for(int i = 0; i < l; i++) {
			
			int s = maxEnds.sum + input[i];
			if(s < 0) {
				// reset
				maxEnds.sum = 0;
				maxEnds.left = i;
				maxEnds.right = i;
			} else {
				// continue
				maxEnds.sum = s;
				maxEnds.right = i;
			}
			
			if(maxSoFar.sum < maxEnds.sum) {
				// keep track of max_so_far
				maxSoFar.sum = maxEnds.sum;
				maxSoFar.left = maxEnds.left;
				maxSoFar.right = maxEnds.right;
			}
		}
		
		return maxSoFar;
	}
	
	public static void main(String a[]) {
	
		int[] input = {-2, -3, 4, -1, -2, 1, 5, -3};
		
		MaximumSum ms = new MaximumSum(input);
		Result r = ms.findSum();
		
		System.out.println("Range: [" + (r.left + 1) + " - " + (r.right + 1) + "]" + ", Sum: " + r.sum);
	}
}