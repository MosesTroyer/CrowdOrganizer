/*******************************************
 * Class: CustomerPrQ
 * Description: This handles everything involving the customer priority queue.
 *      Using a heap structure, it can insert and delete new people to the queue, 
 *      while making sure the smallest valued people are on the top 
 *      of the queue to be served next.
 * Project: CrowdOrganizer
 * Author: Moses Troyer
 * For Dr. Kaminski's 3310 Data and File Structures, WMU
 *******************************************/

package CrowdOrganizerPackage;

import java.io.*;

public class CustomerPrQ {
    
    private int nextInLine = 101;
    private int N = 0, MaxN = 50;
    private HeapNode[] heap = new HeapNode[MaxN];
    private Logger log = new Logger();
    
    public static void CustomerPrQ(){
        
        
        
    } //end constructor
    
    //************************PUBLIC METHODS************************//
    
    //sets up initial priority queue using LineAt6Am.csv
    public boolean setupPrQ() throws IOException {
        FileReader lineAt6 = new FileReader("LineAt6Am.csv");
        BufferedReader inFile = new BufferedReader(lineAt6);
        
        String line;
        
        while((line = inFile.readLine()) != null){
            if(line.charAt(0) != '/'){ //disregards comments
                if(InsertCustomerInPrQ(line)) return true;
            } //end if

        } //end while
        
        inFile.close();
        return false;
    } //end setup
    
    //handles each customer in PrQ in appropriate order
    //The specs say to call delete itself, but it would've been smarter I think
    //to just call removeCustomerFromPRQ
    //the specs also say to remove the node from the heap, but that's already
    //taken care of...
    public void emptyOutPrQ() throws IOException {
        HeapNode node;
        
        while(!heapIsEmpty()){
            node = Delete(0);
            
            log.writeln("SERVE CUSTOMER: " + node.getName() 
                + " (" + node.getPrV() + ")");
        } //end while
        
        log.writeln("**heap now empty");

    } //end emptyOutPrQ
    
    //inserts raw customer line into the queue
    public boolean InsertCustomerInPrQ(String line) throws IOException {
        String customer[] = line.split(",");
        
        if(heapIsFull()) return true;
        
        Insert(customer[0], findPrV(customer));
        return false;
    } //end insertCustomer
    
    //inserts split customer line in queue. Meant to be
    //taken from the events file, so everything is shifted
    //to match the others
    //since it is taken from the events file, this is the ONLY one that
    //displays "new customer"
    public boolean InsertCustomerInPrQ(String[] c) throws IOException {       
        String[] customer = new String[c.length - 1];
        int i;
        HeapNode node;
        
        if(heapIsFull()) return true;
        
        //shifts to match
        for(i = 0;i<customer.length;i++){
            customer[i] = c[i + 1];
        }
        
        node = Insert(customer[0], findPrV(customer));
        
        log.writeln("NEW CUSTOMER: " +
            node.getName() + " (" + node.getPrV() + ")");
                
        return false;
    } //end insertCustomer
    
    public void removeCustomerFromPrQ() throws IOException {
        HeapNode node = Delete(0);
        
        log.writeln("SERVE CUSTOMER: " + node.getName() 
                + " (" + node.getPrV() + ")");
    } //end remove
    
    //gets the number of nodes
    public int getN(){
        return N;
    } //end getN
    
    //closes the log
    public void close() throws IOException {
        log.close();
    } //end cose
    
    //test method to show the entire heap
    public void printHeap(){
        int i;
        System.out.println("Printing Heap--------------");
        for(i = 0;i<N;i++){
            System.out.println(heap[i].getName() + " " + heap[i].getPrV());
        }
        System.out.println("\n\n\n");
    } //end printHeap
    
    //************************PRIVATE METHODS************************//
    
    //finds the prv of an inserting customer: ONLY use in when inserting
    //a new customer!!
    private int findPrV(String[] customer){
        int age, PrV = nextInLine++;
        
        //tests to find prv
        if(!(customer[1].equals(""))){ //employee status
            if(customer[1].equalsIgnoreCase("employee")) PrV -= 25;
            else if(customer[1].equalsIgnoreCase("owner")) PrV -= 80;
            else if(customer[1].equalsIgnoreCase("co-owner")) PrV -= 80;
        }
        if(!(customer[2].equals(""))){ //vip status
            if(customer[2].equalsIgnoreCase("vip")) PrV -= 5;
            else if(customer[2].equalsIgnoreCase("superVIP")) PrV -= 10;
        }
        age = Integer.parseInt(customer[3]); //age status
        if(age >= 65) PrV -= 15;
        if(age >= 80) PrV -= 15; 

        return PrV;
    } //end findPrV
    
    private HeapNode Insert(String name, int PrV) throws IOException {   
        heap[N++] = new HeapNode(name, PrV);
        
        WalkUp(N - 1);
        
        return new HeapNode(name, PrV); //this returns the name and prv for use
                                        //when printing out new node.
                                        //this node is NOT anywhere and will
                                        //assumably be cleaned up once it is used
    } //end HeapInsert
    
    //deletes node at pos
    //the only time you'll really need to delete a node is when you remove
    //a customer from the PRQ (at 0), but the int is just in case
    private HeapNode Delete(int pos){
        HeapNode deletedNode = heap[pos];
        
        heap[pos] = heap[N - 1];
        N--;
        WalkDown(pos);
        
        return deletedNode;
    } //end Delete
    
    //used with insert for sorting
    private void WalkUp(int startFrom){
        int i = startFrom;
        HeapNode temp;
        
        if(heap[i].getPrV() == heap[getParent(i)].getPrV()) return;
        
        while((i > 0) && (heap[i].getPrV() < heap[getParent(i)].getPrV())){
            temp = heap[i]; //swap the nodes
            heap[i] = heap[getParent(i)];
            heap[getParent(i)] = temp;
            
            i = getParent(i);
        } //end while     
    } //end walkup
    
    //used with delete for sorting
    private void WalkDown(int startFrom){
        int i = startFrom;
        int SmCh = SubOfSmCh(i); //smaller child
        HeapNode temp;
        
        //while the node is bigger than it's children, AND the children node 
        //exist, swap the parents with a child
        while((((2*i) + 1) <= (N-1)) && (heap[i].getPrV() > heap[SmCh].getPrV())){
            temp = heap[i];
            heap[i] = heap[SmCh];
            heap[SmCh] = temp;
            i = SmCh;
            SmCh = SubOfSmCh(i);
        }
    } //end walkdown
    
    //gets the parent of the node 
    private int getParent(int i){
        return ((i-1)/2);
    } //end getParent
    
    //gets the spot of the child that's smaller than it's parent, used in walkdown
    //the parenthesis are me being paranoid about oop
    private int SubOfSmCh(int i){
        //really sorry about the parenthesis, wanted to be certain of 
        //order of operations
        //if the right child pointer is bigger than the amount of nodes,
        //OR the left child pointer is less than or equal to the right
        //child pointer, return the left child's pointer
        //Otherwise, return the right child's pointer
        if(( ((2*i) + 2) > (N - 1)) || 
                (heap[((2*i) + 1)].getPrV() <= heap[((2*i) + 2)].getPrV()))
            return ((2*i) + 1);
        else return ((2*i) + 2);
    } //end subofsmch
    
    //returns true if the heap is full
    private boolean heapIsFull(){
         if(N == MaxN) return true;
         else return false;
    } //end isHeapFull
    
    //returns true if heap is empty
    private boolean heapIsEmpty(){
        if(N == 0) return true;
        else return false;
    } //end heapIsEmpty
    
} //end CustomerPrQ class
