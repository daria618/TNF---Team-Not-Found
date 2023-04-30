package tnf.back.db.entityes;

public enum Role {
    USER,
    ADMIN;

    public String getName(){
        return toString();
    }
}