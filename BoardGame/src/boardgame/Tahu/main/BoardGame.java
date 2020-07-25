package boardgame.Tahu.main;

import org.bukkit.plugin.java.JavaPlugin;

public class BoardGame extends JavaPlugin {
	private CommandManager cm = new CommandManager(this);
	@Override
	public void onEnable() {		
		System.out.println("BoardGame Lodding");
		getCommand("bggame").setExecutor(cm);
		getCommand("bgg").setExecutor(cm);
		getCommand("bgrole").setExecutor(cm);
		getCommand("bgr").setExecutor(cm);
	}

	@Override
	public void onDisable() {

	}	
}
