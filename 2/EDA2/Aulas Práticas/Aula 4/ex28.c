#include <stdbool.h>

#define MAX_MARCA 20
#define MAX_MODELO 20
#define T 39

typedef struct car
{
    char marca[MAX_MARCA + 1];   // 21 Bytes
    char modelo[MAX_MODELO + 1]; // 21 Bytes
    short cilindrada;            // 2 Bytes
    int ano_i, ano_r;            // 2 * 2 Bytes
} car_t;                         // Total = 48 Bytes

typedef struct Bnode
{
    short ocupaçao;             // 2 Bytes
    car_t elementos[2 * T - 1]; // 48 * (2*T-1) Bytes + 2 Bytes de alinhamento
    int filhos[2 * T];          // 4 * (2 * T) Bytes
    bool eh_folha;              // 1 Bytes
    // 3 Bytes de alinhamento
} bnode_t; // Total = 8 + (48 * (2*T-1)) + (4*(2*T)) tem de ser <= 4096 <=> T=39