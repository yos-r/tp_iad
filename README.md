# Système Multi-Agents JADE - TP IAD

Ce projet est une introduction aux systèmes multi-agents en Java utilisant le framework **JADE** (Java Agent Development Framework).

## Table des matières

- [Vue d'ensemble](#vue-densemble)
- [Structure du projet](#structure-du-projet)
- [Installation et compilation](#installation-et-compilation)
- [Exécution](#exécution)
- [Agents disponibles](#agents-disponibles)
- [Exemples d'utilisation](#exemples-dutilisation)
- [Concepts clés](#concepts-clés)

## Vue d'ensemble

Ce projet démontre les concepts fondamentaux des systèmes multi-agents FIPA (Foundation for Intelligent Physical Agents) :

- **Enregistrement de services** : Les agents s'enregistrent auprès du Directory Facilitator (DF)
- **Découverte de services** : Les agents cherchent d'autres agents via le DF
- **Communication asynchrone** : Échange de messages entre agents
- **Comportements** : Exécution de tâches en parallèle au sein d'un agent

## Structure du projet

```
tp1/
├── src/projectsma/
│   ├── ProjectAgent.java           # Agent offrant un service
│   ├── SearchAgent.java            # Agent cherchant des services
│   ├── SenderAgent.java            # Agent envoyant un message simple
│   ├── SenderAgent2.java           # Agent envoyant et attendant une réponse
│   ├── ReceiverAgent.java          # Agent recevant les messages
│   ├── ReceiverBehaviour.java      # Comportement simple de réception
│   ├── ReceiverBehaviour2.java     # Comportement alternatif
│   └── ReceiverCyclicBehaviour.java # Comportement cyclique (boucle infinie)
├── lib/
│   └── jade.jar                    # Framework JADE
├── bin/                            # Fichiers compilés (généré automatiquement)
└── CLAUDE.md                       # Guide pour Claude Code
```

## Installation et compilation

### Prérequis

- **Java JDK 8+** installé
- **JADE 4.6.0** (fourni dans le dossier `lib/`)

### Compiler le projet

Pour compiler tous les fichiers Java :

```bash
javac -d bin -cp "lib/*" src/**/*.java
```

Pour compiler un fichier spécifique :

```bash
javac -d bin -cp "lib/*" src/projectsma/ProjectAgent.java
```

## Exécution

### 1. Enregistrement et découverte de services

Démonstration de la découverte de services via le Directory Facilitator :

```bash
javac -d bin -cp "lib/*" src/**/*.java
java -cp "bin;lib/*" jade.Boot -gui -agents "agent1:projectsma.ProjectAgent(construction);agent2:projectsma.ProjectAgent(blanchissement);boss:projectsma.SearchAgent"
```

**Résultat attendu** :
```
Hello. My name is agent1 and I provide construction service.
Hello. My name is agent2 and I provide blanchissement service.
[OK] agent1 registered with DF. Service type: construction
[OK] agent2 registered with DF. Service type: blanchissement
Hello. I am boss.
[RECHERCHE] boss found 2 agents providing 'construction' service
boss: agent1@172.26.32.1:1099/JADE provides construction
[OK] boss search completed successfully
```

### 2. Communication simple (SenderAgent → ReceiverAgent)

```bash
java -cp "bin;lib/*" jade.Boot -gui -agents "sender:projectsma.SenderAgent;Receiver:projectsma.ReceiverAgent"
```

### 3. Communication avec réponse

```bash
java -cp "bin;lib/*" jade.Boot -gui -agents "sender:projectsma.SenderAgent2;Receiver:projectsma.ReceiverAgent"
```

### 4. Lancer la GUI JADE uniquement

```bash
java -cp "bin;lib/*" jade.Boot -gui
```

Vous pourrez alors créer des agents manuellement via l'interface graphique.

## Agents disponibles

### ProjectAgent
- **Rôle** : Offre un service spécifique
- **Service** : Prend un paramètre (ex: "construction", "blanchissement")
- **Actions** :
  - S'enregistre auprès du DF avec son service
  - Affiche un message de confirmation
  - Se désenregistre proprement à l'arrêt

**Utilisation** :
```bash
agent1:projectsma.ProjectAgent(construction)
agent2:projectsma.ProjectAgent(blanchissement)
```

### SearchAgent
- **Rôle** : Cherche et affiche les services disponibles
- **Cible** : Cherche les agents offrant le service "construction"
- **Actions** :
  - Attend 2 secondes (pour que les ProjectAgents se registrent)
  - Interroge le DF
  - Affiche tous les agents offrant le service

**Utilisation** :
```bash
boss:projectsma.SearchAgent
```

### SenderAgent
- **Rôle** : Envoie un message simple
- **Destinataire** : ReceiverAgent
- **Actions** :
  - Crée un message INFORM
  - Envoie "Hello! How are you?" au ReceiverAgent
  - Affiche un message de confirmation

**Utilisation** :
```bash
sender:projectsma.SenderAgent
```

### SenderAgent2
- **Rôle** : Envoie un message et attend une réponse
- **Destinataire** : ReceiverAgent
- **Actions** :
  - Envoie un message
  - Attend la réponse avec `blockingReceive()`
  - Affiche la réponse reçue

**Utilisation** :
```bash
sender:projectsma.SenderAgent2
```

### ReceiverAgent
- **Rôle** : Reçoit et traite les messages
- **Comportement** : Ajoute un `ReceiverBehaviour` en tant que comportement
- **Actions** :
  - Reste prêt à recevoir des messages
  - Traite les messages arrivants
  - Envoie une réponse

**Utilisation** :
```bash
Receiver:projectsma.ReceiverAgent
```

### ReceiverBehaviour
- **Type** : SimpleBehaviour
- **Mode** : S'exécute une seule fois
- **Actions** :
  - Reçoit un message INFORM
  - Affiche le contenu
  - Envoie une réponse "Thank you for your message!"

### ReceiverCyclicBehaviour
- **Type** : CyclicBehaviour
- **Mode** : Boucle infiniment
- **Actions** :
  - Cherche les messages INFORM
  - Si un message arrive : répond
  - Si aucun message : libère les ressources avec `block()`

## Exemples d'utilisation

### Exemple 1 : Découverte de services

```bash
javac -d bin -cp "lib/*" src/**/*.java
java -cp "bin;lib/*" jade.Boot -gui \
  -agents "agent1:projectsma.ProjectAgent(construction);\
           agent2:projectsma.ProjectAgent(blanchissement);\
           boss:projectsma.SearchAgent"
```

**Ce qui se passe** :
1. `agent1` et `agent2` démarrent et s'enregistrent auprès du DF
2. `boss` attend 2 secondes
3. `boss` interroge le DF pour trouver les agents offrant "construction"
4. `boss` affiche les résultats

### Exemple 2 : Communication simple

```bash
java -cp "bin;lib/*" jade.Boot -gui \
  -agents "sender:projectsma.SenderAgent;\
           Receiver:projectsma.ReceiverAgent"
```

**Ce qui se passe** :
1. `sender` envoie "Hello! How are you?" au `Receiver`
2. `Receiver` reçoit le message et répond "Thank you for your message!"
3. `sender` affiche un message de confirmation

### Exemple 3 : Communication avec attente de réponse

```bash
java -cp "bin;lib/*" jade.Boot -gui \
  -agents "sender:projectsma.SenderAgent2;\
           Receiver:projectsma.ReceiverAgent"
```

**Ce qui se passe** :
1. `sender` envoie un message et attend la réponse
2. `Receiver` reçoit et répond
3. `sender` affiche la réponse

## Concepts clés

### Directory Facilitator (DF)

Le DF est un service système JADE qui agit comme un annuaire :
- Les agents s'y enregistrent pour annoncer leurs services
- Les agents interrogent le DF pour trouver des services
- Le DF est lancé automatiquement par JADE

**Enregistrement** :
```java
DFAgentDescription dfd = new DFAgentDescription();
dfd.setName(this.getAID());

ServiceDescription sd = new ServiceDescription();
sd.setType("construction");
sd.setName("construction");
dfd.addServices(sd);

DFService.register(this, dfd);
```

**Recherche** :
```java
DFAgentDescription dfd = new DFAgentDescription();
ServiceDescription sd = new ServiceDescription();
sd.setType("construction");
dfd.addServices(sd);

DFAgentDescription[] result = DFService.search(this, dfd);
```

### Messages ACL (Agent Communication Language)

Les agents communiquent via des messages ACL normalisés par FIPA.

**Types de performatif** :
- `INFORM` : Informer l'autre agent
- `REQUEST` : Demander une action
- `QUERY_REF` : Poser une question
- `AGREE` : Accepter une requête
- `REFUSE` : Refuser une requête

**Exemple d'envoi** :
```java
ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
msg.addReceiver(new AID("Receiver", AID.ISLOCALNAME));
msg.setContent("Hello! How are you?");
send(msg);
```

**Exemple de réception** :
```java
ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
if (msg != null) {
    String content = msg.getContent();
    ACLMessage reply = msg.createReply();
    reply.setContent("Thank you!");
    send(reply);
}
```

### Comportements (Behaviours)

Les comportements permettent aux agents d'exécuter des tâches en parallèle.

**Types de comportements** :
- **SimpleBehaviour** : S'exécute une seule fois
- **CyclicBehaviour** : Boucle infiniment
- **TickerBehaviour** : S'exécute périodiquement
- **ParallelBehaviour** : Exécute plusieurs comportements en parallèle

**Exemple** :
```java
protected void setup() {
    addBehaviour(new ReceiverCyclicBehaviour());
}
```

### Cycle de vie d'un agent

1. **setup()** : Appelée une seule fois au démarrage
2. **run()** : L'agent exécute ses comportements
3. **takeDown()** : Appelée à l'arrêt pour nettoyer

### Blocage et libération des ressources

Pour éviter une consommation excessive de CPU quand il n'y a rien à faire :

```java
ACLMessage msg = receive(mt);
if (msg != null) {
    // Traiter le message
} else {
    this.block();  // Libérer les ressources
}
```

## Syntaxe des paramètres d'agent

Lors du lancement avec `-agents`, on peut passer des paramètres :

```bash
-agents "nomAgent:packageName.ClassName(param1,param2,param3)"
```

**Exemples** :
```bash
-agents "agent1:projectsma.ProjectAgent(construction)"
-agents "agent1:projectsma.ProjectAgent(construction);agent2:projectsma.ProjectAgent(blanchissement)"
-agents "agent1:projectsma.ProjectAgent(construction);agent2:projectsma.ProjectAgent(blanchissement);boss:projectsma.SearchAgent"
```

Les paramètres sont récupérés dans `setup()` via `getArguments()`.

## Ressources supplémentaires

- [Documentation officielle JADE](http://jade.tilab.com/)
- [Guide FIPA](https://www.fipa.org/)
- [Tutoriels JADE](http://jade.tilab.com/doc/tutorials/)

---

**Auteur** : TP IAD 2025
**Framework** : JADE 4.6.0
