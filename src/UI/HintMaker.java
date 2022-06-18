package UI;

import Helper.FillSpace;
import CommandSystem.CommandPipeline;
import CommandSystem.AuthSession;
import Structure.Car;
import CommandSystem.Enum.AuthLevel;
import CommandSystem.Enum.Commands;

import java.util.UUID;

import static UI.ConsoleLogger.*;

public class HintMaker {
    public static void WELCOME_MESSAGE(){
        RETURN_CYAN_MESSAGE_BLOCK(
                "车辆信息管理系统",
                "当前版本 0.9b_zh_cn",
                "请使用支持ANSI 控制序列的终端来运行本应用以获得最佳的使用体验",
                "由 Eticeweng, CJL 和 LJH 开发, 保留所有权利");
        INFO("请输入 help 来查看使用帮助");
    }

    public static void FAILED_VERIFY_PRIVILEGE(){
        ERROR("权限不足");
        ERROR("你所属的用户组没有权限使用该命令");
    }

    public static void INVALID_COMMAND_PATTERN(CommandPipeline commandPipeline){
        if (commandPipeline.getParamsCount() == 0){
            FAILED_MATCH_PARAMS_PATTERN(commandPipeline);
            return;
        }
        ERROR("子命令无效");
        ERROR(commandPipeline.getCommand() + " 没有该子命令: " + commandPipeline.getNextParam());
    }

    public static void FAILED_MATCH_PARAMS_PATTERN(CommandPipeline command){
        ERROR(String.format("%s 需要 %d 个参数但是却接受了 %d 个"
                , command.getCommand()
                , command.getCommand().getParamsCount()
                , command.getParamsCount()));
        SHOW_HELP(command.getCommand());
    }

    public static void INVALID_COMMAND(String cmd){
        ERROR("没有该命令: " + cmd);
    }

    public static void NOT_DUPLICATE_AUTH(){
        WARN("你已经登陆了");
        WARN("请先使用 logout 来退出当前帐号");
    }

    public static void LOGON(AuthSession authSession){
        INFO("欢迎回来, " + authSession.getUsername() + ".");
        INFO("你的权限等级为 " + authSession.getAuthLevel().getAuthName() + ".");
    }

    public static void FAILED_LOGON(){
        ERROR("登陆失败");
        ERROR("请检查你是否输入了正确的密码或者该账号已经注册");
    }

    public static void REGISTERED(AuthSession authSession){
        INFO("你已经成功注册，且已自动登录");
        SHOW_USER(authSession);
    }

    public static void FAILED_REGISTER(){
        ERROR("注册失败");
        ERROR("你的ID已被注册");
    }

    public static void LOGOUT_MESSAGE(){
        INFO("账号已经退出，再见");
    }

    public static void EXIT_MESSAGE(){
        INFO("系统成功关闭，改日再会");
    }

    public static void RETURN_MESSAGE_BLOCK(String... messages){
        RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(messages);
    }

    public static void RETURN_HELP_BLOCK(String... messages){
        RETURN_CYAN_MESSAGE_BLOCK(messages);
    }

    public static void SUCCEEDED_CREATE_CAR(Car car){
        INFO("你所指定的车辆信息已被成功注册:");
        SHOW_CAR(car);
    }

    public static void SHOW_CAR(Car car){
        RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(
                "车牌: " + car.getID(),
                "拥有者: " + car.getOwner().toString(),
                "型号: " + car.getModel(),
                "备注: " + car.getComment()
        );
    }

    public static void FAILED_CREATE_CAR(){
        ERROR("车辆信息注册失败");
        ERROR("请检查该ID是否已经被注册");
    }

    public static void NOT_FOUND_CAR(){
        WARN("无法找你所指定的车辆信息");
        WARN("请检查你输入的车牌然后再试一次");
    }

    public static void SUCCEEDED_REMOVE_CAR(Car car){
        INFO("成功删除你指定的车辆信息");
        SHOW_CAR(car);
    }

    public static void NOT_REMOVE_CAR(){
        WARN("无法删除你所指定的车辆信息");
        WARN("请确保你输入了正确的ID或者你输入的ID是属于你的");
    }

    public static void SUCCEEDED_MODIFY_CAR(Car car){
        INFO("车辆信息修改成功");
        SHOW_CAR(car);
    }

    public static void NOT_MODIFY_CAR(){
        WARN("无法修改你指定的车辆信息");
        WARN("请确保你输入了正确的ID或者你输入的ID是属于你的");
    }

    public static void EMPTY_CAR_LIST(){
        WARN("你名下没有任何车辆信息");
    }

    public static void FAILED_MODIFY_USER(){
        ERROR("无法修改指定用户的信息");
        ERROR("BAD");
    }

    public static void SHOW_USER(AuthSession authSession){
        RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(
                "权限等级: " + authSession.getAuthLevel(),
                "UUID: " + authSession.getUuid(),
                "登录名: " + authSession.getLoginBeacon(),
                "用户名: " + authSession.getUsername(),
                "密码(UUID编译后): " + UUID.nameUUIDFromBytes(authSession.getPassword().getBytes())
        );
    }

    public static void SUCCEEDED_MODIFY_USER(AuthSession authSession){
        INFO("成功修改用户信息");
        SHOW_USER(authSession);
    }

    public static void FAILED_ADMIN_CREATE_USER(){
        ERROR("无法创建用户");
        ERROR("请确保你输入了没有冲突的ID");
    }

    public static void SUCCEEDED_ADMIN_CREATE_USER(AuthSession authSession){
        INFO("成功创建用户");
        SHOW_USER(authSession);
    }

    public static void NOT_ADMIN_REVOKE_USER(){
        WARN("用户信息移除失败");
        WARN("请确保你输入了正确的ID");
    }

    public static void SUCCEEDED_ADMIN_REVOKE_USER(AuthSession authSession) {
        INFO("成功移除用户");
        SHOW_USER(authSession);
    }

    public static void NOT_ADMIN_MODIFY_USER(){
        WARN("用户信息修改失败");
        WARN("请确保你输入了正确的ID");
    }

    public static void SUCCEEDED_ADMIN_MODIFY_USER(AuthSession authSession) {
        INFO("成功以管理员权限修改指定用户信息");
        SHOW_USER(authSession);
    }

    public static void SHOW_HELP(){
        for (Commands commands : Commands.values()){
//            (commands.toString() + ": " + commands.getDescription());
            ConsoleLogger.SINGLE_CYAN(" " + FillSpace.fill(commands.toString(), ' ', Commands.getLongestCmdLength()) + " ");
            ConsoleLogger.SINGLE_DEEPPURPLE(" " + FillSpace.fill(commands.getDescription(), ' ', Commands.getLongestDescLength()) + " ");
            System.out.println();
        }
    }

    public static void SHOW_HELP(Commands command){
        SHOW_COMMAND(command);
    }

    public static void SHOW_HELP(String command){
        Commands construction = Commands.parse(command);
        if (construction == Commands.__INVALID_COMMAND__){
            INVALID_COMMAND(command);
            return;
        }
        SHOW_COMMAND(construction);
    }

    private static void SHOW_COMMAND(Commands construction) {
        ConsoleLogger.RETURN_DEEPPURPLE(construction.getDescription());
        String[] usages = construction.getUsage().split(";");
        if (usages.length > 1){
            RETURN_HELP_BLOCK("检测到多个命令变体:" ,
                    "COMMON: " + usages[0],
                    "ADMIN: " + usages[1]);
            return;
        }
        if (construction == Commands.CAR || construction == Commands.ADMIN ||
                construction == Commands.PROFILE || construction == Commands.USER){
            RETURN_HELP_BLOCK("检测到多个命令变体:" , construction.getUsage(),
                    "使用 help <子命令> 来查看详细信息");
            return;
        }
        RETURN_HELP_BLOCK(construction.getUsage());
    }
}
