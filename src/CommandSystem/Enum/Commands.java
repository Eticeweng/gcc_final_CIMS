package CommandSystem.Enum;

import java.util.ArrayList;
import java.util.Comparator;

public enum Commands {
    LOGIN(2, "use to login", "login <login_beacon> <password>"), // visitor
    REGISTER(3, "register a new account", "register <login_beacon> <username> <password>"), // visitor
    CAR(1, "use to operate car info",
            "CAR_CREATE, CAR_QUERY, CAR_REMOVE, CAR_MODIFY, CAR_LIST, CAR_LISTBY, CAR_LISTALL, CAR_MODIFY_ADMIN"
    ),
    ADMIN(1, "use to operate admin command",
            "USER, CAR"
    ),
    PROFILE(1, "use this command to modify your profile",
            "PROFILE_MODIFY, PROFILE_SHOW"),
    USER(1, "for admin only, use to operate user info",
            "USER_CREATE, USER_REVOKE, USER_MODIFY, USER_LIST"), // empty command set, for verify
    CAR_CREATE(3, "use this command to create a car", "car create <id> <model> <comment>"),
    CAR_QUERY(1, "query a car using its ID and return its info", "car query <id>"),
    CAR_REMOVE(1, "remove a car from your account, or remove from any as a admin",
            "car remove <id>;admin car remove <id>"),
    CAR_MODIFY(3, "mod a car info from your account, or from any as a admin",
            "car mod <id> <model> <comment>;car mod <id> <owner> <model> <comment>"),
    CAR_LIST(0, "list all car from your account",
            "car list"), // common
    CAR_LISTBY(1, "admin only, list all car from specific account",
            "admin car listby <owner>"),
    CAR_LISTALL(0, "admin only, list all car from database, same as car list as admin",
            "admin car listall"), // admin
    CAR_MODIFY_ADMIN(4, "admin only, mod a car information from any account",
            "admin car mod <id> <owner> <model> <comment>"), // car mod admin variant
    PROFILE_MODIFY(2, "mod your profile, including username and password",
            "profile mod <username> <password>"),
    PROFILE_SHOW(0, "display your profile info",
            "profile show"), // common
    USER_CREATE(4, "admin only, create a new user",
            "admin user create <auth_level> <login_beacon> <username> <password>"),
    USER_REVOKE(1, "admin only, delete(revoke) a user",
            "admin user revoke <login_beacon>"),
    USER_MODIFY(4, "admin only, mod a user's info",
            "admin user mod <auth_level> <login_beacon> <username> <password>"), // admin
    USER_LIST(0, "admin only, list all user", "admin user list"),
    HELP(0, "display command's help info, display all with no param or display specific one with param",
            "HELP [command]"),
    LOGOUT(0, "use this to logout your current account", "logout"), // common, admin
    EXIT(0, "shutdown and save all data", "EXIT"), // all
    __INVALID_COMMAND__(0, "no such a command", "use HELP [command] for more info");

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

    // login <login_beacon> <password>
    // register <login_beacon> <username> <password>
    // car create <id> <model> <comment>
    // car query <id>
    // car remove <id>
    // car list
    // car mod <id> <model> <comment>
    // profile show
    // profile mod <username> <password>

    // admin user create <auth_level> <login_beacon> <username> <password>
    // admin user revoke <login_beacon>
    // admin user mod <auth_level> <login_beacon> <username> <password>
    // admin user list
    // admin car remove <id>
    // admin car listby <owner>
    // admin car listall
    // admin car mod <id> <owner> <model> <comment>

}
