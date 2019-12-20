package BinarySearchTree;

import java.nio.ByteBuffer;

public class Page {

    private Node node;
    private Node left;
    private Node right;

    public Page() {

        node = new Node();
        left = new Node();
        right = new Node();
    }

    public Node getNode() { return node; }

    public void setNode(Node node) { this.node = node; }

    public Node getLeft() { return left; }

    public void setLeft(Node left) { this.left = left; }

    public Node getRight() { return right; }

    public void setRight(Node right) { this.right = right; }

    public byte[] toBytes(){

        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES*12);
        buffer.put(node.toBytes());
        buffer.put(left.toBytes());
        buffer.put(right.toBytes());

        return buffer.array();
    }

    public void toPage(byte [] bytes){

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        Node [] arr = new Node[3];
        arr[0] = node;
        arr[1] = left;
        arr[2] = right;

        for(Node n: arr){

            n.setVal(buffer.getInt());
            n.setOffset(buffer.getInt());
            n.setLeft(buffer.getInt());
            n.setRight(buffer.getInt());
        }
    }
}
