import java.util.*;
import java.lang.Math.*;
import java.text.*;

public class artillery {

    private static Random rand = new Random(); // We use this to generate random numbers

    public static String input(String s) {
        /* input() simplifies the process of asking for input
           and getting input at the same time.
           Takes string s, prints it.
           Gets users input, returns it. */

        System.out.print(s);
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }

    public static double roundTwo(double d) {
        /* Returns a given double rounded down to two decimal places */

        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.valueOf(formatter.format(d));
    }
    
    public static double getDistance(double angle, int velocity) {
        /* This function calculates the distance a shot should go
            given an angle in radians and a velocity. */

        return velocity * velocity * Math.sin(2*angle) / 9.8;
    }

    public static void printCustomMessage(int wins, int rounds) {
        /* Patronizes the user if they win 90% or more of the games.
            Congratulates them if they win between 75-90% of the games.
            Insults them if they win less than 75% of the games. */
        
        // Ratios are converted to double to compare against decimals places
        if ((double)wins/rounds > 0.9) {
            System.out.println("Truly, you are a champion of war!");
        }
        else if ((double)wins/rounds > 0.75) {
            System.out.println("Great shooting!");
        }
        else {
            System.out.println("You are a terrible shot!");
        }
    }

    public static int getBlastRadius() {
        /* Asks for the type of weapon the player wants
            and returns the blast radius of that weapon. */
        
        System.out.println("What kind of weapon shall be used today?");
        System.out.println("[1] Rock");
        System.out.println("[2] Hand Grenade");
        System.out.println("[3] Fire-bomb");
        System.out.println("[4] Super Bomb");
        System.out.println("[5] Nuclear missile");
        int selectedWeapon = Integer.parseInt(input("Select Weapon Type Number: "));

        switch (selectedWeapon) {
            case 1: 
                return 1;
            case 2: 
                return 5;
            case 3: 
                return 10;
            case 4: 
                return 20;
            case 5: 
                return 100;
            default:
                System.out.println("Defaulting to Hand Grenade...");
                return 5; // Default to satisfy method's need to return
        }
    }

    public static boolean getCompDifficulty() {
        /* Ask the user what diffculty they want.
            Returns true if hardmode. Returns false if easymode. */

        System.out.println("_____________________________________________");
        System.out.println("Choose Difficulty Level:");
        System.out.println("[1] Easy");
        System.out.println("[2] Hard");
        int selectedDifficutly = Integer.parseInt(input("Select Difficulty Number: "));

        switch (selectedDifficutly) {
            case 1: 
                return false;
            case 2: 
                return true;
            default:
                System.out.println("Defaulting to Easy Difficulty...");
                return false; // Default to satisfy method's need to return
        }
    }

    public static int getCompAngle(boolean hard_mode) {
        /* Selects the computers angle for his shot.
            Random number between 0-90 if on easy.
            Always 57 if on hard mode (this works with 
            forumula for hard mode velocity formula.) */

        if (hard_mode == false) {
            return rand.nextInt(90);
        }
        else {
            return 57;
        }
    }

    public static int getCompVelocity(boolean hard_mode, int distance) {
        /* Selects the computers velocity for his shot.
            Random number between 0-100 if on easy.
            Calculated formula if on hard mode */
        
        if (hard_mode == false) {
            return rand.nextInt(100);
        }
        else {
            return (int)(3.27528 * Math.sqrt(distance) + 0.5);
        }

    }

    public static int playGame(int distance, int blast_radius, boolean hard_mode) {
        /* This runs through a single game of artillery. 
            It returns 1 if you win, therefore adding to your score.
            It reutnrs 0 if you lose, adding nothing to your score. */

        double angle; // angle
        int velocity; // and velcoity of the shot
        double shot_distance; // The distance a shot fired

        // The loop for a single game.
        while (true) {

            /* Start user's turn. */
            angle = Integer.parseInt( input("Enter an angle: ") );
            velocity = Integer.parseInt( input("Enter a velocity: ") );

            angle = Math.toRadians(angle); // getDistance() needs angle in radians
            shot_distance = getDistance(angle, velocity);
            System.out.println("Your shot went " + roundTwo(shot_distance) + " meters!");

            // If your shot landed close enough to the target...
            if ( Math.abs(shot_distance - distance) <= blast_radius) {
                System.out.println("You hit! Great job!");
                return 1; // Add one to your score
            }

            /* Start enemy's turn.
                Basically the same as player's turn except angle
                and velocity are calculated by the computer */
            angle = getCompAngle(hard_mode);
            velocity = getCompVelocity(hard_mode, distance);
            System.out.println("The computer uses an angle of: " + angle);
            System.out.println("The computer uses a velocity of: " + velocity);

            angle = Math.toRadians(angle);
            shot_distance = getDistance(angle, velocity);
            System.out.println("The computer's shot went " + roundTwo(shot_distance) + " meters!");

            if ( Math.abs(shot_distance - distance) <= blast_radius) {
                System.out.println("You got hit! Oh no!");
                return 0; // No score for you!
            }

        }
    }
    
    public static void main(String[] args) {
        boolean cont_playing = true; // Keep playing while this is true
        int distance; // The distance from the player to the enemy
        int blast_radius; // Hit at most this distance away from enemy to hit
        int rounds = 0; // Total games played
        int wins = 0;
        boolean hard_mode; // Sets the AI's difficulty level

        System.out.println("\n___________WELCOME TO ARTILLERY!___________");

        blast_radius = getBlastRadius();

        hard_mode = getCompDifficulty();

        /* This is the main game loop. It will run until the user quits */
        while (cont_playing) {

            // Generate a distance between 100 and 200 meters.
            distance = rand.nextInt(100)+100;
            System.out.println("===========================================");
            System.out.println("For this game, the enemy is " + distance + " meters away.");
            System.out.println("===========================================");

            wins = wins + playGame(distance, blast_radius, hard_mode); // Add to score if you win
            rounds = rounds + 1;

            // 'y' makes the game continue; anything else quits.
            cont_playing = input("Another game? (y/n) ").equals("y");
        }

        /* Tallies what percentage of games you won and congratulates 
            or insults you accordingly */
        System.out.println("################################");
        System.out.println("You won " + 100*wins/rounds + "% of the games.");
        printCustomMessage(wins, rounds);
        System.out.println("################################");
    }
}