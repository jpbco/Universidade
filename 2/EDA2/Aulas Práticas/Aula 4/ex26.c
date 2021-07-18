#include <stdio.h>

#define path "./ficheiro.txt"

int main(int argc, char const *argv[])
{
    FILE *f = fopen(path,"r+");
    char input[5];

    while(scanf("%s",input) != 'q'){
        if(input[0] == '?'){

        }
        else if (input[0] == '?')
        {
        }
        else if (input[0] == '+')
        {
        }
        else if (input[0] == 'r')
        {
        }

    }
    if (f != NULL){
        return 0;
    }
    
    fclose(f);
    return 0;
}
