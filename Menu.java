import java.util.Scanner;

public class Menu {
    private Flights flights;
   // private Scanner input;


    public Menu() {
        flights = new Flights();
        //this.input = new Scanner(System.in);
        this.startMenu();

    }

    public void startMenu() {
        Scanner input = new Scanner(System.in);
        flights.signUp("Admin", "Admin");
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("    WELCOME TO AIRLINE RESERVATION SYSTEM    ");
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::");
        while (true) {
            System.out.println(" ................MENU OPTION................ ");
            System.out.println("    <1> sign in");
            System.out.println("    <2> sign up");
            checkResultStartMenu(input.nextLine());
        }
    }

    public void checkResultStartMenu(String choice) {
        Scanner input = new Scanner(System.in);
        if (choice.equals("1")) {
            System.out.println("Please enter your username and password");
            String userName = input.next();
            String password = input.next();
            User user = flights.signIn(userName, password);
            if (user == null) {
                System.err.println(" invalid account");
                return;
            }
            if (user.getUsername().equals("Admin")) {
                showAdminMenu();
                return;

            }
            showPassengerMenu(user);


        } else if (choice.equals("2")) {
            System.out.println("please creat username and password");
            String userName = input.next();
            String password = input.next();
            User user = flights.signUp(userName, password);
            if (user == null) {
                System.out.println("This username is already used! Please try again");
                return;
            }
            showPassengerMenu(user);


        } else {
            System.err.println("invalid number ");
        }

    }

    public void showPassengerMenu(User user) {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("::::::::::::::::::::::::::::::");
            System.out.println("    PASSENGER MENU OPTIONS    ");
            System.out.println("::::::::::::::::::::::::::::::");
            System.out.println(" ............................ ");
            System.out.println("   <1> Change password ");
            System.out.println("   <2> Search flight tickets ");
            System.out.println("   <3> Booking ticket ");
            System.out.println("   <4> Ticket cancellation ");
            System.out.println("   <5> Booked tickets ");
            System.out.println("   <6> Add charge ");
            System.out.println("   <0> sign out ");
            boolean signOut = checkResultPassengerMenu(input.nextLine(), user);
            if (signOut) {
                break;
            }
        }
    }

    public boolean checkResultPassengerMenu(String choice, User user) {
        Scanner input = new Scanner(System.in);
        if (choice.equals("1")) {
            System.out.println("please enter your new password");                                     //<1>
            String newPassword = input.next();
            user.setPassword(newPassword);
            System.out.println("Your password has been changed. Enter any char to skip: ");
            input.nextLine();
//**********************************************************************************************************************
        } else if (choice.equals("2")) {                                                              //<2>
            showChoice2();
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("3")) {
            System.out.println("please enter the Flight-ID to booking ticket");
            Flight ticket = flights.findTicket(input.nextLine());
            if (ticket == null) {
                System.err.println("There is no flight with the entered flight-ID");
                noSkip();
                return false;
            }
            if (ticket.getSeats() == 0) {
                System.err.println("This flight is full");                                            //<3>
                noSkip();
                return false;
            }
            if (user.getCharge() < ticket.getPrice()) {
                System.err.println("Your charge amount is not enough to buy a ticket");
                noSkip();
                return false;
            }
            ticket.setSeats(ticket.getSeats() - 1);
            String ticketId = user.addFlight(ticket);
            System.out.printf("Your flight has been successfully booked\nyour ticket ID is: %s",ticketId);
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("4")) {
            System.out.println("please enter your tickerId to cancellation");
            String flightId = user.removeTicket(input.nextLine());
            if (flightId == null) {
                System.err.println("You do not have any tickets with the ticket ID entered");
                noSkip();
                return false;                                                                         //<4>
            }
            Flight flights1 = flights.findTicket(flightId);
            user.setCharge(user.getCharge() + flights1.getPrice());
            flights1.setSeats(flights1.getSeats() + 1);
            System.out.println("Your ticket has been successfully cancelled");
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("5")) {
            user.showTicket();                                                                         //<5>
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("6")) {
            System.out.println("Please enter the amount");                                            //<6>
            int money = input.nextInt();
            user.setCharge(user.getCharge() + money);
            System.out.printf("Now your charge is: %d",user.getCharge());
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("0")) {                                                              //<0>
            return true;
//**********************************************************************************************************************
        } else {
            System.out.println("Invalid choice! please enter a number from 0 to 6");
            noSkip();
        }
//**********************************************************************************************************************
        return false;
    }

    public void showChoice2() {
        Scanner input = new Scanner(System.in);
        System.out.print("flight id: ");
        String flightId = input.nextLine();
        System.out.print("origin: ");
        String origin = input.nextLine();
        System.out.print("destination: ");
        String destination = input.nextLine();
        System.out.print("date: ");
        String date = input.nextLine();
        System.out.print("time: ");
        String time = input.nextLine();
        System.out.print("price(example: 300000 to 800000): ");
        String price = input.nextLine();
        if (price.equals("")) {
            int price1 = -1;
            int price2 = -1;
            flights.filterFlight(flightId, origin, destination, date, time, price1, price2);
        } else {
            String[] priceArray = input.nextLine().split(" to ");
            int price1 = Integer.parseInt(priceArray[0]);
            int price2 = Integer.parseInt(priceArray[1]);
            flights.filterFlight(flightId, origin, destination, date, time, price1, price2);
        }


    }

    public void showAdminMenu() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("::::::::::::::::::::::::::::::");
            System.out.println("      ADMIN MENU OPTIONS      ");
            System.out.println("::::::::::::::::::::::::::::::");
            System.out.println(" ............................ ");
            System.out.println("   <1> Add ");
            System.out.println("   <2> Update ");
            System.out.println("   <3> Remove ");
            System.out.println("   <4> Flight schedules ");
            System.out.println("   <0> sign out ");
            boolean signOut = checkResultAdminMenu(input.nextLine());
            if (signOut){
                break;
            }
        }
    }

    public boolean checkResultAdminMenu(String choice) {
        Scanner input = new Scanner(System.in);
        if (choice.equals("1")) {                       //<1>
            addF();
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("2") || choice.equals("3")) {
            System.out.println("Please enter flightId");
            Flight flight = flights.findTicket(input.next());
            if (flight == null) {
                System.err.println("There is no flight with the entered flight ID");
                noSkip();
                return false;
            }
            if (flights.findUserTicket(flight)) {
                System.err.println("This flight was purchased by a user. You cannot update or remove it");
                noSkip();
                return false;
            }
            if (choice.equals("2")) {                               //<2>
                updateFlight(flight);
                System.out.println("This flight was successfully updated. Enter any char to skip: ");
                input.nextLine();
       //**********************************************************************
            } else {
                flights.removeFlight(flight);                       //<3>
                System.out.println("This flight was successfully removed. Enter any char to skip: ");
                input.nextLine();
            }
//**********************************************************************************************************************
        } else if (choice.equals("4")) {                            //<4>
            flights.showSchedules();
            noSkip();
//**********************************************************************************************************************
        } else if (choice.equals("0")) {                            //<0>
            return true;
//**********************************************************************************************************************
        }
        else {
            System.out.println("Invalid choice! please enter a number from 0 to 4");
            noSkip();
//**********************************************************************************************************************
        }
        return false;


    }

    public void addF() {
        Scanner input = new Scanner(System.in);
        String flightId, origin, destination, date, time;
        int price, seats;
        System.out.println("To add a flight, please enter the requested items");
        System.out.print("Flight ID: ");
        flightId = flights.checkFlightId(input.nextLine());
        System.out.print("Origin: ");
        origin = input.nextLine();
        System.out.print("Destination: ");
        destination = input.nextLine();
        System.out.print("Date: ");
        date = checkDate(input.next());
        System.out.print("Time: ");
        time = checkTime(input.next());
        System.out.print("price: ");
        price = input.nextInt();
        System.out.print("Seats: ");
        seats = checkSeats(input.nextInt());
        flights.addFlight(flightId, origin, destination, date, time, price, seats);
    }

    public void updateFlight(Flight flight) {
        Scanner input = new Scanner(System.in);
        String flightId, origin, destination, date, time, price, seats;
        flights.showTicket("FlightId", "Origin", "Destination", "Date", "Time", "Price", "seats");
        flights.showTicket(flight);
        System.out.print("New flight ID: ");
        flightId = input.nextLine();
        if (!flightId.equals("")) {
            flight.setFlightID(flights.checkFlightId(flightId));
        }
        System.out.print("New origin: ");
        origin = input.nextLine();
        if (!origin.equals("")) {
            flight.setOrigin(origin);
        }
        System.out.print("New destination: ");
        destination = input.nextLine();
        if (!destination.equals("")) {
            flight.setDestination(destination);
        }
        System.out.print("New date: ");
        date = input.nextLine();
        if (!date.equals("")) {
            date = checkDate(date);
            flight.setDate(date);
        }
        System.out.print("New time: ");
        time = input.nextLine();
        if (!time.equals("")) {
            time = checkTime(time);
            flight.setTime(time);
        }
        System.out.print("New price: ");
        price = input.nextLine();
        if (!price.equals("")) {
            flight.setPrice(Integer.parseInt(price));
        }
        System.out.print("New Seats: ");
        seats = input.nextLine();
        if (!seats.equals("")) {
            flight.setSeats(checkSeats(Integer.parseInt(seats)));
        }


    }

    public String checkDate(String date) {
        Scanner input = new Scanner(System.in);
        while (true) {
            String[] array = date.split("-");
            //||array[1].length()!=2||array[2].length()!=2
            int month = Integer.parseInt(array[1]);
            int day = Integer.parseInt(array[2]);
            if (month > 12 || day > 31 || month > 6 && day > 30 || month == 12 && day > 29) {
                System.err.println("Invalid date ! Please try again");
                date = input.next();
                continue;
            }
            return date;
        }
    }

    public String checkTime(String time) {
        Scanner input = new Scanner(System.in);
        while (true) {
            String[] array = time.split(":");
            int hour = Integer.parseInt(array[0]);
            int min = Integer.parseInt(array[1]);
            if (hour > 23 || min > 60 || hour < 0 || min < 0) {
                System.err.println("Invalid time ! Please try again");
                time = input.next();
                continue;
            }
            return time;
        }
    }

    public int checkSeats(int seats) {
        Scanner input = new Scanner(System.in);
        while (true) {
            if (seats < 0) {
                System.err.println("The number of seats entered is incorrect ! please try again");
                seats = input.nextInt();
                continue;
            }
            return seats;
        }


    }
    public void noSkip(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter any char to skip: ");
        scanner.nextLine();
    }



}
