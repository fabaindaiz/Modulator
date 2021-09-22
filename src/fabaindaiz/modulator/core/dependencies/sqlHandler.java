package fabaindaiz.modulator.core.dependencies;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;

import java.io.File;
import java.sql.*;

/**
 * Represents a class which can manage sql connection
 */
public class sqlHandler {

    private final Modulator plugin;

    /**
     * @param plugin Modulator main class
     */
    public sqlHandler(Modulator plugin) {
        this.plugin = plugin;

        File file = new File(plugin.getDataFolder(), "db");
        if (!file.exists()) {
            file.mkdir();
        }
        testDatabase();
    }

    /**
     * Gets a module database
     * @param module Module main class
     * @return SQLite connection
     * @throws SQLException
     */
    public Connection getConnection(IModule module) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:plugins/Modulator/db/" + module.getName() + ".db");
    }

    /**
     * Gets the Modulator main database
     * @return SQLite connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:plugins/Modulator/db/modulator.db");
    }

    /**
     * Test Modulator main database creation and connection
     */
    public void testDatabase() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(5);

            statement.executeUpdate("drop table if exists test");
            statement.executeUpdate("create table test (id integer, name string)");
            statement.executeUpdate("insert into test values(1, 'test')");
            ResultSet rs = statement.executeQuery("select * from test");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
