#include <stdbool.h>

typedef struct list list_t;

list_t *list_new();
bool list_insert(list_t *list, int value);
void list_print(list_t *list);
void list_destroy(list_t *list);