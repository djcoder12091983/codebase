package hackerearth.hiring.challenges.date06052018.tw;

import java.util.Scanner;

public class DigitsInNumber {
	
	int digits(long nth) {
		long numbers;
		int digits = 0;
		long power = 3;
		for(numbers=3; numbers<nth; power=power*3, numbers+=power) {
			digits++;
			
		}
		return digits+1;
	}
	
	public static void main(String[] args) {
		
		DigitsInNumber p = new DigitsInNumber();
		
		Scanner scanner = new Scanner(System.in);
		int tc = scanner.nextInt();

		for(int i=0; i<tc; i++) {
			long nth = scanner.nextLong();
			System.out.println(p.digits(nth));
		}

		scanner.close();
		
		/*System.out.println(p.digits(1));
		System.out.println(p.digits(4));
		System.out.println(p.digits(14));
		System.out.println(p.digits(40));*/
	}

}