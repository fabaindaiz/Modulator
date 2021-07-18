package fabaindaiz.modulator.core.depend;

import fabaindaiz.modulator.core.resources.Storage;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;
import fabaindaiz.modulator.modules.IModule;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class sqlHandler extends AModule {

    private final Modulator plugin;

    public sqlHandler(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        File file = new File(plugin.getDataFolder(), "db");
        if (!file.exists()) {
            file.mkdir();
        }
        testDatabase();
    }

    @Override
    public void onDisable() {
    }

    public Connection getConnection(IModule module) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:plugins/Modulator/db/" + module.getName() + ".db");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:plugins/Modulator/db/modulator.db");
    }

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
