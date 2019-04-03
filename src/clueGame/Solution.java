package clueGame;

public class Solution {



	private Card person;
	private Card room;
	private Card weapon;


	public Solution(Card p, Card r, Card w) {	
		person = p;
		room = r;
		weapon = w;
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
}
