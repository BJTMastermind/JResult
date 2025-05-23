package me.bjtmastermind.jresult.utils;

import java.lang.reflect.Field;

public class FormatUtils {

    public static <E> String formatError(E error) {
        if (error instanceof String) {
            return String.format("\"%s\"", error);
        }

        if (error instanceof Throwable) {
            return String.format("%s { message: \"%s\" }", error.getClass().getSimpleName(), ((Exception) error).getMessage());
        }

        StringBuilder sb = new StringBuilder();
        sb.append(error.getClass().getSimpleName()+" {");

        int i = 0;
        for (Field field : error.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                Object value = field.get(error);

                if (value instanceof String) {
                    sb.append(" "+field.getName()+": \""+value+"\"");
                } else {
                    sb.append(" "+field.getName()+": "+value);
                }

                if (i < error.getClass().getDeclaredFields().length - 1) {
                    sb.append(",");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        return sb.append(" }").toString();
    }
}
