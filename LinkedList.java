package structurephase1;

class Node<T> {
    public T data;
    public Node<T> next;
    Object link;

    public Node(T val) {
        data = val;
        next = null;      
    }
}

//LinkedList Class 
public class LinkedList<T> {

    private Node<T> head;      
    private Node<T> current;   

    
    public LinkedList() {
        head = null;
        current = head;
    }

    
    public boolean empty() { 
        return head == null;
    }

  
    public boolean last() {
        return current.next==null;
    }


    public boolean full() {
        return false;
    }

    
    public void findFirst() {
        current = head;
    }

  
    public void findNext() {
      current=head;
        
    }

    
    public T retrieve() {
      return current.data;
    }
    
    public void update (T val){
        current.data=val;
    }

   
    public void insert(T val) {
    Node<T> tmp;
    if (empty()) {
        current = head = new Node<T>(val);
    } else {
        tmp = current.next;
        current.next = new Node<T>(val);
        current = current.next;
        current.next = tmp;
    }
}

    public void remove() {
    if (current == head) {
        head = head.next;
    } else {
        Node<T> tmp = head;

        while (tmp.next != current)
            tmp = tmp.next;

        tmp.next = current.next;
    }

    if (current.next == null)
        current = head;
    else
        current = current.next;
}
    
// add new node at the end of the list
public void addLast(T x) {
    Node<T> nn = new Node<>(x);
    if (head == null) {
        head = nn;
        current = head;
        return;
    }
    Node<T> n = head;
    while (n.next != null) n = n.next;

    n.next = nn;
    current = nn;
}

}//end class

