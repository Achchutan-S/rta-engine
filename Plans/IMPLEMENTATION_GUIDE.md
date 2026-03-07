# Step-by-Step Implementation Guide

Follow this guide to implement each algorithm. Read carefully and implement one method at a time.

---

## Phase 1: Utilities (10-15 minutes)

### File: `ArrayUtils.java`
**Location**: `src/main/java/com/rta/engine/util/ArrayUtils.java`

#### Task 1: generateRandomArray(int size, int min, int max)
```
✅ TO DO:
1. Create a new int array of given size
2. Loop through each index i from 0 to size-1
3. Set arr[i] = min + random.nextInt(max - min)
4. Return arr

💡 HINT: random is already defined as a field
```

#### Task 2: isSorted(int[] arr)
```
✅ TO DO:
1. Handle null or array with length <= 1 (return true)
2. Loop from i = 1 to arr.length
3. If arr[i] < arr[i-1], return false
4. After loop completes, return true
```

#### Task 3: printArray(int[] arr)
```
✅ TO DO:
1. If arr is null, print "Array is null" and return
2. Print opening bracket: "["
3. Loop through array:
   - Print arr[i]
   - If not last element, print ", "
4. Print closing bracket and newline: "]\n"

💡 Example output: [5, 2, 8, 1, 9]
```

#### Task 4: copyArray(int[] arr)
```
✅ TO DO:
1. If arr is null, return null
2. Use arr.clone() to create a copy
3. Return the copy

💡 HINT: clone() creates a shallow copy of array
```

---

## Phase 2: Sorting (45-65 minutes)

### File 1: `BubbleSort.java`
**Location**: `src/main/java/com/rta/engine/algorithms/sorting/BubbleSort.java`

#### Task: sort(int[] arr)
```
✅ TO DO:
1. Handle null/empty case: if (arr == null || arr.length == 0) return;

2. Initialize: n = arr.length

3. Outer loop: for i = 0 to n-2:
     - Initialize: boolean swapped = false
     
4.   Inner loop: for j = 0 to n-1-i:
       - If arr[j] > arr[j+1]:
         * temp = arr[j]
         * arr[j] = arr[j+1]
         * arr[j+1] = temp
         * swapped = true
         
5.   After inner loop: if (!swapped) break;

LOGIC:
- Outer loop runs n-1 times (n passes)
- Inner loop shrinks each iteration (largest element "bubbles" to end)
- Optimization: if no swaps in a pass, array is sorted
- Early termination saves time for nearly-sorted arrays
```

### File 2: `MergeSort.java`
**Location**: `src/main/java/com/rta/engine/algorithms/sorting/MergeSort.java`

#### Task 1: sort(int[] arr) - Entry point
```
✅ TO DO:
1. Handle null/empty: if (arr == null || arr.length == 0) return;
2. Call mergeSort(arr, 0, arr.length - 1);

This method just initializes the recursive process.
```

#### Task 2: mergeSort(int[] arr, int left, int right) - Recursive divide
```
✅ TO DO:
1. Base case: if (left >= right) return;

2. Calculate mid: mid = left + (right - left) / 2;

3. Recursively sort left half: mergeSort(arr, left, mid);

4. Recursively sort right half: mergeSort(arr, mid + 1, right);

5. Merge: merge(arr, left, mid, right);

LOGIC:
- Divide: find middle point
- Conquer: recursively sort left and right
- Combine: merge the two sorted halves
```

#### Task 3: merge(int[] arr, int left, int mid, int right) - Merge sorted parts
```
✅ TO DO:
1. Create temporary arrays:
   int[] leftArr = new int[mid - left + 1];
   int[] rightArr = new int[right - mid];

2. Copy left part: for i = 0 to leftArr.length-1:
   leftArr[i] = arr[left + i];

3. Copy right part: for i = 0 to rightArr.length-1:
   rightArr[i] = arr[mid + 1 + i];

4. Merge back: initialize i = 0, j = 0, k = left

5. Compare and merge:
   while (i < leftArr.length && j < rightArr.length):
     if (leftArr[i] <= rightArr[j]):
       arr[k++] = leftArr[i++];
     else:
       arr[k++] = rightArr[j++];

6. Copy remaining left elements:
   while (i < leftArr.length):
     arr[k++] = leftArr[i++];

7. Copy remaining right elements:
   while (j < rightArr.length):
     arr[k++] = rightArr[j++];

LOGIC:
- Create temp arrays from subarrays
- Compare elements from left and right
- Place smaller element in main array
- Copy any remaining elements
```

---

## Phase 3: Graphs (65-80 minutes)

### File 1: `BFS.java`
**Location**: `src/main/java/com/rta/engine/algorithms/graphs/BFS.java`

#### Task 1: traverse(Map graph, int source) - Level-by-level traversal
```
✅ TO DO:
1. Create result List

2. Handle edge cases:
   if (graph == null || !graph.containsKey(source)) return result;

3. Create visited Set:
   Set<Integer> visited = new HashSet<>();

4. Create queue:
   Queue<Integer> queue = new LinkedList<>();

5. Initialize with source:
   queue.offer(source);
   visited.add(source);

6. Main loop: while (!queue.isEmpty()):
   int vertex = queue.poll();
   result.add(vertex);
   
7.   Get neighbors:
   List<Integer> neighbors = graph.get(vertex);
   
8.   Process neighbors:
   if (neighbors != null):
     for each neighbor in neighbors:
       if (!visited.contains(neighbor)):
         visited.add(neighbor);
         queue.offer(neighbor);

9. Return result;

LOGIC:
- Use queue (FIFO) for level-by-level exploration
- Mark as visited when ADDING to queue (not when processing)
- Explore all neighbors at current level before moving to next
```

#### Task 2: hasPath(Map graph, int source, int target)
```
✅ TO DO:
1. Base case: if (source == target) return true;

2. Create visited set and queue (same as traverse)

3. Initialize queue with source

4. Loop: while (!queue.isEmpty()):
   int vertex = queue.poll();
   
5.   Check if target found:
   if (vertex == target) return true;
   
6.   Process neighbors (same as traverse):
   for each unvisited neighbor:
     visited.add(neighbor);
     queue.offer(neighbor);

7. Loop ends without finding target: return false;

LOGIC:
- Early return when target is found
- Same BFS logic as traverse, but return immediately on match
```

### File 2: `DFS.java`
**Location**: `src/main/java/com/rta/engine/algorithms/graphs/DFS.java`

#### Task 1: traverse(Map graph, int source) - Iterative using Stack
```
✅ TO DO:
1. Create result List

2. Handle edge cases:
   if (graph == null || !graph.containsKey(source)) return result;

3. Create visited Set and stack:
   Set<Integer> visited = new HashSet<>();
   Stack<Integer> stack = new Stack<>();

4. Initialize with source:
   stack.push(source);

5. Main loop: while (!stack.isEmpty()):
   int vertex = stack.pop();
   
6.   Process only if not visited:
   if (!visited.contains(vertex)):
     visited.add(vertex);
     result.add(vertex);
     
7.     Get neighbors in REVERSE order:
     List<Integer> neighbors = graph.get(vertex);
     if (neighbors != null):
       for i = neighbors.size()-1 down to 0:
         int neighbor = neighbors.get(i);
         if (!visited.contains(neighbor)):
           stack.push(neighbor);

8. Return result;

LOGIC:
- Use stack (LIFO) to go deep
- Process when POPPING (not when pushing)
- Push neighbors in reverse for left-to-right traversal order
- Mark visited when processing (not when pushing)
```

#### Task 2: traverseRecursive(Map graph, int source)
```
✅ TO DO:
1. Create result List

2. Handle edge cases:
   if (graph == null || !graph.containsKey(source)) return result;

3. Create visited Set:
   Set<Integer> visited = new HashSet<>();

4. Call helper:
   dfsHelper(graph, source, visited, result);

5. Return result;

LOGIC:
- Initialize and delegate to recursive helper
```

#### Task 3: dfsHelper(Map graph, int vertex, Set visited, List result) - Recursive helper
```
✅ TO DO:
1. Mark as visited and add to result:
   visited.add(vertex);
   result.add(vertex);

2. Get neighbors:
   List<Integer> neighbors = graph.get(vertex);

3. Recursively visit unvisited neighbors:
   if (neighbors != null):
     for each neighbor in neighbors:
       if (!visited.contains(neighbor)):
         dfsHelper(graph, neighbor, visited, result);

LOGIC:
- Mark current as visited
- Recursively visit all unvisited neighbors
- Backtracking happens naturally with recursion
```

#### Task 4: hasCycle(Map graph)
```
✅ TO DO:
1. Create visited Set and recursion stack Set:
   Set<Integer> visited = new HashSet<>();
   Set<Integer> recursionStack = new HashSet<>();

2. For each vertex in graph:
   for each vertex in graph.keySet():
     if (!visited.contains(vertex)):
       if (hasCycleDFS(graph, vertex, visited, recursionStack)):
         return true;

3. No cycle found: return false;

LOGIC:
- Check each unvisited vertex
- If cycle found during DFS, return immediately
- Cycle detection via recursion stack
```

#### Task 5: hasCycleDFS(Map graph, int vertex, Set visited, Set recursionStack)
```
✅ TO DO:
1. Mark as visited and add to recursion stack:
   visited.add(vertex);
   recursionStack.add(vertex);

2. Get neighbors:
   List<Integer> neighbors = graph.get(vertex);

3. Check each neighbor:
   if (neighbors != null):
     for each neighbor in neighbors:
       if (!visited.contains(neighbor)):
         if (hasCycleDFS(graph, neighbor, visited, recursionStack)):
           return true;
       else if (recursionStack.contains(neighbor)):
         cycle found! return true;

4. Remove from recursion stack (backtrack):
   recursionStack.remove(vertex);

5. No cycle from this path: return false;

LOGIC:
- If neighbor not visited, explore it
- If neighbor in recursion stack, cycle exists (back edge)
- Remove from stack when backtracking (for other paths)
```

---

## Phase 4: Dynamic Programming (25-30 minutes)

### File: `FibonacciDP.java`
**Location**: `src/main/java/com/rta/engine/algorithms/dp/FibonacciDP.java`

#### Task 1: fibonacci(int n) - Bottom-up DP
```
✅ TO DO:
1. Handle base cases:
   if (n <= 0) return 0;
   if (n == 1) return 1;

2. Create DP array:
   long[] dp = new long[n + 1];

3. Initialize:
   dp[0] = 0;
   dp[1] = 1;

4. Fill array bottom-up:
   for (int i = 2; i <= n; i++):
     dp[i] = dp[i-1] + dp[i-2];

5. Return dp[n];

LOGIC:
- Build up from F(0) and F(1)
- Each value depends on previous two
- O(n) time, O(n) space
```

#### Task 2: fibonacciMemo(int n) - Top-down with memoization
```
✅ TO DO:
1. Create memo array:
   long[] memo = new long[n + 1];

2. Call helper and return:
   return fibonacciMemoHelper(n, memo);

LOGIC:
- Initialize memo array (all zeros initially)
- Delegate to recursive helper
```

#### Task 3: fibonacciMemoHelper(int n, long[] memo) - Recursive with caching
```
✅ TO DO:
1. Handle base cases:
   if (n <= 0) return 0;
   if (n == 1) return 1;

2. Check memo:
   if (memo[n] != 0) return memo[n];

3. Compute and store:
   memo[n] = fibonacciMemoHelper(n-1, memo) + fibonacciMemoHelper(n-2, memo);

4. Return memo[n];

LOGIC:
- If result cached, return it (avoid recomputation)
- Otherwise compute and cache before returning
- Recursive calls still made, but results reused
```

#### Task 4: fibonacciOptimized(int n) - Space-optimized to O(1)
```
✅ TO DO:
1. Handle base cases:
   if (n <= 0) return 0;
   if (n == 1) return 1;

2. Initialize two variables:
   long prev2 = 0;    // F(0)
   long prev1 = 1;    // F(1)
   long current;

3. Iterate from 2 to n:
   for (int i = 2; i <= n; i++):
     current = prev1 + prev2;
     prev2 = prev1;
     prev1 = current;

4. Return prev1;

LOGIC:
- Only need last two values at any time
- Shift values: prev2 ← prev1, prev1 ← current
- O(n) time, O(1) space (most efficient!)
```

---

## Phase 5: Recursion & Backtracking (45-60 minutes)

### File: `NQueens.java`
**Location**: `src/main/java/com/rta/engine/algorithms/recursion/NQueens.java`

#### Task 1: solveNQueens(int n) - Main solver
```
✅ TO DO:
1. Create results list:
   List<List<String>> results = new ArrayList<>();

2. Initialize board (n×n):
   char[][] board = new char[n][n];

3. Fill board with empty cells:
   for (int i = 0; i < n; i++):
     for (int j = 0; j < n; j++):
       board[i][j] = '.';

4. Start backtracking from row 0:
   backtrack(board, 0, results, n);

5. Return results;

LOGIC:
- Create empty board
- Place queens row by row using backtracking
```

#### Task 2: backtrack(char[][] board, int row, List results, int n)
```
✅ TO DO:
1. Base case (all rows processed):
   if (row == n):
     results.add(buildSolution(board));
     return;

2. Try each column in current row:
   for (int col = 0; col < n; col++):
     
3.   Check if position is valid:
     if (isValid(board, row, col, n)):
       
4.     Place queen:
       board[row][col] = 'Q';
       
5.     Recursively place remaining queens:
       backtrack(board, row + 1, results, n);
       
6.     Backtrack (remove queen):
       board[row][col] = '.';

LOGIC:
- One queen per row
- Try each column
- If valid, place and recurse
- Backtrack by removing queen
- This ensures all solutions are found
```

#### Task 3: isValid(char[][] board, int row, int col, int n)
```
✅ TO DO:
1. Check column (no queen above):
   for (int i = 0; i < row; i++):
     if (board[i][col] == 'Q'):
       return false;

2. Check upper-left diagonal:
   for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--):
     if (board[i][j] == 'Q'):
       return false;

3. Check upper-right diagonal:
   for (int i = row-1, j = col+1; i >= 0 && j < n; i--, j++):
     if (board[i][j] == 'Q'):
       return false;

4. Position is safe: return true;

LOGIC:
- Queen attacks column below (we go up to check)
- Queen attacks diagonals (both directions)
- Only need to check up (rows below current aren't filled yet)
```

#### Task 4: buildSolution(char[][] board)
```
✅ TO DO:
1. Create solution list:
   List<String> solution = new ArrayList<>();

2. Convert each row to String:
   for (char[] row : board):
     solution.add(new String(row));

3. Return solution;

LOGIC:
- Convert 2D char array to list of strings
- Each row becomes a string representation
```

#### Task 5: countSolutions(int n)
```
✅ TO DO:
1. Create empty board:
   char[][] board = new char[n][n];

2. Call helper from row 0:
   return countSolutionsHelper(board, 0, n);

LOGIC:
- Initialize board and delegate to counter helper
```

#### Task 6: countSolutionsHelper(char[][] board, int row, int n)
```
✅ TO DO:
1. Base case (all rows placed):
   if (row == n):
     return 1;    // Found one solution

2. Initialize count:
   int count = 0;

3. Try each column:
   for (int col = 0; col < n; col++):
     
4.   If position is valid:
     if (isValid(board, row, col, n)):
       
5.     Place queen:
       board[row][col] = 'Q';
       
6.     Count solutions from this position:
       count += countSolutionsHelper(board, row + 1, n);
       
7.     Backtrack:
       board[row][col] = '.';

8. Return count;

LOGIC:
- Count all valid solutions instead of storing boards
- Same backtracking pattern as solveNQueens
- Sum solutions from all branches
```

---

## ✅ Verification

After implementing each method:

1. **Check Syntax**: File should have no red errors
2. **Understand Logic**: Could you explain it to someone?
3. **Test Basic Cases**: Use small examples (n=2, n=3, etc.)
4. **Test Edge Cases**: Empty, null, single element
5. **Run Application**: `./mvnw spring-boot:run`
6. **Test Endpoint**: Use curl or Postman
7. **Check Output**: Does it look correct?

---

## 🎯 You're Ready!

Start with Phase 1 (Utilities), then move through each phase. Take your time, understand the logic, and test each implementation.

Good luck! 🚀

