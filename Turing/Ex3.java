package com.Turing;

public class Ex3 {
    public static class SortingTree {

        static TreeNode root;

        static class TreeNode {
            double item;
            TreeNode left;
            TreeNode right;
            TreeNode(double x) {
                item = x;

            }

        }

        static void treeInsert(double x) {
            if ( root == null ){
                root = new TreeNode( x );
                return;
            }

            TreeNode runner;
            runner = root;
            while (true) {
                if ( x < runner.item ) {
                    if ( runner.left == null ) {
                        runner.left = new TreeNode( x );
                        return;
                    }
                    else
                        runner = runner.left;
                }
                else {
                    if ( runner.right == null ) {
                        runner.right = new TreeNode( x );
                        return;
                    }
                    else
                        runner = runner.right;
                }
            }
        }

        static int countLeaves(TreeNode node) {
            if (node == null)
                return 0;
            else if (node.left == null && node.right == null)
                return 1;
            else
                return countLeaves(node.left) + countLeaves(node.right);
        }

        static int sumOfLeafDepths( TreeNode node, int depth ) {
            if ( node == null ) {
                return 0;
            }
            else if ( node.left == null && node.right == null) {
                return depth;
            }
            else {
                return sumOfLeafDepths(node.left, depth + 1)
                        + sumOfLeafDepths(node.right, depth + 1);
            }
        }


        static int maximumLeafDepth( TreeNode node, int depth ){
            if ( node == null ) {
                return 0;
            }
            else if ( node.left == null && node.right == null) {
                return depth;
            }
            else {
                int leftMax = maximumLeafDepth(node.left, depth + 1);
                int rightMax =  maximumLeafDepth(node.right, depth + 1);
                return Math.max(leftMax, rightMax);
            }
        }


        public static void main(String[] args) {
            root = null;
            for (int i = 0; i < 1023; i++)
                treeInsert(Math.random());
            int leafCount = countLeaves(root);
            int depthSum = sumOfLeafDepths(root,0);
            int depthMax = maximumLeafDepth(root,0);
            double averageDepth = ((double)depthSum) / leafCount;

            System.out.println("Total leaves:         " + leafCount);
            System.out.println("Average depth of leaves:  " + averageDepth);
            System.out.println("Maximum depth of leaves:  " + depthMax);
        }
    }

}
