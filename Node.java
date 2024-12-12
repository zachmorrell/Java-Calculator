package class_example;

public class Node<T> {
    private T data;
    private Node<T> next, previous;

    public Node() {
        data = null;
        next = null;
        previous = null;
    }

    public Node(T item) { data = item; }

    public T getData() { return data; }

    public void setData(T data) { this.data = data; }

    public Node<T> getNext() { return next; }

    public void setNext(Node<T> next) { this.next = next; }

    public Node<T> getPrevious() { return previous; }

    public void setPrevious(Node<T> previous) { this.previous = previous; }
}