#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#define SIZE 50 // valor utilizado para o tamanho máximo da Pilha / input do utilizador

// Especificação da estrutura de dados Pilha
typedef struct stack
{
    int capacity; // capacidade máxima da pilha
    int top;      // índice do array da pilha que marca o topo
    float *array; // array de elementos da pilha
} stack_t;

// Função que inicializa a as variáveis de cada Pilha
stack_t *stack_new(int capacity)
{
    stack_t *stack = malloc(sizeof(stack_t));
    stack->capacity = capacity;
    stack->top = -1;
    stack->array = malloc(sizeof(float) * capacity);
    return stack;
}

// Função que retorna o nª de elementos na pilha
int stack_size(stack_t *stack)
{
    return stack->top + 1;
}

// Função que verifica se a Pilha está vazia
bool stack_empty(stack_t *stack)
{
    return stack->top == -1;
}

// Função que verifica se a Pilha está cheia
bool stack_full(stack_t *stack)
{
    return stack_size(stack) == stack->capacity;
}
// Função que empilha um elemento na Pilha
void stack_push(stack_t *stack, float x)
{

    if (stack_full(stack))
    {
        printf("Stack is full, stack_push operation is not allowed\n");
    }
    else
    {
        stack->array[++stack->top] = x;
    }
}

// Função que retorna o elemento no topo da Pilha
float stack_peek(stack_t *stack)
{
    return stack->array[stack->top];
}

// Função que mostra o aspeto atual da Pilha
void stack_display(stack_t *stack)
{
    printf("\nStack:\n");
    if (stack->top >= 0)
    {
        for (int i = 0; i <= stack->top; i++)
            printf("    %0.3f\n", stack->array[i]);
    }
    else
    {
        printf("    (empty)\n");
    }
    printf("\n\n");
}

// Função que remove e retorna o elemento do topo da Pilha
float stack_pop(stack_t *stack)
{
    return stack->array[stack->top--];
}
// Função que "apaga" a Pilha
void stack_destroy(stack_t *stack)
{
    free(stack);
}

// Função que transforma o elemento do topo da Pilha no seu simétrico
void neg(stack_t *stack)
{
    // caso a stack conter pelo menos 1 item
    if (!stack_empty(stack))
    {
        float a = stack_pop(stack);
        stack_push(stack, -a);
    }
    // caso a stack estiver vazia
    else
    {
        printf("Error executing neg: Not enough items in the Stack\n");
    }
}

// Função que troca os dois elementos do topo da Pilha
void swap(stack_t *stack)
{
    // Troca ocorre se a pilha tiver pelo 2 elementos
    if (stack_size(stack) >= 2)
    {
        float a = stack_pop(stack);
        float b = stack_pop(stack);
        stack_push(stack, a);
        stack_push(stack, b);
    }
    // Caso a pilha tenha 1 ou menos elementos
    else
    {
        printf("Error executing swap: Not enough items in the stack\n");
    }
}

// Função que duplica o elemento no topo da Pilha
void dup(stack_t *stack)
{
    // apenas duplica o elemento se existir pelo menos 1 operando na pilha
    if (!stack_empty(stack))
    {
        stack_push(stack, stack_peek(stack));
    }
    else
    {
        printf("Stack is empty, dup operation is not allowed\n");
    }
}

// Função que remove o elemento no topo da Pilha
void drop(stack_t *stack)
{
    // Caso a stack esteja vazia
    if (stack_empty(stack))
    {
        printf("Stack is empty, drop operation is not allowed\n");
    }
    // Caso a stack tenha pelo menos um item
    else

        stack_pop(stack);
}

// Função que "limpa" a pilha
void clear(stack_t *stack)
{
    // "substitui" a pilha atual por outra vazia
    stack_destroy(stack);    // remove o espaço em memória para a stack
    stack = stack_new(SIZE); // o apontador da pilha agora aponta para um nova(vazia) pilha
}

// Função que empilha a soma de dois elementos do topo da Pilha
void add(stack_t *stack)
{
    /* se a pilha tiver pelo menos dois operandos a função remove os 2 operandos do topo da pilha
, realiza a operação e depois empilha o resultado
*/
    if (stack_size(stack) > 1)
    {
        float a, b;
        a = stack_pop(stack);
        b = stack_pop(stack);
        stack_push(stack, b + a);
    }
    else
    {
        printf("Error executing '+': Not enough items in the stack\n");
    }
}

// Função que empilha a subtração de dois elementos do topo da Pilha
void subtract(stack_t *stack)
{
    /* se a pilha tiver pelo menos dois operandos a função remove os 2 operandos do topo da pilha
, realiza a operação e depois empilha o resultado
*/
    if (stack_size(stack) > 1)
    {
        float a, b;
        a = stack_pop(stack);
        b = stack_pop(stack);
        stack_push(stack, b - a);
    }
    else
    {
        printf("Error executing '-': Not enough items in the stack\n");
    }
}

// Função que empilha a divisão de dois elementos do topo da Pilha
void divide(stack_t *stack)
{
    /* se a pilha tiver pelo menos dois operandos a função remove os 2 operandos do topo da pilha
, realiza a operação e depois empilha o resultado
*/
    if (stack_size(stack) > 1)
    {
        float a, b;
        if (stack_peek(stack) == 0)
        {
            printf("Error: division by 0\n");
        }
        else
        {
            a = stack_pop(stack);
            b = stack_pop(stack);
            stack_push(stack, b / a);
        }
    }
    else
    {
        printf("Error executing '/': Not enough items in the stack\n");
    }
}

// Função que empilha a multiplicação de dois elementos do topo da Pilha
void multiply(stack_t *stack)
{
    /* se a pilha tiver pelo menos dois operandos a função remove os 2 operandos do topo da pilha
, realiza a operação e depois empilha o resultado
*/
    if (stack_size(stack) > 1)
    {
        float a, b;
        a = stack_pop(stack);
        b = stack_pop(stack);
        stack_push(stack, b * a);
    }
    else
    {
        printf("Error executing '*': Not enough items in the stack\n");
    }
}

// Função inicial de "boas vindas"
void prompt()
{
    printf("************************************************\n");
    printf("*** RPN - Reverse Polish Notation Calculator ***\n");
    printf("***       João Cavaco e António Barroso      ***\n");
    printf("***           42470        44445             ***\n");
    printf("***             ASC1 2019/2020               ***\n");
    printf("************************************************\n");
    printf("type ’help’ for available commands\n\n");
}

// Função que mostra ao utilizador aquilo que o programa faz
void help()
{
    printf("\nNumbers separated by space are pushed onto the stack.\n");
    printf("Operators are applied to the top of the stack.\n");
    printf("Available operators:  + - * / neg swap dup drop clear off.\n");
    printf("Examples: \n");
    printf("'2*(3+4)' == '3 4 + 2 *' == '2 3 4 + *'\n");
    printf("'(3+4)/2' == '3 4 + 2 /' == '2 3 4 + swap /'\n");
}
// Função que termina o programa
void off()
{
    printf("Bye!\n");
    exit(EXIT_SUCCESS);
}

// Função que avalia o input
void eval(char string[], stack_t *stack)
{

    if (string[0] >= 48 && string[0] <= 57) // se a string for um número
    {
        float value = atof(string); // converte a string para float
        stack_push(stack, value);   // empilha para a stack
    }
    else // se não for um número então pode ser que seja uma operação e chama essa operação
    {
        if (strcmp("+\n", string) == 0 || strcmp("+", string) == 0) // verifica se é a operação soma
            add(stack);
        else if (strcmp("-\n", string) == 0 || strcmp("-", string) == 0) // verifica se é a operação subtração
            subtract(stack);
        else if (strcmp("/\n", string) == 0 || strcmp("/", string) == 0) // verifica se é a operação divisão
            divide(stack);
        else if (strcmp("*\n", string) == 0 || strcmp("*", string) == 0) // verifica se é a operação multiplicação
            multiply(stack);
        else if (strcmp("off\n", string) == 0 || strcmp("off", string) == 0) // verifica se é a operação terminar
            off();
        else if (strcmp("neg\n", string) == 0 || strcmp("neg", string) == 0) // verifica se é a operação tornar simétrico
            neg(stack);
        else if (strcmp("swap\n", string) == 0 || strcmp("swap", string) == 0) // verifica se é a operação trocar operandos
            swap(stack);
        else if (strcmp("dup\n", string) == 0 || strcmp("dup", string) == 0) // verifica se é a operação duplicar
            dup(stack);
        else if (strcmp("drop\n", string) == 0 || strcmp("drop", string) == 0) // verifica se é a operação remover operando
            drop(stack);
        else if (strcmp("clear\n", string) == 0 || strcmp("clear", string) == 0) // verifica se é a operação limpar a pilha
            clear(stack);
        else if (strcmp("help\n", string) == 0 || strcmp("help", string) == 0) // verifica se é a operação mostra ajuda
            help();
        else
            printf("Error: operation '%s' is not supported\n", string); // input é inválido
    }
}

int main(int argc, char const *argv[])
{

    prompt();                         // Dá as "boas vindas" ao utilizador
    stack_t *stack = stack_new(SIZE); //inicializa a pilha

    // Loop principal do programa
    while (true)
    {
        char *input = malloc(sizeof(char) * SIZE); // aloca espaço na memória para guardar o input do utilizador
        printf("-> ");
        fgets(input, SIZE, stdin);   // guarda o input do utilizador na string input
        char *token = strdup(input); // aloca espaço na memória para guardar várias substrings da string de input

        // ciclo que separa a string input em várias substrings
        while ((token = strsep(&input, " ")) != NULL)
        {
            eval(token, stack); // avalia aquilo que deve o programa deve fazer em cada substring
        }

        stack_display(stack); // Mostra o estado da memória atual da calculadora
    }

    stack_destroy(stack); // apaga o espaço alocado da para a pilha
    return 0;
}
