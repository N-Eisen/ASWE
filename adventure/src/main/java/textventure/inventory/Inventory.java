package textventure.inventory;

import java.util.HashMap;
import java.util.Map;

import textventure.enums.ItemsEnum;

public class Inventory {
	private Map<ItemsEnum, Integer> itemCounts = new HashMap<>();

	public String addItem(ItemsEnum item, int count) {
		ItemsEnum itemName = item;
		int countCurrently = itemCounts.getOrDefault(itemName, 0);
		itemCounts.put(itemName, countCurrently + count);
		return "Added Item: " + item.getDisplayName();
	}

	public boolean hasItem(ItemsEnum itemToCheck) {
		return itemCounts.containsKey(itemToCheck);
	}

	public boolean decrease(ItemsEnum itemToDecrease, int count) {
		ItemsEnum itemName = itemToDecrease;
		if (itemCounts.containsKey(itemName)) {
			int countNew = itemCounts.get(itemName) - count;
			if (countNew < 0) {
				return false;
			} else if (countNew == 0) {
				itemCounts.remove(itemName);
			} else {
				itemCounts.put(itemName, countNew);
			}
			return true;
		} else {
			return false;
		}
	}

	public String printInventory() {
		StringBuilder inventoryString = new StringBuilder("Inventory:\n");
		for (Map.Entry<ItemsEnum, Integer> entry : itemCounts.entrySet()) {
			ItemsEnum item = entry.getKey();
			int count = entry.getValue();
			inventoryString.append(item.getDisplayName()).append(": ").append(count).append(" | Price: $")
					.append(item.getPrice()).append(" | Eatable: ").append(item.isEatable() ? "Yes" : "No");

			if (item.isEatable()) {
				inventoryString.append(" | Hunger Value: ").append(item.getHungerValue());
			}
			inventoryString.append("\n");
		}
		return inventoryString.toString();
	}
	
	public int getCount(ItemsEnum item) {
        return itemCounts.getOrDefault(item, 0);
    }
}
