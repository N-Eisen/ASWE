package textventure.player;

import lombok.Getter;
import textventure.enums.ItemsEnum;
import textventure.inventory.Inventory;

@Getter
public class Player {
	private Inventory inventory = new Inventory();
	private int hunger = 100;
	private int hp = 1;
	private String name;
	private double money = 1.0;
	private boolean joystick = false;

	public void step() {
		if (hunger > 90) {
			increaseHp(5);
		}
		decreaseHunger(5);
	}

	public void foundJoystick() {
		joystick = true;
	}

	public String addItem(ItemsEnum item, int count) {
		return inventory.addItem(item, count);
	}

	public String eatItem(ItemsEnum item, int count) {
		if (item.isEatable()) {
			if (inventory.decrease(item, count)) {
				increaseHunger(count * item.getHungerValue());
				return "You ate " + count + " " + item.getDisplayName();
			} else {
				return "Not enough " + item.getDisplayName() + " in inventory.";
			}
		}
		return "Item " + item.getDisplayName() + " is not eatable.";
	}

	public boolean useItem(ItemsEnum item) {
		if (inventory.hasItem(item)) {
			if (inventory.decrease(item, 1)) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	private void increaseHp(int amount) {
		hp += amount;
		if (hp > 100) {
			hp = 100;
		}
	}

	private void decreaseHunger(int amount) {
		hunger -= amount;
		if (hunger <= 0) {
			hp = 0;
		}
	}

	private void increaseHunger(int amount) {
		hunger += amount;
		if (hunger > 100) {
			hunger = 100;
		}
	}

	public void decreaseHp(int count) {
		hp -= count;
	}

	public boolean hasItem(ItemsEnum item) {
		return inventory.hasItem(item);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return "HP: " + hp + "  Hunger: " + hunger;
	}

	public String printInventory() {
		return inventory.printInventory();
	}

	public String sellItem(ItemsEnum item, int count) {
		if (hasItem(item)) {
			int itemCount = inventory.getCount(item);
			if (itemCount >= count) {
				double totalPrice = item.getPrice() * count;
				inventory.decrease(item, count);
				money += totalPrice;
				return "You sold " + count + " " + item.getDisplayName() + "(s) for " + totalPrice + " money.";
			} else {
				return "You do not have enough " + item.getDisplayName() + " to sell.";
			}
		} else {
			return "not";
		}
	}

	public String buyItem(ItemsEnum itemForSale) {
		if (money >= itemForSale.getPrice()) {
			money += itemForSale.getPrice();
			addItem(itemForSale, 1);
			return "Item bought";
		}
		return "Not enough money";
	}

	public int getCount(ItemsEnum item) {
		return inventory.getCount(item);
	}
}
