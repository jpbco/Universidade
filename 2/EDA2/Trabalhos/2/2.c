#include <stdio.h>

void subsetsum(int size, int array[size], int result)
{
    int sum;

    for (int i = 0; i < size; i++)
    {
        sum = array[i];
        if (sum == result)
        {
            printf("s[%d] = %d\n", i + 1, result);
            return;
        }
    }

    for (int i = 0; i < size - 1; i++)
    {
        sum = array[i] + array[i + 1];
        if (sum == result)
        {
            printf("s[%d] + s[%d] = %d\n", i + 1, i + 2, result);
            return;
        }
    }

    for (int i = 0; i < size; i++)
    {
        sum = array[i];

        for (int j = i + 1; j <= size; j++)
        {
            if (sum == result)
            {
                printf("s[%d] + ... + s[%d] = %d", i + 1, j, result);
                return;
            }
            else if (sum > result)
            {
                break;
            }

            sum = sum + array[j];
        }
    }

    printf("nenhuma subsequencia soma %d\n", result);
}

int main(int argc, char const *argv[])
{
    int nums;
    scanf("%d", &nums);
    int array[nums];

    for (int i = 0; nums > 0; nums--, i++)
    {
        scanf("%d", &array[i]);
    }

    int result;
    scanf("%d", &result);
    subsetsum(sizeof(array) / sizeof(array[0]), array, result);

    return 0;
}
