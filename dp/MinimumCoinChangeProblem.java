import java.util.*;

// minimum coin change problem
public class MinimumCoinChangeProblem {

	int coins[];
	MinimumCoinChangeProblem(int coins[]) {
		this.coins = coins;
	}
	
	class Result {
		int minimumCoins;
		List<Integer> coinList = new LinkedList<Integer>();
	}
	
	// with repetition
	Result withRepetition(int value) {
		
		Map<Integer, Result> dp = new HashMap<Integer, Result>();
		
		return getMinimumCoins(value, dp);
	}
	
	// get minimum coins using recursive call
	Result getMinimumCoins(int value, Map<Integer, Result> dp) {
	
		Result cache = dp.get(value);
		if(cache != null) {
			// returns from cache
			return cache;
		}
		
		Result newResult = new Result();
		// -1 because +1 causes overflow
		newResult.minimumCoins = Integer.MAX_VALUE - 1;
		//int minimumCoins = INTEGER.MAX_VALUE;
		
		int l = coins.length;
		List<Integer> requiredCoinList = null;
		int selectedCoin = -1;
		for(int i = 0; i < l; i++) {
			
			if(value >= coins[i]) {
			
				// System.out.println("value: " + value + ", coins[i]: " + coins[i]);
			
				int nextValue = value - coins[i];
				
				Result r = null;
				int requiredCoins = 0;
				if(nextValue == 0) {
					// exactly one particular coin required
					r = new Result();
					r.minimumCoins = requiredCoins = 1;
					//r.coinList.add(coins[i]);
				} else {
					// more iteration required
					r = getMinimumCoins(nextValue, dp);
					requiredCoins = r.minimumCoins + 1;
				}
				
				if(newResult.minimumCoins > requiredCoins) {
					// updates minimum
					newResult.minimumCoins = requiredCoins;
					requiredCoinList = r.coinList;
					selectedCoin = coins[i];
				}
			}
		}
		
		// System.out.println("value: " + value + ", minimumCoins: " + newResult.minimumCoins);
		
		// updates minimum conis list, if available
		if(requiredCoinList != null) {
			newResult.coinList.add(selectedCoin);
			newResult.coinList.addAll(requiredCoinList);
		}
		// saves into cache
		dp.put(value, newResult);
		
		return newResult;
	}
	
	// a common implementation of "with/without repetition"
	Result getMinimumCoins(int value, boolean repeat) {
	
		int dp[] = new int[value + 1];
		dp[0] = 0;
		// rest are infinity
		for(int i = 1; i <= value; i++) {
			// -1 because +1 causes overflow
			dp[i] = Integer.MAX_VALUE - 1;
		}
		
		// coin details
		int coinDetails[] = new int[value + 1];
		for(int i = 0; i <= value; i++) {
			coinDetails[i] = -1;
		}
		
		int l = coins.length;
		for(int i = 0; i < l; i++) {
		
			// System.out.println("For coin: " + coins[i]);
			
			// per coin iteration determine how minimum coins required
			for(int j = 1; j <= value; j++) {
				
				// if coins[i] selected
				int remainingValue = j - coins[i];
				if(remainingValue < 0) {
					// invalid index
					continue;
				}
				
				// recurrence relation
				// if coin selected then required coins
				int requiredCoins = 1 + dp[remainingValue];
				if(requiredCoins < dp[j]) {
				
					if(!repeat) {
						// TODO for without repetition logic does not work
						// check whether used earlier for non-repeat case
						boolean reused = false;
						for(int k = remainingValue; k > 0;) {
							int coin = coins[coinDetails[k]];
							
							if(coin == coins[i]) {
								// coin reused so this minmization skipped
								reused = true;
								break;
							}
							
							// next k
							k = k - coin;
						}
						
						if(reused) {
							// coins not-reused earlier
							// no need to do minimization
							continue;
						}
					}
					
					dp[j] = requiredCoins;
					// select coin details
					coinDetails[j] = i;
				}
			}
			
			// test
			/*for(int k = 0; k <=value; k++) {
				System.out.print(coinDetails[k] + " ");
			}
			System.out.println();
			
			for(int k = 0; k <=value; k++) {
				System.out.print(dp[k] + " ");
			}
			System.out.println();*/
		}
		
		// result
		Result r = new Result();
		r.minimumCoins = dp[value];
		// coin details
		for(int i = value; i > 0;) {
			int coin = coins[coinDetails[i]];
			r.coinList.add(coin);
			
			// next i
			i = i - coin;
		}
		
		return r;
	}
	
	// another implementation of with repetition
	Result withRepetition1(int value) {
	
		return getMinimumCoins(value, true);
	}
	
	// without repetition
	Result withoutRepetition(int value) {
		// count vs coin details for each value
		List<Map<Integer, Integer>> cache = new ArrayList<Map<Integer, Integer>>(value + 1);
		
		// first element (0)
		Map<Integer, Integer> t = new TreeMap<Integer, Integer>();
		t.put(0, -1);
		cache.add(t);
		
		for(int i = 1; i <= value; i++) {
			// tree(BST) is used for better search
			cache.add(new TreeMap<Integer, Integer>());
		}
		
		int l = coins.length;
		for(int i = 0; i < l; i++) {
		
			// per coin value comes up with possibilities
			// save those into cache
			for(int j = 1; j <= value; j++) {
				
				// if coins[i] selected
				int remainingValue = j - coins[i];
				if(remainingValue < 0) {
					// invalid index
					continue;
				}
				
				Map<Integer, Integer> saved = cache.get(remainingValue);
				int s = saved.size();
				
				if(s > 0) {
					// entry found in cached result
					Map<Integer, Integer> current = cache.get(j);
					
					// update coins details
					Set<Integer> counts = saved.keySet();
					for(int count : counts) {
						
						int savedCoin = saved.get(count);
						if(savedCoin == coins[i]) {
							// skip duplicate
							continue;
						}
						
						// System.out.println("[1] For coin: " + coins[i] + ", Count: " + count + ", Coin: " + savedCoin);
						
						current.put(1 + count, coins[i]);
					}
				}
				
			}
		}
		
		Map<Integer, Integer> current = cache.get(value);
		if(cache.get(value) == null || current.isEmpty()) {
			// no suitable answer found
			Result r = new Result();
			r.minimumCoins = -1;
			
			return r;
		}
		
		Result r = new Result();
		
		// finds the best fit without repetition
		Set<Integer> counts = current.keySet();
		
		for(int count : counts) {
			
			int coin = current.get(count);
			
			// System.out.println("[1] Count: " + count + ", Coin: " + coin);
			
			List<Integer> distinctCoins = new LinkedList<Integer>();
			distinctCoins.add(coin);
				
			int oldValue = value;
			int oldCoin = coin;
			boolean duplicate = false;
			for(int k = count - 1; k > 0; k--) {
				int newValue = oldValue - oldCoin;
				Map<Integer, Integer> newCache = cache.get(newValue);
				int newCoin = newCache.get(k);
				
				if(oldCoin == newCoin)  {
					// skip duplicate
					duplicate = true;
					break;
				}
				
				distinctCoins.add(newCoin);
				
				oldValue = newValue;
				oldCoin = newCoin;
			}
			
			// all distinct
			if(!duplicate) {
				r.minimumCoins = count;
				r.coinList.addAll(distinctCoins);
			}
		}
		
		return r;
		
		// return getMinimumCoins(value, false);
	}
	
	// test
	public static void main(String a[]) {
	
		int coins[] = {1, 2, 3, 7, 8, 9, 11, 12, 13, 16, 17, 19};
		// int coins[] = {7, 2, 3, 6};
		// int coins[] = {1, 2, 3, 4, 5};
		
		int v = Integer.valueOf(a[0]);
		
		MinimumCoinChangeProblem p = new MinimumCoinChangeProblem(coins);
		Result r = p.withRepetition(v);
		
		System.out.println("--------------------Minimum coins details with repetition[1]-------------------------");
		System.out.println("Minimum coins required: " + r.minimumCoins);
		System.out.print("Required coin list: [");
		for(int coin : r.coinList) {
			System.out.print(coin + " ");
		}
		System.out.print("]\n\n");
		
		// another implementation test
		r = p.withRepetition1(v);
		
		System.out.println("--------------------Minimum coins details with repetition[2]-------------------------");
		System.out.println("Minimum coins required: " + r.minimumCoins);
		System.out.print("Required coin list: [");
		for(int coin : r.coinList) {
			System.out.print(coin + " ");
		}
		System.out.print("]\n\n");
		
		// without repeat test
		r = p.withoutRepetition(v);
		
		System.out.println("--------------------Minimum coins details  without repetition[1]-------------------------");
		System.out.println("Minimum conis required: " + r.minimumCoins);
		System.out.print("Required coin list: [");
		for(int coin : r.coinList) {
			System.out.print(coin + " ");
		}
		System.out.print("]\n\n");
	}
}