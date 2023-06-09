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
        if (isFull()){
            expand();
        }
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
        if (top >= 1 && top < capacity / 2) {
            shrink();
        }
        top--;
        return storage[top + 1];
    }

    int isEmpty() {
        return top == -1;
    }

    int isFull() {
        return top + 1 == capacity;
    }

    void expand(){
        cout << "Expanding from " << capacity << " to " << capacity * 2 << endl;
        string *newStorage = new string[capacity * 2];
        for (int i = 0; i < capacity; i++){
            newStorage[i] = storage[i];
        }
        delete[] storage;
        storage = newStorage;
        capacity *= 2;
    }

    void shrink(){
        cout << "Shrinking from " << capacity << " to " << capacity / 2 << endl;
        string *newStorage = new string[capacity / 2];
        for (int i = 0; i < top+1; i++){
            newStorage[i] = storage[i];
        }
        delete[] storage;
        storage = newStorage;
        capacity /= 2;
    }

    string toString(){
        string result = "Bottom[";
        for (int i = 0; i <= top; i++){
            result += " ";
            result += storage[i];
        }
        result += " ]Top";
        return result;
    }

    void print(){
        cout << "Stack: " << toString() << endl;
    }

    ~Stack() {
        delete[] storage;
    }
};


int main() {
    Stack myStack(1);
    string empty = (myStack.isEmpty() == 1) ? "true" : "false";
    cout << "is stack empty? " << empty << endl;
    
    myStack.push('1');
    cout << "Pushed " << myStack.peek() << endl;
    myStack.push('2');
    cout << "Pushed " << myStack.peek() << endl;
    myStack.push('3');
    cout << "Pushed " << myStack.peek() << endl;
    myStack.push('4');
    cout << "Pushed " << myStack.peek() << endl;
    myStack.push('5');
    cout << "Pushed " << myStack.peek() << endl;
    myStack.push('6');
    cout << "Pushed " << myStack.peek() << endl;
    myStack.push('7');
    cout << "Peeked " << myStack.peek() << endl;
    
    myStack.print();

    while (myStack.isEmpty() == 0) {
        string temp = myStack.pop();
        cout << "Popped " << temp << endl;
    }

    return 0;
}
