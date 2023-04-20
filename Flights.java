import java.util.Scanner;

public class Flights {
    private int count1, count2;
    private User[] users;
    private Flight[] flights1;

    public Flights() {
        this.count1 = 0;
        this.count2 = 0;
        this.users = new User[100];
        this.flights1 = new Flight[100];
    }

    public User signUp(String userName, String password) {
        if (findUser(userName) != null) {
            return null;
        }
        users[count1] = new User(userName, password);
        count1++;
        return users[count1 - 1];

    }

    public User findUser(String userName) {
        for (User user : users) {
            if (user != null && user.getUsername().equals(userName)) {
                return user;
            }

        }
        return null;

    }


    public User signIn(String userName, String password) {
        User user = findUser(userName);
        if ((user) != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public void addFlight(String flightID, String origin, String destination, String date, String time, int price, int seats) {
        flights1[count2] = new Flight(flightID, origin, destination, date, time, price, seats);
        count2++;

    }

    public void filterFlight(String flightID, String origin, String destination, String date, String time, int price1, int price2) {
        showTicket("FlightId", "Origin", "Destination", "Date", "Time", "Price", "seats");
        for (int i = 0; i < flights1.length; i++) {
            if (flights1[i] != null) {
                boolean bFlightId = flightID.equals("") || flightID.equals(flights1[i].getFlightID());
                boolean bOrigin = origin.equals("") || origin.equals(flights1[i].getOrigin());
                boolean bDestination = destination.equals("") || destination.equals(flights1[i].getDestination());
                boolean bDate = date.equals("") || date.equals(flights1[i].getDate());
                boolean bTime = time.equals("") || time.equals(flights1[i].getTime());
                boolean bPrice = price1 == -1 || (price1 < flights1[i].getPrice() && price2 > flights1[i].getPrice());
                if (bFlightId && bOrigin && bDestination && bDate && bTime && bPrice) {
                    showTicket(flights1[i]);
                }
            }
        }


    }

    public void showTicket(Flight flight) {
        System.out.printf("|%-10s|%-10s|%-13s|%-13s|%-6s|%-10s|%-5s|\n", flight.getFlightID(), flight.getOrigin(),
                flight.getDestination(), flight.getDate(), flight.getTime(), flight.getPrice(), flight.getSeats());
        System.out.println("...........................................................................");

    }

    public void showTicket(String column1, String column2, String column3, String column4, String column5,
                           String column6, String column7) {
        System.out.printf("|%-10s|%-10s|%-13s|%-13s|%-6s|%-10s|%-5s|\n", column1, column2, column3, column4, column5,
                column6, column7);
        System.out.println("...........................................................................");
    }

    public Flight findTicket(String flightId) {
        for (int i = 0; i < flights1.length; i++) {
            if (flights1[i] != null && flightId.equals(flights1[i].getFlightID())) {
                return flights1[i];

            }

        }
        return null;

    }

    public boolean findUserTicket(Flight flight) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].findFlight(flight) != null) {
                return true;
            }
        }
        return false;
    }

    public void removeFlight(Flight flight) {
        for (int i = 0; i < flights1.length; i++) {
            if (flights1[i] != null && flights1[i]==flight) {
                flights1[i] = null;
            }
        }
    }
    public void showSchedules(){
        System.out.printf("|%-10s|%-10s|%-13s|%-13s|%-6s|%-10s|%-5s|\n","FlightId", "Origin", "Destination", "Date",
                "Time", "Price", "seats");
        System.out.println("...........................................................................");
        for (int i = 0; i < flights1.length; i++) {
            if (flights1[i]!=null){
                System.out.printf("|%-10s|%-10s|%-13s|%-13s|%-6s|%-10s|%-5s|\n", flights1[i].getFlightID(),
                        flights1[i].getOrigin(), flights1[i].getDestination(),flights1[i].getDate(),flights1[i].getTime(),
                        flights1[i].getPrice(),flights1[i].getSeats());
                System.out.println("...........................................................................");
            }

        }
    }
    public String checkFlightId(String flightId){
        Scanner input = new Scanner(System.in);
        while (true) {
            if (findTicket(flightId) != null) {
                System.err.println("This flight ID already exists. please enter another flight ID");
                flightId = input.next();
                continue;

            }
            break;
        }
        return flightId;
    }


    //    public User findUser1(String password){
//        for (User user : users) {
//            if (user != null && user.getPassword().equals(password)) {
//                return user;
//            }
//
//        }
//        return null;
//
//    }

}
