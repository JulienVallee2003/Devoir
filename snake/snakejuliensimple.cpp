#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <unistd.h>

#define LARGEUR 20
#define HAUTEUR 10

int gameOver;
int score;
int taille;
int serpentX[LARGEUR * HAUTEUR];
int serpentY[LARGEUR * HAUTEUR];
int fruitsX[3];
int fruitsY[3];
int fruits_manges;
int pommes_mangees[3]; // Tableau pour suivre les pommes déjà mangées

enum Direction { STOP = 0, GAUCHE, DROITE, HAUT, BAS };
enum Direction direction;

// Fonction pour sauvegarder la partie
void sauvegarderPartie() {
    FILE *fichier = fopen("snake_save.txt", "w");
    if (fichier != NULL) {
        fprintf(fichier, "%d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d",
                gameOver, score, taille,
                serpentX[0], serpentY[0],
                serpentX[1], serpentY[1],
                serpentX[2], serpentY[2],
                fruitsX[0], fruitsY[0],
                fruitsX[1], fruitsY[1],
                fruitsX[2], fruitsY[2],
                fruits_manges,
                pommes_mangees[0], pommes_mangees[1], pommes_mangees[2]);

        fclose(fichier);
    }
}

// Fonction pour charger la partie
void chargerPartie() {
    FILE *fichier = fopen("snake_save.txt", "r");
    if (fichier != NULL) {
        fscanf(fichier, "%d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d %d",
               &gameOver, &score, &taille,
               &serpentX[0], &serpentY[0],
               &serpentX[1], &serpentY[1],
               &serpentX[2], &serpentY[2],
               &fruitsX[0], &fruitsY[0],
               &fruitsX[1], &fruitsY[1],
               &fruitsX[2], &fruitsY[2],
               &fruits_manges,
               &pommes_mangees[0], &pommes_mangees[1], &pommes_mangees[2]);

        fclose(fichier);
    }
}

void initialiser() {
    gameOver = 0;
    score = 0;
    taille = 1;
    serpentX[0] = LARGEUR / 2;
    serpentY[0] = HAUTEUR / 2;
    for (int i = 0; i < 3; i++) {
        fruitsX[i] = rand() % LARGEUR;
        fruitsY[i] = rand() % HAUTEUR;
        pommes_mangees[i] = 0; // Initialiser le tableau des pommes déjà mangées
    }
    fruits_manges = 0;
    direction = STOP;

    chargerPartie(); // Charger la partie au démarrage
}

void dessiner() {
    system("cls");

    for (int i = 0; i < LARGEUR + 2; i++)
        printf("#");
    printf("\n");

    for (int i = 0; i < HAUTEUR; i++) {
        for (int j = 0; j < LARGEUR; j++) {
            if (j == 0)
                printf("#");

            int afficheSerpent = 0;
            for (int k = 0; k < taille; k++) {
                if (serpentX[k] == j && serpentY[k] == i) {
                    printf("O");
                    afficheSerpent = 1;
                }
            }

            int estPommeAffichee = 0;
            for (int k = 0; k < 3; k++) {
                if (fruitsX[k] == j && fruitsY[k] == i && !pommes_mangees[k]) {
                    printf("F");
                    estPommeAffichee = 1;
                }
            }

            if (!afficheSerpent && !estPommeAffichee)
                printf(" ");

            if (j == LARGEUR - 1)
                printf("#");
        }
        printf("\n");
    }

    for (int i = 0; i < LARGEUR + 2; i++)
        printf("#");
    printf("\n");

    printf("Score:%d\n", score);
}

void entrerDirection() {
    if (_kbhit()) {
        switch (_getch()) {
            case 'q':
                direction = GAUCHE;
                break;
            case 'd':
                direction = DROITE;
                break;
            case 'z':
                direction = HAUT;
                break;
            case 's':
                direction = BAS;
                break;
            case 'x':
                gameOver = 1;
                break;
        }
    }
}

void deplacer() {
    int ancienX = serpentX[0];
    int ancienY = serpentY[0];
    int ancien2X, ancien2Y;

    serpentX[0] += direction == DROITE ? 1 : direction == GAUCHE ? -1 : 0;
    serpentY[0] += direction == BAS ? 1 : direction == HAUT ? -1 : 0;

    for (int i = 1; i < taille; i++) {
        ancien2X = serpentX[i];
        ancien2Y = serpentY[i];
        serpentX[i] = ancienX;
        serpentY[i] = ancienY;
        ancienX = ancien2X;
        ancienY = ancien2Y;
    }

    for (int i = 0; i < 3; i++) {
        if (serpentX[0] == fruitsX[i] && serpentY[0] == fruitsY[i] && !pommes_mangees[i]) {
            fruits_manges++;
            pommes_mangees[i] = 1;

            if (fruits_manges == 3) {
                fruits_manges = 0;
                for (int j = 0; j < 3; j++) {
                    fruitsX[j] = rand() % LARGEUR;
                    fruitsY[j] = rand() % HAUTEUR;
                    pommes_mangees[j] = 0; // Réinitialiser le tableau des pommes déjà mangées
                }
                taille++;
                score += 10;
            }
        }
    }
}

void verifierCollision() {
    if (serpentX[0] >= LARGEUR || serpentX[0] < 0 || serpentY[0] >= HAUTEUR || serpentY[0] < 0)
        gameOver = 1;

    for (int i = 1; i < taille; i++) {
        if (serpentX[i] == serpentX[0] && serpentY[i] == serpentY[0])
            gameOver = 1;
    }
}

int main() {
    while (!gameOver) {
        dessiner();
        entrerDirection();
        deplacer();
        verifierCollision();
        usleep(100000);
    }

    printf("Game Over\n");
    printf("Score final: %d\n", score);

    sauvegarderPartie(); // Sauvegarder la partie avant de quitter

    return 0;
}

