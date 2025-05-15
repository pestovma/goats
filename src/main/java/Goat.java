import java.time.LocalDate;

public class Goat {

    private String name;
    private String gender;
    private String breed;
    private LocalDate birthDate;
    private String fatherName;
    private String motherName;
    private String color;  // Новое поле для окраса

    public Goat(String name, String gender, String breed, LocalDate birthDate, String fatherName, String motherName, String color) {
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.birthDate = birthDate;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.color = color;  // Инициализация окраса
    }

    // Геттеры
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getBreed() { return breed; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getFatherName() { return fatherName; }
    public String getMotherName() { return motherName; }
    public String getColor() { return color; }  // Геттер для окраса
}