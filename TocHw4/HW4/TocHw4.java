
/**
 * Project : TocHw4
 * Author : �d����
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
		
		Pattern pattern = Pattern.compile(".{2,}[����].+[�m����].+?([�����]|(�j�D))");
		//+? matches shortest ,when (__��__��) happens ,it would only match (__��)
		
		theMap = new HashMap<String,InfoEntry>();
		//check each entry max_price ,min_price ,#distinct_month
		for(int i=0;i<sourceArray.length();i++){
			//for each JSONObject in array
			JSONObject current;
			try {
				current = sourceArray.getJSONObject(i);
				Matcher m = pattern.matcher(current.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P"));
				if(m.find()){
					String groupStr = m.group();					
					if(theMap.containsKey(groupStr)){
						InfoEntry currentInfo = theMap.get(groupStr);
						currentInfo.updatePrice(current.getInt("�`����"));
						currentInfo.updateDistinctMonth(current.getString("����~��"));
					}else{//not mapped yet
						theMap.put(groupStr, 
								new InfoEntry(current.getInt("�`����"),current.getString("����~��")));
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
			System.out.println(currentKey + ", �̰������" + currentEntry.getMaxPrice()
					+ ", �̧C�����" + currentEntry.getMinPrice());
		}
	}
}
