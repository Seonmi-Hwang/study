// 20170995 컴퓨터학과 4학년 황선미

package practice11;

import java.util.ArrayList;

public class GameCharacter {
	private ArrayList<GameItem> list = new ArrayList<GameItem>();
	
	public void add(String name, int type, int price) {
		GameItem gameItem = new GameItem();
		gameItem.name = name;
		gameItem.type = type;
		gameItem.price = price;
		
		list.add(gameItem);
	}
	
	public void print() {
		int totalPrice = 0;
		
		for (GameItem item : list) {
			totalPrice += item.getPrice();
			System.out.println(item.toString());
		}
		System.out.println(totalPrice);
	}
	
	class GameItem {
		String name;
		int type;
		int price;
		
		int getPrice() {
			return price; 
		}

		@Override
		public String toString() {
			return "GameItem [name=" + name + ", type=" + type + ", price=" + price + "]";
		}
	}
	
}
