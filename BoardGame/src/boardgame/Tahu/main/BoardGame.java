package boardgame.Tahu.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import boardgame.Tahu.Role.RoleFile;

public class BoardGame extends JavaPlugin {
	private RoleFile rf = new RoleFile(getDataFolder().getAbsolutePath());
	private Wolf wolf = new Wolf();

	@Override
	public void onEnable() {
		System.out.println("BoardGame Lodding");
	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("bg")) {
			if (args.length == 0) {
				sender.sendMessage("/bg <game> start : <game> ������� ����\n" + "/bg <game> stop : <game> ������� ����\n"
						+ "/bg <game> role : <game> ������� ���� ����");
			} else if (sender.isOp()) {
				if (!rf.isExist(args[0])) {
					sender.sendMessage("�ش��ϴ� ���� ������ �����ϴ�.\n");
				} else {
					if (args.length == 1) {
						sender.sendMessage("/bg <game> start : <game> ������� ����\n" + "/bg <game> stop : <game> ������� ����\n"
								+ "/bg <game> role : <game> ������� ���� ����");
					} else if (args[1].equalsIgnoreCase("start")) {
						rf.setRole(args[1]);
						wolf.start();
					} else if (args[1].equalsIgnoreCase("stop")) {
						rf.delRole(args[1]);
						wolf.stop();
					} else if (args[1].equalsIgnoreCase("role")) {
						if (args.length == 2) {
							sender.sendMessage("/bg <game> role create : <game> ��������� ���� ���� ����\n"
									+ "/bg <game> role add <role> (<count>) : <game> ��������� <role>�� <count>��ŭ �߰�(<count> �Ⱦ��� 1�� ����) \n"
									+ "/bg <game> role except <role> : <role>�� ���ӿ��� ����");
						} else if (args[2].equalsIgnoreCase("create")) {
							rf.createRole(args[0]);
						} else if (args[2].equalsIgnoreCase("add")) {
							if (args.length < 4)
								sender.sendMessage("/bg <game> role add <role> (<count>)");
							else if (args.length == 4)
								rf.addRole(args[0], args[3], 1);
							else if (args.length == 5)
								rf.addRole(args[0], args[3], Integer.parseInt(args[4]));
							else if (args.length > 5)
								sender.sendMessage("�� ���� ����� ġ�̽��ϴ�.");
						} else if (args[2].equalsIgnoreCase("except")) {
							/////////07-13////////////
							if (args.length == 3) {
								sender.sendMessage("/bg <game> role except <role>");
							} else if (args[3].isEmpty()) {
								sender.sendMessage(args[2] + "�� ������ �����ϴ�.");
							}							
						}
					} else if (args[1].equalsIgnoreCase("check")) {
					}
				}
				return true;
			}
		}
		return false;
	}
}
