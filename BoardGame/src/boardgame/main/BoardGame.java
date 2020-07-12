package boardgame.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BoardGame extends JavaPlugin{
	public RoleFile rf = new RoleFile(getDataFolder().getAbsolutePath());
	
	@Override
	public void onEnable() {
		System.out.println("BoardGame Lodding");
	}
	
	@Override
	public void onDisable() {
	
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("bg")) {
			if (args.length == 0) {
				sender.sendMessage("/bg start <game> : <game> 보드게임 실행\n"
						+ "/bg stop <game> : <game> 보드게임 종료\n"
						+ "/bg role : 보드게임 역할");
			}
			else if(args[0].equalsIgnoreCase("role")) {
				if (args.length == 1) {
					sender.sendMessage("/bg role create <game> : <game> 보드게임의 역할 파일 생성\n"
							+ "/bg role add <game> <role> (<count>) : <game> 보드게임의 역할 추가(<count> 안쓸시 1로 저장) \n");
				} else if (args[1].equalsIgnoreCase("create")) {
					rf.createRole(args[2]);
				} else if (args[1].equalsIgnoreCase("add")) {
					if (args.length < 4)
						sender.sendMessage("/bg role add <game> <role> (<count>)");
					else if(args.length == 4) 
						rf.addRole(args[2], args[3], "1");
					else if (args.length == 5)
						rf.addRole(args[2], args[3], args[4]);
					else if (args.length > 5)
						sender.sendMessage("더 많은 명령을 치셨습니다.");
				}
				//sender.sendMessage("error");
				//role	add		<game>	<role>	<count>
				//0		1		2		3		4
			}
			else if(args[0].equalsIgnoreCase("check")) {
			}
			return true;
		}		
		return false;
	}
}
