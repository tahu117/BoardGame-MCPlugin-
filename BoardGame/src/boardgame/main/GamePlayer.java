package boardgame.main;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GamePlayer implements Listener{
	private static BoardGame plugin;
	private HashMap<String, Integer> playerList = new HashMap<>();
	
	public GamePlayer(BoardGame instance) {
		plugin = instance;
		initPlayerlist();
	}
	
	public void initPlayerlist() {
		for(Player player : plugin.getServer().getOnlinePlayers()) {
			playerList.put(player.getName(), 0);
		}
	}
	
	public void setPlayerlist(int num) {
		for(Player player : plugin.getServer().getOnlinePlayers()) {
			playerList.put(player.getName(), num);
		}
	}
	
	public HashMap<String, Integer> getPlayerlist() {
		return playerList;
	}
	
}
