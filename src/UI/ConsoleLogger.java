package UI;

import Helper.FillSpace;

import java.util.ArrayList;
import java.util.Comparator;

public class ConsoleLogger {
    // \u001b[38;2;<R>;<G>;<B>m Foreground
    // \u001b[48;2;<R>;<G>;<B>m Background
    // \u001b[0m Reset All

    public static void INFO(String info){
        System.out.println(
                new ColorText().setText(" " + info + " ")
                    .setForegroundColor(76, 175, 80)
                    .make()
        );
    }

    public static void WARN(String warn){
        System.out.println(
                new ColorText().setText(" " + warn + " ")
                        .setForegroundColor(7, 66, 116)
                        .setBackgroundColor(235, 245, 87)
                        .make()
        );
    }

    public static void ERROR(String error){
        System.out.println(
                new ColorText().setText(" " + error + " ")
                        .setForegroundColor(255, 255, 255)
                        .setBackgroundColor(227, 60, 48)
                        .make()
        );
    }

    public static void RETURN_WHITE(String message){
        System.out.println(
                new ColorText().setText(" " + message + " ")
                        .setForegroundColor(0, 0, 0)
                        .setBackgroundColor(173, 197, 189)
                        .make()
        );
    }

    public static void RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(String... messages){
        ArrayList<Integer> lengths = new ArrayList<>(messages.length);
        for (String message : messages){
            lengths.add(message.length());
        }
        lengths.sort(Comparator.reverseOrder());
        int max = lengths.get(0);
        for (String message : messages){
            RETURN_WHITE(FillSpace.fill(message, ' ', max));
        }
    }

    public static void SINGLE_CYAN(String message){
        System.out.print(
                new ColorText().setText(message)
                        .setForegroundColor(90, 48, 78)
                        .setBackgroundColor(121, 227, 183)
                        .make()
        );
    }

    public static void SINGLE_DEEPPURPLE(String message){
        System.out.print(
                new ColorText().setText(message)
                        .setForegroundColor(121, 227, 183)
                        .setBackgroundColor(90, 48, 78)
                        .make()
        );
    }

    public static void RETURN_CYAN(String message){
        System.out.println(
                new ColorText().setText(" " + message + " ")
                        .setForegroundColor(90, 48, 78)
                        .setBackgroundColor(121, 227, 183)
                        .make()
        );
    }

    public static void RETURN_DEEPPURPLE(String message){
        System.out.println(
                new ColorText().setText(" " + message + " ")
                        .setForegroundColor(121, 227, 183)
                        .setBackgroundColor(90, 48, 78)
                        .make()
        );
    }

    public static void RETURN_CYAN_MESSAGE_BLOCK(String... messages){
        ArrayList<Integer> lengths = new ArrayList<>(messages.length);
        for (String message : messages){
            lengths.add(message.length());
        }
        lengths.sort(Comparator.reverseOrder());
        int max = lengths.get(0);
        for (String message : messages){
            RETURN_CYAN(FillSpace.fill(message, ' ', max));
        }
    }

    public static void RETURN_DEEPPURPLE_MESSAGE_BLOCK(String... messages){
        ArrayList<Integer> lengths = new ArrayList<>(messages.length);
        for (String message : messages){
            lengths.add(message.length());
        }
        lengths.sort(Comparator.reverseOrder());
        int max = lengths.get(0);
        for (String message : messages){
            RETURN_DEEPPURPLE(FillSpace.fill(message, ' ', max));
        }
    }

}
