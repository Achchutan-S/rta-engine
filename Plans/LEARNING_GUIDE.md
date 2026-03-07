## RTA Engine - Spring Boot Algorithms Learning Lab

This is your personal backend + algorithms engineering learning repository. The project is structured to be clean, scalable, and ready for you to implement algorithms and learn Spring Boot.

### 📁 Project Structure

```
com.rta.engine
├── algorithms/                    # Algorithm implementations
│   ├── sorting/                  # Sorting algorithms (TODO)
│   │   ├── BubbleSort.java      # O(n²) sorting
│   │   └── MergeSort.java       # O(n log n) sorting
│   ├── graphs/                   # Graph traversal (TODO)
│   │   ├── BFS.java             # Breadth-first search
│   │   └── DFS.java             # Depth-first search
│   ├── dp/                       # Dynamic programming (TODO)
│   │   └── FibonacciDP.java     # Fibonacci with DP approaches
│   ├── recursion/                # Recursion & backtracking (TODO)
│   │   └── NQueens.java         # N-Queens problem
│   ├── greedy/                   # Greedy algorithms (placeholder)
│   └── problems/                 # DSA practice problems (placeholder)
├── controller/                    # REST API endpoints
│   └── AlgorithmController.java  # Expose algorithms as APIs
├── service/                       # Business logic layer
│   └── AlgorithmService.java     # Service for algorithm operations
├── model/                         # Request/Response DTOs
│   ├── SortRequest.java
│   ├── SortResponse.java
│   ├── FibonacciRequest.java
│   └── FibonacciResponse.java
├── util/                          # Utility functions (TODO)
│   └── ArrayUtils.java           # Array helper methods
├── config/                        # Configuration
│   └── ApplicationConfiguration.java  # CORS & web config
└── RtaEngineApplication.java     # Spring Boot entry point
```

### 🎯 Your Learning Tasks

All algorithm implementations have TODO comments. Here's what you need to implement:

#### 1. **Sorting Algorithms** 
   - **BubbleSort.java**: Simple O(n²) sorting
     - Hint: Nested loops, compare adjacent elements, swap if needed
   - **MergeSort.java**: Efficient O(n log n) divide-and-conquer
     - Hint: Divide array, sort recursively, merge sorted halves

#### 2. **Graph Algorithms**
   - **BFS.java**: Level-by-level traversal using a Queue
     - Hint: Use LinkedList as queue, visited Set, explore neighbors
   - **DFS.java**: Deep traversal using a Stack (or recursion)
     - Hint: Multiple implementations - iterative (Stack) and recursive

#### 3. **Dynamic Programming**
   - **FibonacciDP.java**: Three approaches to calculate Fibonacci
     - Hint: Bottom-up DP, top-down (memoization), space-optimized

#### 4. **Recursion & Backtracking**
   - **NQueens.java**: Classic backtracking problem
     - Hint: Place queens row by row, check validity, backtrack

#### 5. **Utilities**
   - **ArrayUtils.java**: Helper methods for arrays
     - Generate random arrays, check if sorted, print arrays

### 🚀 Getting Started

1. **Start with Sorting**: BubbleSort is the easiest starting point
2. **Then Merge Sort**: Learn divide-and-conquer approach
3. **Move to Graphs**: BFS is simpler than DFS
4. **Learn DP**: Fibonacci shows DP patterns
5. **Master Recursion**: N-Queens ties it all together

### 🔗 REST API Endpoints

Once you implement the algorithms, test them via these endpoints:

```bash
# Health check
GET /algorithm/health

# Get algorithm info
GET /algorithm/info
GET /algorithm/about

# Sorting endpoints
POST /algorithm/sort/merge
POST /algorithm/sort/bubble
# Body: { "array": [3, 1, 4, 1, 5], "algorithm": "merge" }

# DP endpoints
GET /algorithm/dp/fibonacci/{n}
POST /algorithm/dp/fibonacci
# Body: { "n": 10 }

# Recursion endpoints
GET /algorithm/recursion/nqueens/{n}
# Example: GET /algorithm/recursion/nqueens/4
```

### 🏗️ Architecture

```
Request → Controller → Service → Algorithm Implementation
           (HTTP)      (Logic)     (Core Algorithm)
```

- **Controller**: Handles HTTP requests, validates input
- **Service**: Orchestrates algorithm calls, times execution
- **Algorithm**: Pure algorithm logic with clear intent

### 💡 Implementation Tips

1. **Read the JavaDoc**: Each method has detailed comments about what to implement
2. **Follow the TODO structure**: Comments guide implementation step-by-step
3. **Test locally**: Use the REST endpoints once implemented
4. **Time Complexity**: Comments show expected complexity
5. **Edge Cases**: Handle null/empty inputs

### 📚 Learning Resources Hints

- **Sorting**: Think about how elements move through the array
- **Graphs**: Queue (BFS) vs Stack (DFS) - that's the key difference
- **DP**: Break problem into subproblems and store results
- **Recursion**: Base case + recursive case = solution
- **Backtracking**: Try, explore, undo if needed

### ✅ Validation

Once you implement each algorithm:

1. Algorithm logic should compile without errors
2. REST endpoints should accept requests
3. Test with sample inputs
4. Verify output correctness

### 📝 Next Steps After Implementation

1. Add more algorithms (QuickSort, HeapSort, Dijkstra, etc.)
2. Create integration tests
3. Add logging and performance metrics
4. Build a frontend dashboard
5. Deploy to cloud

---

**Remember**: The goal is learning. Focus on understanding WHY each algorithm works, not just getting it to compile. Happy coding! 🎓

