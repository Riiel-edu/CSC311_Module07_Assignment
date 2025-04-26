package model;

public class Animal {

    private int id;
    private String name;
    private Animal_Class animal_class;
    private String species;
    private String date_of_birth;
    private String exhibit;

    public Animal(int id, String name, String animal_class, String species, String date_of_birth, String exhibit) {
        this.id = id;
        this.name = name;

        switch(animal_class) {
            case "Mammal" -> this.animal_class = Animal_Class.Mammal;
            case "Bird" -> this.animal_class = Animal_Class.Bird;
            case "Fish" -> this.animal_class = Animal_Class.Fish;
            case "Reptile" -> this.animal_class = Animal_Class.Reptile;
            case "Amphibian" -> this.animal_class = Animal_Class.Amphibian;
            case "Invertebrate" -> this.animal_class = Animal_Class.Invertebrate;
            default -> this.animal_class = Animal_Class.Unknown;
        }

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

        switch (animal_class) {
            case Mammal -> { return "Mammal"; }
            case Bird -> { return "Bird";}
            case Fish -> { return "Fish";}
            case Reptile -> { return "Reptile";}
            case Amphibian -> { return "Amphibian";}
            case Invertebrate -> { return "Invertebrate";}
            default -> { return "Unknown"; }
        }
    }

    public void setAnimalClass(String animal_class) {

        switch(animal_class) {
            case "Mammal" -> this.animal_class = Animal_Class.Mammal;
            case "Bird" -> this.animal_class = Animal_Class.Bird;
            case "Fish" -> this.animal_class = Animal_Class.Fish;
            case "Reptile" -> this.animal_class = Animal_Class.Reptile;
            case "Amphibian" -> this.animal_class = Animal_Class.Amphibian;
            case "Invertebrate" -> this.animal_class = Animal_Class.Invertebrate;
        }
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

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", animal_class=" + animal_class +
                ", species='" + species + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", exhibit='" + exhibit + '\'' +
                '}';
    }
}
