/*******************************************
 * Class: Logger
 * Description: This writes to the log.txt file, Solves the problems 
 * with trying to open buffered readers in each class. 
 * Project: CrowdOrganizer
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package CrowdOrganizerPackage;

import java.io.*;

public class Logger {

    private static BufferedWriter log;
    
    //constructor, opens file
    public static void UserInterface() throws IOException {
        log = new BufferedWriter(new FileWriter("log.txt", true));
    } //end logger
    
    //************************PUBLIC METHODS************************//
    
    //writes the the log file
    public void write(String s) throws IOException {
        try{
            log.write(s);
            System.out.print(s);
        } catch (NullPointerException|IOException e){ 
            //The catch reopens the file if it was closed somewhere else
            log = new BufferedWriter(new FileWriter("log.txt", true));
            log.write(s);
            System.out.print(s);
        } //end catch
    } //end write
    
    //overloaded method that EITHER writes to just log or log + console
    //false for just log, true for both
    public void write(String s, boolean b) throws IOException{
        try{
            log.write(s);
            if(b)System.out.print(s);
        } catch (NullPointerException|IOException e){ 
            //The catch reopens the file if it was closed somewhere else
            log = new BufferedWriter(new FileWriter("log.txt", true));
            log.write(s);
            if(b)System.out.print(s);
        } //end catch
    } //end write
    
    //this just adds the "\n" to write, so I don't have to write it every time
    public void writeln(String s) throws IOException {
        try{
            log.write(s + "\n");
            System.out.print(s + "\n");
        } catch (NullPointerException|IOException e){ 
            //The catch reopens the file if it was closed somewhere else
            log = new BufferedWriter(new FileWriter("log.txt", true));
            log.write(s + "\n");
            System.out.print(s + "\n");
        } //end catch     
    } //end writeln
    
    //just writes a new line
    public void writeln() throws IOException {
        try{
            log.write("\n");
            System.out.print("\n");
        } catch (NullPointerException|IOException e){            
            //The catch reopens the file if it was closed somewhere else
            log = new BufferedWriter(new FileWriter("log.txt", true));
            log.write("\n");
            System.out.print("\n");
        } //end catch
    } //end writeln
    
    //overloaded method that EITHER writes to just log or log + console
    //false for just log, true for both
    public void writeln(String s, boolean b) throws IOException{
        try{
            log.write(s + "\n");
            if(b) System.out.print(s + "\n");
        } catch (NullPointerException|IOException e){ 
            //The catch reopens the file if it was closed somewhere else
            log = new BufferedWriter(new FileWriter("log.txt", true));
            log.write(s + "\n");
            if(b) System.out.print(s + "\n");       
        } //end catch
    } //end writeln
    
    //be sure to call this when done using the logger
    public void close() throws IOException {
        try{
            log.close();
        } catch (NullPointerException e) {}
    } //end close
    
} //end logger class