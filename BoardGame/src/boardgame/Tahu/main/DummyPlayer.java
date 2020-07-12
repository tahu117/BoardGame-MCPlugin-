package boardgame.Tahu.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DummyPlayer {
	public static List<UUID> uuid = new ArrayList<UUID>();
	public static List<UUID> dummyUuid = new ArrayList<UUID>();

	public static void setDummy(int uuidSize) {
		for (int i = 0; i < uuidSize; i++) {
			uuid.add(UUID.randomUUID());
		}
	}

	public static void dummyPlayer(int dummy) {
		for (int i = 0; i < dummy; i++)
			dummyUuid.add(UUID.randomUUID());
	}
}
