package clueGame;

public class Solution {
	
	public Card person;
	public Card room;
	public Card weapon;
	
	
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
}
