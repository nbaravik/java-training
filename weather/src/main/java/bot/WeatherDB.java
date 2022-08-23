package bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

import java.sql.*;

public class WeatherDB {

    private static final String URL = "jdbc:postgresql://localhost:5432/MyWeatherDB";
    private static final String USERNAME = "postgres";

    private static final String SQL_SELECT = "SELECT info FROM weather WHERE city=?";
    private static final String SQL_DELETE = "DELETE FROM weather WHERE city=?";
    private static final String SQL_INSERT = "INSERT INTO weather (city, info) VALUES (?, cast(? as json))";

    private static final Logger LOGGER = Logger.getLogger(WeatherDB.class);

    private static Connection connection;

    WeatherDB(String pass) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, pass);
            LOGGER.info("Connection to the database is established.");
        } catch (SQLException e) {
            LOGGER.fatal("Connection to the database is failed. " + e);
            throw new RuntimeException("Connection to the database is failed. " + e);
        }
    }

    protected Weather getWeatherByCityName(String cityName) {
        Weather weather = null;

        try (PreparedStatement stmt = selectPreparedStatement(cityName);
                ResultSet rs = stmt.executeQuery()) {
            LOGGER.debug("Statement: " + stmt);
            if (rs.next()) {
                String infoJson = rs.getString("info");
                LOGGER.debug("Query is executed successfully. Result: " + infoJson);
                weather = WeatherObjectMapper.getInstance().getMapper().readValue(infoJson, Weather.class);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem with the database. " + e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing exception. " + e);
        }
        return weather;
    }

    private PreparedStatement selectPreparedStatement(String cityName) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SQL_SELECT);
        pstmt.setString(1, cityName);
        return pstmt;
    }

    protected void deleteExpiredWeatherInfo(String cityName) {

        try (PreparedStatement stmt = deletePreparedStatement(cityName)) {
            LOGGER.debug("Statement: " + stmt);
            if (stmt.executeUpdate() > 0) {
                LOGGER.debug("Expired information about weather in " + cityName + " was deleted from database.");
            } else {
                LOGGER.debug("Database update failed. Information about weather in " + cityName + " wasn't deleted.");
            }
        } catch (SQLException e) {
            LOGGER.error("Problem with the database. " + e);
        }
    }

    private PreparedStatement deletePreparedStatement(String cityName) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE);
        pstmt.setString(1, cityName);
        return pstmt;
    }

    protected void insertWeatherInfo(Weather weather) {
        try (PreparedStatement stmt = insertPreparedStatement(weather)) {
            LOGGER.debug("Statement: " + stmt);
            if (stmt.executeUpdate() > 0) {
                LOGGER.debug("New information about weather in " + weather.getLocation().getCity() + " was added to database.");
            } else {
                LOGGER.debug("Database update failed. Information about weather in " + weather.getLocation().getCity() + " wasn't added.");
            }
        } catch (SQLException e) {
            LOGGER.error("Problem with the database. " + e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing exception. " + e);
        }
    }

    private PreparedStatement insertPreparedStatement(Weather weather) throws SQLException, JsonProcessingException {
        PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT);
        pstmt.setString(1, weather.getLocation().getCity());
        pstmt.setString(2, WeatherObjectMapper.getInstance().getMapper().writeValueAsString(weather));
        return pstmt;
    }
}
