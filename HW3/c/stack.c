#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "stack.h"

struct stack {
    char** array;
    char** top;
    unsigned int size;
    unsigned int capacity;
};

Stack *makeStack(unsigned int capacity) {
    if (capacity < 0) {
        fprintf(stderr, "Error - makeStack(): capacity must be a positive integer.\n");
        exit(EXIT_FAILURE);
    }
    Stack* stack = (Stack*)calloc(1, sizeof(Stack));
    if (stack == NULL) {
        fprintf(stderr, "Error - makeStack(): failed to allocate memory for stack.\n");
        exit(EXIT_FAILURE);
    }
    stack->array = (char**)calloc(capacity, sizeof(char*));
    if (stack->array == NULL) {
        fprintf(stderr, "Error - makeStack(): failed to allocate memory for stack's array.\n");
        exit(EXIT_FAILURE);
    }
    stack->top = stack->array;
    stack->size = 0u;
    stack->capacity = capacity;
    return stack;
}

void push(Stack* stack, char *item) {
    if (isFull(stack) == 1) {
        expand(stack);
    }
    stack->array[stack->size] = item;
    stack->top = stack->array + stack->size;
    stack->size++;
}

char *pop(Stack* stack) {
    if (isEmpty(stack) == 1) {
        fprintf(stderr, "Error: pop() called on empty stack.\n");
        exit(EXIT_FAILURE);
    }
    if (stack->size < stack->capacity / 3) {
        shrink(stack);
    }
    char* item = (char*)calloc(1, sizeof(char*));
    strcpy(item, *(stack->top));
    stack->size--;
    stack->top = stack->array + stack->size - 1;
    return item;
}

char *peek(Stack* stack) {
    if (isEmpty(stack) == 1) {
        fprintf(stderr, "Error: peek() called on empty stack.\n");
        exit(EXIT_FAILURE);
    }
    char* item = (char*)calloc(1, sizeof(char*));
    strcpy(item, *(stack->top));
    return item;
}

int isEmpty(Stack* stack) {
    return stack->size == 0;
}

int isFull(Stack* stack) {
    return stack->size == stack->capacity;
}

void expand(Stack* stack) {
    if (!((stack->size <= stack->capacity) && (stack->size > ((3 * stack->capacity) / 4)))) {
        fprintf(stderr, "Error - expand(): Can only expand array when it is near full.\n");
        exit(EXIT_FAILURE);
    }
    // printf("Expanding stack from %d to %d\n", stack->capacity, stack->capacity * 2);
    stack->array = realloc(stack->array, (stack->capacity * 2) * sizeof(char*));
    if (stack->array == NULL) {
        fprintf(stderr, "Error - expand(): failed to reallocate memory while expanding the stack.\n");
        exit(EXIT_FAILURE);
    }
    stack->capacity = stack->capacity * 2;
}

void shrink(Stack* stack) {
    if (!(stack->size <= 1) && (stack->size < stack->capacity / 3 )) {
        fprintf(stderr, "Error - shrink(): Can only shrink array when size is less than a half of capacity.\n");
        exit(EXIT_FAILURE);
    }
    // printf("Shrinking stack from %d to %d\n", stack->capacity, stack->capacity / 2);
    stack->array = realloc(stack->array, (stack->capacity / 2) * sizeof(char*));
    if (stack->array == NULL) {
        fprintf(stderr, "Error - shrink(): failed to reallocate memory while shrinking the stack.\n");
        exit(EXIT_FAILURE);
    }
    stack->capacity = stack->capacity / 2;
}

void printStack(Stack* stack) {
    printf("Stack: Bottom:{ ");
    for (int i = 0; i < stack->size; i++) {
        printf("%s ", stack->array[i]);
    }
    printf("}:Top\n");
}

void destroy(Stack* stack) {
    while (isEmpty(stack) != 1) {
        pop(stack);
    }
    free(stack->array);
    free(stack->top);
    free(stack);
}

int main() {
    unsigned int capacity = 2;
    Stack* s = makeStack(capacity);
    char *str1 = "Hello";
    char *str2 = "to the";
    char *str3 = "New";
    char *str4 = "and";
    char *str5 = "Improved";
    char *str6 = "Me";

    assert(isEmpty(s) == 1);
    printf("Stack should be Empty, and it is %s\n", isEmpty(s) ? "empty" : "not empty");
    // pop(s); // cannot pop from empty stack

    printStack(s);
    printf("\n");

    printf("pushing 1st item.\n");
    push(s, str1);
    
    printf("Stack should not be Empty, and it is %s\n", isEmpty(s) ? "empty" : "not empty");
    assert(isEmpty(s) == 0);
    
    printf("The top of the stack is: %s\n", peek(s));
    push(s, str2);
    
    printStack(s);
    printf("\n");

    assert(isFull(s) == 1);
    printf("The stack should be full now, and it is %s\n", isFull(s) ? "full" : "not full");
    printf("The size/capacity of the stack is: %d/%d\n", s->size, s->capacity);
    printf("\n");

    push(s, str3);
    printf("The stack should have expanded now.\n");
    printf("The size/capacity of the stack is: %d/%d\n", s->size, s->capacity);
    printf("\n");
    
    push(s, str4);
    push(s, str5);
    push(s, str6);

    printStack(s);

    for (size_t i = 0; i < 6; i++)
    {
        printf("\n");
        printf("peeking %s\n", peek(s));
        printf("popped %s\n", pop(s));
        printStack(s);
    }
    // pop(s); // cannot pop from empty stack

    destroy(s);
    return 0;
}

