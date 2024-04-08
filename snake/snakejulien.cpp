#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <string.h>

#ifdef _WIN32
#include <windows.h>
#else
#include <unistd.h>
#include <time.h>
#endif

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
int pommes_mangees[3];
int vitesse; 
enum Direction { STOP = 0, GAUCHE, DROITE, HAUT, BAS };
enum Direction direction;

// Fonction pour sauvegarder la partie
void sauvegarderPartie() {
    FILE *fichier = fopen("snake_save.txt", "w");
    if (fichier != NULL) {
        fprintf(fichier, "%d\n%d\n%d\n%d\n", score, taille, fruits_manges, vitesse); // Ajout de la vitesse

        for (int i = 0; i < taille; i++) {
            fprintf(fichier, "%d %d\n", serpentX[i], serpentY[i]);
        }

        for (int i = 0; i < 3; i++) {
            fprintf(fichier, "%d %d %d\n", fruitsX[i], fruitsY[i], pommes_mangees[i]);
        }

        fclose(fichier);
    }
}

// Fonction pour charger la partie
void chargerPartie() {
    FILE *fichier = fopen("snake_save.txt", "r");
    if (fichier != NULL) {
        fscanf(fichier, "%d\n%d\n%d\n%d\n", &score, &taille, &fruits_manges, &vitesse); // Ajout de la vitesse

        for (int i = 0; i < taille; i++) {
            fscanf(fichier, "%d %d\n", &serpentX[i], &serpentY[i]);
        }

        for (int i = 0; i < 3; i++) {
            fscanf(fichier, "%d %d %d\n", &fruitsX[i], &fruitsY[i], &pommes_mangees[i]);
        }

        fclose(fichier);
    }
}

// Fonction pour vérifier si un fichier existe
int fichierExiste(const char *nomFichier) {
    FILE *fichier = fopen(nomFichier, "r");
    if (fichier != NULL) {
        fclose(fichier);
        return 1; // Le fichier existe
    }
    return 0; // Le fichier n'existe pas
}

// Ajout de la sélection de vitesse
void selectionnerVitesse() {
    printf("Selectionnez la vitesse :\n");
    printf("1. Lent\n");
    printf("2. Rapide\n");

    char choix;
    scanf(" %c", &choix);

    switch (choix) {
        case '1':
            vitesse = 250000; // Vitesse lente (ajustez selon vos besoins)
            break;
        case '2':
            vitesse = 75000; // Vitesse rapide (ajustez selon vos besoins)
            break;
        default:
            printf("Choix invalide, vitesse par défaut (rapide).\n");
            vitesse = 75000;
    }
}

void initialiser() {
    gameOver = 0;
    score = 0;
    taille = 1;
    direction = STOP;

    if (fichierExiste("snake_save.txt")) {
        chargerPartie();
    } else {
        serpentX[0] = LARGEUR / 2;
        serpentY[0] = HAUTEUR / 2;
        for (int i = 0; i < 3; i++) {
            fruitsX[i] = rand() % LARGEUR;
            fruitsY[i] = rand() % HAUTEUR;
            pommes_mangees[i] = 0;
        }
        fruits_manges = 0;
        selectionnerVitesse(); // Sélection de vitesse pour une nouvelle partie
    }
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
                    printf("*");
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
            score += 10;
            
            if (fruits_manges == 3) {
                fruits_manges = 0;
                for (int j = 0; j < 3; j++) {
                    fruitsX[j] = rand() % LARGEUR;
                    fruitsY[j] = rand() % HAUTEUR;
                    pommes_mangees[j] = 0;
                }
                taille++;

            }
        }
    }

#ifdef _WIN32
    Sleep(vitesse / 1000); // Utilisation de Sleep pour Windows (convertir microsecondes en millisecondes)
#else
    struct timespec attente;
    attente.tv_sec = 0;
    attente.tv_nsec = vitesse * 1000; // Utilisation de nanosleep pour Linux
    nanosleep(&attente, NULL);
#endif
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
    printf("Appuyez sur une touche pour commencer...\n");
    _getch(); // Attendez que l'utilisateur appuie sur une touche pour commencer

    if (fichierExiste("snake_save.txt")) {
        printf("Voulez-vous reprendre la partie sauvegardée ? (o/n)\n");
        char choix;
        scanf(" %c", &choix);

        if (choix == 'o' || choix == 'O') {
            chargerPartie();
        } else {
            remove("snake_save.txt"); // Supprimer le fichier de sauvegarde existant
        }
    }

    initialiser();

    while (!gameOver) {
        dessiner();
        entrerDirection();

        if (direction != STOP) {
            deplacer();
            verifierCollision();
            sauvegarderPartie();
        }
    }

    printf("Game Over\n");
    printf("Score final: %d\n", score);

    return 0;
}
