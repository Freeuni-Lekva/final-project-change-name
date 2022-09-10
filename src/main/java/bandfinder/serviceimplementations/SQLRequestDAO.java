package bandfinder.serviceimplementations;

import bandfinder.dao.RequestDAO;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Request;
import bandfinder.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLRequestDAO implements RequestDAO {
    private final Connection connection;

    public SQLRequestDAO() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_CLASS_NAME);
        connection = DriverManager.getConnection(Constants.DB_URL);
    }

    @Override
    public Request create(Request model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO requests (userId, bandId, isProcessed) VALUES (?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1,model.getUserId());
            statement.setInt(2,model.getBandId());
            statement.setBoolean(3,model.isProcessed());
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
    public Request update(Request model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE requests SET userId = ?, " +
                            "bandId = ?, " +
                            "isProcessed = ?, WHERE id = ?;");
            statement.setInt(4, model.getId());
            statement.setInt(1, model.getUserId());
            statement.setInt(2, model.getBandId());
            statement.setBoolean(3, model.isProcessed());
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
                    "DELETE FROM requests WHERE id = ?;");

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Request getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM requests WHERE id = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return null;
            }
            Request resultRequest = new Request( id,
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getBoolean(4));
            statement.close();
            return resultRequest;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Request> getAll(){
        List<Request> requestList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM requests;"
            );
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                int bandId = resultSet.getInt(3);
                boolean isProcessed = resultSet.getBoolean(4);
                Request nextRequest = new Request(id,userId,bandId,isProcessed);
                requestList.add(nextRequest);
            }
            return requestList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getId(int userId, int bandId){
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM requests WHERE userId = ? AND bandId = ?;");
            statement.setInt(1, userId);
            statement.setInt(2, bandId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return Constants.NO_ID;
            }else{
                int res = resultSet.getInt(1);
                statement.close();
                return res;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
