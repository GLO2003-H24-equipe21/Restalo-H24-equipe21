# Exercices - TP4

## Rétrospective finale

### Processus

### Outils d'intelligence artificielle

## Open Sourcing

## Stories

En fonction des commentaires et suggestions de notre correcteur, voici en détails les deux _stories_ que nous avons 
implémentées lors de ce TP.

### Ajouter une évaluation

En tant que client, je peux ajouter une évaluation au restaurant.

#### Critères de succès

- La note doit être comprise entre 0 et 5 inclusivement. La note est un entier.
- Le commentaire ne peut pas être vide.
- Chaque évaluation est horodatée au moment de sa création.

#### Spécifications

##### Requête

**Path**

`POST restaurants/<id>/reviews`

**Body**

```ts
{
  rating: number, // integer between 0 and 5
  comment: string
}
```

##### Réponses

- `201 CREATED`: succès.

- `400 BAD REQUEST`: un des paramètres obligatoires est manquant.

  **Body**

  ```ts
  {
    error: "MISSING_PARAMETER",
    description: string
  }
  ```

- `400 BAD REQUEST`: un des paramètres n'est pas valide.

  **Body**

  ```ts
  {
    error: "INVALID_PARAMETER",
    description: string
  }
  ```

- `404 NOT FOUND`: le restaurant n'existe pas.

### Rechercher des évaluations

En tant qu'utilisateur, je peux voir les évaluations d'un restaurant.

#### Critères de succès

- Il est possible de rechercher par notes. Une note est un entier entre 0 et 5 inclusivement.
- Il est possible de rechercher par intervalle de dates.
- On peut combiner les deux paramètres de recherche.
- Si aucun paramètre n'est spécifié, toutes les évaluations du restaurant sont affichées.
- L'ordre d'affichage des évaluations n'a pas d'importance, tant qu'elles respectent les paramètres de recherche.

#### Spécifications

##### Requête

**Path**

`GET restaurants/<id>/reviews?<queryParams>`

**Query params**

- `rating?: string`
- `from?: string  // date without timezone (ISO)`
- `to?: string  // date without timezone (ISO)`

**Exemples**

- `GET restaurants/<id>/reviews`
- `GET restaurants/<id>/reviews?rating=2`
- `GET restaurants/<id>/reviews?rating=1&rating=2&rating=3`
- `GET restaurants/<id>/reviews?from=2023-03-16`
- `GET restaurants/<id>/reviews?from=2023-03-16&to=2024-03-16`
- `GET restaurants/<id>/reviews?from=2023-03-16&to=2023-12-31&rating=1&rating=4`

#### Réponses
- `200 OK`: succès.
  **Body**
```ts
[
  {
    rating: string,
    comment: string,
    date: string //date without timezone (ISO)
  },
  ...
]
```

- `400 BAD REQUEST`: un des paramètres n'est pas valide.
  **Body**
```ts
{
  error: "INVALID_PARAMETER",
  description: string
}
```

- `404 NOT FOUND`: le restaurant n'existe pas.

## Architecture

## Planification du travail

### GitHub Project
![Project]()

### Milestone
![Milestone]()

### Issues

Issue #1
![Issue_2]()

Issue #2
![Issue_1]()

Issue #3
![Issue_1]()

### Pull requests

Pull request #1
![Pull_request_1]()

Pull request #2
![Pull_request_2]()

Pull request #3
![Pull_request_3]()

### Arbre de commits

![Commit_tree]()
