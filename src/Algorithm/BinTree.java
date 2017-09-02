package Algorithm;

import java.util.Stack;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Second
 * @Description: 二叉树的递归和非递
 * @date 2017/8/11 15:22
 */

public class BinTree {
    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    // 先序遍历递归
    public static void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // 中序遍历递归
    public static void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);

    }

    // 后序遍历递归
    public static void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + " ");

    }


    // 先序遍历非递归
    public static void preOrder2(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                System.out.print(node.val + " ");
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.right;
            }
        }
    }

    // 中序遍历非递归
    public static void inOrder2(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                System.out.print(node.val + " ");
                node = node.right;
            }
        }

    }

    // 后序遍历非递归
    public static void postOrder2(TreeNode node) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        while (node != null || !stack1.isEmpty()) {
            while (node != null) {
                stack1.push(node);
                stack2.push(node);
                node = node.right;
            }
            if (!stack1.isEmpty()) {
                node = stack1.pop();
                node = node.left;
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().val + " ");
        }
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        /**
         *      1
         *    /   \
         *   2     3
         *  /       \
         * 4         5
         / \
         7   6
         */


        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.right = node5;
        node4.right = node6;
        node4.left = node7;
        System.out.println();
        preOrder(node1);
        System.out.println();
        preOrder2(node1);
        System.out.println();
        inOrder(node1);
        System.out.println();
        inOrder2(node1);
        System.out.println();
        postOrder(node1);
        System.out.println();
        postOrder2(node1);
    }

}
