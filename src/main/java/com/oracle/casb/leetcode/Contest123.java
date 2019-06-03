    package com.oracle.casb.leetcode;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Stack;
    import java.util.stream.Collectors;

    public class Contest123 {

        public static void main(String[] args) {
            Contest123 ct = new Contest123();
            //ct.testTraversal();
           /* int N = 16;
            int x = (N==0) ? 1 : (~(N<<(N=Integer.numberOfLeadingZeros(N))))>>N;
            int num = 16;
            System.out.println("Leading: " + Integer.numberOfLeadingZeros(num) + ", Trailing: " + Integer.numberOfTrailingZeros(num) );*/
            //ct.testCousin();
            ct.testMinSize();
        }

        private void testMinSize() {
            int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            int res = shipWithinDays(weights, 5);
        }

        private void testCousin() {
            TreeNode root = createTree1();
            System.out.println(isCousins(root, 2, 3));
        }

        public int canCompleteCircuit(int[] gas, int[] cost) {
            int startingPos = 0;
            int totalCost = 0;
            int currentCost = 0;
            for (int i = 0; i < gas.length; i++) {
                currentCost = (gas[i] - cost[i]);
                totalCost += currentCost;
                if (currentCost < 0) {
                    currentCost = 0;
                    startingPos = i + 1;
                }
            }
            return startingPos;
        }

        public boolean canPartition(int[] nums) {
            int sum = 0;

            for (int num : nums) {
                sum += num;
            }

            if ((sum & 1) == 1) {
                return false;
            }
            sum /= 2;

            int n = nums.length;
            boolean[] dp = new boolean[sum+1];
            Arrays.fill(dp, false);
            dp[0] = true;

            for (int num : nums) {
                for (int i = sum; i > 0; i--) {
                    if (i >= num) {
                        dp[i] = dp[i] || dp[i-num];
                    }
                }
            }

            return dp[sum];
        }

        private boolean canBePartitioned(int[] nums) {


        int sum = 0;

        for(
        int num :nums)

        {
            sum += num;
        }

        if((sum &1)==1)

        {
            return false;
        }

        sum /=2;

        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];
        for(
        int i = 0;
        i<dp.length;i++)

        {
            Arrays.fill(dp[i], false);
        }

        dp[0][0]=true;

        for(
        int i = 1;
        i<n+1;i++)

        {
            dp[i][0] = true;
        }
        for(
        int j = 1;
        j<sum+1;j++)

        {
            dp[0][j] = false;
        }

        for(
        int i = 1;
        i<n+1;i++)

        {
            for (int j = 1; j < sum + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    dp[i][j] = (dp[i][j] || dp[i - 1][j - nums[i - 1]]);
                }
            }
        }

        return dp[n][sum];
    }

        public int shipWithinDays(int[] weights, int D) {
            int hi = 0;
            int lo = Integer.MIN_VALUE;
            for (int weight :  weights) {
                hi += weight;
                lo = Math.max(lo, weight);
            }
            while (lo < hi) {
                int mid = lo + (hi - lo)/2;
                if (canBeShippedInDays(weights, mid, D)) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }

        private boolean canBeShippedInDays(int[] weights, int maxCapacity, int days) {
            int required = 1;
            int currentCapacity = 0;
            for (int currWeight : weights) {
                if ((currentCapacity + currWeight) <= maxCapacity) {
                    currentCapacity += currWeight;
                } else {
                    currentCapacity = currWeight;
                    required += 1;
                }
            }
            boolean cudBeShipped = false;
            if (required <= days) {
                cudBeShipped = true;
            }
            return cudBeShipped;
        }
        private void testTraversal() {
            TreeNode root = createTree();
            List<List<Integer>> res = levelOrder(root);
            for (List<Integer> level : res) {
                level.stream().map(String::valueOf).collect(Collectors.joining(","));
            }
        }



        int  i = 0;
        public TreeNode bstFromPreorder(int[] preorder) {
            return bstFromPreorder(preorder, Integer.MAX_VALUE);
        }

        public TreeNode bstFromPreorder(int[] preorder, int bound) {
            if (i == preorder.length || preorder[i] > bound) {
                return null;
            }
            TreeNode root = new TreeNode(preorder[i++]);
            root.left = bstFromPreorder(preorder, root.val);
            root.right = bstFromPreorder(preorder, bound);
            return root;
        }

        public boolean isCousins(TreeNode root, int x, int y) {
            if (root == null) {
                return false;
            }
            List<TreeNode> parent = new ArrayList();
            List<TreeNode> child = new ArrayList();
            parent.add(root);
            while(!parent.isEmpty()) {
                //Set<Integer> label = new ArrayList();
                boolean gotAny = false;
                for (TreeNode node : parent) {
                    if (node.val == x || node.val == y) {
                        if (gotAny) {
                            return true;
                        } else {
                            gotAny = true;
                        }
                    }
                    if (node.left != null) child.add(node.left);
                    if (node.right != null) child.add(node.right);
                }
                if (gotAny) {
                    break;
                }
                parent = child;
                child = new ArrayList<>();
            }
            return false;
        }

        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>>  res = new ArrayList();
            if (root == null) {
                return res;
            }
            List<TreeNode> parent = new ArrayList();
            List<TreeNode> child = new ArrayList();
            parent.add(root);
            while(!parent.isEmpty()) {
                List<Integer> label = new ArrayList();
                for (TreeNode node : parent) {
                    label.add(node.val);
                    if (node.left != null) child.add(node.left);
                    if (node.right != null) child.add(node.right);
                }
                res.add(label);
                parent = child;
                child = new ArrayList<>();
            }
            return res;
        }

        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            if (n * k == 0) return new int[0];
            if (k == 1) return nums;

            int[] left  = new int[n];
            left[0] = nums[0];
            int[] right  = new int[n];
            right[n-1] = nums[n-1];

            for (int i = 1; i < n; i++) {
                if (i % k == 0) {//Block Start
                    left[i] = nums[i];
                } else {
                    left[i] = Integer.max(left[i-1], nums[i]);
                }

                int j = n - i - 1;

                if ((j+1) % k == 0) {
                    right[j] = nums[j];
                } else {
                    right[j] = Integer.max(right[j+1], nums[j]);
                }
            }
            int[] res = new int[n - k +1];
            for (int i = 0; i < n-k+1; i++) {
                res[i] = Math.max(right[i], left[i + k -1]);   //max(right[i], left[j]);
            }

            return res;
        }


        public int maximalRectangle(char[][] matrix) {

            if (matrix.length == 0) return 0;
            int maxarea = 0;
            int[] dp = new int[matrix[0].length];

            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {

                    // update the state of this row's histogram using the last row's histogram
                    // by keeping track of the number of consecutive ones

                    dp[j] = matrix[i][j] == '1' ? dp[j] + 1 : 0;
                }
                // update maxarea with the maximum area from this row's histogram
                maxarea = Math.max(maxarea, leetcode84(dp));
            } return maxarea;
        }

        public int leetcode84(int[] heights) {
            Stack< Integer > stack = new Stack < > ();
            stack.push(-1);
            int maxarea = 0;
            for (int i = 0; i < heights.length; ++i) {
                while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                    maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
                stack.push(i);
            }
            while (stack.peek() != -1)
                maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() -1));
            return maxarea;
        }
        public int getMostWork(int[] cabinets, int empCount) {
            int hi = 0;
            int lo = Integer.MIN_VALUE;
            for (int current_cab_docs : cabinets) {
                hi += current_cab_docs;
                lo = Integer.max(lo, current_cab_docs);
            }

            while (lo < hi) {
                int x = lo + (hi - lo)/2;
                if (canAccomodateDocuments(cabinets, x, empCount)) {
                    hi = x;
                } else {
                    lo = x + 1;
                }
            }
            return lo;
        }
        private boolean canAccomodateDocuments(int[] cabinets, int docCount, int empCount) {
            int required = 1;
            int currentLoad = 0;
            for (int currentCabinet : cabinets) {
                if (currentLoad + currentCabinet < docCount) {
                    currentLoad += currentCabinet;
                } else {
                    required += 1;
                    currentLoad = 0;
                }
            }
            boolean cudAccomodate = false;
            if (required <= empCount) {
                cudAccomodate = true;
            }
            return cudAccomodate;
        }

        private TreeNode createTree1() {
            TreeNode root = new TreeNode(1);
            TreeNode left = new TreeNode(2);
            left.right = new TreeNode(4);
            root.left = left;
            root.right = new TreeNode(3);
            return root;
        }

        private TreeNode createTree() {
            TreeNode root = new TreeNode(3);
            root.left = new TreeNode(9);
            TreeNode right = new TreeNode(20);
            right.left = new TreeNode(15);
            right.right = new TreeNode(7);
            root.right = right;
            return root;
        }

        private void connectLeft(TreeNode root) {
            if (root == null) {
                return;
            }
            if (root.left != null) {
                root.left.next = root.right;
            }
            if (root.next != null && root.right != null) {
                root.right.next = root.next.left;
            }
            connectLeft(root.left);
            connectLeft(root.right);
        }
        /**
         * Definition for a binary tree node.
         */
        public class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode next;
              TreeNode(int x) { val = x; }
        }



    }
