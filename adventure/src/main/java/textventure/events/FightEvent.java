package textventure.events;

import lombok.Getter;
import textventure.enums.EnemiesEnum;

@Getter
public class FightEvent extends BasisEvent {

	public FightEvent(String message, EnemiesEnum enemiesKind, boolean needsTrigger, int hpEnemy) {
		super(message);
		this.enemiesKind = enemiesKind;
		this.needsTrigger = needsTrigger;
		this.hpEnemy = hpEnemy;
	}

	private EnemiesEnum enemiesKind;
	private boolean needsTrigger;
	private boolean defeated = false;
	private int hpEnemy;
	
	public void defeated() {
		defeated = true;
	}
	
	public void decreasehp(int count) {
		hpEnemy-=count;
		if ( hpEnemy<= 0) {
			defeated();
		}
	}
}
