package boardgame.Tahu.Role;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class RoleFile {
	private String folderPath = null;
	private File file = null;
	private String saveStr = "Tahu";

	public RoleFile(String path) {
		folderPath = path;
	}

	public void createRole(String gameName) {
		file = new File(folderPath + "/" + gameName + ".yml");
		File folderLocation = new File(folderPath);
		try {
			if (!file.exists()) {
				folderLocation.mkdir();
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

	public void addRole(String gameName, String role, int count) {
		readFile(gameName);
		writeFile(gameName, role, count);
		saveStr = "";
	}

	public void setRole(String gameName) {
		readFile(gameName);
		String rolePart = saveStr.substring(saveStr.indexOf("\n", saveStr.indexOf("Role :")) + 1);

		String[] pairs = rolePart.split("\n");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			RoleDB.role.put(keyValue[0].trim(), Integer.parseInt(keyValue[1].trim()));
		}
	}

	public void delRole(String string) {
		RoleDB.role.clear();
	}

	public boolean isExist(String gameName) {
		file = new File(folderPath + "/" + gameName + ".yml");
		return file.exists();
	}

	private String readFile(String gameName) {
		saveStr = "";
		try {
			file = new File(folderPath + "/" + gameName + ".yml");
			Scanner s = new Scanner(file);

			while (s.hasNextLine()) {
				String str = s.nextLine();
				saveStr = saveStr.concat(str + "\n");
			}
			s.close();
			return saveStr;
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		}
		return saveStr;
	}

	private void writeFile(String gameName, String role, int count) {
		try {
			file = new File(folderPath + "/" + gameName + ".yml");
			FileOutputStream fout = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fout);
						
			if (saveStr.contains("Role : Count")) {
				saveStr = saveStr.replace("  Role : Count\n", "");
			}

			String search = role + " :";
			if (saveStr.contains(search)) {
				int c = Integer.parseInt(saveStr.substring(saveStr.indexOf(search) + search.length() + 1,
						saveStr.indexOf("\n", saveStr.indexOf(search))));
				c += count;

				String fs = saveStr.substring(0, saveStr.indexOf(search) + search.length() + 1);
				String bs = saveStr.substring(saveStr.indexOf("\n", saveStr.indexOf(search)) + 1);

				osw.append(fs);
				count = c;
				osw.append(count + "\n");
				osw.append(bs);
			} else {
				osw.append(saveStr);
				osw.append("  " + role + " : " + count + "\n");
			}
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
