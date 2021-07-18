#include <stdio.h>
#include <stdlib.h>

#include "stack.h"

typedef struct stack
{
    int top;
    int capacity;
    int *array;

} stack_t;

stack_t *stack_new(int capacity)
{
    stack_t *stack = malloc(sizeof(stack_t));
    stack->capacity = capacity;
    stack->top = -1;
    stack->array = malloc(stack->capacity * sizeof(int));
    return stack;
}

bool stack_empty(stack_t *stack)
{
    return stack->top == -1;
}

bool stack_full(stack_t *stack)
{
    return stack->top == stack->capacity - 1;
}

bool stack_push(stack_t *stack, int item)
{
    stack->top += 1;
    stack->array[stack->top] = item;
    return true;
}

int stack_pop(stack_t *stack)
{
    return stack->array[stack->top--];
}

void stack_destroy(stack_t *stack)
{
    free(stack);
}