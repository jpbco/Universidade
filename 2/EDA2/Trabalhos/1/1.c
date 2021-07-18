#include <stdio.h>

int factors(int n)
{
    int i = 2;
    int count = 0;
    while (i * i <= n)
    {
        if (n % i != 0)
        {
            i += 1;
        }
        else
        {
            n /= i;
            count++;
        }
    }
    if (n > 1)
    {
        count++;
    }
    return count;
}
int main(int argc, char const *argv[])
{

    int z;
    int m = 0;
    scanf("%d", &z);
    int n = z;
    int numbers[n];

    while (n > 0)
    {
        scanf("%d", &numbers[m]);
        n--;
        m++;
    }

    for (int i = 0; i < z; i++)
    {
        printf("%d: %d\n", numbers[i], factors(numbers[i]));
    }

    return 0;
}
