package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatCafe implements Iterable<Cat> {
    public CatNode root;
    
    public CatCafe() {
    }
    
    
    public CatCafe(CatNode dNode) {
        this.root = dNode;
    }
    
    // Constructor that makes a shallow copy of a CatCafe
    // New CatNode objects, but same Cat objects
    public CatCafe(CatCafe cafe) {
        
        for (var cat: cafe) {
            hire(cat);
        }
        
    }
    
    
    // add a cat to the cafe database
    
    public void hire(Cat c) {
        if (root == null)
            root = new CatNode(c);
        else
            root = root.hire(c);
    }
    
    // removes a specific cat from the cafe database
    public void retire(Cat c) {
        if (root != null)
            root = root.retire(c);
    }
    
    // get the oldest hire in the cafe
    public Cat findMostSenior() {
        if (root == null)
            return null;
        
        return root.findMostSenior();
    }
    
    // get the newest hire in the cafe
    public Cat findMostJunior() {
        if (root == null)
            return null;
        
        return root.findMostJunior();
    }
    
    // returns a list of cats containing the top numOfCatsToHonor cats
    // in the cafe with the thickest fur. Cats are sorted in descending
    // order based on their fur thickness.
    public ArrayList<Cat> buildHallOfFame(int numOfCatsToHonor) {
        System.out.println("building hall of fame....");
        ArrayList<Cat> arr = new ArrayList<Cat>();
        
        // root has thickest fur
        CatNode cur = this.root;
        if (cur != null) {
            arr.add(cur.catEmployee);
            
            for (int i = 1; i < numOfCatsToHonor; i++) {
                // if no more children, go back up and try parent's other child
                if (cur.senior == null && cur.junior == null) {
                    if (cur.parent.senior == cur && cur.parent.junior != null &&
                            cur.parent.junior.catEmployee.getFurThickness() < cur.catEmployee.getFurThickness()) {
                        
                        cur = cur.parent.junior;
                        
                    } else if (cur.parent.junior == cur && cur.parent.senior != null &&
                            cur.parent.senior.catEmployee.getFurThickness() < cur.catEmployee.getFurThickness()) {
                        
                        cur = cur.parent.senior;
                    }
                }
                
                // normal conditions
                else if (cur.senior != null && cur.junior != null && cur.senior.catEmployee.getFurThickness() >
                        cur.junior.catEmployee.getFurThickness()) {
                    cur = cur.senior;
                } else if (cur.junior != null && cur.senior != null && cur.junior.catEmployee.getFurThickness() >
                        cur.senior.catEmployee.getFurThickness()) {
                    cur = cur.junior;
                } else if (cur.senior != null && cur.junior == null) {
                    cur = cur.senior;
                } else if (cur.junior != null && cur.senior == null) {
                    cur = cur.junior;
                }
                
                // if repeating the same cat
                if (arr.get(i - 1) == cur.catEmployee)
                    break;
                
                arr.add(cur.catEmployee);
                
            }
        }
        return arr;
    }
    
    // Returns the expected grooming cost the cafe has to incur in the next numDays days
    public double budgetGroomingExpenses(int numDays) {
        int numWeeks = getWeek(numDays);
        int i = 0;
        double cost = 0;
        ArrayList<ArrayList<Cat>> arr = getGroomingSchedule();
        
        for (var arrlist: arr) {
            if (i > numWeeks) {
                break;
            }
            for (var cat: arrlist) {
                if (cat.getDaysToNextGrooming() <= numDays) {
                    cost += cat.getExpectedGroomingCost();
                }
            }
            i++;
        }
        
        return cost;
    }
    
    private int getWeek(int i) {
        if (i % 7 == 0) {
            return i;
        }
        else
            return getWeek(i-1);
    }
    
    // returns a list of list of Cats.
    // The cats in the list at index 0 need be groomed in the next week.
    // The cats in the list at index i need to be groomed in i weeks.
    // Cats in each sublist are listed in from most senior to most junior.
    
    /*
    private ArrayList<ArrayList<Cat>> resize(ArrayList<ArrayList<Cat>> arr) {
        ArrayList<ArrayList<Cat>> bigger = new ArrayList<ArrayList<Cat>>(arr.size()*2);
        for (int i = 0; i < arr.size(); i++) {
            bigger.add(arr.get(i));
        }
        return bigger;
    }
    */
    public ArrayList<ArrayList<Cat>> getGroomingSchedule() {
        ArrayList<ArrayList<Cat>> arr = new ArrayList<>();
        
        int index;
        System.out.println(" ");
        
        for (var cat: this) {
            System.out.println(cat.name);
            
            index = getWeek(cat.getDaysToNextGrooming()) / 7; // get a multiple of 7
            
            System.out.println("index is " + index);
            System.out.println("size is " + arr.size());
            
            while (index >= arr.size()) {
                ArrayList<Cat> empty = new ArrayList<>();
                arr.add(empty);
            }
            arr.get(index).add(cat);
        }
        
        System.out.println(arr);
        return arr;
    }
    
    
    public Iterator<Cat> iterator() {
        return new CatCafeIterator(this.root);
    }
    
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public class CatNode {
        public Cat catEmployee;
        public CatNode junior;
        public CatNode senior;
        public CatNode parent;
        
        public CatNode(Cat c) {
            this.catEmployee = c;
            this.junior = null;
            this.senior = null;
            this.parent = null;
        }
        
        private void rotation (CatNode p) {
            if (p.senior != null && p.senior.catEmployee.getFurThickness() > p.catEmployee.getFurThickness()) {
                leftRotation(p);
            }
            else if (p.junior != null && p.junior.catEmployee.getFurThickness() > p.catEmployee.getFurThickness()) {
                rightRotation(p);
            }
        }
        
        private void rightRotation (CatNode p){
            System.out.println("right rotation....");
            System.out.println("root is " + root.catEmployee.name);
            if (p.junior != null) {
                CatNode pnode = p;
                CatNode L = p.junior;
                CatNode Pparent = p.parent;
                
                p.junior = p.junior.senior; // P.senior = B
                
                L.senior = p;
                p.parent = L;
                L.parent = Pparent;
                
                // updating p.parent node
                if (Pparent != null) {
                    // finding if p is senior or junior by checking memory location
                    if (Pparent.junior == p) {
                        Pparent.junior = L;
                    }
                    else if (Pparent.senior == p) {
                        Pparent.senior = L;
                    }
                    
                    if (Pparent.catEmployee.getFurThickness() < L.catEmployee.getFurThickness()) {
                        System.out.println("root is " + root.catEmployee.name);
                        printTree(root, 2);
                        rotation(Pparent);
                    }
                    
                }
                else if (pnode == root) {
                    root = L;
                }
            }
            System.out.println("root is " + root.catEmployee.name);
        }
        private void leftRotation (CatNode p) {
            System.out.println("left rotation....");
            System.out.println("root is " + root.catEmployee.name);
            if (p.senior != null) {
                CatNode pnode = p;
                CatNode R = p.senior;
                CatNode Pparent = p.parent;
                
                p.senior = p.senior.junior; // P.senior = A
                
                R.junior = p; // R.junior = P
                p.parent = R; // P.parent = R
                R.parent = Pparent; // R.parent = P.parent
                
                // updating p.parent node
                if (Pparent != null) {
                    // finding if p is senior or junior by checking memory location
                    if (Pparent.junior == p) {
                        Pparent.junior = R;
                    }
                    else if (Pparent.senior == p) {
                        Pparent.senior = R;
                    }
                    if (Pparent.catEmployee.getFurThickness() < R.catEmployee.getFurThickness()) {
                        System.out.println("root is " + root.catEmployee.name);
                        printTree(root, 1);
                        rotation(Pparent);
                    }
                }
                
                else if (pnode == root) {
                    root = R;
                }
                
            }
            
        }
        
        // adds the c to the tree rooted at this and returns the root of the resulting tree
        public CatNode hire (Cat c) {
            System.out.println("\n-------------------------------------------------");
            System.out.println("hiring " + c.name + "...");
            
            int month = c.getMonthHired();
            int fur = c.getFurThickness();
            
            CatNode node = new CatNode(c);
            
            CatNode cur = root;
            CatNode prev = null;
            
            // binary search tree property
            while (cur != null) {
                if (cur.catEmployee.getMonthHired() < month) {
                    prev = cur;
                    cur = cur.junior;
                }
                else if (cur.catEmployee.getMonthHired() > month) {
                    prev = cur;
                    cur = cur.senior;
                }
            }
            
            if (prev != null && prev.catEmployee.getMonthHired() < month) {
                prev.junior = node;
                node.parent = prev;
            }
            
            else if (prev != null && prev.catEmployee.getMonthHired() > month) {
                prev.senior = node;
                node.parent = prev;
            }
            
            // once found place, check for rotations
            if (node.parent != null && node.parent.catEmployee.getFurThickness() < fur) {
                System.out.println(node.parent.catEmployee.getFurThickness() + "<" + fur);
                rotation(node.parent);
            }
            printTree(root, 2);
            return root;
        }
        // remove c from the tree rooted at this and returns the root of the resulting tree
        public CatNode retire(Cat c) {
            
            int month = c.getMonthHired();
            
            CatNode curr = root;
            CatNode prev = null;
            
            if (c == root.catEmployee) {
                if (root.senior != null) {
                    root.senior.parent = null;
                    root = root.senior;
                    
                }
                else if (root.junior != null) {
                    root.junior.parent = null;
                    root = root.junior;
                }
                return root;
            }
            
            // binary search tree property
            while (curr != null) {
                if (curr.catEmployee.compareTo(c) == 0) {
                    break;
                }
                if (curr.catEmployee.getMonthHired() < month) {
                    prev = curr;
                    curr = curr.junior;
                }
                else if (curr.catEmployee.getMonthHired() > month) {
                    prev = curr;
                    curr = curr.senior;
                }
            }
            
            if (curr.senior == null && curr.junior == null) {
                if (prev.senior == curr) {
                    prev.senior = null;
                }
                else if (prev.junior == curr) {
                    prev.junior = null;
                }
                curr.parent = null;
            }
            else if (curr.senior == null && curr.junior != null) {
                if (prev.senior == curr) {
                    prev.senior = curr.junior;
                } else if (prev.junior == curr) {
                    prev.junior = curr.junior;
                }
                curr.junior.parent = prev;
                curr.parent = null;
                curr.junior = null;
                if (prev.senior != null && prev.catEmployee.getFurThickness() < prev.senior.catEmployee.getFurThickness()) {
                    rotation(prev);
                }
                if (prev.junior != null && prev.catEmployee.getFurThickness() < prev.junior.catEmployee.getFurThickness()) {
                    rotation(prev);
                }
                
            }
            else if (curr.senior != null && curr.junior == null) {
                if (prev.senior == curr) {
                    prev.senior = curr.senior;
                } else if (prev.junior == curr) {
                    prev.junior = curr.senior;
                }
                curr.senior.parent = prev;
                curr.parent = null;
                curr.senior = null;
                if (prev.senior != null && prev.catEmployee.getFurThickness() < prev.senior.catEmployee.getFurThickness()) {
                    rotation(prev);
                }
                if (prev.junior != null && prev.catEmployee.getFurThickness() < prev.junior.catEmployee.getFurThickness()) {
                    rotation(prev);
                }
            }
            else if (curr.senior != null && curr.junior != null) {
                CatNode current = curr.junior;
                CatNode seniorC = null;
                
                while (current != null) {
                    seniorC = current;
                    current = current.senior;
                }
                
                if (prev.senior == curr) {
                    prev.senior = seniorC;
                } else if (prev.junior == curr) {
                    prev.junior = seniorC;
                }
                seniorC.parent = prev;
                curr.parent = null;
                curr.junior = null;
                curr.senior = null;
                
                if (prev.catEmployee.getFurThickness() < seniorC.catEmployee.getFurThickness()) {
                    rotation(prev);
                }
            }
            
            System.out.println("\nretiring " + c.name + "...");
            printTree(root,2);
            return root;
            
        }
        
        // find the cat with highest seniority in the tree rooted at this
        public Cat findMostSenior() {
            CatNode cur = this;
            
            while (cur.senior != null)
                cur = cur.senior;
            
            return cur.catEmployee;
        }
        
        // find the cat with lowest seniority in the tree rooted at this
        public Cat findMostJunior() {
            CatNode cur = this;
            
            while (cur.junior != null)
                cur = cur.junior;
            
            return cur.catEmployee;
        }
        
        // Feel free to modify the toString() method if you'd like to see something else displayed.
        public String toString() {
            String result = this.catEmployee.toString() + "\n";
            if (this.junior != null) {
                result += "junior than " + this.catEmployee.toString() + " :\n";
                result += this.junior.toString();
            }
            if (this.senior != null) {
                result += "senior than " + this.catEmployee.toString() + " :\n";
                result += this.senior.toString();
            } /*
			if (this.parent != null) {
				result += "parent of " + this.catEmployee.toString() + " :\n";
				result += this.parent.catEmployee.toString() +"\n";
			}*/
            return result;
        }
    }
    
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class CatCafeIterator implements Iterator<Cat> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
        private ArrayList<CatNode> arr;
        private int indexOfNext;
        
        private CatCafeIterator(CatNode root) {
            this.indexOfNext = 1;
            this.arr = new ArrayList<CatNode>();
            
            if (root != null) {
                arr.add(root);
                helper(root);
            }
        }
        
        private void helper (CatNode node) {
            
            if (node.junior != null) {
                helper(node.junior);
            }
            arr.add(node);
            if (node.senior != null) {
                helper(node.senior);
            }
        }
        // getter
        public Cat next(){
            if (this.indexOfNext > arr.size()) {
                throw new NoSuchElementException("Error: There are no more elements to iterate on.");
            }
            Cat cat = arr.get(indexOfNext).catEmployee;
            this.indexOfNext++;
            return cat;
        }
        
        public boolean hasNext() {
            return !(this.indexOfNext >= arr.size());
        }
        
    }
    
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private static void printTree(CatNode root, int spaceCount)
    {
        if(root==null)
            return;
        
        int spacing = spaceCount+24;
        
        printTree(root.senior, spacing);
        
        System.out.println();
        for(int index=0; index < spacing+root.catEmployee.toString().length()+1; index++) {
            System.out.print(" ");
        }
        if (root.senior != null) {
            System.out.print("/");
            System.out.println();
            for(int index=0; index < spacing+root.catEmployee.toString().length(); index++) {
                System.out.print(" ");
            }
            if (root.senior != null) {
                System.out.print("/");
            }
        }
        
        System.out.println();
        for(int index=0; index < spacing; index++) {
            System.out.print(" ");
        }
        System.out.println(root.catEmployee);
        
        for(int index=0; index < spacing+root.catEmployee.toString().length(); index++) {
            System.out.print(" ");
        }
        if (root.junior != null) {
            System.out.print("\\");
            System.out.println();
            for(int index=0; index < spacing+root.catEmployee.toString().length()+1; index++) {
                System.out.print(" ");
            }
            if (root.junior != null) {
                System.out.print("\\");
            }
        }
        printTree(root.junior, spacing);
    }
    
    public static void main(String[] args) {
        Cat A = new Cat("a",  3,1, 5, 85.0);
        Cat B = new Cat("b", 13,2, 9, 20.0);
        Cat C = new Cat("c",  7,3, 2, 250.0);
        Cat D = new Cat("d",  21, 4,2, 250.0);
        Cat E = new Cat("e",  12,5, 2, 250.0);
        Cat F = new Cat("f",  16,6, 2, 250.0);
        Cat G = new Cat("g",  9,7, 2, 250.0);
        
        
        CatCafe catCafe = new CatCafe();
        catCafe.hire(A);
        catCafe.hire(B);
        catCafe.hire(C);
        catCafe.hire(D);
        catCafe.hire(E);
        catCafe.hire(F);
        catCafe.hire(G);
        
    }
    
}


