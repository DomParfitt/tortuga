package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StringUtils {

    public static Set<Character> toCharacterSet(String input) {
        Set<Character> characters = new TreeSet<>();
        for(char character : input.toCharArray()) {
            characters.add(new Character(character));
        }
        return characters;
    }

    public static List<Character> toCharacterList(String input) {
        List<Character> characters = new ArrayList<>();
        for(char character : input.toCharArray()) {
            characters.add(new Character(character));
        }
        return characters;
    }

}
