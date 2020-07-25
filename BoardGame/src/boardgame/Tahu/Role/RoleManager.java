package boardgame.Tahu.Role;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import boardgame.Tahu.main.BoardGame;

public class RoleManager {
	private BoardGame plugin;
	private String folderPath;
	private FileConfiguration roleFile;
	private File file;
	private String[] traitArr = { "kill", "detect", "guard", "shift", "match", "alone", "change" };

	public RoleManager(BoardGame plugin) {
		this.plugin = plugin;
		folderPath = plugin.getDataFolder().getAbsolutePath();
		File folderLocation = new File(folderPath);
		folderLocation.mkdir();
	}

	public void getList(String gameName, CommandSender sender) {
		loadRoleFile(gameName);

		sender.sendMessage("role : (role : count)\n");
		for (String s : roleFile.getConfigurationSection(gameName + ".role").getKeys(false))
			sender.sendMessage(s + " : " + roleFile.getConfigurationSection(gameName + ".role").getInt(s) + "\n");
		sender.sendMessage("team : (role : team)\n");
		for (String s : roleFile.getConfigurationSection(gameName + ".team").getKeys(false))
			sender.sendMessage(s + " : " + roleFile.getConfigurationSection(gameName + ".team").getString(s) + "\n");
		sender.sendMessage("trait : (role : trait)\n");
		for (String s : roleFile.getConfigurationSection(gameName + ".trait").getKeys(false))
			sender.sendMessage(s + " : " + roleFile.getConfigurationSection(gameName + ".trait").getString(s) + "\n");
	}

	public void set(String gameName) {
		loadRoleFile(gameName);

		ConfigurationSection data;
		data = roleFile.getConfigurationSection(gameName + ".role");
		for (String s : data.getKeys(false))
			RoleDB.role.put(s, data.getInt(s));
		data = roleFile.getConfigurationSection(gameName + ".team");
		for (String s : data.getKeys(false))
			RoleDB.trait.put(s, data.getString(s));
		data = roleFile.getConfigurationSection(gameName + ".trait");
		for (String s : data.getKeys(false))
			RoleDB.trait.put(s, data.getString(s));

		RoleDB.isSet = true;
	}

	public void unset(String gameName) {
		RoleDB.role.clear();
		RoleDB.team.clear();
		RoleDB.trait.clear();
		RoleDB.isSet = false;
	}

	public void reload(String gameName) {
		if (RoleDB.isSet) {
			loadRoleFile(gameName);
			unset(gameName);
			set(gameName);
		}
	}

	public void createFile(String gameName) {
		file = new File(folderPath, gameName + ".yml");
		try {
			if (!file.exists()) {
				copy(plugin.getResource("role.yml"), file, gameName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(String gameName, String role, String count, String trait) {
		loadRoleFile(gameName);

		if (roleFile.isString(gameName + ".role.role"))
			roleFile.set(gameName + ".role.role", null);

		int i = Integer.parseInt(count);
		if (roleFile.isInt(gameName + ".role." + role)) {
			i += roleFile.getInt(gameName + ".role." + role);
		}
		roleFile.set(gameName + ".role." + role, i);

		saveRoleFile(gameName);

		grant(gameName, role, trait);
	}

	public void grant(String gameName, String role, String trait) {
		loadRoleFile(gameName);

		String[] t = trait.split(",");

		for (int i = 0; i < t.length; i++) {
			String[] s = t[i].split(":");
			if (s[0].equalsIgnoreCase("team"))
				grantTeam(gameName, role, s[1]);
			else if (s[0].equalsIgnoreCase("trait"))
				grantTrait(gameName, role, s[1]);
		}

		saveRoleFile(gameName);
	}

	public void delete(String gameName, String role) {
		loadRoleFile(gameName);

		if (roleFile.isInt(gameName + ".role." + role)) {
			roleFile.set(gameName + ".role." + role, null);
			roleFile.set(gameName + ".team." + role, null);
			roleFile.set(gameName + ".trait." + role, null);
		}

		ConfigurationSection data = roleFile.getConfigurationSection(gameName + ".role");
		if (data.getKeys(false).isEmpty()) {
			roleFile.set(gameName + ".role.role", "count");
			roleFile.set(gameName + ".team.role", "team");
			roleFile.set(gameName + ".trait.role", "trait");
		}

		saveRoleFile(gameName);
	}

	public void delete(String gameName) {
		file = new File(folderPath, gameName + ".yml");
		roleFile = null;
		try {
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isRoleExist(String gameName, String role) {
		loadRoleFile(gameName);

		if (roleFile.isInt(gameName + ".role." + role))
			return true;
		return false;
	}

	public boolean isFileExist(String gameName) {
		file = new File(folderPath, gameName + ".yml");
		return file.exists();
	}

	private void grantTeam(String gameName, String role, String team) {
		if (roleFile.isString(gameName + ".team.role"))
			roleFile.set(gameName + ".team.role", null);

		roleFile.set(gameName + ".team." + role, team);
	}

	private void grantTrait(String gameName, String role, String trait) {
		if (roleFile.isString(gameName + ".trait.role"))
			roleFile.set(gameName + ".trait.role", null);

		for (String s : traitArr)
			if (trait.equalsIgnoreCase(s))
				roleFile.set(gameName + ".trait." + role, trait);
	}

	private void loadRoleFile(String gameName) {
		file = new File(folderPath, gameName + ".yml");
		try {
			roleFile = YamlConfiguration.loadConfiguration(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void saveRoleFile(String gameName) {
		file = new File(folderPath, gameName + ".yml");
		try {
			roleFile.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void copy(InputStream in, File file, String gameName) {
		try {
			OutputStream out = new FileOutputStream(file);
			StringWriter sw = new StringWriter();

			int n;
			byte[] buf = new byte[1024];

			while ((n = in.read(buf)) != -1) {
				sw.write(new String(buf, 0, n));
			}

			String s = sw.toString().replace("example", gameName);
			buf = s.getBytes();
			out.write(buf);
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
