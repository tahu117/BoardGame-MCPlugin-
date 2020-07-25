package boardgame.Tahu.Role;

public class Role {
	private String roleName = "Villager";
	private String team = "Villager";
	private boolean survive = true;

	private boolean kill = false;
	private boolean detect = false;
	private boolean guard = false;
	private boolean shift = false;
	private boolean match = false;
	private boolean alone = false;
	private boolean change = false;

	public Role(String name) {
		roleName = name;
		team = RoleDB.team.get(name);
		activeTrait(RoleDB.trait.get(name));
	}

	public String getRoleName() {
		return roleName;
	}

	public String getTeam() {
		return team;
	}

	public boolean getSurvive() {
		return survive;
	}

	public void kill() {
		if (kill)
			if (survive)
				survive = false;
	}

	public void detect() {
		if (detect) {

		}
	}

	public void guard() {
		if (guard) {

		}
	}

	public void shift() {
		if (shift) {

		}
	}

	public void match() {
		if (match) {

		}
	}

	public void alone() {
		if (alone) {

		}
	}

	public void change() {
		if (change) {

		}
	}

	private void activeTrait(String trait) {
		switch (trait) {
		case "kill":
			kill = true;
			break;
		case "detect":
			detect = true;
			break;
		case "guard":
			guard = true;
			break;
		case "shift":
			shift = true;
			break;
		case "match":
			match = true;
			break;
		case "alone":
			alone = true;
			break;
		case "change":
			change = true;
			break;
		default:
			break;
		}

	}

}
