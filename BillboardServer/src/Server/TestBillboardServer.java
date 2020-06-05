package Server;

import Database.Permissions;
import Database.Schedule;
import Database.User;
import Exceptions.IncorrectPasswordException;
import Exceptions.IncorrectUserException;
import Exceptions.MissingPermissionException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestBillboardServer {

    @Test
    public void TestLoginWithCorrectPassword() throws NoSuchAlgorithmException, IncorrectPasswordException, IncorrectUserException {
        User dbUser = new User();
        dbUser.setUsername("testUsername");
        dbUser.setPassword(Hash.getHash("testPassword" + dbUser.getPasswordSalt()));

        //password given when by the Person
        String inputPassword = "testPassword";
        Token token = BillboardServer.logIn(dbUser,inputPassword);

        assert(token.getToken().length() == 20);
    }

    @Test
    public void TestLoginWithWrongPassword() throws NoSuchAlgorithmException, IncorrectPasswordException, IncorrectUserException {
        User dbUser = new User();
        dbUser.setUsername("testUsername");
        dbUser.setPassword(Hash.getHash("testPassword" + dbUser.getPasswordSalt()));

        //password given when by the Person
        String inputPassword = "wrong password";

        assertThrows(IncorrectPasswordException.class, ()->{
            Token token = BillboardServer.logIn(dbUser,inputPassword);
        });
    }

    @Test
    public void TestGetCurrentBillboardWithGoodSchedule(){
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        Schedule earlyBillboard = new Schedule(
                "testUser",
                "earlyBillboard",
                LocalDateTime.now().minusHours(1).toString(),
                LocalDateTime.now().minusMinutes(30).toString(),
                "THURSDAY",
                "week");
        Schedule currentBillboard = new Schedule(
                "testUser",
                "currentBillboard",
                LocalDateTime.now().minusMinutes(10).toString(),
                LocalDateTime.now().plusMinutes(30).toString(),
                "none",
                "day");
        scheduleList.add(earlyBillboard);
        scheduleList.add(currentBillboard);

        String currentBillboardName = BillboardServer.getCurrentBillboard(scheduleList);
        System.out.println(currentBillboardName);
        assert(currentBillboardName.equals("currentBillboard"));
    }

    @Test
    public void TestGetCurrentBillboardWithNoCurrentBillboard(){
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        Schedule earlyBillboard = new Schedule(
                "testUser",
                "earlyBillboard",
                LocalDateTime.now().minusHours(1).toString(),
                LocalDateTime.now().minusMinutes(30).toString(),
                "THURSDAY",
                "week");
        Schedule currentBillboard = new Schedule(
                "testUser",
                "currentBillboard",
                LocalDateTime.now().plusMinutes(10).toString(),
                LocalDateTime.now().plusMinutes(30).toString(),
                "none",
                "day");
        scheduleList.add(earlyBillboard);
        scheduleList.add(currentBillboard);

        String currentBillboardName = BillboardServer.getCurrentBillboard(scheduleList);
        assert(currentBillboardName == null);
    }

    @Test
    public void TestGetCurrentBillboardWithNoSchedule(){
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();

        String currentBillboardName = BillboardServer.getCurrentBillboard(scheduleList);
        assert(currentBillboardName == null);
    }

    @Test
    public void TestSetUpUser() throws NoSuchAlgorithmException, MissingPermissionException {
        Permissions permissions = new Permissions(
                "testUser",
                "true",
                "true",
                "true",
                "true");
        String username = "testUser";
        String password = "password";

        User user = BillboardServer.setUpUser(permissions, username, password);

        assert (user.getUsername() == "testUser");
        assert (user.getPassword() != password);
    }

    @Test
    public void TestSetUpUserMissingAllPermissions() throws NoSuchAlgorithmException, MissingPermissionException {
        Permissions permissions = new Permissions();

        String username = "testUser";
        String password = "password";

        assertThrows(MissingPermissionException.class, ()->{
            User user = BillboardServer.setUpUser(permissions, username, password);
        });
    }

    @Test
    public void TestSetUpUserMissingSomePermissions() throws NoSuchAlgorithmException, MissingPermissionException {
        Permissions permissions = new Permissions();
        permissions.setCreateBillboard("true");
        permissions.setEditSchedule("true");
        String username = "testUser";
        String password = "password";

        assertThrows(MissingPermissionException.class, ()->{
            User user = BillboardServer.setUpUser(permissions, username, password);
        });
    }
}
