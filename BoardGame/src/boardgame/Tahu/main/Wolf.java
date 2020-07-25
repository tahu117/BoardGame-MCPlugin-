package boardgame.Tahu.main;

import org.bukkit.Bukkit;

public class Wolf {
	private GameSetting setting = new GameSetting();
	private boolean startCheck = false;

	public void start() {
		if (!startCheck) {
			setting.gameinit();
			setting.gameSetting();
			startCheck = true;
		}
	}

	public void stop() {
		if (startCheck) {
			startCheck = false;
		}
	}

	public void day() {
		Bukkit.broadcastMessage("낮이 되었습니다.");

	}

	public void night() {
		Bukkit.broadcastMessage("밤이 되었습니다.");
		
	}

	public void vote() {
		
		
	}
	

}
