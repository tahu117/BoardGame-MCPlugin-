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
				sender.sendMessage("/bg <game> start : <game> 보드게임 실행\n" + "/bg <game> stop : <game> 보드게임 종료\n"
						+ "/bg <game> role : <game> 보드게임 역할 설정");
			} else if (sender.isOp()) {
				if (!rf.isExist(args[0])) {
					sender.sendMessage("해당하는 보드 게임은 없습니다.\n");
				} else {
					if (args.length == 1) {
						sender.sendMessage("/bg <game> start : <game> 보드게임 실행\n" + "/bg <game> stop : <game> 보드게임 종료\n"
								+ "/bg <game> role : <game> 보드게임 역할 설정");
					} else if (args[1].equalsIgnoreCase("start")) {
						rf.setRole(args[0]);
						wolf.start();
					} else if (args[1].equalsIgnoreCase("stop")) {
						rf.delRole(args[0]);
						wolf.stop();
					} else if (args[1].equalsIgnoreCase("role")) {
						if (args.length == 2) {
							sender.sendMessage("/bg <game> role create : <game> 보드게임의 역할 파일 생성\n"
									+ "/bg <game> role add <role> (<count>) : <game> 보드게임의 <role>을 <count>만큼 추가(<count> 안쓸시 1로 저장) \n"
									+ "/bg <game> role except <role> : <role>을 게임에서 제외");
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
								sender.sendMessage("더 많은 명령을 치셨습니다.");
						} else if (args[2].equalsIgnoreCase("except")) {
							if (args.length == 3) {
								sender.sendMessage("/bg <game> role except <role>");
							} else if (rf.isRoleExist(args[0], args[3])) {
								sender.sendMessage(args[3] + "의 역할은 사용되지 않습니다.");
								Setting.exceptRole(args[3]);
							} else {
								sender.sendMessage(args[3] + "의 역할은 없습니다.");
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
