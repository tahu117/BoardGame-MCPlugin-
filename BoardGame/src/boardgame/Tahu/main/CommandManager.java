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
				sender.sendMessage("/bgg start : 보드게임 실행\n" + "/bgg stop : 보드게임 종료\n");
			} else if (sender.isOp()) {
				if (RoleDB.isSet) {
					if (args[0].equalsIgnoreCase("start"))
						wolf.start();
					else if (args[0].equalsIgnoreCase("stop"))
						wolf.stop();
					else
						sender.sendMessage("/bgg start, stop");
				} else {
					sender.sendMessage("먼저 보드게임을 세팅하십시오. (/bgr <game> set(s))");
				}
			}
		} else if ((label.equalsIgnoreCase("bgrole")) || label.equalsIgnoreCase("bgr")) {
			if (args.length == 0) {
				sender.sendMessage("/bgrole(bgr) <game> create(c) : <game> 보드게임의 역할 파일 생성\n"
						+ "/bgrole(bgr) <game> list(l) : <game> 보드게임의 역할 리스트\n"
						+ "/bgrole(bgr) <game> insert(i) <role> (<count>) (<feature>) : <game> 보드게임의 <role> 을 <count> 만큼 추가 및 역할의 <feature> 설정 (<count> 안쓸시 1로 저장)\n"
						+ "/bgrole(bgr) <game> grant(g) <role> <feature(team, kill, detect, guard, etc)> : <role> 의 특징 설정\n"
						+ "/bgrole(bgr) <game> delete(d) <role> : <game> 보드게임의 <role>을 제거\n"
						+ "/bgrole(bgr) <game> deletefile(df) : <game> 보드게임 파일 제거\n"
						+ "/bgrole(bgr) <game> set(s) : <game> 보드게임의 역할 세팅\n"
						+ "/bgrole(bgr) <game> reload(r) : <game> 보드게임의 역할 reload\n"
						+ "/bgrole(bgr) except(e) <role> : <role> 을 게임에서 제외\n");
			} else if (sender.isOp()) {
				if (args.length != 1) {
					if ((args[1].equalsIgnoreCase("create")) || (args[1].equalsIgnoreCase("c"))) {
						if (!rm.isFileExist(args[0])) {
							rm.createFile(args[0]);
							sender.sendMessage("\"" + args[0] + "\"" + "의 역할 파일 생성하였습니다.");
						} else {
							sender.sendMessage("\"" + args[0] + "\"" + "의 역할 파일은 이미 있습니다.");
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
								sender.sendMessage("해당하는 역할이 없습니다.");
						else
							sender.sendMessage("/bgrole(bgr) <game> grant(g) <role> <feature>");

					} else if ((args[1].equalsIgnoreCase("delete")) || (args[1].equalsIgnoreCase("d"))) {
						if (args.length == 3)
							if (rm.isRoleExist(args[0], args[2]))
								rm.delete(args[0], args[2]);
							else
								sender.sendMessage(args[2] + "의 역할은 없습니다.");
						else
							sender.sendMessage("/bgrole(bgr) <game> delete(d) <role>");

					} else if ((args[1].equalsIgnoreCase("deletefile")) || (args[1].equalsIgnoreCase("df"))) {
						rm.delete(args[0]);
						sender.sendMessage(args[0] + "의 파일을 삭제하였습니다.");

					} else if ((args[1].equalsIgnoreCase("set")) || (args[1].equalsIgnoreCase("s"))) {
						if (!RoleDB.isSet) {
							rm.set(args[0]);
							sender.sendMessage(args[0] + "의 역할이 세팅되었습니다.");
						} else {
							rm.unset(args[0]);
							sender.sendMessage(args[0] + "의 역할을 해제했습니다.");
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
										sender.sendMessage(args[1] + "의 역할은 사용되지 않습니다.");
										rs.exceptRole(args[1]);
									} else {
										sender.sendMessage(args[1] + "의 역할을 사용합니다.");
										rs.unExceptRole(args[1]);
									}
								} else {
									sender.sendMessage(args[1] + "라는 역할은 없습니다.");
								}
							} else {
								sender.sendMessage("역할 세팅이 되어있지 않습니다.");
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
