package utils;

public class PasswordValidator {

    public static boolean verifMdp(String password) {
        if (password.length() < 8) {
            return false;
        }

        int lowerCount = 0;
        int upperCount = 0;
        int digitCount = 0;
        int specialCount = 0;

        for (char ch : password.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                lowerCount++;
            } else if (Character.isUpperCase(ch)) {
                upperCount++;
            } else if (Character.isDigit(ch)) {
                digitCount++;
            } else if (!Character.isLetterOrDigit(ch)) {
                specialCount++;
            }
        }

        return lowerCount >= 3 && upperCount >= 1 && digitCount >= 2 && specialCount >= 1;
    }
}
