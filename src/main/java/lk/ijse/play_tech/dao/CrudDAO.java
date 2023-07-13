package lk.ijse.play_tech.dao;

import java.sql.SQLException;

public interface CrudDAO <T, user> extends SuperDAO{

    boolean save(String userName) throws SQLException, ClassNotFoundException;

}
