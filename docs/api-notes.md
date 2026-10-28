# Atelier 5 - Notes sur l'API REST AutoLoc

## A. Endpoints
| Contrôleur | Verbe + Route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| ContratController | GET /api/contrats | findAll() | 200 | - |
| ContratController | GET /api/contrats/{id} | findById() | 200 | 404 |
| ContratController | POST /api/contrats | create() | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | update() | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | deleteById() | 204 | 404 |
| PaiementController | GET /api/paiements | findAll() | 200 | - |
| PaiementController | GET /api/paiements/{id} | findById() | 200 | 404 |
| (Idem pour les autres contrôleurs : Agence, Client, Vehicule, etc.) | ... | ... | ... | ... |

## B. Annotations Jackson
- **Contrat / Paiement** : Utilisation de @JsonManagedReference (sur Contrat) et @JsonBackReference (sur Paiement). 
  - *Justification* : C'est une relation de composition. Le contrat possède les paiements. Le @JsonBackReference coupe la boucle infinie (StackOverflowError) lors de la sérialisation JSON.
- **Agence / Vehicule** : Utilisation de @JsonIgnore sur la liste des véhicules dans Agence.
  - *Justification* : Un véhicule existe indépendamment de l'agence. On l'utilise pour éviter d'exposer toute la flotte quand on demande une agence.
- *Différence* : @JsonBackReference est utilisé pour casser une boucle bidirectionnelle parent-enfant, tandis que @JsonIgnore exclut complètement un champ du JSON.

## C. Postman et Swagger
- **Postman** : Sert à tester les requêtes HTTP réelles, vérifier les codes d'erreur (404, 400) et tester la cascade PERSIST (création d'un contrat avec ses paiements dans le même JSON).
- **Swagger UI** : Sert de documentation interactive générée automatiquement. Il permet de visualiser les endpoints et de les tester depuis le navigateur. Les deux sont complémentaires.

## D. Anomalies SonarQube
| # | Fichier(s) | Anomalie | Règle | Correction |
|---|---|---|---|---|
| 1 | Tous les contrôleurs | Replace this persistent entity with a simple POJO or DTO object | S4684 | Non corrigée volontairement. Le PDF (p.3) précise que l'exposition des entités est temporaire. Les DTO seront vus au Chapitre 10. |
| 2 | ContratController | Import inutilisé | S1128 | Supprimé via le raccourci Ctrl+Alt+O. |

## E. Questions de compréhension
1. **Quel composant reçoit toutes les requêtes ?** Le DispatcherServlet. Il utilise le HandlerMapping pour trouver la méthode du contrôleur correspondant à l'URL et au verbe HTTP.
2. **Pourquoi dépendre de IContratService et non du Repository ?** Pour respecter le principe d'inversion de dépendance (SOLID). Le contrôleur ne doit pas connaître la couche d'accès aux données, il délègue la logique métier au service.
3. **Pourquoi POST = 201 et DELETE = 204 ?** POST crée une ressource (201 Created). DELETE la supprime et il n'y a plus rien à retourner (204 No Content). Une ressource absente donne 404 (Not Found) car c'est une erreur côté client, pas une erreur serveur (500).
4. **Pourquoi PaiementController n'a-t-il que des GET ?** Car les paiements sont gérés exclusivement via le Contrat (cascade). Un POST sur /paiements renvoie une erreur **405 Method Not Allowed**.
5. **Risques d'exposer les entités JPA ?** Cela expose la structure de la base de données et peut créer des problèmes de sécurité ou de performance. @JsonManagedReference est utilisé pour afficher les enfants (paiements), @JsonIgnore pour cacher les relations inutiles (véhicules d'une agence).
