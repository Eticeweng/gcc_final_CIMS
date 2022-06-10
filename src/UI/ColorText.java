package UI;

public class ColorText {
    String string;
    int[] foregroundColor = null;
    int[] backgroundColor = null;
    final static String RESET = "\u001B[0m";
    final static String FOREGROUND_TEMPLATE = "\u001b[38;2;%d;%d;%dm";
    final static String BACKGROUND_TEMPLATE = "\u001b[48;2;%d;%d;%dm";

    public ColorText() {
    }

    public ColorText setText(String string){
        this.string = string;
        return this;
    }

    public ColorText setForegroundColor(int R, int G, int B){
        this.foregroundColor = new int[3];
        this.foregroundColor[0] = R > 255 || R < 0 ? 0 : R;
        this.foregroundColor[1] = G > 255 || G < 0 ? 0 : G;
        this.foregroundColor[2] = B > 255 || B < 0 ? 0 : B;
        return this;
    }

    public ColorText setBackgroundColor(int R, int G, int B){
        this.backgroundColor = new int[3];
        this.backgroundColor[0] = R > 255 || R < 0 ? 0 : R;
        this.backgroundColor[1] = G > 255 || G < 0 ? 0 : G;
        this.backgroundColor[2] = B > 255 || B < 0 ? 0 : B;
        return this;
    }

    public String make(){
        StringBuilder stringBuilder = new StringBuilder();
        if (this.foregroundColor != null){
            stringBuilder.append(String.format(FOREGROUND_TEMPLATE,
                    this.foregroundColor[0],
                    this.foregroundColor[1],
                    this.foregroundColor[2]
                    ));
        }
        if (this.backgroundColor != null){
            stringBuilder.append(String.format(BACKGROUND_TEMPLATE,
                    this.backgroundColor[0],
                    this.backgroundColor[1],
                    this.backgroundColor[2]
            ));
        }
        stringBuilder.append(this.string);
        stringBuilder.append(RESET);
        return stringBuilder.toString();
    }
}
