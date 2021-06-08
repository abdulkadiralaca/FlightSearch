import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {
    private String flightId;
    public Airport departure;
    private Airport arrival;
    private Date departureDate;             //attributes
    private String duration;
    private int price;
    private Date arrivalDate;      //arrival date is the 'departure date + duration'. I mean it is landing date

    public Flight() {}

    public Flight(String flightId, Airport departure, Airport arrival, Date departureDate, String duration, int price) {
        this.flightId = flightId;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;            //constructor
        this.duration = duration;
        this.price = price;
    }

    public String getFlightId() {
        return flightId;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airport getArrival() {
        return arrival;
    }        //getters

    public Date getDepartureDate() {
        return departureDate;
    }

    public String getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public Date getArrivalDate() throws ParseException {        //calculating landing date of flight and returning it
        Date date = new Date(getDepartureDate().getTime());
        String clock = getDuration();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date durationDate = format.parse(clock);
        date.setHours(date.getHours()+ durationDate.getHours());
        date.setMinutes(date.getMinutes()+durationDate.getMinutes());
        return date;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId='" + flightId + '\'' +
                ", departure='" + departure.getName() + '\'' +
                ", arrival='" + arrival.getName() + '\'' +
                ", departureDate='" + departureDate.toString() + '\'' +
                ", duration='" + duration + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
