package utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<Character> toCharacterList(String input) {
        List<Character> characters = new ArrayList<>();
        for(char character : input.toCharArray()) {
            characters.add(new Character(character));
        }
        return characters;
    }
}
