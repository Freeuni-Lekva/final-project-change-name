package bandfinder.serviceimplementations;

import bandfinder.dao.InvitationDAO;
import bandfinder.infrastructure.Constants;
import bandfinder.models.Invitation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLInvitationDAO implements InvitationDAO {
    private final Connection connection;

    public SQLInvitationDAO() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_CLASS_NAME);
        connection = DriverManager.getConnection(Constants.DB_URL);
    }

    @Override
    public Invitation create(Invitation model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO invitations (userId, bandId, isProcessed) VALUES (?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
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
    public Invitation update(Invitation model) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE invitations SET userId = ?, " +
                            "bandId = ?, " +
                            "isProcessed = ? WHERE id = ?;");
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
                    "DELETE FROM invitations WHERE id = ?;");

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Invitation getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM invitations WHERE id = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement.close();
                return null;
            }
            Invitation resultInvitation = new Invitation( id,
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getBoolean(4));
            statement.close();
            return resultInvitation;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Invitation> getAll(){
        List<Invitation> invitationList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM invitations;"
            );
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                int bandId = resultSet.getInt(3);
                boolean isProcessed = resultSet.getBoolean(4);
                Invitation nextInvitation = new Invitation(id,userId,bandId,isProcessed);
                invitationList.add(nextInvitation);
            }
            return invitationList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getId(int userId, int bandId){
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM invitations WHERE userId = ? AND bandId = ?;");
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
