package class_example;

public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() { top = null; size = 0; }

    public void push(T data) {
        Node<T> new_node = new Node<>(data);
        new_node.setNext(top);
        top = new_node;
        size += 1;
    }

    public T pop() {
        if(isEmpty()) { return null; }
        Node<T> _top = top;
        top = top.getNext();
        size -= 1;
        return _top.getData();
    }

    public void print() {
        Node<T> current = top;
        System.out.println("\n== Attempting to print stack ==\n");
        while (current.getNext() != null) {
            T current_data = current.getData();
            System.out.println(current_data.toString());
            current = current.getNext();
        }
        System.out.println("\n == Print complete == \n");
    }

    public int getSize() { return size; }
    public T peek() { return (top != null) ? top.getData() : null; }

    public boolean isEmpty() { return top == null; }
}