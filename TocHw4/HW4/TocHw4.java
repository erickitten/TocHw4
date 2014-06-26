
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import org.json.*;


public class TocHw4 {
	
	public static final int[] DEF_RECORD = {0,Integer.MAX_VALUE,0,Integer.MAX_VALUE};
	//{max_price,min_price,max_date,min_date}

	public static void main(String[] args) {
		JSONArray sourceArray = null;
		Map<String,int[]> theMap = null;
		
		if(args.length != 1){
			System.out.println("incorrect arguments length ,system will exit");
			System.exit(0);
		}
		
		
		try {//retrieve JSON data array
			URL url = new URL(args[0]);
			//due to encoding issue ,just use url.openStream() will result in error
			//also ,JSONTokener automatically use BufferedReader inside
			JSONTokener tok = new JSONTokener(new InputStreamReader(url.openStream(),"UTF-8"));
			sourceArray = new JSONArray(tok);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		Pattern pattern = Pattern.compile(".{2,}[縣市].+[鄉鎮市區].+?([路街巷]|(大道))");
		//+? matches shortest ,when (__路__巷) happens ,it would only match (__路)
		
		for(int i=0;i<sourceArray.length();i++){
			//for each JSONObject in array
			JSONObject current;
			try {
				current = sourceArray.getJSONObject(i);
				current.getString("土地區段位置或建物區門牌");
				
				
			} catch (JSONException e) {
				e.printStackTrace();
				System.exit(0);
			}	
		}

	}
	
	
	private class InfoEntry{
		int maxPrice,minPrice;
		List<String> distinctDate;
		
		InfoEntry(int initPrize,String initDate){
			maxPrice = minPrice = initPrize;
			distinctDate = new ArrayList<String>();
			distinctDate.add(initDate);
		}
		
		void updatePrize(){
			
		}
		
		void updateDate(){
			
		}
		
	}

}
