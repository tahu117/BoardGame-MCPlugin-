package boardgame.Tahu.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import boardgame.Tahu.Role.RoleDB;
import boardgame.Tahu.Role.RoleManager;

public class CommandManager implements CommandExecutor {
	private RoleManager rm;
//	private Wolf wolf = new Wolf();

	public CommandManager(BoardGame plugin) {
		rm = new RoleManager(plugin.getDataFolder().getAbsolutePath());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((label.equalsIgnoreCase("bggame")) || label.equalsIgnoreCase("bgg")) {
			if (args.length == 0) {
				sender.sendMessage("/bg <game> start : <game> ������� ����\n" + "/bg <game> stop : <game> ������� ����\n"
						+ "/bg <game> role : <game> ������� ���� ����");
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
						+ "/bgrole(bgr) <game> insert(i) <role> (<count>) : <game> ��������� <role>�� <count>��ŭ �߰�(<count> �Ⱦ��� 1�� ����) \n"
						+ "/bgrole(bgr) <game> delete(d) <role> : <game> ��������� <role>�� ����\n"
						+ "/bgrole(bgr) <game> except(e) <role> : <role>�� ���ӿ��� ����\n"
						+ "/bgrole(bgr) <game> setting(s) : <game> ��������� ���� ����\n");
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
						if (rm.isRoleExist(args[0], args[2]))
							rm.delRoletoFile(args[0], args[2]);
						else
							sender.sendMessage(args[2] + "�� ������ �����ϴ�.");
					} else
						sender.sendMessage("/bgrole(bgr) <game> delete(d) <role>");
				} else if ((args[1].equalsIgnoreCase("except")) || (args[1].equalsIgnoreCase("e"))) {
					if (args.length == 3) {
						if (RoleDB.isSetting.get(args[0])) {
							if (rm.isRoleExist(args[0], args[2])) {
								if (!rm.isExceptRole(args[0], args[2])) {
									sender.sendMessage(args[2] + "�� ������ ������ �ʽ��ϴ�.");
									rm.exceptRole(args[0], args[2]);
								} else {
									sender.sendMessage(args[2] + "�� ������ ����մϴ�.");
									rm.undoExceptRole(args[0], args[2]);
								}
							} else {
								sender.sendMessage(args[2] + "��� ������ �����ϴ�.");
							}
						} else {
							sender.sendMessage("���� ������ �Ǿ����� �ʽ��ϴ�.");
						}
					} else
						sender.sendMessage("/bgrole(bgr) <game> except(e) <role>");
				} else if ((args[1].equalsIgnoreCase("setting")) || (args[1].equalsIgnoreCase("s"))) {
					if(!RoleDB.isSetting.get(args[0])) {
						rm.setRoletoFile(args[0]);
						sender.sendMessage(args[0] + "�� ������ ���õǾ����ϴ�.");
						rm.fs(args[0]);
					} else {
						rm.unsetRoletoFile(args[0]);
						sender.sendMessage(args[0] + "�� ������ ������ �����Ǿ����ϴ�.");
					}						
				}
			}
		}
		return false;
	}
}
