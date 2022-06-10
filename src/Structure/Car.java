package Structure;

import CommandSystem.AuthSession;

import java.util.UUID;

public class Car {
    private String ID;
    private UUID Owner;
    private String Model;
    private String Comment;

    public Car(String ID, UUID owner, String model, String comment) {
        this.ID = ID;
        Owner = owner;
        Model = model;
        Comment = comment;
    }

    public Car(String serialization){
        String[] fields = serialization.split(" ");
        this.ID = fields[0];
        this.Owner = UUID.fromString(fields[1]);
        this.Model = fields[2];
        this.Comment = fields[3];
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public UUID getOwner() {
        return Owner;
    }

    public void setOwner(UUID owner) {
        this.Owner = owner;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                this.ID,
                this.Owner,
                this.Model,
                this.Comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return ID.equals(car.ID);
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    public boolean belongTo(AuthSession authSession){
        return authSession.getUuid().equals(this.Owner);
    }
}
