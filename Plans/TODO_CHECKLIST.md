# TODO Implementation Checklist

This file tracks which algorithms and utilities you need to implement.

## ✅ Sorting Algorithms

### BubbleSort.java
- [ ] Implement `sort(int[] arr)` method
  - Handle null/empty cases
  - Use nested loops for element comparison
  - Implement swapping logic
  - Add optimization with swapped flag

**Estimated Time**: 15-20 minutes

### MergeSort.java
- [ ] Implement `sort(int[] arr)` method - entry point
- [ ] Implement `mergeSort(int[] arr, int left, int right)` - recursive method
- [ ] Implement `merge(int[] arr, int left, int mid, int right)` - merge logic

**Estimated Time**: 30-45 minutes

---

## ✅ Graph Algorithms

### BFS.java
- [ ] Implement `traverse(Map, int)` method
  - Use Queue for level-by-level traversal
  - Maintain visited set
  - Return vertices in BFS order
- [ ] Implement `hasPath(Map, int, int)` method
  - Return true if path exists between two vertices

**Estimated Time**: 25-30 minutes

### DFS.java
- [ ] Implement `traverse(Map, int)` - iterative approach using Stack
- [ ] Implement `traverseRecursive(Map, int)` - recursive approach
- [ ] Implement `dfsHelper(Map, int, Set, List)` - recursive helper
- [ ] Implement `hasCycle(Map)` - cycle detection
- [ ] Implement `hasCycleDFS(...)` - cycle detection helper

**Estimated Time**: 40-50 minutes

---

## ✅ Dynamic Programming

### FibonacciDP.java
- [ ] Implement `fibonacci(int n)` - bottom-up approach
- [ ] Implement `fibonacciMemo(int n)` - top-down memoization
- [ ] Implement `fibonacciMemoHelper(int, long[])` - recursive helper
- [ ] Implement `fibonacciOptimized(int n)` - space-optimized approach

**Estimated Time**: 25-30 minutes

---

## ✅ Recursion & Backtracking

### NQueens.java
- [ ] Implement `solveNQueens(int n)` - main solver
  - Initialize board
  - Call backtrack from row 0
- [ ] Implement `backtrack(...)` - core backtracking logic
  - Base case: all queens placed
  - Try each column
  - Check validity, place, recurse, backtrack
- [ ] Implement `isValid(...)` - check if position is safe
  - Check column
  - Check upper-left diagonal
  - Check upper-right diagonal
- [ ] Implement `buildSolution(char[][])` - convert board to strings
- [ ] Implement `countSolutions(int n)` - count total solutions
- [ ] Implement `countSolutionsHelper(...)` - recursive counter

**Estimated Time**: 45-60 minutes

---

## ✅ Utility Functions

### ArrayUtils.java
- [ ] Implement `generateRandomArray(int size, int min, int max)`
  - Create array of given size
  - Fill with random values in range
- [ ] Implement `isSorted(int[] arr)`
  - Check if array is sorted ascending
  - Handle null/empty cases
- [ ] Implement `printArray(int[] arr)`
  - Print in format: [elem1, elem2, elem3, ...]
  - Handle null case
- [ ] Implement `copyArray(int[] arr)`
  - Return a deep copy of array

**Estimated Time**: 10-15 minutes

---

## 📊 Summary

| Category | Files | Methods | Est. Time |
|----------|-------|---------|-----------|
| Sorting | 2 | 4 | 45-65 min |
| Graphs | 2 | 8 | 65-80 min |
| DP | 1 | 4 | 25-30 min |
| Recursion | 1 | 6 | 45-60 min |
| Utilities | 1 | 5 | 10-15 min |
| **TOTAL** | **7** | **27** | **190-250 min** |

---

## 🎯 Recommended Implementation Order

1. **Start Easy**: ArrayUtils (10-15 min) - Get comfortable with the codebase
2. **Then Sorting**: BubbleSort (15-20 min) → MergeSort (30-45 min)
3. **Next Graphs**: BFS (25-30 min) → DFS (40-50 min)
4. **Then DP**: FibonacciDP (25-30 min)
5. **Finally Complex**: NQueens (45-60 min)

---

## 🧪 Testing Your Implementation

### Compile Check
```bash
cd /Users/achchutans/Downloads/rta-engine
./mvnw clean compile
```

### Run Tests (if any)
```bash
./mvnw test
```

### Start Application
```bash
./mvnw spring-boot:run
```

### Test Endpoints
```bash
# Health check
curl http://localhost:8080/algorithm/health

# Sort
curl -X POST http://localhost:8080/algorithm/sort/merge \
  -H "Content-Type: application/json" \
  -d '{"array":[3,1,4,1,5,9]}'

# Fibonacci
curl http://localhost:8080/algorithm/dp/fibonacci/10

# N-Queens
curl http://localhost:8080/algorithm/recursion/nqueens/4
```

---

## 💡 Learning Resources

- **Sorting**: Visualize at https://www.visualgo.net/sorting
- **Graphs**: https://www.visualgo.net/graphtraversal
- **DP**: Think of breaking problems into overlapping subproblems
- **Backtracking**: Understand the try-explore-undo pattern

---

## ✨ After Implementation

Once you complete all TODOs:
1. ✅ Project compiles cleanly
2. ✅ All endpoints work
3. ✅ Add more algorithms (QuickSort, Dijkstra, etc.)
4. ✅ Write unit tests
5. ✅ Document learnings
6. ✅ Share with others!

---

Last Updated: March 7, 2026
Good luck with your learning journey! 🚀

