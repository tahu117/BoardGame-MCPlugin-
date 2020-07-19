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
				sender.sendMessage("미완");
//				sender.sendMessage("/bg <game> start : <game> 보드게임 실행\n" + "/bg <game> stop : <game> 보드게임 종료\n"
//						+ "/bg <game> role : <game> 보드게임 역할 설정");
			}
//			else if (sender.isOp()) {
//				if (!rm.isExist(args[0])) {
//					sender.sendMessage("해당하는 보드 게임은 없습니다.\n");
//				} else {
//					if (args.length == 1) {
//						sender.sendMessage("/bg <game> start : <game> 보드게임 실행\n" + "/bg <game> stop : <game> 보드게임 종료\n"
//								+ "/bg <game> role : <game> 보드게임 역할 설정");
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
				sender.sendMessage("/bgrole(bgr) <game> create(c) : <game> 보드게임의 역할 파일 생성\n"
						+ "/bgrole(bgr) <game> insert(i) <role> (<count>) : <game> 보드게임의 <role> 을 <count> 만큼 추가 (<count> 안쓸시 1로 저장)\n"
						+ "/bgrole(bgr) <game> delete(d) <role> : <game> 보드게임의 <role>을 제거\n"
						+ "/bgrole(bgr) <game> except(e) <role> : <role> 을 게임에서 제외\n"
						+ "/bgrole(bgr) <game> load(l) : <game> 보드게임의 역할 세팅\n"
						+ "/bgrole(bgr) <game> set(s) <role> <feature(team, detect, heal, etc)> : <role> 의 특징 설정\n");
			} else if (sender.isOp()) {
				if ((args[1].equalsIgnoreCase("create")) || (args[1].equalsIgnoreCase("c"))) {
					rm.createFile(args[0]);
					sender.sendMessage("\"" + args[0] + "\"" + "의 역할 파일 생성하였습니다.");
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
							sender.sendMessage(args[2] + "의 역할은 없습니다.");
					} else
						sender.sendMessage("/bgrole(bgr) <game> delete(d) <role>");
				} else if ((args[1].equalsIgnoreCase("except")) || (args[1].equalsIgnoreCase("e"))) {
					if (args.length == 3) {
						if (RoleDB.isLoad.get(args[0])) {
							if (rs.isRoleExist(args[0], args[2])) {
								if (!rs.isExceptRole(args[0], args[2])) {
									sender.sendMessage(args[2] + "의 역할은 사용되지 않습니다.");
									rs.exceptRole(args[0], args[2]);
								} else {
									sender.sendMessage(args[2] + "의 역할을 사용합니다.");
									rs.undoExceptRole(args[0], args[2]);
								}
							} else {
								sender.sendMessage(args[2] + "라는 역할은 없습니다.");
							}
						} else {
							sender.sendMessage("역할 세팅이 되어있지 않습니다.");
						}
					} else
						sender.sendMessage("/bgrole(bgr) <game> except(e) <role>");
				} else if ((args[1].equalsIgnoreCase("load")) || (args[1].equalsIgnoreCase("l"))) {
					if (!RoleDB.isLoad.get(args[0])) {
						rm.loadRoletoFile(args[0]);
						sender.sendMessage(args[0] + "의 역할이 로딩되었습니다.");
					} else {
						rm.unloadRoletoFile(args[0]);
						sender.sendMessage(args[0] + "의 역할을 해제했습니다.");
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
