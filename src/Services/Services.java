package Services;

import Services.Interface.IServices;
import CommandSystem.AuthSession;
import Structure.Car;
import CommandSystem.Enum.AuthLevel;
import Structure.SequentialList;

import java.util.UUID;

public class Services implements IServices {
    SequentialList<AuthSession> authSessionSequentialList;
    SequentialList<Car> carSequentialList;

    public Services(SequentialList<AuthSession> authSessionSequentialList, SequentialList<Car> carSequentialList) {
        this.authSessionSequentialList = authSessionSequentialList;
        this.carSequentialList = carSequentialList;
    }

    @Override
    public AuthSession login(String loginBeacon, String password) {
        AuthSession pending = authSessionSequentialList.get(
                authSessionSequentialList.indexOf(new AuthSession(
                        null,
                        loginBeacon,
                        null,
                        null
                ))
        );
        if (pending == null || !pending.getPassword().equals(password)){
            return null;
        };
        return pending;
    }

    @Override
    public AuthSession register(String loginBeacon, String username, String password) {
        AuthSession construction = new AuthSession(AuthLevel.COMMON, loginBeacon, username, password);
        int pending = authSessionSequentialList.indexOf(construction);
        if (pending != -1) {
            return null;
        }
        return authSessionSequentialList.add(construction) ? construction : null;
    }

    @Override
    public Car createCar(AuthSession authSession, String ID, String model, String comment) {
        Car construction = new Car(ID, authSession.getUuid(), model, comment);
        int pending = carSequentialList.indexOf(construction);
        if (pending != -1) {
            return null;
        }
        return carSequentialList.add(construction) ? construction : null;
    }

    @Override
    public Car queryCar(String ID) {
        return carSequentialList.get(
                carSequentialList.indexOf(new Car(ID, null, null, null))
        );
    }

    @Override
    public Car removeCar(AuthSession authSession, String ID) {
        int pending = carSequentialList.indexOf(new Car(ID, null, null, null));
        if (pending == -1){
            return null;
        }
        if (!carSequentialList.get(pending).belongTo(authSession)){
            return null;
        }
        return carSequentialList.remove(pending);
    }

    @Override
    public Car modifyCar(AuthSession authSession, String ID, String model, String comment) {
        Car construction = new Car(ID, authSession.getUuid(), model, comment);
        int pending = carSequentialList.indexOf(construction);
        return carSequentialList.update(pending, construction) ? construction : null;
    }

    @Override
    public SequentialList<Car> listCar(AuthSession authSession) {
        SequentialList<Car> pending = new SequentialList<>(null);
        for (int i = 0; i < carSequentialList.size(); i++){
            Car carPending = carSequentialList.get(i);
            if (carPending.belongTo(authSession)){
                pending.add(carPending);
            }
        }
        return pending;
    }

    @Override
    public String showProfile(AuthSession authSession) {
        return authSession.toString();
    }

    @Override
    public AuthSession modifyProfile(AuthSession authSession, String username, String password) {
        int pending = authSessionSequentialList.indexOf(authSession);
        AuthSession construction = authSessionSequentialList.get(pending);
        construction.setUsername(username.equals("") ? construction.getUsername() : username);
        construction.setPassword(password.equals("") ? construction.getPassword() : password);
        return authSessionSequentialList.update(pending, construction) ? construction : null;
    }

    @Override
    public AuthSession createUser(String authLevel, String loginBeacon, String username, String password) {
        AuthSession construction = new AuthSession(AuthLevel.parse(authLevel.toUpperCase()), loginBeacon, username, password);
        int pending = authSessionSequentialList.indexOf(construction);
        if (pending != -1){
            return null;
        }
        return authSessionSequentialList.add(construction) ? construction : null;
    }

    @Override
    public AuthSession revokeUser(String loginBeacon) {
        int pending = authSessionSequentialList.indexOf(new AuthSession(null, loginBeacon, null, null));
        return authSessionSequentialList.remove(pending);
    }

    @Override
    public AuthSession modifyUser(String authLevel, String loginBeacon, String username, String password) {
        AuthSession construction = new AuthSession(AuthLevel.parse(authLevel), loginBeacon, username, password);
        int pending = authSessionSequentialList.indexOf(construction);
        return authSessionSequentialList.update(pending, construction) ? construction : null;
    }

    @Override
    public SequentialList<AuthSession> listUser() {
        return authSessionSequentialList;
    }

    @Override
    public Car removeCar(String ID) {
        int pending = carSequentialList.indexOf(new Car(ID, null, null, null));
        return carSequentialList.remove(pending);
    }

    @Override
    public Car modifyCar(String ID, String owner, String model, String comment) {
        Car construction = new Car(ID, UUID.nameUUIDFromBytes(owner.getBytes()), model, comment);
        int pending = carSequentialList.indexOf(construction);
        return carSequentialList.update(pending, construction) ? construction : null;
    }

    @Override
    public SequentialList<Car> listCar() {
        return carSequentialList;
    }

}
