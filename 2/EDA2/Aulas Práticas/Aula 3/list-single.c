#include <stdio.h>
#include <stdlib.h>
#include "list.h"

typedef struct node
{
    int element;
    struct node *next;
} node_t;

typedef struct list
{
    node_t *head;

} list_t;

node_t *node_new(int val, node_t *next)
{
    node_t *node = malloc(sizeof(node_t));
    node->element = val;
    node->next = next;
    return node;
}

list_t *list_new(int val)
{
    list_t *list = malloc(sizeof(list_t));
    list->head = node_new(0, NULL);

    return list;
}

bool list_insert(struct list *list, int value)
{
    node_t *temp = node_new(value, list->head->next);
    list->head->next = temp;

    return true;
}

void list_print(struct list *list)
{
    node_t *current = list->head;
    printf("[");
    while (current != NULL)
    {
        printf(" %d", current->element);
        current = current->next;
    }
    printf("]");
}

void list_destroy(struct list *list)
{
    node_t *current = list->head;

    while (current->next != NULL)
    {
        node_t *temp = current->next;
        free(current);
        current = temp;
    }
}
