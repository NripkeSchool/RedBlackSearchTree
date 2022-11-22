public class RBST<Key extends Comparable<Key>, Value>
{
  public static final boolean RED = true;
  public static final boolean BLACK = false;
  
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
  
  private class Node
  {
    Key key;
    Value val;
    Node left, right;
    boolean color;
    int size;
  }
}
