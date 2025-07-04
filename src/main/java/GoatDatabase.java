
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;


public class GoatDatabase {
    private static final String URL = "jdbc:sqlite:goats.db";

    public GoatDatabase() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS goats (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    gender TEXT,
                    breed TEXT,
                    birth_date TEXT,
                    father_name TEXT,
                    mother_name TEXT,
                    color TEXT
                );
                """;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void insertGoat(Goat goat) {
        String sql = """
                INSERT INTO goats (name, gender, breed, birth_date, father_name, mother_name, color)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, goat.getName());
            pstmt.setString(2, goat.getGender());
            pstmt.setString(3, goat.getBreed());
            pstmt.setString(4, goat.getBirthDate().toString());
            pstmt.setString(5, goat.getFatherName());
            pstmt.setString(6, goat.getMotherName());
            pstmt.setString(7, goat.getColor());  // Добавляем окрас

            pstmt.executeUpdate();
            System.out.println("Коза успешно добавлена в базу.");
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении козы: " + e.getMessage());
        }
    }

    public List<Goat> getAllGoats() {
        List<Goat> goats = new ArrayList<>();
        String sql = "SELECT * FROM goats";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                goats.add(extractGoat(rs));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех коз: " + e.getMessage());
        }

        return goats;
    }

    public List<Goat> findGoatsByName(String name) {
        List<Goat> goats = new ArrayList<>();
//        String sql = "SELECT * FROM goats WHERE name = ? COLLATE NOCASE";
//
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, name.trim());
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                goats.add(extractGoat(rs));
//            }
        String sql = "SELECT * FROM goats";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String dbName = rs.getString("name");
                if (dbName != null && dbName.equalsIgnoreCase(name.trim())) {
                    goats.add(extractGoat(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при поиске коз по имени: " + e.getMessage());
        }
               return goats;

    }

    public boolean deleteGoatByName(String name) {
        String sql = "DELETE FROM goats WHERE name = ? COLLATE NOCASE";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении козы: " + e.getMessage());
        }

        return false;
    }

    public boolean updateGoatByName(String name, Goat newGoat) {
        List<Goat> goats = getAllGoats();

        // Ищем козу с совпадающим именем без учёта регистра
        for (Goat goat : goats) {
            if (goat.getName() != null && goat.getName().equalsIgnoreCase(name.trim())) {
                String sql = """
                UPDATE goats SET
                    name = ?, gender = ?, breed = ?, birth_date = ?, father_name = ?, mother_name = ?, color = ?
                WHERE name = ?;
            """;

                try (Connection conn = DriverManager.getConnection(URL);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, newGoat.getName());
                    pstmt.setString(2, newGoat.getGender());
                    pstmt.setString(3, newGoat.getBreed());
                    pstmt.setString(4, newGoat.getBirthDate().toString());
                    pstmt.setString(5, newGoat.getFatherName());
                    pstmt.setString(6, newGoat.getMotherName());
                    pstmt.setString(7, newGoat.getColor());
                    pstmt.setString(8, goat.getName());

                    int rows = pstmt.executeUpdate();
                    return rows > 0;

                } catch (SQLException e) {
                    System.err.println("Ошибка при обновлении данных козы: " + e.getMessage());
                    return false;
                }
            }
        }

        System.out.println("Коза с именем '" + name + "' не найдена.");
        return false;
    }
    private Goat extractGoat(ResultSet rs) throws SQLException {
        return new Goat(
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("breed"),
                LocalDate.parse(rs.getString("birth_date")),
                rs.getString("father_name"),
                rs.getString("mother_name"),
                rs.getString("color")
        );
    }
}
