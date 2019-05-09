package sample;

import java.util.HashMap;

public class MapData {

    private static HashMap<String, String> map = new HashMap<>();

    public static void setData(String file_path, String file_password) {

        map.put(file_path, file_password);
    }
    public static String getData(String file_path) {

        return map.get(file_path);
    }
}
