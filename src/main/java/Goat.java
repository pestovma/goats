import java.time.LocalDate;

public class Goat {

   //private int id;
    private String name;
    private String gender;
    private String breed;
    private LocalDate birthDate;
    private String fatherName;
    private String motherName;
    private String color;

    public Goat(String name, String gender, String breed, LocalDate birthDate, String fatherName, String motherName, String color) {
       // this.id=id;
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.birthDate = birthDate;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.color = color;
    }

    // Геттеры

   // public int getId() {return id;}
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getBreed() { return breed; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getFatherName() { return fatherName; }
    public String getMotherName() { return motherName; }
    public String getColor() { return color; }
}