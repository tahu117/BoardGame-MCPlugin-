package boardgame.Tahu.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import boardgame.Tahu.Role.Role;
import boardgame.Tahu.Role.RoleDB;

public class Setting {	
	private List<String> confirmedRole = new ArrayList<String>();
	private List<UUID> playerList = new ArrayList<UUID>();
	private HashMap<UUID, Role> roleMap = new HashMap<UUID, Role>();
	private String normalName = "villager";

	public void gameinit() {
		confirmedRole.clear();		
		playerList.clear();		
		roleMap.clear();
		DummyPlayer.dummyInit();
	}

	public void gameSetting() {
		setPlayer(); //player 저장
		confirm(); //role 저장
		random(); //random
	}

	public static void exceptRole(String Role) {
		RoleDB.exceptRole.add(Role);
	}

	public void normalName(String name) {
		normalName = name;
	}

	private void setPlayer() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			playerList.add(player.getUniqueId());
		}
		earlyDummy();// 완성시 제거
	}

	private void earlyDummy() {// 완성시 제거
		DummyPlayer.setDummy(10);
		for (int i = 0; i < DummyPlayer.uuid.size(); i++)
			playerList.add(DummyPlayer.uuid.get(i));
	}

	private void confirm() {
		Set<String> keys = RoleDB.role.keySet();
		for (String key : keys) {
			if (!RoleDB.exceptRole.isEmpty()) {
				for (String er : RoleDB.exceptRole)
					if (!key.equalsIgnoreCase(er)) {
						for (int i = 0; i < RoleDB.role.get(key); i++)
							confirmedRole.add(key);
					}
			} else {
				for (int i = 0; i < RoleDB.role.get(key); i++)
					confirmedRole.add(key);
			}
		}
		if (playerList.size() > confirmedRole.size()) {
			int moreRole = playerList.size() - confirmedRole.size();
			for (int i = 0; i < moreRole; i++) {
				confirmedRole.add(normalName);
			}
		}
	}

	private void random() {
		Collections.shuffle(confirmedRole);
		if (confirmedRole.size() > playerList.size()) {
			DummyPlayer.dummyPlayer(confirmedRole.size() - playerList.size());
			for (int i = 0; i < DummyPlayer.dummyUuid.size(); i++)
				playerList.add(DummyPlayer.dummyUuid.get(i));
		}
		for (int i = 0; i < confirmedRole.size(); i++) {
			roleMap.put(playerList.get(i), new Role(confirmedRole.get(i)));
			
		}
		
		Set<UUID> keys = roleMap.keySet();
		for (UUID key : keys) {
			System.out.println(key + " : " + roleMap.get(key).getRoleName());
		}
		
	}

}
