package lk.ijse.play_tech.dao.custom;

import lk.ijse.play_tech.dao.CrudDAO;
import lk.ijse.play_tech.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User, String> {

    boolean valid(String userName) throws SQLException, ClassNotFoundException;

    ResultSet getImage(String userName) throws SQLException, ClassNotFoundException;

    //String getId(String userName) throws SQLException, ClassNotFoundException;
}
