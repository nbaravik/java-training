package bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

import java.sql.*;

public class WeatherDB {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/MyWeatherDB";
    private static final String USERNAME = "postgres";

    private static final String SQL_SELECT = "SELECT info FROM weather WHERE city=?";
    private static final String SQL_DELETE = "DELETE FROM weather WHERE city=?";
    private static final String SQL_INSERT = "INSERT INTO weather (city, info) VALUES (?, cast(? as json))";

    private static final Logger LOGGER = Logger.getLogger(WeatherDB.class);

    private String password;

    WeatherDB(String pass) {
        password = pass;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("No Postgresql Driver found. " + e);
        }
    }

    protected Weather getWeatherByCityName(String cityName) {
        Weather weather = null;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, password)) {
            if (!conn.isClosed()) {
                LOGGER.info("Connection to the database is established.");
            }
            PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT);
            pstmt.setString(1, cityName);
            ResultSet rs = pstmt.executeQuery();
            LOGGER.debug("Statement: " + pstmt);
            if (rs.next()) {
                String infoJson = rs.getString("info");
                LOGGER.debug("Query is executed successfully. Result: " + infoJson);
                weather = WeatherObjectMapper.getInstance().getMapper().readValue(infoJson, Weather.class);
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            LOGGER.error("Problem with the database. " + e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing exception. " + e);
        }
        return weather;
    }

    protected void deleteExpiredWeatherInfo(String cityName) {

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, password)) {
            if (!conn.isClosed()) {
                LOGGER.info("Connection to the database is established.");
            }
            PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE);
            pstmt.setString(1, cityName);
            LOGGER.debug("Statement: " + pstmt);
            if (pstmt.executeUpdate() > 0) {
                LOGGER.debug("Expired information about weather in " + cityName + " was deleted from database.");
            } else {
                LOGGER.debug("Database update failed. Information about weather in " + cityName + " wasn't deleted.");
            }
            pstmt.close();

        } catch (SQLException e) {
            LOGGER.error("Problem with the database. " + e);
        }
    }

    protected void insertWeatherInfo(Weather weather) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, password)) {
            if (!conn.isClosed()) {
                LOGGER.info("Connection to the database is established.");
            }
            PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT);
            pstmt.setString(1, weather.getLocation().getCity());
            pstmt.setString(2, WeatherObjectMapper.getInstance().getMapper().writeValueAsString(weather));

            LOGGER.debug("Statement: " + pstmt);
            if (pstmt.executeUpdate() > 0) {
                LOGGER.debug("New information about weather in " + weather.getLocation().getCity() + " was added to database.");
            } else {
                LOGGER.debug("Database update failed. Information about weather in " + weather.getLocation().getCity() + " wasn't added.");
            }
            pstmt.close();

        } catch (SQLException e) {
            LOGGER.error("Problem with the database. " + e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing exception. " + e);
        }
    }
}
