// matrix related problem
public class Matrix {

    int matrix[][];
    
    Matrix(int matrix[][]) {
        this.matrix = matrix;
    }
    
    class Result {
        
        int left;
        int top;
        int right;
        int bottom;
    }
    
    // find max rectengular with one
    Result findMaximumRectangleWith1() {
    
        // this method assumes that matrix is fill with 1 or 0
        // no check is applied TODO (validation should be applied before proceed)
        
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        int oldAreaOf1 = 0;
        
        int cols = matrix[0].length;
        int rows = matrix.length;
		
		// System.out.println("Cols: " +  cols + ", Rows: " + rows);
		
        boolean columnVector[] = new boolean[rows];
        
        for(int cc = 0; cc < cols; cc++) {
        
            for(int i = 0; i < rows; i++) {
                columnVector[i] = true;
            }
            
            for(int i = cc; i < cols; i++) {
                
                //boolean previous = columnVector[0] & (matrix[0] == 1);
                int p1 = 0;
                int p2 = 0;
                for(int j = 0; j < rows;) {
                    boolean andedValue = columnVector[j] & (matrix[j][i] == 1);
                    columnVector[j] = andedValue;
					
					// System.out.println("[outside] j: " + j + ", andedValue: " + andedValue);
                    
                    if(andedValue) {
						// 1 starts
                        int newP1 = j;
                        int newP2 = j;
						
						j++;
                        for(int k = j; k < rows; k++, j++) {
                            andedValue = columnVector[k] & (matrix[k][i] == 1);
                            columnVector[k] = andedValue;
							
							// System.out.println("[inside] j: " + j + ", andedValue: " + andedValue);
                            
                            if(!andedValue) {
                                // sequence break
                                break;
                            }
                            
                            newP2++;
                        }
                        
                        if((newP2 - newP1) > (p2 - p1)) {
                            // new range found
                            p1 = newP1;
                            p2 = newP2;
                        }
                    } else {
						j++;
					}
                }
                
                // new are of 1's
                int newLeft = cc;
                int newTop = p1;
                int newRight = i;
                int newBottom = p2;
                
                int newAreaOf1 = ((newRight - newLeft) + 1) * ((newBottom - newTop) + 1);
                // int oldAreaOf1 = ((right - left) + 1) * ((bottom - top) + 1);
                
                if(newAreaOf1 > oldAreaOf1) {
                    oldAreaOf1 = newAreaOf1;
                    
                    // update positions
                    left = newLeft;
                    top = newTop;
                    right = newRight;
                    bottom = newBottom;
                }
            }
        }
		
		Result r = new Result();
		r.left = left;
		r.top = top;
		r.right = right;
		r.bottom = bottom;
		
		return r;
    }
	
	public static void main(String a[]) {
		
		int matrix[][] = {
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0},
			{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
			{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
			{0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
			{0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
			{0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
			{0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
			{0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0}
		};
		
		Matrix m = new Matrix(matrix);
		Result r = m.findMaximumRectangleWith1();
		
		System.out.println("(" + r.left + ", " + r.top + ") (" + r.right + ", " + r.bottom + ")");
	}
}