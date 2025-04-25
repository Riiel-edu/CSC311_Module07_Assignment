package model;

public class Animal {
    private int id;
    private String name;
    private String animal_class;
    private String species;
    private String date_of_birth;
    private String exhibit;

    public Animal(int id, String name, String animal_class, String species, String date_of_birth, String exhibit) {
        this.id = id;
        this.name = name;
        this.animal_class = animal_class;
        this.species = species;
        this.date_of_birth = date_of_birth;
        this.exhibit = exhibit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimalClass() {
        return animal_class;
    }

    public void setAnimalClass(String animal_class) {
        this.animal_class = animal_class;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getExhibit() {
        return exhibit;
    }

    public void setExhibit(String exhibit) {
        this.exhibit = exhibit;
    }
}
