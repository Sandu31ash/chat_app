package lk.ijse.play_tech.dao;

import lk.ijse.play_tech.dao.custom.impl.*;

public class DAOFactory {


    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            default:
                return null;

        }
    }

    public enum DAOTypes {
        //CUSTOMER, ITEM, ORDER, ORDER_DETAILS, QUERY_DAO

        EMPLOYEE , INGREDIENT , INVENTORY , CATEGORY, MENU, ORDER, ORDER_DETAILS, RECIPE, RECIPE_ING_DETAILS, SUPPLIER, USER, QUERY_DAO
    }

}
