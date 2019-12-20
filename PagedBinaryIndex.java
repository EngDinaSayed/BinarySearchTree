package BinarySearchTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PagedBinaryIndex {

    private int numOfPages;

    public void createRecordsFile (String filename, int numberOfRecords){

        numOfPages = (int) Math.ceil(numberOfRecords/3.0);
        File file = new File(filename + ".txt");
        if(file.exists())
            file.delete();

        try {

            RandomAccessFile indexFile = new RandomAccessFile(filename + ".txt","rw");

            Node firstRecord = new Node();
            firstRecord.setVal(1);
            indexFile.write(firstRecord.toBytes());

            for(int i=0; i<numOfPages; i++){

                Page page = new Page();
                byte [] bytes = page.toBytes();
                indexFile.write(bytes);
            }

            indexFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int search(RandomAccessFile file, int key, int currentPage, Page page){

        page = readPage(currentPage, file);
        if(page.getNode().getVal() != -1) {

            if (page.getNode().getVal() == key) {
                return page.getNode().getOffset();
            }
            else if (page.getRight().getVal() == key) {
                return page.getRight().getOffset();
            }
            else if (page.getLeft().getVal() == key) {
                return page.getLeft().getOffset();
            }
            else if (key < page.getLeft().getVal()) {
                return search(file, key, page.getLeft().getLeft(), page);
            }
            else if (key > page.getLeft().getVal() && key < page.getNode().getVal()) {
                return search(file, key, page.getLeft().getRight(), page);
            }
            else if (key < page.getRight().getVal()) {
                return search(file, key, page.getRight().getLeft(), page);
            }
            else if (key > page.getRight().getVal()) {
                return search(file, key, page.getRight().getRight(), page);
            }
            else if(currentPage == numOfPages) return -1;
        }
        return -1;
    }

    public int searchRecordInIndex(String filename, int key){

        int offset = -1;
        try {

            RandomAccessFile file = new RandomAccessFile(filename + ".txt" , "r");
            int currentPage = 1;

            offset = search(file,key,currentPage,new Page());

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return offset;
    }

    void writePage(int pageNumber, RandomAccessFile file, Page page){

        if(pageNumber > 0) {

            try {

                file.seek(((pageNumber - 1) * 12 * Integer.BYTES) + 16);
                file.write(page.toBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Page readPage(int pageNumber, RandomAccessFile file){

        Page page = new Page();

        if(pageNumber > 0) {

            byte[] bytes = new byte[12 * Integer.BYTES];
            try {

                file.seek(((pageNumber - 1) * 12 * Integer.BYTES) + 16);
                file.read(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }

            page.toPage(bytes);
        }

        return page;
    }

    private void inOrder(RandomAccessFile file, Page page){

        if(page.getNode().getVal() > -1) {

            if(page.getLeft().getLeft() != -1)
                inOrder(file, readPage(page.getLeft().getLeft(), file));
            if(page.getLeft().getRight() != -1)
                inOrder(file, readPage(page.getLeft().getRight(), file));

            if (page.getLeft().getVal() != -1)
                System.out.print(page.getLeft().getVal() + "\t");

                System.out.print(page.getNode().getVal() + "\t");

            if (page.getRight().getVal() != -1)
                System.out.print(page.getRight().getVal() + "\t");

            if(page.getRight().getLeft() != -1)
                inOrder(file, readPage(page.getRight().getLeft(), file));
            if(page.getRight().getRight() != -1)
            inOrder(file, readPage(page.getRight().getRight(), file));

        }
    }

    public void displayBinarySearchTreeInOrder(String fileName){

        File file = new File(fileName + ".txt");
        if(!file.exists())
            return;

        try {

            RandomAccessFile dataFile = new RandomAccessFile(fileName + ".txt", "r");
            Page page = readPage(1,dataFile);
            inOrder(dataFile,page);

            dataFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayIndexFileContent(String filename){

        File file = new File(filename + ".txt");
        if(!file.exists())
            return;

        try {

            RandomAccessFile dataFile = new RandomAccessFile(filename + ".txt", "r");
            Page page;

            byte [] bytes = new byte[16];
            dataFile.read(bytes);
            Node root = new Node();
            root.toNode(bytes);
            System.out.println(root);

            for(int i=1; i<=numOfPages; i++){

                page = readPage(i,dataFile);
                System.out.println("page : " + i);
                System.out.println(page.getNode());
                System.out.println(page.getLeft());
                System.out.println(page.getRight());
            }

            dataFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int insert(RandomAccessFile file, int key, int offset, int currentPage, int previousPage, Page page){

        Node node = new Node();
        try {

            file.seek(0);
            byte[] bytes = new byte[16];
            file.read(bytes);
            node.toNode(bytes);

            boolean temp = false;
            page = readPage(currentPage, file);

            if (page.getNode().getVal() == -1) {

                page.getNode().setVal(key);
                page.getNode().setOffset(offset);
                page.getNode().setLeft(-1);
                page.getNode().setRight(-1);
                temp = true;
            } else if (key < page.getNode().getVal() && page.getLeft().getVal() == -1) {

                page.getLeft().setVal(key);
                page.getLeft().setOffset(offset);
                page.getLeft().setLeft(-1);
                page.getLeft().setRight(-1);
                page.getNode().setLeft(currentPage);
                previousPage = currentPage;
                temp = true;
            } else if (key > page.getNode().getVal() && page.getRight().getVal() == -1) {

                page.getRight().setVal(key);
                page.getRight().setOffset(offset);
                page.getRight().setLeft(-1);
                page.getRight().setRight(-1);
                page.getNode().setRight(currentPage);
                previousPage = currentPage;
                temp = true;
            }

            if (!temp) currentPage++;

            if (temp && previousPage > 0) {

                if(previousPage >= node.getVal() && previousPage < numOfPages)
                    node.setVal(previousPage + 1);
                file.seek(0);
                file.write(node.toBytes());
                writePage(previousPage, file, page);

                return previousPage;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        if(key < page.getLeft().getVal()) {

            if(page.getLeft().getLeft() == -1){
                currentPage = node.getVal();
            }

            page.getLeft().setLeft(insert(file, key, offset, page.getLeft().getLeft(), currentPage, page));
            writePage(previousPage,file,page);
            return previousPage;
        }
        else if(key > page.getLeft().getVal() && key < page.getNode().getVal()) {

            if(page.getLeft().getRight() == -1){
                currentPage = node.getVal();
            }

            page.getLeft().setRight(insert(file, key, offset, page.getLeft().getRight(), currentPage, page));
            writePage(previousPage,file,page);
            return previousPage;
        }
        else if(key < page.getRight().getVal() && key > page.getNode().getVal()) {

            if(page.getRight().getLeft() == -1) {
                currentPage = node.getVal();
            }

            page.getRight().setLeft(insert(file, key, offset, page.getRight().getLeft(), currentPage, page));
            writePage(previousPage,file,page);
            return previousPage;
        }
        else if(key > page.getRight().getVal()) {

            if(page.getRight().getRight() == -1) {
                currentPage = node.getVal();
            }

            page.getRight().setRight(insert(file, key, offset, page.getRight().getRight(), currentPage, page));
            writePage(previousPage,file,page);
            return previousPage;
        }

        return -1;
    }

    public int insertNewRecordAtIndex(String filename, int key, int byteOffset){

        int operation = -1;
        File file = new File(filename + ".txt");
        if(!file.exists())
            operation =  -2;

        try {

            RandomAccessFile dataFile = new RandomAccessFile(filename + ".txt", "rw");

            if(searchRecordInIndex(filename,key) != -1) return -1;
            operation = insert(dataFile,key,byteOffset,1,1,new Page());

            dataFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return operation;
    }
}
