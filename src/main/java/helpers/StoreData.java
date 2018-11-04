package helpers;

import java.util.HashMap;
import java.util.Map;

public class StoreData {
    private Map<String,String> data = new HashMap<String, String>();
    private static StoreData instance = new StoreData();

    public static StoreData getInstance(){
        return instance;
    }

    public void addData(String key,String value){
        data.put(key, value);
    }

    public String getData(String key){
        return data.get(key);
    }

    public void emptyData(){
        data.clear();
    }
}

