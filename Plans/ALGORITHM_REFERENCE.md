# Algorithm Implementation Quick Reference

A quick guide to the key concepts you'll need while implementing the algorithms.

## 🔤 Sorting Algorithms

### Bubble Sort - O(n²)
```
Key Concept: Compare adjacent elements, swap if needed
Pattern: 
  for each position i from start to end:
    for each position j from start to end-i:
      if arr[j] > arr[j+1]:
        swap them
Optimization: If no swaps happen, array is already sorted
```

### Merge Sort - O(n log n)
```
Key Concept: Divide-and-conquer approach
Pattern:
  sort(arr):
    if length > 1:
      split into left and right
      sort(left)
      sort(right)
      merge(left, right)
  merge(left, right):
    create result array
    compare elements from left and right
    add smaller to result
    add remaining elements
```

---

## 📊 Graph Algorithms

### BFS (Breadth-First Search) - O(V + E)
```
Key Concept: Explore level-by-level using Queue (FIFO)
Data Structure: Queue, Set<visited>
Pattern:
  create queue, set
  queue.add(source)
  visited.add(source)
  while queue not empty:
    vertex = queue.remove()
    process(vertex)
    for each neighbor of vertex:
      if not visited:
        visited.add(neighbor)
        queue.add(neighbor)
```

### DFS (Depth-First Search) - O(V + E)
```
Key Concept: Explore deep using Stack (LIFO) or recursion
Data Structure: Stack or Recursion, Set<visited>
Pattern (iterative):
  create stack, set
  stack.push(source)
  while stack not empty:
    vertex = stack.pop()
    if not visited:
      visited.add(vertex)
      process(vertex)
      for each neighbor of vertex:
        if not visited:
          stack.push(neighbor)

Pattern (recursive):
  dfs(vertex, visited):
    visited.add(vertex)
    process(vertex)
    for each neighbor of vertex:
      if not visited:
        dfs(neighbor, visited)
```

### Cycle Detection in DFS
```
Key Concept: Use recursion stack to detect back edges
Pattern:
  visited: set of all explored nodes
  recursionStack: set of nodes in current path
  
  for each node:
    if not visited:
      if hasCycleDFS(node):
        cycle found
        
  hasCycleDFS(node):
    visited.add(node)
    recursionStack.add(node)
    for each neighbor:
      if not visited:
        if hasCycleDFS(neighbor):
          return true
      else if neighbor in recursionStack:
        cycle detected!
        return true
    recursionStack.remove(node)
    return false
```

---

## 🎯 Dynamic Programming

### Fibonacci - O(n)
```
Key Concept: Store results to avoid recomputation

Approach 1 - Bottom-Up:
  dp[0] = 0
  dp[1] = 1
  for i = 2 to n:
    dp[i] = dp[i-1] + dp[i-2]
  return dp[n]

Approach 2 - Top-Down (Memoization):
  memo = empty map
  fib(n):
    if n in memo:
      return memo[n]
    if n <= 1:
      return n
    result = fib(n-1) + fib(n-2)
    memo[n] = result
    return result

Approach 3 - Space Optimized:
  prev2 = 0, prev1 = 1
  for i = 2 to n:
    current = prev1 + prev2
    prev2 = prev1
    prev1 = current
  return prev1
```

---

## 🔄 Recursion & Backtracking

### N-Queens - O(N!)
```
Key Concept: Place queens row by row, backtrack when stuck
Pattern:
  solve(board, row, results):
    if row == n:
      add board to results
      return
    for each col in row:
      if isValid(board, row, col):
        board[row][col] = 'Q'
        solve(board, row + 1, results)
        board[row][col] = '.'  # Backtrack

  isValid(board, row, col):
    check column above: no 'Q' in same col
    check upper-left diagonal: no 'Q' in diagonal
    check upper-right diagonal: no 'Q' in diagonal
    return all checks passed
```

---

## 📚 Common Patterns

### Queue (for BFS)
```java
Queue<Integer> queue = new LinkedList<>();
queue.offer(element);      // Add to back
int elem = queue.poll();    // Remove from front
boolean empty = queue.isEmpty();
```

### Stack (for DFS)
```java
Stack<Integer> stack = new Stack<>();
stack.push(element);        // Add to top
int elem = stack.pop();     // Remove from top
boolean empty = stack.isEmpty();
```

### Visited Set (for traversal)
```java
Set<Integer> visited = new HashSet<>();
visited.add(vertex);
visited.contains(vertex);
if (!visited.contains(vertex)) { ... }
```

### Result List (for collecting)
```java
List<Integer> result = new ArrayList<>();
result.add(element);
return result;
```

---

## 🎪 Edge Cases to Handle

### Arrays
- ❌ null array
- ❌ empty array (length 0)
- ❌ single element array
- ✅ already sorted array
- ✅ reverse sorted array

### Graphs
- ❌ null graph
- ❌ source not in graph
- ❌ target not in graph
- ✅ disconnected graph
- ✅ cyclic graph

### Numbers
- ❌ negative input
- ❌ zero input
- ❌ very large numbers (overflow)
- ✅ boundary values

### String conversions (N-Queens)
- ❌ null input
- ✅ convert char[] to String
- ✅ handle empty cells ('.')
- ✅ handle queen placement ('Q')

---

## 🧮 Complexity Reference

| Algorithm | Time | Space | Notes |
|-----------|------|-------|-------|
| Bubble Sort | O(n²) | O(1) | Simplest, in-place |
| Merge Sort | O(n log n) | O(n) | Stable, divide-conquer |
| BFS | O(V+E) | O(V) | Uses queue, level-based |
| DFS | O(V+E) | O(V) | Uses stack/recursion |
| Fibonacci DP | O(n) | O(1) to O(n) | Depends on approach |
| N-Queens | O(N!) | O(N) | Exponential, backtrack |

---

## ✅ Testing Checklist

For each algorithm, verify:
- [ ] Handles null/empty input gracefully
- [ ] Works with small inputs (1-5 elements)
- [ ] Works with medium inputs (10-100 elements)
- [ ] Works with edge cases
- [ ] Returns correct type/format
- [ ] Time complexity matches expected
- [ ] No infinite loops or stack overflow
- [ ] No off-by-one errors

---

## 🎓 Key Takeaways

1. **Read the TODO comments** - They guide implementation
2. **Understand before coding** - Visualize the algorithm
3. **Test edge cases** - Most bugs hide there
4. **Complexity matters** - O(n²) fails on large inputs
5. **Code clarity** - Document your logic
6. **Reuse patterns** - Queue for BFS, Stack for DFS
7. **Practice matters** - Implement multiple times
8. **Have fun!** - Learning algorithms is rewarding

---

Good luck! Remember: If you understand WHY, the HOW becomes obvious. 🚀

