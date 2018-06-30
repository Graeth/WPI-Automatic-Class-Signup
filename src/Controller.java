import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Console;
import java.net.URI;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Garrett on 12/6/2017.
 */
public class Controller {
    static Robot robot;
    public static final int loadInterval = 1000;
    public static String username = "f", password = "f";
    public static final Point backPoint = new Point(20,44), regPoint = new Point(47,592),
    studentSevPoint = new Point(185,356);
    static int tabsReq = 0;

    private static class setup extends TimerTask {

        public void run() {


                thread1.stop();
            try {
                if(Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI("https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_WWWLogin"));
                }
                Thread.sleep(15000);
                type(username);
                robot.keyRelease(KeyEvent.VK_SHIFT);

                tabToLast(1);
                type(password);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                Thread.sleep(50);
                robot.keyPress(KeyEvent.VK_ENTER);

                robot.keyRelease(KeyEvent.VK_ENTER);
                tabTo(11);
//        robot.keyPress(KeyEvent.VK_ENTER);
                tabTo(12);
                tabTo(22);
                tabTo(12);
                tabToLast(10+2*tabsReq);

//                robot.keyPress(KeyEvent.VK_ENTER);
            } catch (Exception e) {

            }
        }
    }
    private static class end extends TimerTask
    {

        public void run()
        {
            cons.printf("" + System.currentTimeMillis() + " " + date.getTime());
            try {
                System.out.println("did something");
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            } catch (Exception e){

        }

//            try {
//                robot = new Robot();
//            } catch (AWTException e) {
//                e.printStackTrace();
//            }
//            try {
//                        Thread.sleep(1000);
//                    } catch(Exception e) {}
//        System.out.println(MouseInfo.getPointerInfo().getLocation());
//        while(true) {
//            robot.mouseMove((int) regPoint.getX(), (int) regPoint.getY());
//            robot.mousePress(InputEvent.BUTTON1_MASK);
//            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//            try {
//                Thread.sleep(loadInterval);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            int x = MouseInfo.getPointerInfo().getLocation().x;
//            if (x < 19) System.exit(0);
//            robot.mouseMove((int) backPoint.getX(), (int) backPoint.getY());
//            robot.mousePress(InputEvent.BUTTON1_MASK);
//            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//            try {
//                Thread.sleep(loadInterval);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            x = MouseInfo.getPointerInfo().getLocation().x;
//            if (x < 19) System.exit(0);
        }

    }
    static Console cons;
    static Date date1, date;
    public static Thread thread1 = new Thread () {
        private volatile boolean exit = false;
        public void exit() {
            exit =  true;
        }
        public void run () {
            try {
                while(!exit) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    cons.printf("\nRegistration Time: " + secondTask + "\nLogin Scheduled for: " + firstTask + "\n");
                    String formatStr = "yyyy-MM-dd HH:mm:ss";

                    SimpleDateFormat sdf1 = new SimpleDateFormat(formatStr);


                    long finalTime = date1.getTime() - System.currentTimeMillis();
                    String time = formatTime(finalTime);
                    cons.printf(time + "\n\n");
                    cons.printf("Cancel Task by Entering n\n");
                    Thread.sleep(1000);
                }
            } catch(Exception e){}
        }
    };
    public static void click() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    static String secondTask ="", firstTask = "";
    public static void main(String[] args) throws Exception, java.net.URISyntaxException, java.awt.AWTException, java.lang.InterruptedException, java.text.ParseException {
        robot = new Robot();
        cons = System.console();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        username = new String(cons.readLine("Enter your username:"));
        password = new String(cons.readPassword("Enter your password for use in login (this will print and be deleted a few seconds after):\n"));

        tabsReq = Integer.parseInt(new String(cons.readLine("How many courses are displayed in your registration cart? (My hack needs this):\n ")));
        cons.printf(""+tabsReq);
        secondTask = new String(cons.readLine("What time is registration? yyyy-MM-dd HH:mm:ss\n\n"+
        "so for September 6, 2017 at 7:00 AM it would be 2017-09-06 07:00:00 :\n "));
        cons.printf("\nClose all browser tabs before this time, make sure your computer is on and is secure- this will print your password in the login prompt\n" +
                "unless it is intercepted by a roommate that wants your password for whatever reason.\n\nAdditionally, make sure the system clock is " +
                "set to your local time zone.");



        int newMinute = Integer.parseInt(secondTask.substring(14,16))-15;
        int newHour = Integer.parseInt(secondTask.substring(11,13));
        if(newMinute < 0) {
            newMinute+=60;
            newHour -=1;
        }
        boolean minOneDig = newMinute < 10;
        boolean hourOneDig = newHour < 10;
        String minAdd = ""+newMinute, hourAdd = ""+newHour;
        if(minOneDig)
            minAdd = "0"+newMinute;
        if(hourOneDig)
            hourAdd="0"+newHour;


        firstTask = secondTask.substring(0,11) + hourAdd+":"+minAdd+secondTask.substring(16,secondTask.length());
        cons.printf("\nRegistration Time: " + secondTask + "\nLogin Scheduled for: "+firstTask);

        date = dateFormatter.parse(secondTask);
        date.setTime(date.getTime()+200);
        Timer timer = new Timer();
        timer.schedule(new end(), date);
        date1 = date;
        date1.setTime(date.getTime()-15*1000*60);
        timer.schedule(new setup(), date1);
        Scanner n;
        n = new Scanner(System.in);
        Thread.sleep(5000);
        thread1.start();
        while(true) {

            while(n.hasNextLine()) {
                if (n.next().toLowerCase().equals("n")) {
                    cons.printf("Process Ended");
                    System.exit(0);
                }
            }

        }

    }
    public static void tabTo(int n) throws Exception{
        Thread.sleep(15000);
        for(int i = 0; i < n; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(50);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    public static void tabToLast(int n) throws Exception{
        Thread.sleep(15000);
        for(int i = 0; i < n; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(50);
        }
    }
    public static void keyType(int keyCode) {
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
    }

    public static void keyType(int keyCode, int keyCodeModifier) {
        robot.keyPress(keyCodeModifier);
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
        robot.keyRelease(keyCodeModifier);
    }
    public static String formatTime(long millis) {

        String output = "00:00";
        try {
            long seconds = millis / 1000;
            long minutes = seconds / 60;
            long hours = seconds / 3600;
            long days = seconds / (3600 * 24);

            seconds = seconds % 60;
            minutes = minutes % 60;
            hours = hours % 24;
            days = days % 30;

            String sec = String.valueOf(seconds);
            String min = String.valueOf(minutes);
            String hur = String.valueOf(hours);
            String day = String.valueOf(days);

            if (seconds < 10)
                sec = "0" + seconds;
            if (minutes < 10)
                min = "0" + minutes;
            if (hours < 10)
                hur = "0" + hours;
            if (days < 10)
                day = "0" + days;

            output = day + "D " + hur + "H " + min + "M " + sec + "S";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
static boolean[] uppercase;

    public static void type(String text) {
        uppercase = new boolean[text.length()];
        for(int i = 0; i < text.length(); i++) {
            uppercase[i] = text.charAt(i) == (text.toUpperCase().charAt(i)) && text.charAt(i) != text.toLowerCase().charAt(i);
        }
        String textUpper = text.toUpperCase();

        for (int i=0; i<text.length(); ++i) {
            if(uppercase[i])
                robot.keyPress(KeyEvent.VK_SHIFT);
            else
                robot.keyRelease(KeyEvent.VK_SHIFT);
            typeChar(textUpper.charAt(i));
        }
    }

    private static void typeChar(char c) {
        boolean shift = true;
        int keyCode;

        switch (c) {
            case '~':
                keyCode = (int)'`';
                break;
            case '!':
                keyCode = (int)'1';
                break;
            case '@':
                keyCode = (int)'2';
                break;
            case '#':
                keyCode = (int)'3';
                break;
            case '$':
                keyCode = (int)'4';
                break;
            case '%':
                keyCode = (int)'5';
                break;
            case '^':
                keyCode = (int)'6';
                break;
            case '&':
                keyCode = (int)'7';
                break;
            case '*':
                keyCode = (int)'8';
                break;
            case '(':
                keyCode = (int)'9';
                break;
            case ')':
                keyCode = (int)'0';
                break;
            case ':':
                keyCode = (int)';';
                break;
            case '_':
                keyCode = (int)'-';
                break;
            case '+':
                keyCode = (int)'=';
                break;
            case '|':
                keyCode = (int)'\\';
                break;
            case '?':
                keyCode = (int)'/';
                break;
            case '{':
                keyCode = (int)'[';
                break;
            case '}':
                keyCode = (int)']';
                break;
            case '<':
                keyCode = (int)',';
                break;
            case '>':
                keyCode = (int)'.';
                break;
            default:
                keyCode = (int)c;
                shift = false;
        }
        if (shift)
            keyType(keyCode, KeyEvent.VK_SHIFT);
        else
            keyType(keyCode);
    }

    private static int charToKeyCode(char c) {
        switch (c) {
            case ':':
                return ';';
        }
        return (int)c;
    }
}


