package boardgame.Tahu.Role;

public class RoleSetting {
	
	public void exceptRole(String role) {
		RoleDB.except.add(role);
	}

	public void unExceptRole(String role) {
		RoleDB.except.remove(role);
	}
	
	
	public boolean isExceptRole(String role) {
		return RoleDB.except.contains(role);
	}
}
