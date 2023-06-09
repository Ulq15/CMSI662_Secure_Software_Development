#include <iostream>
#include <string>
using namespace std;

struct Stack {
private:
    int top;
    int capacity;
    string *storage;

public:
    Stack(int capacity) {
        if (capacity <= 0)
            throw string("Stack's capacity must be positive");
        storage = new string[capacity];
        this->capacity = capacity;
        top = -1;
    }

    void push(int value) {
        if (top == capacity)
            throw string("Stack's underlying storage is overflow");
        top++;
        storage[top] = value;
    }

    string peek() {
        if (top == -1)
            throw string("Stack is empty");
        return storage[top];
    }

    string pop() {
        if (top == -1)
            throw string("Stack is empty");
        top--;
        return storage[top + 1];
    }

    bool isEmpty() {
        return (top == -1);
    }

    ~Stack() {
        delete[] storage;
    }
};


int main() {
    Stack myStack(3);
    cout << myStack.isEmpty() << endl;
    myStack.push(1);
    myStack.push(2);
    myStack.push(3);
    myStack.push(4);
    myStack.push(5);
    myStack.push(6);
    myStack.push(7);

    cout << myStack.peek() << endl;
    myStack.pop();
    cout << myStack.peek() << endl;
    myStack.pop();
    cout << myStack.peek() << endl;
    myStack.pop();
    cout << myStack.peek() << endl;
    myStack.pop();
    cout << myStack.peek() << endl;
    myStack.pop();

    return 0;
}

/*
#include <string>
#include <iostream>
using namespace std;

// constant amount at which stack is increased
#define BOUND 4

// top of the stack
int top = -1;
 
// length of stack
int length = 0;
 
// function to create new stack
int* create_new(int* a) {
    // allocate memory for new stack
    int* new_a = new int[length + BOUND];
 
    // copying the content of old stack
    for (int i = 0; i < length; i++)
        new_a[i] = a[i];
 
    // re-sizing the length
    length += BOUND;
    return new_a;
}
 
// function to push new element
int* push(int* a, int element) {
    // if stack is full, create new one
    if (top == length - 1)
        a = create_new(a);
 
    // insert element at top of the stack
    a[++top] = element;
    return a;
}
 
// function to pop an element
void pop(int* a) {
    if (top < 0) {
        cout << "Stack is empty" << endl;
        return;
    }
    top--;
}
 
// function to display
void display(int* a) {
    // if top is less than 0, that means stack is empty
    if (top < 0)
        cout << "Stack is Empty" << endl;
    else {
        cout << "Stack: ";
        for (int i = 0; i <= top; i++)
            cout << a[i] << " ";
        cout << endl;
    }
}
 
// Driver Code
int main() {
    // creating initial stack
    int* a = create_new(a);
 
    // pushing element to top of stack
    a = push(a, 1);
    a = push(a, 2);
    a = push(a, 3);
    a = push(a, 4);
    display(a);
 
    // pushing more element when stack is full
    a = push(a, 5);
    a = push(a, 6);
    display(a);
 
    a = push(a, 7);
    a = push(a, 8);
    display(a);
 
    // pushing more element so that stack can grow
    a = push(a, 9);
    display(a);
 
    return 0;
}
*/