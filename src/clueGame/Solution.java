package clueGame;

public class Solution {



	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution() {}


	public Solution(Card p, Card r, Card w) {	
		person = p;
		room = r;
		weapon = w;
	}
	
	public Solution(String p, String r, String w) {	
		person = new Card(p, CardType.PERSON);
		room = new Card(r, CardType.ROOM);
		weapon = new Card(w, CardType.WEAPON);
	}

	public boolean equals(Solution other) {
		if(person.equals(other.person) && room.equals(other.room) && weapon.equals(other.weapon)) {
			return true;
		}
		return false;
	}

	public boolean contains(Card card) {
		if(card.equals(person) || card.equals(room) || card.equals(weapon)) {
			return true;
		}
		return false;
	}
	
	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
	
	public String toString() {
		return (person.getName() + " in the " + room.getName() + " with the " + weapon.getName());
	}
}
