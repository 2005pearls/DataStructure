/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draft;

class BSTNode<T>{
    public int key;
    public T data;
    public BSTNode<T> left , right;
    
    public BSTNode(int key ,T data){
     this.key=key;
     this.data=data;
     left= right=null;
    }  
}

public class BST <T>{
    private BSTNode<T> root ,current ;
    
    public BST() {
        root = null;
        current=null;
    }
    public boolean isEmpty() {
        return root == null;
    }
     public boolean full() {
        return false;
    }
      public  T retrieve() {
        return current.data;
    }
      
      public boolean findKey(int k) {
    if (isEmpty())         
        return false;
    BSTNode<T> p = root, q = root;
    while (p != null) {
        q = p;  
        if (p.key == k) {   
            current = p;
            return true;
        } else if (k < p.key) {
            p = p.left;
        } else {
            p = p.right;
        }
    }
    current = q;
    return false;
}
      
    public boolean insert(int k, T val) {
    BSTNode<T> p, q = current;
    if (findKey(k)) {          
        current = q;          
        return false;  
    }
    p = new BSTNode<T>(k, val);
    if (isEmpty()) {         
        root = current = p;
        return true;
    } else {
        if (k < current.key)
            current.left = p;
        else
            current.right = p;
        current = p;         
        return true;
    }
}
   private void deleteNode(BSTNode<T> n, BSTNode<T> parent) {
    BSTNode<T> child;
    if (n.left != null)
        child = n.left;
    else
        child = n.right;
    if (parent == null) {
        root = child;
    } else {
        if (n.key < parent.key)
            parent.left = child;
        else
            parent.right = child;
    }
}
   
   
      // /////// helping methods
public void inOrder() {
    if (root == null)
        System.out.println("empty tree");
    else
        inOrder(root);
}

// recursive in-order traversal
private void inOrder(BSTNode<T> p) {
    if (p == null) return;

    inOrder(p.left);                          
    System.out.print("key= " + p.key);       
    System.out.println();                    
    inOrder(p.right);                         
}
public boolean removeKey(int k) {

    // Search for k
    BSTNode<T> p = root;     
    BSTNode<T> q = null;      
    boolean found = false;

    while (p != null && !found) {
        int res = k - p.key;
        if (res < 0) {
            q = p;
            p = p.left;
        } else if (res > 0) {
            q = p;
            p = p.right;
        } else {
            found = true;
        }
    }

    if (found) {
        //node has two children
        if (p.left != null && p.right != null) {
            BSTNode<T> min = p.right;
            q = p;
            while (min.left != null) {
                q = min;
                min = min.left;
            }
            p.key  = min.key;
            p.data = min.data;
            deleteNode(min, q);
        } else {
            // 0 or 1 child
            deleteNode(p, q);
        }
        current = root;
        return true;
    }
    return false;   // not found
}

public void findRoot() {
    current = root;
}

public int curkey() {
    return current.key;
}

public T curData() {
    return current.data;
}

  public BSTNode<T> getRoot() {
    return root;
}
    
