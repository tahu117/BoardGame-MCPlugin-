package boardgame.Tahu.Role;

public class Role {
	private String roleName = "Villager";
	private boolean survive = true;
	private boolean detective = false;
	private boolean healing = false;
	
	public Role(String name) {
		roleName = name;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void kill() {
		if(survive)
			survive = false;
	}
	
	public boolean getSurvive() {
		return survive;
	}
	
	public void detect() {
		if(detective) {
			
		}
	}
	
	public void heal() {
		if(healing) {
			
		}
	}

}
