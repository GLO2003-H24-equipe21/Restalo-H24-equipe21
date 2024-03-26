# Exercices - TP3

## Rétrospective - Intégration continue et tests

### Pipeline CI

1. Avant l'implémentation du pipeline de tests automatisés, nous consacrions de 10 à 30 minutes à la vérification de 
   notre code lors des intégrations et plusieurs heures lors des remises. Ces périodes de travail devaient donc être 
   planifiées et prévues à l'avance afin de ne pas manquer de temps.
2. Depuis l'implémentation du pipeline, nous consacrons environ 5 minutes à la validation de notre code lors des 
   intégrations et 2 à 3 heures au moment de la remise. C'est une réduction de temps 
   considérable et positive dans notre planification du travail.
3. Voici 3 points positifs démontrant les bénéfices de l'intégration continue sur notre processus. 
   - **Intégrations plus fréquentes** : Notre équipe intègre le nouveau code plus rapidement et à une 
     plus grande fréquence qu'avant dans le répertoire de travail.
   - **Rétroaction instantanée** : Le succès ou l'échec des tests automatisés offre une rétroaction instantanée à 
     celui ou celle qui implémente une nouvelle fonctionnalité. Les erreurs sont donc corrigées tôt dans le 
     projet.
   - **Uniformité du code** : Les inspections automatisées du code (_spotless_) permettent à l'équipe d'avoir un code 
     uniforme et formaté de la même manière en tout temps.
4. Le pipeline CI automatise certaines vérifications du code à l'aide de tests, mais il revient à l'équipe de 
   garantir la qualité et la couverture suffisante de ces tests. Sans cette vigilance, les tests qui réussissent lors 
   des intégrations de code pourraient ne pas être fiables. Ainsi, nous devons évaluer et réviser régulièrement 
   ces tests afin qu'ils conviennent aux besoins changeants du projet.

### Tests

1. À l'heure actuelle, nous passons 70 % de notre temps à implémenter du code fonctionnel et 30 % à implémenter des 
   tests unitaires, ce qui représente une grande amélioration par rapport au début du projet, où cette proportion 
   tournait aux alentours des 90 / 10. D'ici la fin du projet, nous aimerions atteindre un ratio équivalent entre 
   l'implémentation de code et de tests, car nous sommes conscients des bénéfices et de l'importance des tests dans 
   l'évaluation de la qualité du logiciel.
2. Avec l'implémentation des tests, la taille de nos _issues_ a naturellement augmenté, les rendant ainsi plus 
   longues à compléter par les membres de l'équipe. Les _pull-requests_ prennent elles aussi plus de temps à 
   approuver, étant donné que nous devons non seulement valider le code lui-même, mais aussi les tests qui lui sont 
   associés. Par ailleurs, au cours du deuxième travail pratique, nous avons ajouté des _issues_ afin de réaliser les
   tests d'intégration de certaines parties du système.
3. Notre niveau de confiance envers notre code s'est renforcé depuis l'ajout de tests. En effet, lors de 
   l'implémentation d'une nouvelle fonctionnalité au logiciel, il est simple et rapide de tester si ces ajouts
   n'endommagent pas les fonctionnalités existantes du système.
4. Pour améliorer l'état de nos tests, nous pouvons
   - Effectuer un _refactoring_ des tests afin qu'ils soient plus lisibles et compacts. L'utilisation de _fixtures_ 
     peut notamment aider dans ce contexte.
   - Valider la couverture des tests existants pour que l'ensemble des cas spécifiques des fonctions du programme 
     soient testés. Ajouter de nouveaux tests au besoin.
   - Faire des revues de tests chaque semaine afin de s'assurer que l'ensemble des tests soit mis à jour selon les 
     nouvelles fonctionnalités du système.

## Planification du travail

### GitHub Project

### Milestone

### Issues

Issue #1

Issue #2

Issue #3

### Pull requests

Pull request #1

Pull request #2

Pull request #3

### Arbre de commits
