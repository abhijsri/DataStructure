package com.oracle.casb.leetcode;

import org.junit.BeforeClass;
import org.junit.Test;

public class BSTIteratorTest {


    private static TreeNode root;
    @BeforeClass
    public static void createTree() {
        root = constructTree();
    }

    private static TreeNode constructTree() {
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(3);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(20);

        return root;
    }

    @Test
    public void testNext() {
        BSTIterator bt = new BSTIterator(root);
        while (bt.hasNext()) {
            System.out.printf("Next value: %d\n", bt.next());
        }
    }
}
