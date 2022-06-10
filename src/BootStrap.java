import Helper.FileIO;
import Services.FlowServices;
import CommandSystem.AuthSession;
import Structure.Car;
import Structure.SequentialList;

import java.io.IOException;

public class BootStrap {
    public static void main(String[] args) throws IOException, InterruptedException {
        String dataPath = "./res/data"; // 车辆信息数据储存路径
        String authPath = "./res/auth"; // 账号信息数据储存路径

        // 数据处理
        String[] dataLines = FileIO.read(dataPath).split("\n");
        String[] authLines = FileIO.read(authPath).split("\n");
        Car[] cars = new Car[dataLines.length];
        AuthSession[] authSessions = new AuthSession[authLines.length];
        for (int i = 0; i < dataLines.length; i++) {
            if (!dataLines[i].equals("")){
                cars[i] = new Car(dataLines[i]);
            }
        }
        for (int i = 0; i < authLines.length; i++) {
            if (!authLines[i].equals("")){
                authSessions[i] = new AuthSession(authLines[i]);
            }
        }

        // 建立数据表
        SequentialList<Car> carSequentialList = new SequentialList<Car>(cars);
        SequentialList<AuthSession> authSessionSequentialList = new SequentialList<AuthSession>(authSessions);

        // 初始化流程控制并启动系统
        FlowServices mainFlowServices = new FlowServices(authSessionSequentialList, carSequentialList);
        mainFlowServices.start();

        // 系统退出前将数据写入硬盘
        FileIO.write(dataPath, carSequentialList.toString());
        FileIO.write(authPath, authSessionSequentialList.toString());
    }
}
