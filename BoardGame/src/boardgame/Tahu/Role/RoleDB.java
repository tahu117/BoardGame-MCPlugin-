package boardgame.Tahu.Role;

import java.util.HashMap;
import java.util.List;

public class RoleDB {
	public static String folderPath = "";
	public static HashMap<String, HashMap<String, Integer>> role = new HashMap<String, HashMap<String, Integer>>();
	public static HashMap<String, Boolean> isLoad = new HashMap<String, Boolean>();
	public static HashMap<String, List<String>> exceptRole = new HashMap<String, List<String>>();
}
