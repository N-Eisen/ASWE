package textventure.enums;

public enum ItemsEnum {
	TRASH("TRASH", 0.0, false, 0), BERRY("Berry", 0.5,true,5), MUSHROOM("Mushroom", 0.2, true, 1), BULLET("Bullet", 0.5, false, 0),  PISTOL("Pistol", 5.0, false, 0);

	private final String displayName;
	private final double price;
	private final boolean eatable;
	private final int hungerValue;

	ItemsEnum(String displayName, double price, boolean eatable, int hungerValue) {
        this.displayName = displayName;
        this.price = price;
		this.eatable = eatable;
		this.hungerValue = hungerValue;
    }

	public String getDisplayName() {
		return displayName;
	}

	public double getPrice() {
		return price;
	}

	public boolean isEatable() {
		return eatable;
	}

	public int getHungerValue() {
		return hungerValue;
	}
}
