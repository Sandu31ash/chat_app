package lk.ijse.play_tech.dao.custom.impl;

import lk.ijse.play_tech.dao.SQLUtil;
import lk.ijse.play_tech.dao.custom.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean valid(String userName) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE username = ? ", userName);

        if(rst.next()) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean save(String userName) throws SQLException, ClassNotFoundException {
        boolean isSaved = SQLUtil.execute("INSERT INTO user (username) VALUES (?)", userName);
        return isSaved;
    }
    @Override
    public ResultSet getImage(String userName) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT pic FROM pic_user WHERE username = ?", userName);

        if(rst.next()){
            return rst;
        }
        return null;
    }

    /*@Override
    public String getId(String userName) throws SQLException, ClassNotFoundException {
        String id = SQLUtil.execute("SELECT id FROM user WHERE username = ?", userName);
        return id;
    }*/


}
