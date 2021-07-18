#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define LINE_MAX 1001

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
    if (!stack_full(stack))
    {
        stack->top += 1;
        stack->array[stack->top] = item;
        return true;
    }

    return false;
}

int stack_pop(stack_t *stack)
{
    if (!stack_full(stack))
    {
        return stack->array[stack->top--];
    }
    return -1;
}

void stack_destroy(stack_t *stack)
{
    free(stack);
}

void operation(stack_t *stack, int a, int b){
    
}
int main(int argc, char const *argv[])
{
    char polish[LINE_MAX];
    bool div = false;

    while (scanf("%s", polish) != EOF)
    {
        stack_t *stack = stack_new(LINE_MAX);
        for (int j = 0; polish[j] != '\0'; j++)
        {
            if (polish[j] >= 48 && polish[j] <= 57)
            {
                stack_push(stack, polish[j] - 48);
            }
            else
            {
                int a, b;
                switch (polish[j])
                {
                case '+':
                    a = stack_pop(stack);
                    b = stack_pop(stack);
                    stack_push(stack, b + a);
                    break;
                case '-':
                    a = stack_pop(stack);
                    b = stack_pop(stack);
                    stack_push(stack, b - a);
                    break;
                case '*':
                    a = stack_pop(stack);
                    b = stack_pop(stack);
                    stack_push(stack, b * a);
                    break;
                case '/':
                    a = stack_pop(stack);
                    b = stack_pop(stack);
                    if (a == 0)
                    {
                        div = true;
                        break;
                    }
                    stack_push(stack, b / a);
                    break;
                case '~':
                    a = stack_pop(stack);
                    stack_push(stack, -a);
                    break;
                }
            }
        }
        if (!div)
            printf("%d\n", stack_pop(stack));
        else
        {
            stack_pop(stack);
            printf("divisao por 0\n");
        }
        stack_destroy(stack);
        div = false;
    }
    return 0;
}