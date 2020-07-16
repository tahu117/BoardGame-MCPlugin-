package boardgame.Tahu.Role;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class RoleManager {
	private String folderPath = null;
	private File file = null;
	private String saveStr = "Tahu";

	public RoleManager(String path) {
		folderPath = path;
		File folderLocation = new File(folderPath);
		folderLocation.mkdir();
		onFile();
	}

	public void fs(String gameName) {
		HashMap<String, Integer> h = RoleDB.role.get(gameName);
		Set<String> keys = h.keySet();
		for (String key : keys)
			System.out.println(key + " " + h.get(key));
	}

	public void setRoletoFile(String gameName) {
		readFile(gameName);

		String rolePart = saveStr.substring(saveStr.indexOf("\n", saveStr.indexOf("Role :")) + 1);
		HashMap<String, Integer> h = new HashMap<String, Integer>();

		String[] pairs = rolePart.split("\n");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			h.put(keyValue[0].trim(), Integer.parseInt(keyValue[1].trim()));
		}
		RoleDB.role.put(gameName, h);
		RoleDB.isSetting.put(gameName, true);
	}

	public void unsetRoletoFile(String gameName) {
		RoleDB.role.clear();
		RoleDB.isSetting.replace(gameName, false);
	}

	public void exceptRole(String gameName, String role) {
		ArrayList<String> l = new ArrayList<String>(RoleDB.exceptRole.get(gameName));
		l.add(role);
		
		if(RoleDB.exceptRole.isEmpty())
			RoleDB.exceptRole.put(gameName, l);
		else
			RoleDB.exceptRole.replace(gameName, l);
	}

	public void undoExceptRole(String gameName, String role) {
		ArrayList<String> l = new ArrayList<String>(RoleDB.exceptRole.get(gameName));
		l.remove(role);
		RoleDB.exceptRole.replace(gameName, l);
	}

	public boolean isFileExist(String gameName) {
		file = new File(folderPath + "/" + gameName + ".yml");
		return file.exists();
	}

	public boolean isRoleExist(String gameName, String exceptRole) {
		readFile(gameName);
		String rolePart = saveStr.substring(saveStr.indexOf("\n", saveStr.indexOf("Role :")) + 1);

		String[] pairs = rolePart.split("\n");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			if (exceptRole.equalsIgnoreCase(keyValue[0].trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean isExceptRole(String gameName, String eRole) {
		for (String key : RoleDB.exceptRole.get(gameName)) {
			if (key.equalsIgnoreCase(eRole)) {
				return true;
			}
		}
		return false;
	}

	public void insRoletoFile(String gameName, String role, int count) {
		readFile(gameName);
		String search = role + " :";
		if (saveStr.contains(search)) {
			int c = Integer.parseInt(saveStr.substring(saveStr.indexOf(search) + search.length() + 1,
					saveStr.indexOf("\n", saveStr.indexOf(search))));
			c += count;

			String fs = saveStr.substring(0, saveStr.indexOf(search) + search.length() + 1);
			String bs = saveStr.substring(saveStr.indexOf("\n", saveStr.indexOf(search)) + 1);
			saveStr = fs + c + "\n" + bs;
		} else {
			saveStr = saveStr + "  " + role + " : " + count + "\n";
		}
		writeFile(gameName);
		saveStr = "";
	}

	public void delRoletoFile(String gameName, String role) {
		readFile(gameName);
		String search = role + " :";
		String fs = saveStr.substring(0, saveStr.indexOf(search) - search.length() + 1);
		String bs = saveStr.substring(saveStr.indexOf("\n", saveStr.indexOf(search)) + 1);
		saveStr = fs + bs;
		writeFile(gameName);
		saveStr = "";
	}

	public void createFile(String gameName) {
		file = new File(folderPath + "/" + gameName + ".yml");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter w = new BufferedWriter(new FileWriter(file));
			w.append("Useage : 역할과 역할 개수 작성(<Role> : <Count>))\n");
			w.append("[" + gameName + "]" + " Role :\n");
			w.append("  Role : Count");
			w.flush();
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readFile(String gameName) {
		saveStr = "";
		try {
			file = new File(folderPath + "/" + gameName + ".yml");
			Scanner s = new Scanner(file);

			while (s.hasNextLine()) {
				String str = s.nextLine();
				saveStr = saveStr.concat(str + "\n");
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		}
	}

	private void writeFile(String gameName) {
		try {
			file = new File(folderPath + "/" + gameName + ".yml");
			FileOutputStream fout = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fout);

			if (saveStr.contains("Role : Count")) {
				saveStr = saveStr.replace("  Role : Count\n", "");
			}

			osw.append(saveStr);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onFile() {
		ArrayList<String> l = new ArrayList<String>();
		l.add("");
		for (File fileEntry : new File(folderPath).listFiles()) {
			RoleDB.isSetting.put(fileEntry.getName().replace(".yml", ""), false);
			RoleDB.exceptRole.put(fileEntry.getName().replace(".yml", ""), l);
		}
	}
}
