package main;

public class KeyboardInterface {
	private static String shipName = "";
	private static char[] acceptingLetters = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};
	
	public static boolean nameAccepted() {
		if (shipName.length() > 2) {
			return true;
		}
		return false;
	}
	
	public static boolean letterAccepted(char cChar) {
		for(int i =0; i < acceptingLetters.length; i++) {
			if (acceptingLetters[i] == cChar) {
				return true;
			}
		}
		return false;
	}
	
	public static void handleInput(char cChar) {
		if (shipName.length() < 15) {
			if(letterAccepted(cChar)) {
				if (cChar == ' ') {
					if(!shipName.endsWith(" ")) {
						shipName += String.valueOf(cChar);
					}
				}else {
					shipName += String.valueOf(cChar);
				}
			}
		}
		if(cChar == '\b') {
			shipName = shipName.substring(0, Math.max(0, shipName.length()-1));
		}
	}
	
	public static String getShipName() {
		return shipName;
	}
}
