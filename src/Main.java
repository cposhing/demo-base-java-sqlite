import java.sql.*;

public class Main {
    public static void main(String[] args) {

        loadDriverClass();

        // demoInsert();
        // demoSelect();
        // demoDelete();
        demoUpdate();
    }


    private static void demoUpdate() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:demo.db")) {
            // get a customer by id ;
            String selectByIdSql = "select id, name from customers where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdSql);
            preparedStatement.setInt(1, 1);

            // check the exists customer
            ResultSet resultSet = preparedStatement.executeQuery();
            int fetchSize = resultSet.getFetchSize();
            System.out.println("fetchSize = " + fetchSize);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("customer id = 1 is [" + id + "-" + name + "]");
            }

            // update
            String updateSql = "update customers set name = ? where id = ?";
            PreparedStatement updatePreparedStatement = connection.prepareStatement(updateSql);
            updatePreparedStatement.setString(1, "poshing1");
            updatePreparedStatement.setInt(2, 1);
            int i = updatePreparedStatement.executeUpdate();
            System.out.println("updated data count is " + i);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    private static void demoDelete() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:demo.db")) {
            String deleteSql = "delete from customers where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, 5);
            boolean execute = preparedStatement.execute();
            System.out.println("execute = " + execute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unused")
    private static void demoInsert() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:demo.db")) {
            var insertSql = """
                    insert into customers (id, name) values (?, ?);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "goodman");
            boolean execute = preparedStatement.execute();
            System.out.println("execute = " + execute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    private static void demoSelect() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:demo.db")) {
            Statement statement = connection.createStatement();
            String s = """
                    SELECT * FROM CUSTOMERS ORDER BY ID DESC;
                    """;
            ResultSet resultSet = statement.executeQuery(s);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(STR. "id=\{ id }, name=\{ name }" );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriverClass() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("jdbc driver load success !");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}