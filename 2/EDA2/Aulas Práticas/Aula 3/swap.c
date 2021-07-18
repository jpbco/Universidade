#include <stdio.h>

/*
  ...
*/
void troca(int *a, int *b)
{
    int t = *a;
    *a = *b;
    *b = t;
}
int main(void)
{
    int a, b;

    // lê dois valores inteiros
    printf("insira dois inteiros: ");
    scanf("%d %d", &a, &b);

    // mostra-os
    printf("antes da troca: a = %d, b = %d\n", a, b);

    troca(&a, &b); // troca os valores das variáveis

    // mostra os valores depois da troca
    printf("depois da troca: a = %d, b = %d\n", a, b);

    return 0;
}