#include <stdio.h>
#include <string.h>
#include "trie.h"

char *words[] = {
    "acabais",
    "acocora",
    "amuareis",
    "aramada",
    "arpoavam",
    "azoto",
    "barceiro",
    "barco",
    "cabulais",
    "coragem",
    "corasse",
    "cuja",
    "embarace",
    "ensaquem",
    "enxuguem",
    "festeiro",
    "laringes",
    "mar",
    "propugno",
    "remem",
    "salteada",
    "repudiei",
    "reactive",
    "festa",
    "leque",
    "festarola",
    "achar",
    "enteados",
    "marujar",
    "vinte",
    NULL};

/*
   Fazer um programa que:

   1. insere todas as palavras em words[] numa trie

   2. confirma que trie_find() as encontra todas

   3. diz quantas palavras existem na trie

   4. mostra as palavras na trie com prefixos "a" "ac" "mar" "por" "vinte"

   5. remove 5 palavras da trie

   6. mostra todas as palavras na trie, antes e depois de remover palavras
*/
void insert_word(struct trie *trie, int size, char words[size])
{
  
}
int main(void)
{
  struct trie *trie = trie_new();
  //1. ...
  for (int i = 0; words[i] != NULL; i++)
  {
    insert_word(trie, strlen(words[i]), words[i]);
  }

  return 0;
}
