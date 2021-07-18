#include <stdio.h>

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

    int i = 0;
    int wheel[] = {2, 2, 4, 2, 4, 2, 4, 6, 2, 6};
    unsigned int f = 3;

    while (f * f <= n)
    {
        if (n % f == 0)
        {
            printf(" %u", f);
            n /= f;
        }
        else
        {
            f += wheel[i];
            if (i == 9)
                i = 3;
            else
                i++;
        }
    }
    printf(" %u", n);
}

int main(int argc, char const *argv[])
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