package bandfinder.serviceimplementations;

import bandfinder.dao.UserDAO;
import bandfinder.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final Connection connection;

    public SQLUserDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/bandfinder?user=root&password=rootroot");
    }

    @Override
    public User create(User model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (email, password_hash, first_name, surname, stage_name) VALUES (?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getEmail());
            statement.setString(2, model.getPasswordHash());
            statement.setString(3, model.getFirstName());
            statement.setString(4, model.getSurname());
            statement.setString(5, model.getStageName());
            statement.executeUpdate();

            ResultSet idResultSet = statement.getGeneratedKeys();
            idResultSet.next();
            int newId = idResultSet.getInt(1);
            model.setId(newId);

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET email = ?, " +
                            "password_hash = ?, " +
                            "first_name = ?, " +
                            "surname = ?, " +
                            "stage_name = ? WHERE id = ?;");
            statement.setInt(6, model.getId());
            statement.setString(1, model.getEmail());
            statement.setString(2, model.getPasswordHash());
            statement.setString(3, model.getFirstName());
            statement.setString(4, model.getSurname());
            statement.setString(5, model.getStageName());
            statement.executeUpdate();
            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?;");

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return null;
            }
            User resultUser = new User( id,
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));
            statement.close();
            return resultUser;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll(){
        List<User> userList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users;"
            );
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String passwordHash = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String surname = resultSet.getString(5);
                String stageName = resultSet.getString(6);
                User nextUser = new User(id, email, passwordHash, firstName, surname, stageName);
                userList.add(nextUser);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?;");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return null;
            }
            User resultUser = new User( resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));
            statement.close();
            return resultUser;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> searchUsers(String query) {
        query = query.trim().toLowerCase();
        try {
            PreparedStatement statement;
            if(query.length() <= 2) {
                statement = connection.prepareStatement(
                        "SELECT * FROM users WHERE ? IN(LOWER(first_name), LOWER(surname), LOWER(stage_name));"
                );
            } else {
                statement = connection.prepareStatement(
                        "SELECT * FROM users WHERE MATCH(first_name, surname, stage_name) AGAINST(? IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION);"
                );
            }
            statement.setString(1, query);
            ResultSet resultSet = statement.executeQuery();
            List<User> resultList = new ArrayList<>();
            while(resultSet.next()) {
                User currentUser = new User( resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                resultList.add(currentUser);
            }
            statement.close();
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}