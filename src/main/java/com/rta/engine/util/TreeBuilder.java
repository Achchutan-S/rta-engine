package com.rta.engine.util;

import com.rta.engine.model.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class TreeBuilder {
    public static TreeNode buildTree(Integer[] arr) {
        if (arr == null || arr.length == 0|| arr[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int index = 0;
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            // Left child

            if(arr[index]!= null){
                current.left = new TreeNode(arr[index]);
                queue.add(current.left);
            }
            index++;

            //Right child
            if(index< arr.length && arr[index] != null){
                current.right = new TreeNode(arr[index]);
                queue.add(current.right);
            }
            index++;
        }
        return root;
    }
}
