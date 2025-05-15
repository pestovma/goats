
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class GoatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        GoatDatabase db = new GoatDatabase();

        while (true) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Добавить козу");
            System.out.println("2. Показать всех коз");
            System.out.println("3. Найти козу по имени");
            System.out.println("4. Удалить козу по имени");
            System.out.println("5. Обновить данные козы");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            switch (scanner.nextLine()) {
                case "1" -> {
                    Goat goat = readGoatFromUser(scanner);
                    db.insertGoat(goat);
                }
                case "2" -> {
                    // Показать всех коз с полными данными
                    System.out.println("\n--- Список всех коз ---");
                    List<Goat> goats = db.getAllGoats();
                    for (int i = 0; i < goats.size(); i++) {
                        Goat g = goats.get(i);
                        System.out.println("Номер: " + (i + 1));  // Порядковый номер
                        System.out.println("Имя: " + g.getName());
                        System.out.println("Пол: " + g.getGender());
                        System.out.println("Порода: " + g.getBreed());
                        System.out.println("Дата рождения: " + g.getBirthDate());
                        System.out.println("Отец: " + g.getFatherName());
                        System.out.println("Мать: " + g.getMotherName());
                        System.out.println("Окрас: " + g.getColor());  // Выводим окрас
                        System.out.println("-----------------------------");
                    }
                }
                case "3" -> {
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    db.findGoatsByName(name).forEach(g -> {
                        System.out.println("Имя: " + g.getName());
                        System.out.println("Пол: " + g.getGender());
                        System.out.println("Порода: " + g.getBreed());
                        System.out.println("Дата рождения: " + g.getBirthDate());
                        System.out.println("Отец: " + g.getFatherName());
                        System.out.println("Мать: " + g.getMotherName());
                        System.out.println("Окрас: " + g.getColor());
                        System.out.println("-----------------------------");
                    });
                }
                case "4" -> {
                    System.out.print("Введите имя козы для удаления: ");
                    String name = scanner.nextLine();
                    if (db.deleteGoatByName(name)) {
                        System.out.println("Коза успешно удалена.");
                    } else {
                        System.out.println("Не удалось найти козу с таким именем.");
                    }
                }
                case "5" -> {
                    System.out.print("Введите имя козы для обновления: ");
                    String name = scanner.nextLine();
                    Goat goat = readGoatFromUser(scanner);
                    if (db.updateGoatByName(name, goat)) {
                        System.out.println("Данные козы обновлены.");
                    } else {
                        System.out.println("Не удалось обновить данные.");
                    }
                }
                case "0" -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private static Goat readGoatFromUser(Scanner scanner) {
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Пол: ");
        String gender = scanner.nextLine();
        System.out.print("Порода: ");
        String breed = scanner.nextLine();
        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.print("Дата рождения (ГГГГ-ММ-ДД): ");
            try {
                birthDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат.");
            }
        }
        System.out.print("Отец: ");
        String father = scanner.nextLine();
        System.out.print("Мать: ");
        String mother = scanner.nextLine();
        System.out.print("Окрас: ");
        String color = scanner.nextLine();

        return new Goat(name, gender, breed, birthDate, father, mother, color);
    }
}
