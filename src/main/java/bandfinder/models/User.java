package bandfinder.models;

public class User {
    private int id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String surname;
    private String stageName;
    public User(int id, String email, String passwordHash, String firstName, String surname, String stageName) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.surname = surname;
        this.stageName = stageName;
    }

    public User(String email, String passwordHash, String firstName, String surname, String stageName) {
        this(-1, email, passwordHash, firstName, surname, stageName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getFullName() {
        if(stageName.isBlank()) {
            return firstName + ' ' + surname;
        }
        return firstName + " \"" + stageName + "\" " + surname;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }
}
