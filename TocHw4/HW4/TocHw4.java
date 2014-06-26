
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import org.json.*;


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
					}else{
						theMap.put(groupStr, 
								new InfoEntry(current.getInt("�`����"),current.getString("����~��")));
					}
				}			
			} catch (JSONException e) {
				e.printStackTrace();
				System.exit(0);
			}	
		}

	}

}
