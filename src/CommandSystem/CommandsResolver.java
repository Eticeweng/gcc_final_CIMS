package CommandSystem;

import CommandSystem.Enum.Commands;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandsResolver {
    public static CommandPipeline resolve(String cmd){
        String[] fields = cmd.trim().split(" ");
        CommandPipeline pipeline = new CommandPipeline();
        switch (fields[0]){
            case "login":
                if (fields.length != Commands.LOGIN.getParamsCount() + 1){
                    pipeline.setCommand(Commands.LOGIN)
                            .setParams(paramsResolve(Arrays.copyOfRange(fields, 1, fields.length)));
                    break;
                }
                pipeline.setCommand(Commands.LOGIN)
                        .setParams(paramsResolve(fields[1], fields[2]));
                break;
            case "register":
                if (fields.length != Commands.REGISTER.getParamsCount() + 1){
                    pipeline.setCommand(Commands.REGISTER)
                            .setParams(paramsResolve(Arrays.copyOfRange(fields, 1, fields.length)));
                    break;
                }
                pipeline.setCommand(Commands.REGISTER)
                        .setParams(paramsResolve(fields[1], fields[2], fields[3]));
                break;
            case "car":
                if (fields.length < Commands.CAR.getParamsCount() + 1){
                    pipeline.setCommand(Commands.CAR)
                            .setParams(paramsResolve(Arrays.copyOfRange(fields, 1, fields.length)));
                    break;
                }
                pipeline = carResolve(Arrays.copyOfRange(fields, 1, fields.length));
                break;
            case "profile":
                if (fields.length < Commands.PROFILE.getParamsCount() + 1){
                    pipeline.setCommand(Commands.PROFILE)
                            .setParams(paramsResolve(Arrays.copyOfRange(fields, 1, fields.length)));
                    break;
                }
                pipeline = profileResolve(Arrays.copyOfRange(fields, 1, fields.length));
                break;
            case "user":
                if (fields.length < Commands.USER.getParamsCount() + 1){
                    pipeline.setCommand(Commands.USER)
                            .setParams(paramsResolve(Arrays.copyOfRange(fields, 1, fields.length)));
                    break;
                }
                pipeline = userResolve(Arrays.copyOfRange(fields, 1, fields.length));
                break;
            case "admin":
                if (fields.length < Commands.ADMIN.getParamsCount() + 1){
                    pipeline.setCommand(Commands.ADMIN)
                            .setParams(paramsResolve(Arrays.copyOfRange(fields, 1, fields.length)));
                    break;
                }
                pipeline = adminResolve(Arrays.copyOfRange(fields, 1, fields.length));
                break;
            case "logout":
                pipeline.setCommand(Commands.LOGOUT);
                break;
            case "exit":
                pipeline.setCommand(Commands.EXIT);
                break;
            case "help":
                if (fields.length > Commands.HELP.getParamsCount() + 1){
                    pipeline.setCommand(Commands.HELP)
                            .setParams(paramsResolve(fields[1]));
                    break;
                }
                pipeline.setCommand(Commands.HELP);
                break;
            default:
                pipeline.setCommand(Commands.__INVALID_COMMAND__)
                        .setParams(paramsResolve(fields[0]));
                break;
        }
        return pipeline;
    }

    private static ArrayList<String> paramsResolve(String... params){
        return new ArrayList<String>(Arrays.asList(params));
    }

    private static CommandPipeline carResolve(String[] cmds){
        CommandPipeline pipeline = new CommandPipeline();
        switch (cmds[0]){
            case "create":
                if (cmds.length != Commands.CAR_CREATE.getParamsCount() + 1){
                    pipeline.setCommand(Commands.CAR_CREATE)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.CAR_CREATE)
                        .setParams(paramsResolve(cmds[1], cmds[2], cmds[3]));
                break;
            case "query":
                if (cmds.length != Commands.CAR_QUERY.getParamsCount() + 1){
                    pipeline.setCommand(Commands.CAR_QUERY)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.CAR_QUERY)
                        .setParams(paramsResolve(cmds[1]));
                break;
            case "remove":
                if (cmds.length != Commands.CAR_REMOVE.getParamsCount() + 1){
                    pipeline.setCommand(Commands.CAR_REMOVE)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.CAR_REMOVE)
                        .setParams(paramsResolve(cmds[1]));
                break;
            case "mod":
                if (cmds.length != Commands.CAR_MODIFY.getParamsCount() + 1){
                    pipeline.setCommand(Commands.CAR_MODIFY)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.CAR_MODIFY)
                        .setParams(paramsResolve(cmds[1], cmds[2], cmds[3]));
                break;
            case "list":
                pipeline.setCommand(Commands.CAR_LIST);
                break;
            case "listby":
                if (cmds.length != Commands.CAR_LISTBY.getParamsCount() + 1){
                    pipeline.setCommand(Commands.CAR_LISTBY)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.CAR_LISTBY)
                        .setParams(paramsResolve(cmds[1]));
                break;
            case "listall":
                pipeline.setCommand(Commands.CAR_LISTALL);
                break;
            default:
                pipeline.setCommand(Commands.CAR)
                        .setParams(paramsResolve(cmds[0]));
                break;
        }
        return pipeline;
    }

    private static CommandPipeline profileResolve(String[] cmds){
        CommandPipeline pipeline = new CommandPipeline();
        switch (cmds[0]){
            case "show":
                pipeline.setCommand(Commands.PROFILE_SHOW);
                break;
            case "mod":
                if (cmds.length != Commands.PROFILE_MODIFY.getParamsCount() + 1){
                    pipeline.setCommand(Commands.PROFILE_MODIFY)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.PROFILE_MODIFY)
                        .setParams(paramsResolve(cmds[1], cmds[2]));
                break;
            default:
                pipeline.setCommand(Commands.PROFILE)
                        .setParams(paramsResolve(cmds[0]));
                break;
        }
        return pipeline;
    }

    private static CommandPipeline userResolve(String[] cmds){
        CommandPipeline pipeline = new CommandPipeline();
        switch (cmds[0]){
            case "create":
                if (cmds.length != Commands.USER_CREATE.getParamsCount() + 1){
                    pipeline.setCommand(Commands.USER_CREATE)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.USER_CREATE)
                        .setParams(paramsResolve(cmds[1], cmds[2], cmds[3], cmds[4]));
                break;
            case "revoke":
                if (cmds.length != Commands.USER_REVOKE.getParamsCount() + 1){
                    pipeline.setCommand(Commands.USER_REVOKE)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.USER_REVOKE)
                        .setParams(paramsResolve(cmds[1]));
                break;
            case "mod":
                if (cmds.length != Commands.USER_MODIFY.getParamsCount() + 1){
                    pipeline.setCommand(Commands.USER_MODIFY)
                            .setParams(paramsResolve(Arrays.copyOfRange(cmds, 1, cmds.length)));
                    break;
                }
                pipeline.setCommand(Commands.USER_MODIFY)
                        .setParams(paramsResolve(cmds[1], cmds[2], cmds[3], cmds[4]));
                break;
            case "list":
                pipeline.setCommand(Commands.USER_LIST);
                break;
            default:
                pipeline.setCommand(Commands.USER)
                        .setParams(paramsResolve(cmds[0]));
                break;
        }
        return pipeline;
    }

    private static CommandPipeline adminResolve(String[] cmds){
        CommandPipeline pipeline = new CommandPipeline();
        switch (cmds[0]){
            case "user":
                pipeline = userResolve(Arrays.copyOfRange(cmds, 1, cmds.length));
                break;
            case "car":
                pipeline = carResolve(Arrays.copyOfRange(cmds, 1, cmds.length));
                break;
            default:
                pipeline.setCommand(Commands.ADMIN)
                        .setParams(paramsResolve(cmds[0]));
                break;

        }
        return pipeline;
    }
}
