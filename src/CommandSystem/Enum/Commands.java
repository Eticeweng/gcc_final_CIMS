package CommandSystem.Enum;

import java.util.ArrayList;
import java.util.Comparator;

public enum Commands {
    LOGIN(2, "用于登录", "login <登录名> <密码>"), // visitor
    REGISTER(3, "注册新账号", "register <登录名> <用户名> <密码>"), // visitor
    CAR(1, "用于操作车辆信息",
            "CAR_CREATE, CAR_QUERY, CAR_REMOVE, CAR_MODIFY, CAR_LIST, CAR_LISTBY, CAR_LISTALL, CAR_MODIFY_ADMIN"
    ),
    ADMIN(1, "用于管理员权限",
            "USER, CAR"
    ),
    PROFILE(1, "用于个人资料修改",
            "PROFILE_MODIFY, PROFILE_SHOW"),
    USER(1, "管理员命令列表",
            "USER_CREATE, USER_REVOKE, USER_MODIFY, USER_LIST"), // empty command set, for verify
    CAR_CREATE(3, "用于创建车辆信息", "car create <id> <车辆型号> <备注>"),
    CAR_QUERY(1, "通过车牌ID查询信息", "car query <id>"),
    CAR_REMOVE(1, "删除你所属车辆信息，或者以管理员身份删除任意车辆",
            "car remove <id>;admin car remove <id>"),
    CAR_MODIFY(3, "修改你所属车辆信息，或者以管理员身份修改任意车辆",
            "car mod <id> <车辆型号> <备注>;car mod <id> <拥有者> <车辆型号> <备注>"),
    CAR_LIST(0, "列出你名下的所有车辆信息",
            "car list"), // common
    CAR_LISTBY(1, "管理员指令，列出任意用户的车辆信息",
            "admin car listby <拥有者>"),
    CAR_LISTALL(0, "管理员指令，列出所有车辆信息",
            "admin car listall"), // admin
    CAR_MODIFY_ADMIN(4, "管理员指令，修改任意账户下的任意车辆",
            "admin car mod <id> <拥有者> <车辆型号> <备注>"), // car mod admin variant
    PROFILE_MODIFY(2, "用于修改你的个人资料",
            "profile mod <用户名> <密码>"),
    PROFILE_SHOW(0, "显示你的个人资料",
            "profile show"), // common
    USER_CREATE(4, "管理员指令，创建用户",
            "admin user create <权限等级> <登录名> <用户名> <密码>"),
    USER_REVOKE(1, "管理员指令，删除用户",
            "admin user revoke <登录名>"),
    USER_MODIFY(4, "管理员指令，修改用户信息",
            "admin user mod <权限等级> <登录名> <用户名> <密码>"), // admin
    USER_LIST(0, "管理员指令，列出所有用户", "admin user list"),
    HELP(0, "显示帮助，不带参数显示所有用法或者指定一个指令查看详细用法",
            "help [指令]"),
    LOGOUT(0, "退出当前帐号", "logout"), // common, admin
    EXIT(0, "关闭系统并且保存所有数据修改", "exit"), // all
    __INVALID_COMMAND__(0, "没有该指令", "使用 help [指令] 来查看更多详细信息");

    private final int paramsCount;
    private final String description;
    private final String usage;
    private static final int longestDescLength;
    private static final int longestCmdLength;

    static {
        ArrayList<Integer> descLength = new ArrayList<>(Commands.values().length);
        ArrayList<Integer> cmdLength = new ArrayList<>(Commands.values().length);
        for (Commands commands : Commands.values()){
            descLength.add(commands.description.length());
            cmdLength.add(commands.toString().length());
        }
        descLength.sort(Comparator.reverseOrder());
        cmdLength.sort(Comparator.reverseOrder());
        longestDescLength = descLength.get(0);
        longestCmdLength = cmdLength.get(0);
    }

    Commands(int paramsCount, String description, String usage) {
        this.paramsCount = paramsCount;
        this.description = description;
        this.usage = usage;
    }

    public static Commands parse(String cmd){
        Commands construction;
        try {
            construction = valueOf(cmd.toUpperCase());
        } catch (Exception e){
            construction = __INVALID_COMMAND__;
        }
        return construction;
    }

    public static int getLongestDescLength(){
        return longestDescLength;
    }

    public static int getLongestCmdLength(){
        return longestCmdLength;
    }

    public int getParamsCount(){
        return paramsCount;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    // login <登录名> <密码>
    // register <登录名> <用户名> <密码>
    // car create <id> <车辆型号> <备注>
    // car query <id>
    // car remove <id>
    // car list
    // car mod <id> <车辆型号> <备注>
    // profile show
    // profile mod <用户名> <密码>

    // admin user create <权限等级> <登录名> <用户名> <密码>
    // admin user revoke <登录名>
    // admin user mod <权限等级> <登录名> <用户名> <密码>
    // admin user list
    // admin car remove <id>
    // admin car listby <拥有者>
    // admin car listall
    // admin car mod <id> <拥有者> <车辆型号> <备注>

}
