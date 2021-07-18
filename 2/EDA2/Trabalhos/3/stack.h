#include <stdbool.h>

typedef struct stack stack_t;

stack_t *stack_new();
bool stack_push(stack_t *stack, int value);
int stack_pop(stack_t *stack);
bool stack_full(stack_t *stack);
bool stack_empty(stack_t *stack);
void stack_destroy(stack_t *stack);
int stack_peek(stack_t *stack);