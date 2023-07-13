package lk.ijse.play_tech.bo.custom.impl;

import lk.ijse.play_tech.bo.custom.UserBO;
import lk.ijse.play_tech.dao.DAOFactory;
import lk.ijse.play_tech.dao.custom.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean valid(String userName) throws SQLException, ClassNotFoundException {
        return userDAO.valid(userName);
    }

    @Override
    public boolean save(String userName) throws SQLException, ClassNotFoundException {
        return userDAO.save(userName);
    }

    @Override
    public ResultSet getImage(String userName) throws SQLException, ClassNotFoundException {
        return userDAO.getImage(userName);
    }

    /*@Override
    public String getId(String userName) throws SQLException, ClassNotFoundException {
        return userDAO.getId(userName);
    }*/
}