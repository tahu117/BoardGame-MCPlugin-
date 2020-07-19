package boardgame.Tahu.main;

import boardgame.Tahu.Role.RoleDB;

public class Wolf {
	private GameSetting setting = new GameSetting();
	private boolean startCheck = false;
		
	public void start(String gameName) {
		if(!startCheck) {
			setting.gameinit();
			setting.gameSetting(gameName);
			startCheck = true;
		}
	}

	public void stop() {
		if(startCheck) {
			setting.gameinit();
			RoleDB.exceptRole.clear();
			startCheck = false;
		}
	}

	public void day() {
		
	}

	public void night() {
		
	}

	public void vote() {
		
	}

}
