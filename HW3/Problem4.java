
public class Problem4 {
    public static void main(String args[]) {
        Stack stack = new Stack(2);
        for (int i = 1; i <= 5; i++) {
            stack.push(String.valueOf(i));
            System.out.println("Pushed "+stack.peek());
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("Popping "+stack.pop());
        }
        stack.push("a");
        System.out.println("Pushed "+stack.peek());
        stack.push("b");
        System.out.println("Pushed "+stack.peek());
        while (!stack.isEmpty()) {
            System.out.println("Popping "+stack.pop());
        }
    }
}

class Stack {
    private String[] elements;
    private int top;
    
    enum Resizing{
        DOUBLE, HALF
    }

    public Stack(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Stack's capacity must be positive");
        this.elements = new String[capacity];
        this.top = -1;
    }

    public void push(String value) {
        if (top+1 == this.elements.length) {
            this.resize(Resizing.DOUBLE);
        }
        top++;
        this.elements[top] = value;
    }

    public String peek() {
        if (this.isEmpty())
            throw new RuntimeException("Stack is empty");
        return this.elements[top];
    }

    public String pop() {
        if (this.isEmpty())
            throw new RuntimeException("Stack is empty");
        if (this.top < this.elements.length/2) {
            this.resize(Resizing.HALF);
        }
        top--;
        return this.elements[top+1];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    private void resize(Resizing resizing) {
        int newSize = (resizing == Resizing.DOUBLE) ? this.elements.length * 2 : this.elements.length / 2;
        String[] resized = new String[newSize];
        System.arraycopy(this.elements, 0, resized, 0, this.top+1);
        this.elements = resized;
    }
}