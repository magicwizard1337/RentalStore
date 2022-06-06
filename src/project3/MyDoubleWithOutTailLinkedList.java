package project3;

import java.io.Serializable;
import java.util.Random;

/******************************************************************
 * Double linked list that contains rentals sorted by date.
 * sorts by games then consoles, if dates are the same sorts by
 * name of renter
 *
 * @author Daniel Nguyen
 * @vesion April 2022
 */
public class MyDoubleWithOutTailLinkedList implements Serializable {
    /** top node */
    private DNode top;

    /******************************************************************
     * default constructor sets top to null
     */
    public MyDoubleWithOutTailLinkedList() {
        top = null;
    }

    /******************************************************************
     * finds the number of nodes in the linked list
     * @return size of list
     * @throws RuntimeException if number of next pointers and
     * prev pointers do not match
     */
    public int size() {
        if (top == null)
            return 0;

        int total = 0;
        DNode temp = top;
        while (temp != null) {
            total++;
            temp = temp.getNext();
        }

        int totalBack = 0;
        temp = top;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }

        while (temp != null) {
            totalBack++;
            temp = temp.getPrev();
        }


        if (total != totalBack) {
            System.out.println("next links " + total + " " +
                    "do not match prev links " + totalBack);
            throw new RuntimeException();
        }

        return total;

    }

    /******************************************************************
     * randomly removes all nodes from the list
     */
    public void clear() {
        //top = null;
        Random rand = new Random(13);
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    /******************************************************************
     * adds rental into list in a sorted way
     * @return once node has been inserted
     */
    public void add(Rental s) {
        DNode temp = top;

        // no list, s becomes top
        if (top == null) {
            top = new DNode(s, null, null);
            return;
        }

        //top cases
        //top is a game
        if(top.getData() instanceof Game){
            // s is a Game, and s goes before top
            if (s instanceof Game && top.getData().
                    dueBack.after(s.dueBack)) {
                top = new DNode(s, top, null);
                top.getNext().setPrev(top);
                return;
            }
            // s is a game, and s.dueback == top.dueback,
            // sort by nameofrenter
            if(s instanceof Game && top.getData().
                    dueBack.equals(s.dueBack)){
                //s.nameofrenter is before top.nameofrenter
                if (top.getData().nameOfRenter.
                        compareTo(s.nameOfRenter) > 0){
                    top = new DNode(s, top, null);
                    top.getNext().setPrev(top);
                    return;
                }
            }
        }

        //top is a console
        if(top.getData() instanceof Console ){
            // s is a game, and s goes before top
            if (s instanceof Game){
                top = new DNode(s, top, null);
                top.getNext().setPrev(top);
                return;
            }
            // s is a console, and s goes before top
            if (s instanceof Console && top.getData().
                    dueBack.after(s.dueBack)) {
                top = new DNode(s, top, null);
                top.getNext().setPrev(top);
                return;
            }
            // s is a console, s.dueback == top.dueback,
            // sort by nameofrenter
            if(s instanceof Console && top.getData().
                    dueBack.equals(s.dueBack)){
                //s.nameofrenter is before top.nameofrenter
                if (top.getData().nameOfRenter.
                        compareTo(s.nameOfRenter) > 0){
                    top = new DNode(s, top, null);
                    top.getNext().setPrev(top);
                    return;
                }
            }
        }

        //intermediate cases
        //s is a game
        if (s instanceof Game) {
            while (temp.getNext() != null &&
                    temp.getNext().getData() instanceof Game) {
                //find the correct position for an s that is a game.
                if (temp.getNext().getData().
                        dueBack.after(s.dueBack)) {
                    DNode newNode = new DNode(s, temp.getNext(), temp);
                    temp.setNext(newNode);
                    newNode.getNext().setPrev(newNode);
                    return;
                }
                //equals case
                if (temp.getNext().getData().
                        dueBack.equals(s.dueBack)) {
                    // s name < temp.getnext name,
                    // s goes before temp.getnext
                    if (temp.getNext().getData().nameOfRenter.
                            compareTo(s.nameOfRenter) > 0){
                        DNode newNode = new DNode
                                (s, temp.getNext(), temp);
                        temp.setNext(newNode);
                        newNode.getNext().setPrev(newNode);
                        return;
                    }
                }
                temp = temp.getNext();
            }
        }

        //points temp to last game
        while (temp.getNext() != null && temp.getNext().getData()
                instanceof Game)
            temp = temp.getNext();


        if (s instanceof Console) {
            while (temp.getNext() != null &&
                    temp.getNext().getData() instanceof Console) {
                //find the correct position for an s that is a game.
                if (temp.getNext().getData().
                        dueBack.after(s.dueBack)) {
                    DNode newNode = new DNode(s, temp.getNext(), temp);
                    temp.setNext(newNode);
                    newNode.getNext().setPrev(newNode);
                    return;
                }
                //equals case
                if (temp.getNext().getData().
                        dueBack.equals(s.dueBack)) {
                    // s name < temp.getnext name,
                    // s goes before temp.getnext
                    if (temp.getNext().getData().nameOfRenter.
                            compareTo(s.nameOfRenter) > 0){
                        DNode newNode =
                                new DNode(s, temp.getNext(), temp);
                        temp.setNext(newNode);
                        newNode.getNext().setPrev(newNode);
                        return;
                    }
                }
                temp = temp.getNext();
            }
        }
        //bottom case
        //bottom case for games
        if (temp.getNext() != null &&
                temp.getNext().getData() instanceof Console){
            DNode newNode = new DNode(s, temp.getNext(), temp);
            temp.setNext(newNode);
            newNode.getNext().setPrev(newNode);
            return;
        }

        //points temp to last node
        while (temp.getNext() != null)
            temp = temp.getNext();

        if (temp.getNext() == null){
            DNode newNode = new DNode(s, null, temp);
            temp.setNext(newNode);
            return;
        }
    }

    /******************************************************************
     * removes rental from list
     * @param index position of rental to be removed
     * @return rental being removed, or null if theres no rental
     * at position
     */
    public Rental remove(int index)  {
        //base case
        if (top == null)
            return null;

        //top is getting removed
        if (index == 0){
            if(top.getNext() != null){
                top.getNext().setPrev(null);
                top = top.getNext();
                return top.getData();
            }

            if(top.getNext() == null){
                top = null;
                return null;
            }
        }

        DNode temp = top;
        //sets temp to node at index
        while (temp.getNext() != null && index > 0){
            temp = temp.getNext();
            index--;
        }

        //if temp is the last node
        if(temp.getNext() == null){
            temp.getPrev().setNext(null);
            return temp.getData();
        }

        //temp is intermediate node
        if(temp.getNext() != null)
            temp.getNext().setPrev(temp.getPrev());

        if (temp.getPrev() != null)
            temp.getPrev().setNext(temp.getNext());

        return top.getData();
    }

    /******************************************************************
     * gets rental from list
     * @param index position of rental to be returned
     * @return rental at index
     * @throws IllegalArgumentException if index is more than size
     * or less than zero
     */
    public Rental get(int index) {

        // no list, base case
        if (top == null)
            return null;

        //if index is bigger than list size
        if (index > size() || index < 0)
            throw new IllegalArgumentException();

        DNode temp = top;

        //if index is zero
        if (index == 0)
            return temp.getData();

        //if index is smaller than list size
        if (index < size()) {
            for (int i = 0; temp != null && i < index; i++) {
                temp = temp.getNext();
            }
        }

        //if index is the same as list size
        if (index == size()) {
            while (temp.getNext() != null)
                temp = temp.getNext();
        }


        return temp.getData();  // this line will need to be changed
    }

    /******************************************************************
     * prints list
     */
    public void display() {
        DNode temp = top;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    /******************************************************************
     * converts rental data to string
     * @return rental data in string form
     * (LL{top = top, size = size})
     */
    public String toString() {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}';
    }


}