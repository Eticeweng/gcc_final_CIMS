package Services.Interface;

import CommandSystem.AuthSession;
import Structure.Car;
import Structure.SequentialList;

public interface IServices {

    // visitor
    AuthSession login(String username, String password);
    AuthSession register(String loginBeacon, String username, String password);

    // common
    Car createCar(AuthSession authSession, String ID, String model, String comment);
    Car queryCar(String ID);
    Car removeCar(AuthSession authSession, String ID);
    Car modifyCar(AuthSession authSession, String ID, String model, String comment);
    SequentialList<Car> listCar(AuthSession authSession);
    String showProfile(AuthSession authSession);
    AuthSession modifyProfile(AuthSession authSession, String username, String password);

    // admin
    AuthSession createUser(String authLevel, String loginBeacon, String username, String password);
    AuthSession revokeUser(String loginBeacon);
    AuthSession modifyUser(String authLevel, String loginBeacon, String username, String password);
    SequentialList<AuthSession> listUser();
    Car removeCar(String ID);
    Car modifyCar(String ID, String owner, String model, String comment);
    SequentialList<Car> listCar();
}
