package sersystem;

public class Subject {
    private int id;
    private String name;
    private String proctor;
    private String schedule;
    private String room;
    private int units;
    private String semester;
    private int year;
 
    public Subject(int id, String name, String proctor, String schedule, String room, int units, String semester, int year) {
        this.id = id;
        this.name = name;
        this.proctor = proctor;
        this.schedule = schedule;
        this.room = room;
        this.units = units;
        this.semester = semester;
        this.year = year;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getProctor() { return proctor; }
    public String getSchedule() { return schedule; }
    public String getRoom() { return room; }
    public int getUnits() { return units; }
    public String getSemester() { return semester; }
    public int getYear() { return year; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProctor(String proctor) { this.proctor = proctor; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public void setRoom(String room) { this.room = room; }
    public void setUnits(int units) { this.units = units; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return "Subject{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", proctor='" + proctor + '\'' +
               ", schedule='" + schedule + '\'' +
               ", room='" + room + '\'' +
               ", units=" + units +
               ", semester='" + semester + '\'' +
               ", year=" + year +
               '}';
    }
}

