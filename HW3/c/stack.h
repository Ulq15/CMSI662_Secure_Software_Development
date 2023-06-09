#ifndef STACK_H
#define STACK_H

/*
 * An abstract data type for expandable array-based stack of strings.
 * Using Linked List approach to implement stack.
 */

typedef struct stack Stack;

/*
 * A Stack needs to store a collection of strings, and keep track of
 * how many strings are in the collection, and where the top of the stack is.
 */

typedef char** array;
typedef char** top;
typedef unsigned int size;
typedef unsigned int capacity;

/*
 * Stack Operations.
 */

Stack* makeStack(unsigned int capacity);
void push(Stack* stack, char* item);
char* pop(Stack* stack);
char* peek(Stack* stack);
int isEmpty(Stack* stack);
int isFull(Stack* stack);
void expand(Stack* stack);
void shrink(Stack* stack);
void printStack(Stack* stack);
void destroy(Stack* stack);

#endif // STACK_H