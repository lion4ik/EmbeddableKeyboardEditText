package ru.alexey.lion4ik;

import android.text.LoginFilter;

/**
 * Created by Alexey_Pushkarev1 on 07/19/2016.
 */
public class CharactersFilter extends LoginFilter.UsernameFilterGeneric {
    private String allowedCharacters;

    public CharactersFilter(String allowedCharacters) {
        super(false);
        this.allowedCharacters = allowedCharacters;
    }

    @Override
    public boolean isAllowed(char c) {
        if (allowedCharacters.indexOf(c) != -1) {
            return true;
        }

        return false;
    }
}
