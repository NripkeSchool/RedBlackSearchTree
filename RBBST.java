public class RBBST<Key extends Comparable<Key>, Value>
{
  public static final boolean RED = true;
  public static final boolean BLACK = false;
  private Node root;
  
  private boolean isRed(Node n)
  {
    if (n == null) {return BLACK;}
    return n.color == RED;
  }
  
  private void flipColors(Node n)
  {
    n.color = !n.color;
    if (n.left != null) {n.left.color = !n.left.color;}
    if (n.right != null) {n.right.color = !n.right.color;}
  }
  
  private Node rotateLeft(Node top)
  {
    //Top node takes its right-leaning red link, and makes itself the red-link, make the old red-link the new top
    Node x = top.right; //Get red right node
    top.right = x.left;
    x.left = top;
    
    //Change colors
    x.color = top.color;
    top.color = RED;
    
    //Change Size
    x.size = top.size;
    top.size = 1 + size(top.left) + size(top.right);
    
    return x;
  }
  
  private Node rotateRight(Node top)
  {
    //Top node takes its left-leaning red link, and makes itself the red-link, make the old red-link the new top
    Node x = top.left; //Get red left node
    top.left = x.right;
    x.right = top;
    
    //Change colors
    x.color = top.color;
    top.color = RED;
    
    //Change Size
    x.size = top.size;
    top.size = 1 + size(top.left) + size(top.right);
    
    return x;
  }
  
  public void put(Key k, Value v)
  {
    root = put(root, k, v);
    root.color = BLACK;
  }
  
  private Node put(Node top, Key k, Value v)
  {
    if (top == null) {return new Node(k, v, RED);}
    
    int c = k.compareTo(top.key);
    if (c < 0) {top.left = put(top.left, k, v);}
    else if (c > 0) {top.right = put(top.right, k, v);}
    else {top.val = v;}
    
    if (isRed(top.right) && !isRed(top.left))
    {
      top = rotateLeft(top);
    }
    
    if (isRed(top.left) && isRed(top.left.left))
    {
      top = rotateRight(top);
    }
    
    if (isRed(top.left) && isRed(top.right))
    {
      flipColors(top);
    }
    
    top.size = size(top.left) + size(top.right);
    return top;
  }
  
  private Node moveRedLeft(Node top)
  {
    flipColors(top);
    if (isRed(top.right.left))
    {
      top.right = rotateRight(top.right);
      top = rotateLeft(top);
      flipColors(top);
    }
    
    return top;
  }
  
  private Node moveRedRight(Node top)
  {
    flipColors(top);
    if (isRed(top.left.left))
    {
      top = rotateRight(top);
      flipColors(top);
    }
    
    return top;
  }
  
  public void deleteMin()
  {
    root = deleteMin(root);
    root.color = BLACK;
  }
  
  private Node deleteMin(Node top)
  {
    if (top.left == null) {return null;}
    if (!isRed(top.left) && !isRed(top.left.left)) {top = moveRedLeft(top);}
    
    top.left = deleteMin(top.left);
    return balance(top);
  }
  
  public void delete(Key k)
  {
    root = delete(root, k);
    root.color = BLACK;
  }
  
  private Node delete(Node top, Key k)
  {
    if (k == null) {return null;}
    if (k.compareTo(top.key) < 0) //Going left
    {
      if (!isRed(top.left) && !isRed(top.left.left)) {top = moveRedLeft(top);}
      top.left = delete(top.left, k);
    }
    else 
    {
      if (isRed(top.left)) {top = rotateRight(top);} //Make sure right node is red
      if (k.compareTo(top.key) == 0 && top.right == null) {return null;} //right being null implies left is null, and that we are red
      if (!isRed(top.right) && !isRed(top.right.left)) {top = moveRedRight(top);}
      if (k.compareTo(top.key) == 0)
      {
        top.val = get(top.right, min(top.right).k);
        top.key = min(top.right).key;
        top.right = deleteMin(top.right);
      }
      else {top.right = delete(top.right, k);}
    }
    
    return balance(top);
  }
  
  public Value get(Key k)
  {
      Node n = get(root, k);
      if (n == null) {return null;}
      return n.val;
  }
    
  private Node get(Node parent, Key k)
  {
      if (parent == null) {return null;}
      if (parent.k == k) {return parent;}
      if (k.compareTo(parent.k) > 0) {return get(parent.right, k);}
        
      return get(parent.left, k);
  }
  
  private Node balance(Node top)
  {
    if (isRed(top.right) && !isRed(top.left))
    {
      top = rotateLeft(top);
    }
    
    if (isRed(top.left) && isRed(top.left.left))
    {
      top = rotateRight(top);
    }
    
    if (isRed(top.left) && isRed(top.right))
    {
      flipColors(top);
    }
    
    return top;
  }
  
  private int size(Node n)
  {
    if (n == null) {return 0;}
    return n.size;
  }
  
  private class Node
  {
    Key key;
    Value val;
    Node left, right;
    boolean color;
    int size = 1;
    
    public Node(Key k, Value v, boolean c)
    {
        this.k = k;
        this.v = v;
        this.color = c;
    }
  }
}
