
/**
 * Project : TocHw4
 * Author : 吳哲綱
 * Author_ID : AN4006048
*/
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.*;
import org.json.*;

/**
 * args :
 * [0] source JSON URL
*/
public class TocHw4 {	

	public static void main(String[] args) {
		JSONArray sourceArray = null;
		Map<String,InfoEntry> theMap = null;
		
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
		
		theMap = new HashMap<String,InfoEntry>();
		//check each entry max_price ,min_price ,#distinct_month
		for(int i=0;i<sourceArray.length();i++){
			//for each JSONObject in array
			JSONObject current;
			try {
				current = sourceArray.getJSONObject(i);
				Matcher m = pattern.matcher(current.getString("土地區段位置或建物區門牌"));
				if(m.find()){
					String groupStr = m.group();					
					if(theMap.containsKey(groupStr)){
						InfoEntry currentInfo = theMap.get(groupStr);
						currentInfo.updatePrice(current.getInt("總價元"));
						currentInfo.updateDistinctMonth(current.getString("交易年月"));
					}else{//not mapped yet
						theMap.put(groupStr, 
								new InfoEntry(current.getInt("總價元"),current.getString("交易年月")));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		int maxNumberOfDistinctMonth = 0;
		List<String> maxKey = new ArrayList<String>();
		//iterate through map ,find max #distinct_month and it's road
		for(Map.Entry<String,InfoEntry> entry : theMap.entrySet()){
			if(entry.getValue().getNumberOfDistinctMonth() > maxNumberOfDistinctMonth){
				maxNumberOfDistinctMonth = entry.getValue().getNumberOfDistinctMonth();
				maxKey.clear();
				maxKey.add(entry.getKey());
			}else if(entry.getValue().getNumberOfDistinctMonth() == maxNumberOfDistinctMonth){
				maxKey.add(entry.getKey());
			}
		}
		
		for(int i=0;i<maxKey.size();i++){
			String currentKey = maxKey.get(i);
			InfoEntry currentEntry = theMap.get(currentKey);
			System.out.println(currentKey + ", 最高成交價" + currentEntry.getMaxPrice()
					+ ", 最低成交價" + currentEntry.getMinPrice());
		}
	}
}
