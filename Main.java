package BinarySearchTree;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*try {
            PagedBinaryIndex page = new PagedBinaryIndex();
            page.createRecordsFile("index", 15);
            RandomAccessFile file = new RandomAccessFile("index.txt","rw");
            /*Page p = new Page();
            p.setNode(new Node(6));
            p.setLeft(new Node(5));
            p.setRight(new Node(7));

            Page pp = new Page();
            pp.setNode(new Node(3));
            pp.setLeft(new Node(2));
            pp.setRight(new Node(4));

            p.getNode().setRight(1);
            p.getNode().setLeft(1);
            p.getLeft().setLeft(2);
            p.getLeft().setRight(-1);

            p.getRight().setLeft(-1);
            p.getRight().setRight(-1);

            pp.getNode().setLeft(2);
            pp.getNode().setRight(2);
            pp.getLeft().setLeft(-1);
            pp.getLeft().setRight(-1);
            pp.getRight().setLeft(-1);
            pp.getRight().setRight(-1);


            page.writePage(1,file,p);
            page.writePage(2,file,pp);

            System.out.println("teest " + page.searchToInsert(file,8,1,1,new Page()));
            //file.seek(16);
            //file.write(p.toBytes());
            //System.out.println(Arrays.toString(p.toBytes()));
            //System.out.println(page.searchRecordInIndex("index", 3));
            System.out.println(page.insertNewRecordAtIndex("index",1,12));
            //page.displayIndexFileContent("index");

            System.out.println(page.insertNewRecordAtIndex("index",2,24));
            //page.displayIndexFileContent("index");

            System.out.println(page.insertNewRecordAtIndex("index",3,36));
            //page.displayIndexFileContent("index");

            System.out.println(page.insertNewRecordAtIndex("index",4,48));
            //page.displayIndexFileContent("index");

            System.out.println(page.insertNewRecordAtIndex("index",5,53));
            //page.displayIndexFileContent("index");
            System.out.println(page.insertNewRecordAtIndex("index",7,72));
            //page.displayIndexFileContent("index");

            System.out.println(page.insertNewRecordAtIndex("index",11,84));

            //System.out.println(page.insertNewRecordAtIndex("index",8,84));
            System.out.println(page.insertNewRecordAtIndex("index",0,84));
            System.out.println(page.insertNewRecordAtIndex("index",8,84));
            System.out.println( page.insertNewRecordAtIndex("index",9,84));

            //System.out.println("search: " + page.searchRecordInIndex("index",11));
           page.displayIndexFileContent("index");
            //
            //page.displayBinarySearchTreeInOrder("index");

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        Scanner scanner = new Scanner(System.in);
        PagedBinaryIndex pagedBinaryIndex = new PagedBinaryIndex();

        System.out.println("welcome :)\n");
        System.out.println("file name ");
        String filename = scanner.nextLine();
        System.out.println(" number of records");
        pagedBinaryIndex.createRecordsFile(filename,scanner.nextInt());

        while (true){

            System.out.println();
            System.out.println("\tplease chose.");
            System.out.println("-------------------------------");
            System.out.println("1.insert into index. \n2.display index content. \n3.display in order. \n4.search \n5.exit.");

            int choice = scanner.nextInt();

            switch (choice){

                case 1:

                    System.out.print("key: ");
                    int key = scanner.nextInt();
                    System.out.print("offset: ");
                    int offset = scanner.nextInt();

                    if(pagedBinaryIndex.insertNewRecordAtIndex(filename,key,offset) > 0)
                        System.out.println("done!");
                    else
                        System.out.println("something error");
                    break;

                case 2:
                    pagedBinaryIndex.displayIndexFileContent(filename);
                    break;

                case 3:
                    pagedBinaryIndex.displayBinarySearchTreeInOrder(filename);
                    break;

                case 4:
                    System.out.print("key: ");
                    System.out.println(pagedBinaryIndex.searchRecordInIndex(filename,scanner.nextInt()));
                    break;

                case 5:
                    return;
            }
        }
    }
}
