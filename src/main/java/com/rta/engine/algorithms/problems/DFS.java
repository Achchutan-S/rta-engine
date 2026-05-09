package com.rta.engine.algorithms.problems;

import com.rta.engine.model.tree.TreeNode;
import com.rta.engine.util.TreeBuilder;

import java.util.ArrayList;
import java.util.List;

public class DFS {

    // Problems from leetcode https://leetcode.com/problem-list/depth-first-search/

    //inorder traversal - left root right
    public List<Integer> inOrderTraversal(TreeNode node) {
        List<Integer> result = new ArrayList<>();
        Integer[] arr = {1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9};
        TreeNode root = TreeBuilder.buildTree(arr);
        inOrder(root,result);
        return result;
    }

    private void inOrder(TreeNode root, List<Integer> result) {
        if(root == null) return;
        inOrder(root.left, result);
        result.add(root.val);
        inOrder(root.right, result);
    }



}
