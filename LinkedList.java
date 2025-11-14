package draft;

class Node<T> {
    T data;
    Node<T> next;
    Node(T d){ data = d; }
}

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;

    public LinkedList(){ head = null; current = null; }

    public boolean empty(){ return head == null; }

    public boolean last(){
        return current != null && current.next == null;
    }

    public void findFirst(){
        current = head;
    }

    public void findNext(){
        if (current != null) current = current.next;
    }

    public T retrieve(){
        return (current == null) ? null : current.data;
    }

    public void update(T val){
        if (current != null) current.data = val;
    }

    public void insert(T val){
        Node<T> nn = new Node<>(val);
        if (empty()){
            head = nn;
            current = head;
        }else{
            nn.next = (current == null) ? head : current.next;
            if (current == null){ 
                head = nn;
                current = head;
            }else{
                current.next = nn;
                current = nn;
            }
        }
    }

    public void remove(){
        if (empty() || current == null) return;

        if (current == head){
            head = head.next;
            current = head;
            return;
        }
        Node<T> p = head;
        while (p != null && p.next != current) p = p.next;
        if (p == null) return; 
        p.next = current.next;
        current = (p.next == null) ? head : p.next;
    }

    public void addLast(T val){
        Node<T> nn = new Node<>(val);
        if (empty()){
            head = nn;
            current = head;
            return;
        }
        Node<T> p = head;
        while (p.next != null) p = p.next;
        p.next = nn;
        current = nn;
    }
}
