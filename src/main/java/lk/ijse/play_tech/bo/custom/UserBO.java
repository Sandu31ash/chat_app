package lk.ijse.play_tech.bo.custom;

import lk.ijse.play_tech.bo.SuperBO;
import lk.ijse.play_tech.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserBO extends SuperBO {

    boolean valid(String userName) throws SQLException, ClassNotFoundException;

    boolean save(String userName) throws SQLException, ClassNotFoundException;

    ResultSet getImage(String userName) throws SQLException, ClassNotFoundException;

    //String getId(String userName) throws SQLException, ClassNotFoundException;
}