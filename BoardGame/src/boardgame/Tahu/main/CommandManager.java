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
	private Wolf wolf = new Wolf();

	public CommandManager(BoardGame plugin) {
		rm = new RoleManager(plugin);
		rs = new RoleSetting();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((label.equalsIgnoreCase("bggame")) || label.equalsIgnoreCase("bgg")) {
			if (args.length == 0) {
				sender.sendMessage("/bgg start : ������� ����\n" + "/bgg stop : ������� ����\n");
			} else if (sender.isOp()) {
				if (RoleDB.isSet) {
					if (args[0].equalsIgnoreCase("start"))
						wolf.start();
					else if (args[0].equalsIgnoreCase("stop"))
						wolf.stop();
					else
						sender.sendMessage("/bgg start, stop");
				} else {
					sender.sendMessage("���� ��������� �����Ͻʽÿ�. (/bgr <game> set(s))");
				}
			}
		} else if ((label.equalsIgnoreCase("bgrole")) || label.equalsIgnoreCase("bgr")) {
			if (args.length == 0) {
				sender.sendMessage("/bgrole(bgr) <game> create(c) : <game> ��������� ���� ���� ����\n"
						+ "/bgrole(bgr) <game> list(l) : <game> ��������� ���� ����Ʈ\n"
						+ "/bgrole(bgr) <game> insert(i) <role> (<count>) (<feature>) : <game> ��������� <role> �� <count> ��ŭ �߰� �� ������ <feature> ���� (<count> �Ⱦ��� 1�� ����)\n"
						+ "/bgrole(bgr) <game> grant(g) <role> <feature(team, kill, detect, guard, etc)> : <role> �� Ư¡ ����\n"
						+ "/bgrole(bgr) <game> delete(d) <role> : <game> ��������� <role>�� ����\n"
						+ "/bgrole(bgr) <game> deletefile(df) : <game> ������� ���� ����\n"
						+ "/bgrole(bgr) <game> set(s) : <game> ��������� ���� ����\n"
						+ "/bgrole(bgr) <game> reload(r) : <game> ��������� ���� reload\n"
						+ "/bgrole(bgr) except(e) <role> : <role> �� ���ӿ��� ����\n");
			} else if (sender.isOp()) {
				if (args.length != 1) {
					if ((args[1].equalsIgnoreCase("create")) || (args[1].equalsIgnoreCase("c"))) {
						if (!rm.isFileExist(args[0])) {
							rm.createFile(args[0]);
							sender.sendMessage("\"" + args[0] + "\"" + "�� ���� ���� �����Ͽ����ϴ�.");
						} else {
							sender.sendMessage("\"" + args[0] + "\"" + "�� ���� ������ �̹� �ֽ��ϴ�.");
						}

					} else if ((args[1].equalsIgnoreCase("list")) || (args[1].equalsIgnoreCase("l"))) {
						rm.getList(args[0], sender);

					} else if ((args[1].equalsIgnoreCase("insert")) || (args[1].equalsIgnoreCase("i"))) {
						if (args.length == 3)
							rm.insert(args[0], args[2], Integer.toString(1), "");
						else if (args.length == 4)
							rm.insert(args[0], args[2], args[3], "");
						else if (args.length == 5)
							rm.insert(args[0], args[2], args[3], args[4]);
						else
							sender.sendMessage("/bgrole(bgr) <game> insert(i) <role> (<count>) (<feature>)");

					} else if ((args[1].equalsIgnoreCase("grant")) || (args[1].equalsIgnoreCase("g"))) {
						if (args.length == 4)
							if (rm.isRoleExist(args[0], args[2]))
								if (args[3].contains(":"))
									rm.grant(args[0], args[2], args[3]);
								else
									sender.sendMessage("trait : kill, detect, guard, shift, match, alone, change\n"
											+ "feature ex) team:A or trait:kill");
							else
								sender.sendMessage("�ش��ϴ� ������ �����ϴ�.");
						else
							sender.sendMessage("/bgrole(bgr) <game> grant(g) <role> <feature>");

					} else if ((args[1].equalsIgnoreCase("delete")) || (args[1].equalsIgnoreCase("d"))) {
						if (args.length == 3)
							if (rm.isRoleExist(args[0], args[2]))
								rm.delete(args[0], args[2]);
							else
								sender.sendMessage(args[2] + "�� ������ �����ϴ�.");
						else
							sender.sendMessage("/bgrole(bgr) <game> delete(d) <role>");

					} else if ((args[1].equalsIgnoreCase("deletefile")) || (args[1].equalsIgnoreCase("df"))) {
						rm.delete(args[0]);
						sender.sendMessage(args[0] + "�� ������ �����Ͽ����ϴ�.");

					} else if ((args[1].equalsIgnoreCase("set")) || (args[1].equalsIgnoreCase("s"))) {
						if (!RoleDB.isSet) {
							rm.set(args[0]);
							sender.sendMessage(args[0] + "�� ������ ���õǾ����ϴ�.");
						} else {
							rm.unset(args[0]);
							sender.sendMessage(args[0] + "�� ������ �����߽��ϴ�.");
						}

					} else if ((args[1].equalsIgnoreCase("reload")) || (args[1].equalsIgnoreCase("r"))) {
						if (RoleDB.isSet)
							rm.reload(args[0]);

					}

					if ((args[0].equalsIgnoreCase("except")) || (args[0].equalsIgnoreCase("e"))) {
						if (args.length == 2) {
							if (RoleDB.isSet) {
								if (RoleDB.role.containsKey(args[1])) {
									if (!rs.isExceptRole(args[1])) {
										sender.sendMessage(args[1] + "�� ������ ������ �ʽ��ϴ�.");
										rs.exceptRole(args[1]);
									} else {
										sender.sendMessage(args[1] + "�� ������ ����մϴ�.");
										rs.unExceptRole(args[1]);
									}
								} else {
									sender.sendMessage(args[1] + "��� ������ �����ϴ�.");
								}
							} else {
								sender.sendMessage("���� ������ �Ǿ����� �ʽ��ϴ�.");
							}
						} else
							sender.sendMessage("/bgrole(bgr) except(e) <role>");
					}
				}
			}

		}
		return false;
	}
}
