#include <stdio.h>
#include <math.h>

void factors(unsigned int n)
{
    if (n < 2)
    {
        return;
    }

    while (n % 2 == 0)
    {
        printf(" %d", 2);
        n /= 2;
    }
    unsigned int i = 3;
    while (i <= sqrt(n))
    {
        if (n % i == 0)
        {
            printf(" %u", i);
            n /= i;
        }
        else
            i += 2;
    }
    if (n != 1)
        printf(" %u", n);
}

int main()
{
    int z;
    scanf("%d", &z);
    unsigned int k;

    for (; z >= 1; z--)
    {
        scanf("%u", &k);
        printf("%u:", k);
        factors(k);
        printf("\n");
    }

    return 0;
}