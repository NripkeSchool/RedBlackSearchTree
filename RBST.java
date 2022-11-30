public class RBST<Key extends Comparable<Key>, Value>
{
  public static final boolean RED = true;
  public static final boolean BLACK = false;
  
  private boolean isRed(Node n)
  {
    return n.color == RED;
  }
  
  private void flipColors(Node n)
  {
  
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
      top = rotateRight(h);
    }
    
    if (isRed(top.left) && isRed(top.right))
    {
      flipColors(top);
    }
    
    top.size = size(top.left) + size(top.right);
    return top;
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
