public class Airport {
    private String name;
    private String city;                                    //attributes
                                                            //this class storing the city name and airport name of each airport
    public Airport(){}

    public Airport(String name){
        this.name = name;
    }       //constructors

    public Airport(String city, String name) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }
                                                            //getters
    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }                                                       //setters

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
