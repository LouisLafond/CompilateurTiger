# TP Projet compilation des langages 

## Introduction

L'objectif de ce TP est l'apprentissage et la mise en oeuvre de l'utilisation d'Antlr.

Antlr est un générateur d’analyseur syntaxique descendant. Il permet de générer un "parseur" de langage à partir d'une grammaire.

Pour ce TP, nous utiliserons la version 4 d'Antlr. En première année, vous avez étudié les analyseurs syntaxiques LL(1). Antlr permet de générer des analyseurs LL(*). Cela signifie que l'analyseur connaît le nombre minimum de caractères nécessaires à lire pour pouvoir analyser un texte sans ambiguïté. Grâce à cela, nous allons pouvoir écrire les grammaires beaucoup plus simplement.

## Partie 0 : Préambule

Ce dépôt git contient déjà le jar d'Antlr. 

Notez que plusieurs IDE comme visual studio code ou Jetbrains InteliJ possèdent des extensions qui facilitent le dévelopement avec Antlr. Nous vous invitons à les installer. 


## Partie 1 : Première grammaire

#### a - Etude de la grammaire

Ouvrir le fichier __expr.g4__ situé à la racine du projet.

Le fichier commence par le nom de la grammaire.

```grammar expr;```. 
Il est important de noter que la grammaire doit avoir le même nom que le fichier.

Une règle grammaticale se construit de la façon suivante: 
```nom_de_la_regle : contenu ; ```

ainsi, la règle grammaticale A -> AB s'écrit dans antlr sous la forme:
```a : ab ;```

On peut remarquer deux types de règles différentes. 
Les règles "en majuscules" (comme par exemple la règle INT)  permettent de décrire les terminaux de la grammaire. On les appelle les règles d'analyse lexicale.

Les règles, écrites  "en minuscules" (comme par exemple la règle exp), sont des productions de la grammaire. Notez bien que les non-terminaux sont écrits en minuscules sous Antlr. C'est l'inverse de ce l'on voit habituellement dans les ouvrages de théorie des langages.

On peut remarquer les choses suivantes :

* Les tokens (ou unités lexicales) dans les règles grammaticales sont encadrés par des quotes simple (‘+’)
* La syntaxe des expressions régulières (?, +, *) est également utilisable dans Antlr.
* Chaque règle se termine par un ;
* Il est nécessaire de différencier les règles terminales (en majuscule) des règles non terminales (en minuscule). Dans le fichier contenant la grammaire, les régles décrivant les unités lexicales sont placées habituellement à la fin du fichier. 
* Dans les règles terminales, on peux utiliser la syntaxe .. pour désigner un ensemble de caractères. (‘0’..’9’) signifie l’ensemble des caractères entre 0 et 9. 
* La barre verticale | est un OU, elle permet de lister deux alternatives pour une même règle (et même à l’intérieur d’une règle). 
On reconnait dans cette notation la syntaxe des expressions régulières (regexp) vue dans le module "Shell".


Bon, il est temps de pratiquer un peu maintenant.

#### b - Les identifiants

En vous appuyant sur la grammaire expr, écrivez la règle de lexer permettant de definir les identifiants (on la notera IDF). Ils obéissent aux règles suivante :
* Un identifiant peut contenir des lettres, des chiffres et le caractère '_'
* Un identifiant ne peux pas commencer par un chiffre

#### c - Affectation de variable

Ecrivez une rêgle permettant l'affectation de variable à une expression de la forme.

```nom_variable = expression ;```

Cette règle doit respecter la syntaxe suivante :
* La règle se termine par un __;__
* Seule une variable peut être affectée
* Une expression peut être une variable ou un calcul

(indice : Vous avez le droit de modifier les règles précédentes et d'ajouter des règles auxiliaires)

#### d - Instruction et programme

Créez une rêgle __instruction__ qui contiendra l'ensemble des instructions du langage. Pour le moment, la seule instruction que nous connaissons est l'affectation, nous allons en ajouter dans les parties suivantes.

Modfiez ensuite la rêgle __program__ en commentaires qui correspond à une suite non nulle d'instructions terminées par le caractère spécial __EOF__ (ne l'oubliez pas sinon vous aurez des erreurs à la fin).

Cette règle sera l'axiome de notre grammaire (nous verrons ça plus tard).


#### d - Instruction conditionnelle If

Ajoutez une règle permettant l'écriture d'une instruction conditionnelle __if__ dont la syntaxe sera la suivante : 
``` if (condition) {instruction+} else {instruction+}```

Le if obéit aux contraintes suivantes:
* La condition est soit une expression, soit une variable
* La partie ```else {instruction+}``` est optionnelle
Ajoutez ensuite cette rêgle dans la liste des instructions

#### d - Instruction d'affichage Print

Ajoutez une règle print qui obéit à la syntaxe suivante: 
```print valeur ;```

La règle print obéit aux contraintes suivantes : 
* La valeur est soit une expression, soit une variable

Ajoutez ensuite cette règle à la liste des instructions

#### e - Ignorer certains caractères

Lorsque l'on écrit dans un langage de programmation, on ignore les espaces et les retours à la ligne (bon sauf en Python). 
Espacer les identificateur et les lignes  permet une meilleure lisibilité du code.

Il faut donc dire au lexer d'ignorer certains caractères lors de l'analyse.

Antlr permet de réaliser cette opération grâce à  l'instruction suivante:
```
WS : [\n]+ -> skip ;
```

Cette instruction permet de dire a l'analyseur lexical d'ignorer les caractères de l'expression régulière ```[\n]+```. 
Cette règle permet donc d'ignorer les retours à la ligne.

Ajoutez cette règle dans votre grammaire (dans la partie lexicale, c'est à dire à la fin du fichier) et modifiez-la pour ignorer les tabulations et les espaces.


## Partie 2 : Priorisation des opérateurs

#### a - Petit rappel
En première année, vous avez travaillé sur l'analyse syntaxique descendante en MI. Durant l'un des TD, on vous a présenté le concept de priorisation d'opérateur. Comme un petit rappel ne fait jamais de mal, nous vous proposons l'exercice suivant:

Ecrivez l'arbre syntaxique correspondant à l'expression arithmétique __2*3+4__ en utilisant la grammaire suivante:
A -> B (*|+) A 
     B       
B -> idf 
     int

Vous devez remarquer que l'ordre des opérateurs n'est pas respecté, la multiplication se trouvant "plus haut" dans l'arbre que l'addition. 

Durant l'exécution, le calcul 3+4 sera donc effectué avant le calcul 2*3, ce qui ne respecte pas la priorité des opérateurs.

Il faut donc utiliser une méthode pour prioriser l'opérateur * sur l'opérateur +.

Pour cela, on décompose la première règle en plusieurs sous règles (une par niveau de priorité). Cela donne la grammaire :
A -> B + A | B
B -> C * B | C
C -> idf | int

De cette façon, l'opérateur + apparaîtra toujours avant l'opérateur * dans l'arbre syntaxique, et plus tard dans l'arbre abstrait (AST).

#### b - Implémentation dans la grammaire expr

Antlr permet d'éviter l'utilisation de grammaire récursive comme celle de la partie précédente. Nous pouvons à la place utiliser l'étoile __*__ pour écrire toutes les opérations de la même priorité sur une seule règle. Dans la règle exp, vous devrez avoir quelque chose de la forme :
```exp : (INT|IDF) ( ('+'|'-'|'*'|'/')   (INT|IDF) )* ;```

Dans cette règle, les opérateurs sont ajoutés les uns à la suite des autres. Bien que cette façon de faire soit contre-intuitive (on aimerait construire l'arbre de priorité tout de suite), elle simplifiera énormément l'écriture de l'AST dans les prochains TP.

En vous appuyant sur ce principe, priorisez les opérateurs de la rêgle exp.


## Partie 3 : Compilation de la grammaire et tests


#### a - compilation du parser

Nous avons terminé l'écriture de notre grammaire, il serait temps de la lancer pour voir si ça fonctionne, non ?

Pour cette partie, il est nécessaire de posséder un JDK dans sa version 14. Pour l'installation du jdk, référez-vous aux liens suivants[INSERER LIEN JAVA]


Commençons par exécuter le jar situé dans le dossier lib.

```bash
java -jar antlr-4.9.2-complete.jar
```

Cela devrait vous afficher l'ensemble des commandes utilisables. Les plus importantes sont :
* ```-o [path]``` qui permet de choisir la destination des fichiers compilés par antlr
* ```-visitor```, ```-no-visitor```, ```-listener```, ```-no-listener``` qui permettent de créer ou non des classes facilitant le traitement de l'arbre syntaxique (nous allons les ignorer pour le moment mais elles seront très utiles au prochain TP)
* ```-Dlanguage=[language]``` qui permet de spécifier le langage de sortie d'Antlr. En effet Antlr permet de générer des parsers dans de nombreux langages (Python, C/C++, Java, Rust, Javascript ...).


Pour le moment, nous utiliserons le langage Java qui est le langage par défaut d'Antlr. 

Compilez avec la commande :
```bash
java -jar antlr-4.9.2-complete.jar expr.g4 -no-listener -no-visitor -o ./src/parser
```

Si tout se passe bien, cela devrait générer dans le dossier src un sous dossier parser ainsi que 2 classes java :
* ```exprLexer.java``` : L'analyseur lexical
* ```exprParser.java``` : L'analyseur syntaxique

Ces deux classes permettent d'analyser un texte et de vérifier s'il peut être reconnu par la grammaire expr.


#### b - Utilisation du parser

Le parser généré par Antlr utilise les instructions suivantes:


```java        
CharStream input = CharStreams.fromFileName(testFile);
exprLexer lexer = new exprLexer(input); 
CommonTokenStream stream = new CommonTokenStream(lexer);
exprParser parser = new exprParser(stream);
```

Le programme lit d'abord une chaîne de caractères, puis il la passe à l'analyseur lexical (ligne 2). Ceci permet de transformer la chaîne de caractères en une suite de mots (ou token) du langage (par exemple __'if'__).
On utilise ensuite le lexer pour transformer la chaîne de départ en chaîne de token (ligne 3). Pour finir, on analyse syntaxiquement (parse) la chaine de Token grâce à la classe de parser générée par antlr.

Pour récupérer l'arbre syntaxique, il suffit d'exécuter la commande suivante:

```java
ProgramContext program = parser.program();
```

L'objet program représente la racine de l'arbre syntaxique.

Pour l'afficher, nous utilisons un petit code situé dans la fonction main().


#### c - Affichage de l'arbre syntaxique


La classe Main à la racine de src contient tout ce qui est nécessaire pour tester notre parser. Essayez de compiler Main.java avec la commande :
```javac -target 14 -cp ./lib/antlr-4.9.2-complete.jar:./src ./src/Main.java -d ./bin```

Normalement, vous devriez avoir une erreur de compilation car java ne reconnait pas exprLexer et exprParser. En effet, ces deux fichiers ne se trouvent pas dans un package, donc Java ne les reconnait pas. Pour corriger, nous allons modifier le fichier expr.g4 pour lui préciser d'ajouter des headers lorsqu'il compile la grammaire.

Au début du fichier expr.g4 (après la définition du nom de la grammaire), ajoutez les lignes suivantes : 
```
@header{
package parser;
}
```

Recompilez ensuite la grammaire, puis réexécutez la commande précédente. Vous devriez générer des fichiers .class dans le dossier bin



Vous pouvez tester avec les deux programmes du dossier examples avec la commande : 
```
java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main ./examples/good.exp
```

```
java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main ./examples/bad.exp
```







