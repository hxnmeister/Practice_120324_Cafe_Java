package com.ua.project;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        StringBuilder builder = new StringBuilder();
        String[] strings = {"quantity", "amount", "price"};

        builder.append("UPDATE assortment SET ");
        for (String string : strings) {
            builder.append(string).append("=?, ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" WHERE id=?");

        System.out.println(builder.toString());
    }
}
