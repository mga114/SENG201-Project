package main;

class Rand{

	public static int[][] generatePrices(){
	    // This function assumes that the random seed
	    // has already been set.
	    
	    // hardcoded prices:
	    int anchorPrices[] = {
	        150, 200, 300, 400, 500, 600, 800
	    };

	    double PRICE_AMPLITUDE = 0.35; //  35 %  price amplitude

	    int[][] prices = new int[5][7];

	    for (int i=0; i < 5; i++){
	        for (int j=0; j < 7; j++){
	            double priceShift = ((Math.random() * 2.0) - 1.0) * PRICE_AMPLITUDE;
	            int anchor = anchorPrices[j];
	            int price = (int)((1.0 + priceShift) * (double)anchor);
	            price = roundTo10(price);
	            prices[i][j] = Math.max(Math.min(990, price), 100);
	        }
	    }

	    return prices;
	}
	
	public static int[][] modifyPrices(int[][] prices, double percentage) throws IllegalArgumentException {
	    // lowers prices by percentage

	    if (prices.length <= 0 || prices[0].length <= 0){
	        throw new IllegalArgumentException("prices array had length less than 0");
	    };

	    int[][] ret = new int[prices.length][prices[0].length];

	    for (int i=0; i < prices.length; i++){
	        for (int j=0; j < prices[0].length; j++){
	            ret[i][j] = prices[i][j] + (int)(((double)prices[i][j]) * percentage);
	            ret[i][j] = roundTo10(ret[i][j]);
	            ret[i][j] = Math.max(Math.min(990, ret[i][j]), 100);     
	        }
	    }

	    return ret;
	}

public static void generatePriceTest(){
    // This function should print a bunch of random prices for values
    int[][] prices = new int[5][7];
    prices = generatePrices();

    for (int i=0; i < 5; i++){
        System.out.println("=== new island ===");
        for (int j=0; j < 7; j++){
            System.out.println("price:  " + prices[i][j]);
        }
    }
}

public static int roundTo10(int price){
	return ((int)(price / 10)) * 10;
}

}
