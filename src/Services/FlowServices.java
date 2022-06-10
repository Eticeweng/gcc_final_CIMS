package Services;

import CommandSystem.CommandPipeline;
import CommandSystem.CommandsResolver;
import CommandSystem.PrivilegeVerifier;
import CommandSystem.AuthSession;
import Structure.Car;
import CommandSystem.Enum.AuthLevel;
import CommandSystem.Enum.Commands;
import Structure.SequentialList;
import UI.HintMaker;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class FlowServices {
    static AuthSession emptySession;
    AuthSession currentSession;
    SequentialList<AuthSession> authSessionSequentialList;
    SequentialList<Car> carSequentialList;
    Services services;
    boolean isEnd = false;
    boolean isLogon = false;

    static {
        emptySession = new AuthSession(AuthLevel.VISITOR, new Date().getTime() + "", null, null);
    }

    public FlowServices(SequentialList<AuthSession> authSessionSequentialList, SequentialList<Car> carSequentialList) {
        this.currentSession = emptySession;
        this.authSessionSequentialList = authSessionSequentialList;
        this.carSequentialList = carSequentialList;
    }

    public boolean start() throws IOException, InterruptedException {
        services = new Services(authSessionSequentialList, carSequentialList);
        Scanner inputStream = new Scanner(System.in);
        CommandPipeline mainPipeline;
        Car cacheCar;
        AuthSession cacheAuth;
        SequentialList<Car> cacheCarList;
        SequentialList<AuthSession> cacheAuthList;
        System.out.println();
        HintMaker.WELCOME_MESSAGE();
        System.out.println();
        while (!isEnd) {
            if (currentSession.getAuthLevel() == AuthLevel.VISITOR){
                System.out.print(currentSession.getAuthLevel().getAuthName());
            }else {
                System.out.print(String.format("%s/%s", currentSession.getUsername(),
                        currentSession.getAuthLevel().getAuthName()));
            }
            System.out.print("> ");
            mainPipeline = CommandsResolver.resolve(inputStream.nextLine());
            switch (mainPipeline.getCommand()) {
                case LOGIN:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (isLogon){
                        HintMaker.NOT_DUPLICATE_AUTH();
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())) {
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuth = services.login(mainPipeline.getNextParam(), mainPipeline.getNextParam());
                    if (cacheAuth == null) {
                        HintMaker.FAILED_LOGON();
                        break;
                    }
                    currentSession = cacheAuth;
                    HintMaker.LOGON(currentSession);
                    isLogon = true;
                    cacheAuth = null;
                    break;
                case REGISTER:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (isLogon){
                        HintMaker.NOT_DUPLICATE_AUTH();
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())) {
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuth = services.register(mainPipeline.getNextParam(), mainPipeline.getNextParam(),
                            mainPipeline.getNextParam());
                    if (cacheAuth == null){
                        HintMaker.FAILED_REGISTER();
                        break;
                    }
                    HintMaker.REGISTERED(cacheAuth);
                    currentSession = cacheAuth;
                    isLogon = true;
                    break;
                case CAR_CREATE:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheCar = services.createCar(currentSession, mainPipeline.getNextParam(),
                            mainPipeline.getNextParam(), mainPipeline.getNextParam());
                    if (cacheCar != null) {
                        HintMaker.SUCCEEDED_CREATE_CAR(cacheCar);
                        break;
                    }
                    HintMaker.FAILED_CREATE_CAR();
                    cacheCar = null;
                    break;
                case CAR_QUERY:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheCar = services.queryCar(mainPipeline.getNextParam());
                    if (cacheCar != null){
                        HintMaker.SHOW_CAR(cacheCar);
                        break;
                    }
                    HintMaker.NOT_FOUND_CAR();
                    cacheCar = null;
                    break;
                case CAR_REMOVE:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    if (currentSession.getAuthLevel() == AuthLevel.ADMIN){
                        cacheCar = services.removeCar(mainPipeline.getNextParam());
                    }else {
                        cacheCar = services.removeCar(currentSession, mainPipeline.getNextParam());
                    }
                    if (cacheCar == null){
                        HintMaker.NOT_REMOVE_CAR();
                        break;
                    }
                    HintMaker.SUCCEEDED_REMOVE_CAR(cacheCar);
                    cacheCar = null;
                    break;
                case CAR_MODIFY:
                    if (currentSession.getAuthLevel() == AuthLevel.ADMIN){
                        if (!mainPipeline.isValid(Commands.CAR_MODIFY_ADMIN)){
                            HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                            break;
                        }
                        cacheCar = services.modifyCar(mainPipeline.getNextParam(), mainPipeline.getNextParam(),
                                mainPipeline.getNextParam(), mainPipeline.getNextParam());
                    }else {
                        if (mainPipeline.isValid()){
                            HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                            break;
                        }
                        if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                            HintMaker.FAILED_VERIFY_PRIVILEGE();
                            break;
                        }
                        cacheCar = services.modifyCar(currentSession, mainPipeline.getNextParam(),
                                mainPipeline.getNextParam(), mainPipeline.getNextParam());
                    }
                    if (cacheCar == null){
                        HintMaker.NOT_MODIFY_CAR();
                        break;
                    }
                    HintMaker.SUCCEEDED_MODIFY_CAR(cacheCar);
                    cacheCar = null;
                    break;
                case CAR_LIST:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheCarList = services.listCar(currentSession);
                    if (cacheCarList.size() == 0) {
                        HintMaker.EMPTY_CAR_LIST();
                        break;
                    }
                    for (int i = 0; i < cacheCarList.size(); i++){
                        HintMaker.SHOW_CAR(cacheCarList.get(i));
                        System.out.println();
                    }
                    cacheCarList = null;
                    break;
                case CAR_LISTBY:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheCarList = services.listCar(new AuthSession(null,
                            mainPipeline.getNextParam(), null, null));
                    if (cacheCarList.size() == 0) {
                        HintMaker.EMPTY_CAR_LIST();
                        break;
                    }
                    for (int i = 0; i < cacheCarList.size(); i++){
                        HintMaker.SHOW_CAR(cacheCarList.get(i));
                        System.out.println();
                    }
                    cacheCarList = null;
                    break;
                case CAR_LISTALL:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheCarList = services.listCar();
                    if (cacheCarList.size() == 0) {
                        HintMaker.EMPTY_CAR_LIST();
                        break;
                    }
                    for (int i = 0; i < cacheCarList.size(); i++){
                        HintMaker.SHOW_CAR(cacheCarList.get(i));
                        System.out.println();
                    }
                    cacheCarList = null;
                    break;
                case USER_LIST:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuthList = services.listUser();
                    for (int i = 0; i < cacheAuthList.size(); i++){
                        HintMaker.SHOW_USER(cacheAuthList.get(i));
                        System.out.println();
                    }
                    cacheAuthList = null;
                    break;
                case PROFILE_MODIFY:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuth = services.modifyProfile(currentSession, mainPipeline.getNextParam(),
                            mainPipeline.getNextParam());
                    if (cacheAuth == null) {
                        HintMaker.FAILED_MODIFY_USER();
                        break;
                    }
                    currentSession = cacheAuth;
                    HintMaker.SUCCEEDED_MODIFY_USER(cacheAuth);
                    cacheAuth = null;
                    break;
                case PROFILE_SHOW:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    HintMaker.SHOW_USER(currentSession);
                    break;
                case USER_CREATE:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuth = services.createUser(mainPipeline.getNextParam(), mainPipeline.getNextParam(),
                            mainPipeline.getNextParam(), mainPipeline.getNextParam());
                    if (cacheAuth == null){
                        HintMaker.FAILED_ADMIN_CREATE_USER();
                        break;
                    }
                    HintMaker.SUCCEEDED_ADMIN_CREATE_USER(cacheAuth);
                    cacheAuth = null;
                    break;
                case USER_REVOKE:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuth = services.revokeUser(mainPipeline.getNextParam());
                    if (cacheAuth == null){
                        HintMaker.NOT_ADMIN_REVOKE_USER();
                        break;
                    }
                    HintMaker.SUCCEEDED_ADMIN_REVOKE_USER(cacheAuth);
                    break;
                case USER_MODIFY:
                    if (mainPipeline.isValid()){
                        HintMaker.FAILED_MATCH_PARAMS_PATTERN(mainPipeline);
                        break;
                    }
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    cacheAuth = services.modifyUser(mainPipeline.getNextParam(), mainPipeline.getNextParam(),
                            mainPipeline.getNextParam(), mainPipeline.getNextParam());
                    if (cacheAuth == null){
                        HintMaker.NOT_ADMIN_MODIFY_USER();
                        break;
                    }
                    HintMaker.SUCCEEDED_ADMIN_MODIFY_USER(cacheAuth);
                    break;
                case LOGOUT:
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE();
                        break;
                    }
                    this.currentSession = emptySession;
                    this.isLogon = false;
                    HintMaker.LOGOUT_MESSAGE();
                    break;
                case EXIT:
                    if (!PrivilegeVerifier.verify(currentSession, mainPipeline.getCommand())){
                        HintMaker.FAILED_VERIFY_PRIVILEGE(); // should no one hit this segment
                        break;
                    }
                    this.isEnd = true;
                    HintMaker.EXIT_MESSAGE();
                    break;
                case HELP:
                    if (mainPipeline.getParamsCount() == 0){
                        HintMaker.SHOW_HELP();
                        break;
                    }
                    HintMaker.SHOW_HELP(mainPipeline.getNextParam());
                    break;
                case CAR:
                case ADMIN:
                case PROFILE:
                case USER:
                    HintMaker.INVALID_COMMAND_PATTERN(mainPipeline);
                    break;
                default:
                    HintMaker.INVALID_COMMAND(mainPipeline.getNextParam());
                    break;
            }
            System.out.println();
        }
        return false;
    }
}
