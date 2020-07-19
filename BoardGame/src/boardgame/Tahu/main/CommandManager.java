package boardgame.Tahu.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import boardgame.Tahu.Role.RoleDB;
import boardgame.Tahu.Role.RoleManager;
import boardgame.Tahu.Role.RoleSetting;

public class CommandManager implements CommandExecutor {
	private RoleManager rm;
	private RoleSetting rs;
//	private Wolf wolf = new Wolf();

	public CommandManager(BoardGame plugin) {
		RoleDB.folderPath = plugin.getDataFolder().getAbsolutePath();
		rm = new RoleManager();
		rs = new RoleSetting();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((label.equalsIgnoreCase("bggame")) || label.equalsIgnoreCase("bgg")) {
			if (args.length == 0) {
				sender.sendMessage("�̿�");
//				sender.sendMessage("/bg <game> start : <game> ������� ����\n" + "/bg <game> stop : <game> ������� ����\n"
//						+ "/bg <game> role : <game> ������� ���� ����");
			}
//			else if (sender.isOp()) {
//				if (!rm.isExist(args[0])) {
//					sender.sendMessage("�ش��ϴ� ���� ������ �����ϴ�.\n");
//				} else {
//					if (args.length == 1) {
//						sender.sendMessage("/bg <game> start : <game> ������� ����\n" + "/bg <game> stop : <game> ������� ����\n"
//								+ "/bg <game> role : <game> ������� ���� ����");
//					} else if (args[1].equalsIgnoreCase("start")) {
//						rm.setRole(args[0]);
//						wolf.start();
//					} else if (args[1].equalsIgnoreCase("stop")) {
//						rm.delRole(args[0]);
//						wolf.stop();
//					} else if (args[1].equalsIgnoreCase("check")) {
//					}
//				}
//				return true;
//			}
//			return true;
		} else if ((label.equalsIgnoreCase("bgrole")) || label.equalsIgnoreCase("bgr")) {
			if (args.length <= 1) {
				sender.sendMessage("/bgrole(bgr) <game> create(c) : <game> ��������� ���� ���� ����\n"
						+ "/bgrole(bgr) <game> insert(i) <role> (<count>) : <game> ��������� <role> �� <count> ��ŭ �߰� (<count> �Ⱦ��� 1�� ����)\n"
						+ "/bgrole(bgr) <game> delete(d) <role> : <game> ��������� <role>�� ����\n"
						+ "/bgrole(bgr) <game> except(e) <role> : <role> �� ���ӿ��� ����\n"
						+ "/bgrole(bgr) <game> load(l) : <game> ��������� ���� ����\n"
						+ "/bgrole(bgr) <game> set(s) <role> <feature(team, detect, heal, etc)> : <role> �� Ư¡ ����\n");
			} else if (sender.isOp()) {
				if ((args[1].equalsIgnoreCase("create")) || (args[1].equalsIgnoreCase("c"))) {
					rm.createFile(args[0]);
					sender.sendMessage("\"" + args[0] + "\"" + "�� ���� ���� �����Ͽ����ϴ�.");
				} else if ((args[1].equalsIgnoreCase("insert")) || (args[1].equalsIgnoreCase("i"))) {
					if (args.length == 3)
						rm.insRoletoFile(args[0], args[2], 1);
					else if (args.length == 4)
						rm.insRoletoFile(args[0], args[2], Integer.parseInt(args[3]));
					else
						sender.sendMessage("/bgrole(bgr) <game> insert(i) <role> (<count>)");
				} else if ((args[1].equalsIgnoreCase("delete")) || (args[1].equalsIgnoreCase("d"))) {
					if (args.length == 3) {
						if (rs.isRoleExist(args[0], args[2]))
							rm.delRoletoFile(args[0], args[2]);
						else
							sender.sendMessage(args[2] + "�� ������ �����ϴ�.");
					} else
						sender.sendMessage("/bgrole(bgr) <game> delete(d) <role>");
				} else if ((args[1].equalsIgnoreCase("except")) || (args[1].equalsIgnoreCase("e"))) {
					if (args.length == 3) {
						if (RoleDB.isLoad.get(args[0])) {
							if (rs.isRoleExist(args[0], args[2])) {
								if (!rs.isExceptRole(args[0], args[2])) {
									sender.sendMessage(args[2] + "�� ������ ������ �ʽ��ϴ�.");
									rs.exceptRole(args[0], args[2]);
								} else {
									sender.sendMessage(args[2] + "�� ������ ����մϴ�.");
									rs.undoExceptRole(args[0], args[2]);
								}
							} else {
								sender.sendMessage(args[2] + "��� ������ �����ϴ�.");
							}
						} else {
							sender.sendMessage("���� ������ �Ǿ����� �ʽ��ϴ�.");
						}
					} else
						sender.sendMessage("/bgrole(bgr) <game> except(e) <role>");
				} else if ((args[1].equalsIgnoreCase("load")) || (args[1].equalsIgnoreCase("l"))) {
					if (!RoleDB.isLoad.get(args[0])) {
						rm.loadRoletoFile(args[0]);
						sender.sendMessage(args[0] + "�� ������ �ε��Ǿ����ϴ�.");
					} else {
						rm.unloadRoletoFile(args[0]);
						sender.sendMessage(args[0] + "�� ������ �����߽��ϴ�.");
					}
				} else if ((args[1].equalsIgnoreCase("set")) || (args[1].equalsIgnoreCase("s"))) {
					if (RoleDB.isLoad.get(args[0])) {
						
					}
				}
			}
		}
		return false;
	}
}
