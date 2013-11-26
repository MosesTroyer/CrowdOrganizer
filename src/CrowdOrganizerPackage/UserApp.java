/*******************************************
 * Class: UserApp
 * Description: This is the main controller of the program.
 *      It opens and reads the events file, and makes calls to CustomerPrQ
 *      based on the events in the file.
 * Project: CrowdOrganizer
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package CrowdOrganizerPackage;
import java.io.*;

public class UserApp {

    public static void main(String[] args) throws IOException {
        DeleteFile("log.txt"); //starts clean log
        
        Logger log = new Logger();
        log.writeln("**Crowd Organizer App starting");
        
        CustomerPrQ prq = new CustomerPrQ();
        FileReader inFile = new FileReader("events.txt");
        BufferedReader events = new BufferedReader(inFile);
        
        String line;
        String[] command;
        
        while((line = events.readLine()) != null){ //loop until there is no more events
            command = line.split(",");
            
            switch (command[0].charAt(0)){
                case 'O': //open store
                    log.writeln("STORE OPENING");
                    if(prq.setupPrQ()) log.writeln("**Heap is full");
                    log.writeln("**initial heap built containing " + prq.getN() + " nodes\n");
                    break;
                    
                case 'C': //close store
                    log.writeln("STORE CLOSING---------------------------");
                    log.writeln("**heap currently has " 
                            + prq.getN() + " nodes remaining");
                    prq.emptyOutPrQ();
                    break;
                
                case 'N':
                    prq.InsertCustomerInPrQ(command);
                    break;
                    
                case 'S':
                    prq.removeCustomerFromPrQ();
                    break;
                    
                case '/': //comment
                default: break;
            } //end switch
            
            
        } //end while loop
        
        log.writeln("**Crowd Organizer App ending");
        prq.close();
        log.close();
    } //end main
    
    //**************************** PRIVATE METHOD ******************************
    
    //from Dr. Kaminski, World Data Project 1
    private static boolean DeleteFile(String fileName) {
        boolean delete = false;
        File f = new File(fileName);
        if (f.exists()) {
            delete = f.delete();
        }
        return delete;
    } 
    
} //end UserApp class
