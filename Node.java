package BinarySearchTree;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Node {

    private int val;
    private int offset;
    private int left;
    private int right;

    public Node(int val, int offset, int left, int right) {

        this.val = val;
        this.offset = offset;
        this.left = left;
        this.right = right;
    }

    public Node() {

        val = -1;
        offset = 0;
        left = 0;
        right = 0;
    }

    public Node(int val) { this.val = val; }

    public int getVal() { return val; }

    public void setVal(int val) { this.val = val; }

    public int getOffset() { return offset; }

    public void setOffset(int offset) { this.offset = offset; }

    public int getLeft() { return left; }

    public void setLeft(int left) { this.left = left; }

    public int getRight() { return right; }

    public void setRight(int right) { this.right = right; }

    public byte [] toBytes(){

        ByteBuffer buffer = ByteBuffer.allocate(4*Integer.BYTES);
        buffer.putInt(val);
        buffer.putInt(offset);
        buffer.putInt(left);
        buffer.putInt(right);

        return buffer.array();
    }

    public void toNode(byte [] bytes){

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        val = buffer.getInt();
        offset = buffer.getInt();
        left = buffer.getInt();
        right = buffer.getInt();
    }


    @Override
    public String toString() {
        return "" + val + " " + offset + " " + left + " " + right;
    }
}
