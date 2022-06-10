package Helper;

public class FillSpace {
    public static String fill(String string, char filler, int length){
        StringBuilder out = new StringBuilder(length);
        out.append(string);
        int gap = length - string.length();
        if (gap > 0){
            for (int i = 0; i < gap; i++){
                out.append(filler);
            }
        }
        return out.toString();
    }
}
