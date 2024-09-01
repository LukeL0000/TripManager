package ca.ubc.cs304.model;

import java.util.Random;
public class GenerateID {

    public GenerateID() {
    }

    public String getID(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rsg = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rsg.nextFloat() * chars.length());
            salt.append(chars.charAt(index));
        }
        String randID = salt.toString();
        return randID;
    }
}
