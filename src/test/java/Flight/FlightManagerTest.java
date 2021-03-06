package Flight;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class FlightManagerTest {
    private FlightManager fm;

    @Before
    public void setUp() {
        //initialize departure time
        ArrayList<String> dTime = new ArrayList<>(5);
        dTime.add("2021");
        dTime.add("12");
        dTime.add("1");
        dTime.add("8");
        dTime.add("50");
        dTime.add("0");
        //initialize arrival time
        ArrayList<String> aTime = new ArrayList<>(5);
        aTime.add("2021");
        aTime.add("12");
        aTime.add("1");
        aTime.add("10");
        aTime.add("45");
        aTime.add("0");
        //initialize seatNumberArray
        ArrayList<String> seatNumArray = new ArrayList<>();
        seatNumArray.add("1A");
        seatNumArray.add("1B");
        seatNumArray.add("2A");
        seatNumArray.add("2B");
        seatNumArray.add("3A");
        seatNumArray.add("3B");
        seatNumArray.add("4A");
        seatNumArray.add("4B");
        seatNumArray.add("5A");
        seatNumArray.add("5B");

        fm = new FlightManager();
        fm.AddFlight("CZ311", "Toronto", "Vancouver", dTime, aTime, "Small",
                3600, "Gate1");
    }
    @Test(timeout = 50)
    public void TestGetDCity(){
        assertEquals(fm.getFlightByNum("CZ311").getOriginCity(), "Toronto");

    }
    @Test(timeout = 50)
    public void TestGetACity(){
        assertEquals(fm.getFlightByNum("CZ311").getDestinationCity(), "Vancouver");

    }
    @Test(timeout = 500)
    public void TestAddFlight(){
        ArrayList<String> dTime = new ArrayList<>(Arrays.asList("2021","12","1","8","50","0"));
        ArrayList<String> aTime = new ArrayList<>(Arrays.asList("2021","12","1","10","45","0"));
        fm.AddFlight("CZ312","Toronto", "Vancouver", dTime, aTime, "Small",
                3600, "Gate2");
        fm.AddFlight("CZ313","Toronto", "Vancouver", dTime, aTime, "Medium",
                3600, "Gate2");
        fm.AddFlight("CZ314","Toronto", "Vancouver", dTime, aTime, "Large",
                3600, "Gate2");
        assertEquals(fm.getFlightByNum("CZ312").getTotalSeats(),10);
        assertEquals(fm.getFlightByNum("CZ313").getTotalSeats(),20);
        assertEquals(fm.getFlightByNum("CZ314").getTotalSeats(),30);

    }

    @Test(timeout = 500)
    public void TestGetFlightByRoute() {
        String des = "Vancouver";
        String dep = "Toronto";
        ArrayList<String> flights = new ArrayList<>();
        flights.add("CZ311");
        assertEquals(fm.getFlightByRoute(dep, des), flights);
    }

    @Test(timeout = 500)
    public void TestReserveSeat() {
        String seat_num = "1A";
        String flight_num = "CZ311";
        String s_return = "You have successfully selected this seat " + seat_num + " of flight " + flight_num;
        String f_return = "This seat has been reserved or does not exist, please select another seat.";
        assertEquals(s_return, fm.reserveSeat("CZ311", "1A"));
        assertEquals(f_return, fm.reserveSeat("CZ311", "1G"));
    }

    @Test(timeout = 500)
    public void TestCancelSeat() {
        String seatNum = "1A";
        String flightNum = "CZ311";
        String s_return = "You have successfully canceled seat" + seatNum +" of flight " + flightNum;
        String f_return = "This seat has not been reserved or does not exist.";
        fm.reserveSeat("CZ311", "1A");
        assertEquals(s_return, fm.cancelSeat("CZ311", "1A"));
        assertEquals(f_return, fm.cancelSeat("CZ311", "1G"));

    }

    @Test(timeout = 500)
    public void TestPrintAvailableSeat() {
        ArrayList<ArrayList<ArrayList<String>>> seatNumArray = new ArrayList<>();
        String[][] temp = {{"1A", "First"}, {"1B", "First"}, {"2A", "First"}, {"2B", "First"}, {"3A", "First"},
                {"3B", "First"}, {"4A", "First"}, {"4B", "First"}, {"5A", "First"}, {"5B", "First"}};
        for (String[] item: temp) {
            seatNumArray.add(new ArrayList(List.of(item)));
        }

        assertEquals(seatNumArray, fm.printAvailableSeat("CZ311"));
    }

    @Test(timeout = 500)
    public void TestPrintAvailableSeatByClass() {
        ArrayList<ArrayList<ArrayList<String>>> seatNumArray = new ArrayList<>();
        String[][] temp = {{"1A", "First"}, {"1B", "First"}, {"2A", "First"}, {"2B", "First"}, {"3A", "First"},
                {"3B", "First"}, {"4A", "First"}, {"4B", "First"}, {"5A", "First"}, {"5B", "First"}};
        for (String[] item: temp) {
            seatNumArray.add(new ArrayList(List.of(item)));
        }
        assertEquals(seatNumArray, fm.printAvailableSeatByClass("CZ311", "First"));
    }



}
