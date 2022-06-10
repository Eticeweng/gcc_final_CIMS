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
                "Car Information Manage System",
                "Version 0.9a",
                "ANSI escape sequences supporting is required for best experience",
                "Developed by Eticeweng, CJL and LJH, All rights reserved");
        INFO("Please type help for usage help.");
    }

    public static void FAILED_VERIFY_PRIVILEGE(){
        ERROR("Permission denied.");
        ERROR("The user group you belong to doesn't have the permission to execute this command.");
    }

    public static void INVALID_COMMAND_PATTERN(CommandPipeline commandPipeline){
        if (commandPipeline.getParamsCount() == 0){
            FAILED_MATCH_PARAMS_PATTERN(commandPipeline);
            return;
        }
        ERROR("Invalid sub command.");
        ERROR(commandPipeline.getCommand() + " has no sub command: " + commandPipeline.getNextParam());
    }

    public static void FAILED_MATCH_PARAMS_PATTERN(CommandPipeline command){
        ERROR(String.format("%s expects %d params but received %d"
                , command.getCommand()
                , command.getCommand().getParamsCount()
                , command.getParamsCount()));
        SHOW_HELP(command.getCommand());
    }

    public static void INVALID_COMMAND(String cmd){
        ERROR("No such a command: " + cmd);
    }

    public static void NOT_DUPLICATE_AUTH(){
        WARN("You already logon.");
        WARN("Please use \"logout\" to logout first.");
    }

    public static void LOGON(AuthSession authSession){
        INFO("Welcome, " + authSession.getUsername() + ".");
        INFO("Your permission level is " + authSession.getAuthLevel().getAuthName() + ".");
    }

    public static void FAILED_LOGON(){
        ERROR("Failed to login.");
        ERROR("Please check your login name or password.");
    }

    public static void REGISTERED(AuthSession authSession){
        INFO("Your account have successfully registered, and logon as you created.");
        SHOW_USER(authSession);
    }

    public static void FAILED_REGISTER(){
        ERROR("Failed to register.");
        ERROR("Please check your registration info is duplicated or not.");
    }

    public static void LOGOUT_MESSAGE(){
        INFO("You have successfully logout, bye.");
    }

    public static void EXIT_MESSAGE(){
        INFO("System exited, see you.");
    }

    public static void RETURN_MESSAGE_BLOCK(String... messages){
        RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(messages);
    }

    public static void RETURN_HELP_BLOCK(String... messages){
        RETURN_CYAN_MESSAGE_BLOCK(messages);
    }

    public static void SUCCEEDED_CREATE_CAR(Car car){
        INFO("New car information has successfully created:");
        SHOW_CAR(car);
    }

    public static void SHOW_CAR(Car car){
        RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(
                "Car ID: " + car.getID(),
                "Owner: " + car.getOwner().toString(),
                "Car Model: " + car.getModel(),
                "Comment: " + car.getComment()
        );
    }

    public static void FAILED_CREATE_CAR(){
        ERROR("Failed to create car information.");
        ERROR("The database might be full or the ID already registered.");
    }

    public static void NOT_FOUND_CAR(){
        WARN("We cant find the car information you requested.");
        WARN("Please check the car ID you enter, and try again.");
    }

    public static void SUCCEEDED_REMOVE_CAR(Car car){
        INFO("You have successfully remove the car you requested for.");
        SHOW_CAR(car);
    }

    public static void NOT_REMOVE_CAR(){
        WARN("We cant remove the car information you requested.");
        WARN("Please make sure the car you requested belongs to you OR check the ID you enter.");
    }

    public static void SUCCEEDED_MODIFY_CAR(Car car){
        INFO("You have successfully modify this car information.");
        SHOW_CAR(car);
    }

    public static void NOT_MODIFY_CAR(){
        WARN("We can't help you to modify this car information.");
        WARN("Please make sure the car you requested belongs to you OR check the ID you enter.");
    }

    public static void EMPTY_CAR_LIST(){
        WARN("We found no car that belongs to you.");
    }

    public static void FAILED_MODIFY_USER(){
        ERROR("Failed to modify user profile.");
        ERROR("Something BAD might happened inside the database OR program logic.");
    }

    public static void SHOW_USER(AuthSession authSession){
        RETURN_WHITE_VERTICAL_ALIGNED_BLOCK(
                "Permission level: " + authSession.getAuthLevel(),
                "UUID: " + authSession.getUuid(),
                "Login name: " + authSession.getLoginBeacon(),
                "Username: " + authSession.getUsername(),
                "Password(UUID encoded): " + UUID.nameUUIDFromBytes(authSession.getPassword().getBytes())
        );
    }

    public static void SUCCEEDED_MODIFY_USER(AuthSession authSession){
        INFO("You have successfully modify your account information.");
        SHOW_USER(authSession);
    }

    public static void FAILED_ADMIN_CREATE_USER(){
        ERROR("Failed to create user as admin.");
        ERROR("Please check the capacity of the database OR is there a duplicated ID.");
    }

    public static void SUCCEEDED_ADMIN_CREATE_USER(AuthSession authSession){
        INFO("Create user as admin successfully.");
        SHOW_USER(authSession);
    }

    public static void NOT_ADMIN_REVOKE_USER(){
        WARN("Unable to revoke this user.");
        WARN("Please check the user ID you requested is exists or not.");
    }

    public static void SUCCEEDED_ADMIN_REVOKE_USER(AuthSession authSession) {
        INFO("Successfully revoked this user.");
        SHOW_USER(authSession);
    }

    public static void NOT_ADMIN_MODIFY_USER(){
        WARN("Unable to modify this user.");
        WARN("Please check the user ID you requested is exist or not.");
    }

    public static void SUCCEEDED_ADMIN_MODIFY_USER(AuthSession authSession) {
        INFO("Successfully modified this user's account information.");
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
            RETURN_HELP_BLOCK("multiple sub command:" ,
                    "as common: " + usages[0],
                    "as admin: " + usages[1]);
            return;
        }
        if (construction == Commands.CAR || construction == Commands.ADMIN ||
                construction == Commands.PROFILE || construction == Commands.USER){
            RETURN_HELP_BLOCK("multiple sub command:" , construction.getUsage(),
                    "use HELP <sub command> for more info");
            return;
        }
        RETURN_HELP_BLOCK(construction.getUsage());
    }
}
