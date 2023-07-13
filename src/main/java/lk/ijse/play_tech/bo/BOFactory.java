package lk.ijse.play_tech.bo;

import lk.ijse.play_tech.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public lk.ijse.play_tech.bo.SuperBO getBO(BOTypes boTypes){
        switch (boTypes) {
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes{
        USER
    }
}
