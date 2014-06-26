import java.util.ArrayList;
import java.util.List;


	public class InfoEntry{
		private int maxPrice,minPrice;
		private List<String> distinctMonth;
		
		InfoEntry(int initPrice,String initDate){
			maxPrice = minPrice = initPrice;
			distinctMonth = new ArrayList<String>();
			distinctMonth.add(initDate);
		}
		
		public void updatePrice(int p){
			if(p > maxPrice){
				maxPrice = p;
			}
			if(p < minPrice){
				minPrice = p;
			}
		}
		
		public void updateDistinctMonth(String date){
			if(!distinctMonth.contains(date)){
				distinctMonth.add(date);
			}
		}

		public int getMaxPrice() {
			return maxPrice;
		}

		public int getMinPrice() {
			return minPrice;
		}
		
		public int getNumberOfDistinctMonth(){
			return distinctMonth.size();
		}
	}