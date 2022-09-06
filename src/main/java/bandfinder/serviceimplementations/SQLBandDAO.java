package bandfinder.serviceimplementations;

import bandfinder.dao.BandDAO;
import bandfinder.models.Band;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLBandDAO implements BandDAO {

    private final Connection connection;

    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bandfinder?user=root&password=R00tR**t";

    public SQLBandDAO() throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        connection = DriverManager.getConnection(URL);
    }

    private static final String IS_USER_IN_BAND_QUERY = "SELECT * FROM band_users " +
                                                        "WHERE band_id = ? AND user_id = ?;";

    @Override
    public boolean isUserInBand(int userId, int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(IS_USER_IN_BAND_QUERY);
            statement.setInt(1, bandId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                statement.close();
                return true;
            }

            statement.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ADD_MEMBER_TO_BAND_QUERY = "INSERT INTO band_users (band_id, user_id) " +
                                                           "VALUES (?, ?);";

    @Override
    public boolean addMemberToBand(int memberId, int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_MEMBER_TO_BAND_QUERY);
            statement.setInt(1, bandId);
            statement.setInt(2, memberId);
            int rowsAffected = statement.executeUpdate();
            statement.close();

            int numBandMembers = countBandMembers(bandId);
            updateNumberOfBandMembers(numBandMembers + 1, bandId);

            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String REMOVE_MEMBER_FROM_BAND_QUERY = "DELETE FROM band_users " +
                                                                "WHERE band_id = ? AND user_id = ?;";

    @Override
    public boolean removeMemberFromBand(int memberId, int bandId) {
        try {
            PreparedStatement removeStatement = connection.prepareStatement(REMOVE_MEMBER_FROM_BAND_QUERY);
            removeStatement.setInt(1, bandId);
            removeStatement.setInt(2, memberId);
            int rowsAffected = removeStatement.executeUpdate();
            removeStatement.close();

            int numBandMembers = countBandMembers(bandId);
            updateNumberOfBandMembers(numBandMembers - 1, bandId);

            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_NUMBER_OF_BAND_MEMBERS = "UPDATE bands SET num_members = ? WHERE id = ?;";

    private boolean updateNumberOfBandMembers(int n, int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_NUMBER_OF_BAND_MEMBERS);
            statement.setInt(1, n);
            statement.setInt(2, bandId);
            int numRowsAffected = statement.executeUpdate();

            statement.close();

            return numRowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_NUMBER_OF_BAND_MEMBERS = "SELECT num_members FROM bands WHERE id = ?;";

    @Override
    public int countBandMembers(int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_NUMBER_OF_BAND_MEMBERS);
            statement.setInt(1, bandId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int numMembers = rs.getInt(1);

            statement.close();

            return numMembers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BAND_MEMBER_IDS_QUERY = "SELECT user_id FROM band_users WHERE band_id = ?;";

    @Override
    public List<Integer> getBandMemberIDs(int bandId) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BAND_MEMBER_IDS_QUERY);
            statement.setInt(1, bandId);
            ResultSet rs = statement.executeQuery();

            List<Integer> memberIds = new ArrayList<>();
            while(rs.next()) memberIds.add(rs.getInt(1));

            statement.close();
            return memberIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BAND_IDS_FOR_USER_QUERY = "SELECT band_id FROM band_users WHERE user_id = ?;";

    @Override
    public List<Integer> getAllBandIDsForUser(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BAND_IDS_FOR_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            List<Integer> bandIds = new ArrayList<>();
            while(rs.next()) bandIds.add(rs.getInt(1));

            statement.close();
            return bandIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CREATE_QUERY = "INSERT INTO bands (name) VALUES (?);";

    @Override
    public Band create(Band model) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, model.getName());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            model.setId(id);

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_QUERY = "UPDATE bands SET name = ? WHERE id = ?;";

    @Override
    public Band update(Band model) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(2, model.getId());
            statement.setString(1, model.getName());
            statement.executeUpdate();

            statement.close();
            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_QUERY = "DELETE FROM bands WHERE id = ?;";

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            statement.close();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String GET_BY_ID_QUERY = "SELECT name FROM bands WHERE id = ?;";
    @Override
    public Band getById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                Band model = new Band(id, rs.getString(1));
                statement.close();
                return model;
            }

            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String GET_ALL_QUERY = "SELECT * FROM bands;";

    @Override
    public List<Band> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_QUERY);

            List<Band> bands = new ArrayList<>();
            while(rs.next()) {
                Band band = new Band(rs.getInt(1), rs.getString(2));
                bands.add(band);
            }

            statement.close();
            return bands;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Band> searchBands(String query) {
        query = query.trim().toLowerCase();
        try {
            PreparedStatement statement;
            if(query.length() <= 2) {
                statement = connection.prepareStatement(
                        "SELECT * FROM bands WHERE LOWER(name) = ? OR LOWER(tags_string) LIKE CONCAT('% ', ?, ',')"
                );
                statement.setString(1, query);
                statement.setString(2, query);
            } else {
                statement = connection.prepareStatement(
                        "SELECT * FROM bands WHERE MATCH(name, tags_string) AGAINST(? IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION);"
                );
                statement.setString(1, query);
            }
            ResultSet resultSet = statement.executeQuery();
            List<Band> resultList = new ArrayList<>();
            while(resultSet.next()) {
                Band currentBand = new Band(resultSet.getInt(1), resultSet.getString(2));
                resultList.add(currentBand);
            }
            statement.close();
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
