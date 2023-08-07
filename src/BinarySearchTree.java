import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BinarySearchTree<Key extends Comparable<Key>, Value> implements Iterable<BinarySearchTree.Entry<Key, Value>> {

    private Node root;
    private int size;

    private static class Node<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value value;
        private Node<Key, Value> left, right;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node<Key, Value> put(Node<Key, Value> node, Key key, Value value) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left, key, value);
        else if (cmp > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        return node;
    }

    public int size() {
        return size;
    }


    public Value find(Key key) {
        return (Value) find(root, key);
    }

    private Value find(Node<Key, Value> node, Key key) {
        if (node == null)
            return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            return find(node.left, key);
        else if (cmp > 0)
            return find(node.right, key);
        else
            return node.value;
    }

    @Override
    public Iterator<Entry<Key, Value>> iterator() {
        return new InOrderIterator();
    }

    public static class Entry<Key, Value> {
        public Key key;
        public Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private class InOrderIterator implements Iterator<Entry<Key, Value>> {
        private Node<Key, Value> current;
        private java.util.Stack<Node<Key, Value>> stack;

        public InOrderIterator() {
            stack = new java.util.Stack<>();
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public Entry<Key, Value> next() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            if (stack.isEmpty())
                throw new NoSuchElementException();

            Node<Key, Value> node = stack.pop();
            current = node.right;

            return new Entry<>(node.key, node.value);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
        tree.put(5, "Apple");
        tree.put(2, "Banana");
        tree.put(8, "Cherry");
        tree.put(1, "Grapes");
        tree.put(4, "Mango");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key to find its value: ");
        int keyToFind = scanner.nextInt();
        scanner.close();

        String value = tree.find(keyToFind);
        if (value != null) {
            System.out.println("Value for key " + keyToFind + ": " + value);
        } else {
            System.out.println("Key not found!");
        }
    }
}