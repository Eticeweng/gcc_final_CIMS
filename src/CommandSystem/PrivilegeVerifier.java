package CommandSystem;

import CommandSystem.Enum.AuthLevel;
import CommandSystem.Enum.Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PrivilegeVerifier {
    static HashMap<AuthLevel, HashSet<Commands>> privilegeList = new HashMap<>();

    static {
        privilegeList.put(AuthLevel.ADMIN, new HashSet<Commands>(Arrays.asList(Commands.values())));
        privilegeList.put(AuthLevel.VISITOR, new HashSet<Commands>(Arrays.asList(
                Commands.LOGIN, Commands.REGISTER, Commands.EXIT
        )));
        privilegeList.put(AuthLevel.COMMON, new HashSet<Commands>(Arrays.asList(
                Commands.CAR_CREATE, Commands.CAR_QUERY, Commands.CAR_MODIFY, Commands.CAR_REMOVE, Commands.CAR_LIST,
                Commands.PROFILE_MODIFY, Commands.PROFILE_SHOW,
                Commands.LOGOUT, Commands.EXIT
        )));
    }

    public static boolean verify(AuthSession authSession, Commands commands) {
        switch (authSession.getAuthLevel()) {
            case ADMIN:
                return privilegeList.get(AuthLevel.ADMIN).contains(commands);
            case COMMON:
                return privilegeList.get(AuthLevel.COMMON).contains(commands);
            case VISITOR:
                return privilegeList.get(AuthLevel.VISITOR).contains(commands);
            default:
                return false;
        }
    }
}
