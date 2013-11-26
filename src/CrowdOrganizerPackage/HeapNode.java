/*******************************************
 * Class: HeapNode
 * Description: Class that holds name and priority value for each person        
 * Project: CrowdOrganizer
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package CrowdOrganizerPackage;

public class HeapNode {
    
    private String name;
    private int priorityValue;
    
    public HeapNode(String n, int p){
        name = n;
        priorityValue = p;
    } //end heapnode constructor
    
    //************************PUBLIC METHODS************************//
    
    public String getName(){
        return name;
    } //end getName
    
    public int getPrV(){
        return priorityValue;
    } //end getPrV
    
} //end heapnode class
