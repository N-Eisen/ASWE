package textventure.map;

import lombok.Getter;
import lombok.Setter;
import textventure.events.BasisEvent;

@Getter
@Setter
public class BasisFeld {
	public BasisFeld(BasisEvent event, String description) {
		this.event = event;
		this.description = description;
	}

	private BasisEvent event;
	private String description;
	private boolean searched = false;
}
