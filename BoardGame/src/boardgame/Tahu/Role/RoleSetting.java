package boardgame.Tahu.Role;

import java.util.ArrayList;
import java.util.HashMap;

public class RoleSetting {
	public void exceptRole(String gameName, String role) {
		ArrayList<String> l = new ArrayList<String>(RoleDB.exceptRole.get(gameName));
		l.add(role);
		
		if(RoleDB.exceptRole.isEmpty())
			RoleDB.exceptRole.put(gameName, l);
		else
			RoleDB.exceptRole.replace(gameName, l);
	}

	public void undoExceptRole(String gameName, String role) {
		ArrayList<String> l = new ArrayList<String>(RoleDB.exceptRole.get(gameName));
		l.remove(role);
		RoleDB.exceptRole.replace(gameName, l);
	}
	
	public boolean isRoleExist(String gameName, String exceptRole) {
		HashMap<String, Integer> h = RoleDB.role.get(gameName);
		for(String keys : h.keySet())
			if(keys.equalsIgnoreCase(exceptRole))
				return true;
		return false;
	}
	
	public boolean isExceptRole(String gameName, String eRole) {
		for (String key : RoleDB.exceptRole.get(gameName)) {
			if (key.equalsIgnoreCase(eRole)) {
				return true;
			}
		}
		return false;
	}
}
