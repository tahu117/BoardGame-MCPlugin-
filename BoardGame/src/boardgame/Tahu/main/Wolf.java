package boardgame.Tahu.main;

public class Wolf {
	private Setting setting = new Setting();
	private boolean startCheck = false;
	
	public void start() {
		if(!startCheck) {
			setting.gameinit();
			setting.gameSetting();
			startCheck = true;
		}
	}

	public void stop() {
		if(startCheck)
			setting.gameinit();
	}

	public void pick() {

	}

	public void day() {

	}

	public void night() {

	}

	public void vote() {

	}

}
