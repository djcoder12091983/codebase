package hackerearth.hiring.challenges.date07012018.capillary;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Point {
	int x;
	int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	static BigDecimal distance(Point point1, Point point2) {
		int xDiff = point1.x - point2.x;
		int yDiff = point1.y - point2.y;
		
		return new BigDecimal(Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));
	}
}

class Circle {
	
	Point center;
	int radius;
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	void updateRadius(int radius) {
		this.radius = radius;
	}
	
	static boolean cross(Circle circle1, Circle circle2) {
		
		BigDecimal distance = Point.distance(circle1.center, circle2.center);
		
		// they crossed or touched
		BigDecimal sum = new BigDecimal(circle1.radius + circle2.radius);
		return sum.compareTo(distance) >= 0;
	}
	
	boolean isPointInside(Point point) {
		
		BigDecimal distance = Point.distance(this.center, point);
		
		// point is inside or on the circumference
		return new BigDecimal(this.radius).compareTo(distance) >= 0;
	}
}

public class IncenseSticksFragrance {
	
	Circle incenseCircle1;
	Circle incenseCircle2;
	
	List<Point> locations = new LinkedList<Point>();
	
	public IncenseSticksFragrance(Point incenseCenter1, Point incenseCenter2) {
		this.incenseCircle1 = new Circle(incenseCenter1, 0);
		this.incenseCircle2 = new Circle(incenseCenter2, 0);
	}
	
	void addLocation(Point location) {
		locations.add(location);
	}
	
	int[] findLocations(int[] queries) {
		
		int l = queries.length;
		int affectedLocations[] = new int[l];
		
		int affectedLocationCounter = 0;
		boolean crossFlag = false;
		for(int i = 0; i < l; i++) {
			
			int query = queries[i];
			incenseCircle1.updateRadius(query);
			incenseCircle2.updateRadius(query);
			
			if(!crossFlag) {
				// not reacted
				crossFlag = Circle.cross(incenseCircle1, incenseCircle2);
			}
			
			if(crossFlag) {
				// reacted
				
				Iterator<Point> locationsIterator = locations.iterator();
				while(locationsIterator.hasNext()) {
					Point location = locationsIterator.next();
					
					boolean inside1 = incenseCircle1.isPointInside(location);
					boolean inside2 = incenseCircle2.isPointInside(location);
					
					if(inside1 || inside2) {
						// location affected
						affectedLocationCounter++;
						// no need to check the point again
						locationsIterator.remove();
					}
				}
			}
			
			affectedLocations[i] = affectedLocationCounter;
		}
		
		return affectedLocations;
	}
	
	public static void main(String args[] ) throws Exception {
        /* Sample code to perform I/O:
         * Use either of these methods for input

        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();                // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        //Scanner
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();                 // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        */

        // Write your code here
		Point incenseCenter1 = new Point(1, 2);
		Point incenseCenter2 = new Point(4, 5);
		IncenseSticksFragrance p = new IncenseSticksFragrance(incenseCenter1, incenseCenter2);
		p.addLocation(new Point(2, 3));
		p.addLocation(new Point(5, 6));
		p.addLocation(new Point(7, 8));
		
		int queries[] = new int[]{1, 6, 15};
		
		int affectedLocations[] = p.findLocations(queries);
		for(int locations : affectedLocations) {
			System.out.println(locations);
		}
		
		//Scanner
        /*Scanner scanner = new Scanner(System.in);
        
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();
        Point incenseCenter1 = new Point(x1, y1);
		Point incenseCenter2 = new Point(x2, y2);
		
		IncenseSticksFragrance p = new IncenseSticksFragrance(incenseCenter1, incenseCenter2);
		
		// add locations
		int count = scanner.nextInt();
		int x[] = new int[count];
		int y[] = new int[count];
		for(int i = 0; i < count; i++) {
			x[i] = scanner.nextInt();
		}
		for(int i = 0; i < count; i++) {
			y[i] = scanner.nextInt();
		}
		
		for(int i = 0; i < count; i++) {
			p.addLocation(new Point(x[i], y[i]));
		}
		
		// queries
		count = scanner.nextInt();
		int queries[] = new int[count];
		for(int i = 0; i < count; i++) {
			queries[i] = scanner.nextInt();
		}
		
		// run
		int affectedLocations[] = p.findLocations(queries);
		for(int locations : affectedLocations) {
			System.out.print(locations + " ");
		}
		System.out.println();
		
		scanner.close();*/
		
    }

}
