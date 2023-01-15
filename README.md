# resource-module

## Aperçu
Cet article regroupe un ensemble de tests pour comprendre la gestion des resources dans des projets java avec des modules.


## La documentation officielle (Javadoc) 
La méthode java.lang.Class#getResource(String) est utilisée pour obtenir l'URL d'un fichier de données embarquée dans un JAR. 
Ce JAR peut être le JAR courant ou un JAR externe.

Sa documentation (S1) indique qu'il y a des changements relatifs aux modules&nbsp;:
```text
If this class is in a named Module then this method will attempt to find the resource in the module. 
This is done by delegating to the module's class loader findResource(String,String) method, invoking it with the 
module name and the absolute name of the resource. 
Resources in named modules are subject to the rules for encapsulation specified in the Module getResourceAsStream method 
and so this method returns null when the resource is a non-".class" resource in a package that is not open to the 
caller's module. 
```
Cela indique que l'accès aux ressources est conditionnée par la configuration du module.

La documentation relative à la méthode java.lang.Module#getResourceAsStream(String) (S2) indique&nbsp;:
```text
A resource in a named module may be encapsulated so that it cannot be located by code in other modules. 
Whether a resource can be located or not is determined as follows:

   If the resource name ends with ".class" then it is not encapsulated.
   A package name is derived from the resource name. If the package name is a package in the module then the resource 
   can only be located by the caller of this method when the package is open to at least the caller's module. 
   If the resource is not in a package in the module then the resource is not encapsulated.

In the above, the package name for a resource is derived from the subsequence of characters that precedes the last '/' 
in the name and then replacing each '/' character in the subsequence with '.'. 
A leading slash is ignored when deriving the package name. As an example, the package name derived for a resource named 
"a/b/c/foo.properties" is "a.b.c". 
A resource name with the name "META-INF/MANIFEST.MF" is never encapsulated because "META-INF" is not a legal package name.

This method returns null if the resource is not in this module, the resource is encapsulated and cannot be located by 
the caller, or access to the resource is denied by the security manager. 
```

Cet extrait présente le concept de ressource et l'algorithme permettant d'identifier quand il s'applique à une ressource.








## Objectifs détaillés

L'accès à une resource est conditionné par 3 facteurs :
* *Resource module* : le JAR/module contenant la resource
* *Resource path* : le type de localisation du fichier resource au sein du JAR/module.
* *Resource loader* : le module contenant la classe qui effectue le chargement

La combinatoire de ces trois facteurs doit permettre de mieux comprendre le fonctionnement de l'accès aux modules.

Dans le cadre des tests, l'environnement sera le suivant :
* *theapp* une application *Named module*
* *lib* une librairie *Named module*' qui est utilisée par *theapp*
* *commons-io*, une librairie *Unnamed module* qui est utilisée par *theapp* et/ou *liba*

Les différents types de 'resource path' sont les suivants&nbsp;:
  * *root package* : la resource est à la racine du JAR
  * *exported package* : la ressource est dans un package contenant au moins une classe et qui est marquée comme 'exports' dans **module-info.java**.
  * *opened package* : la resource est dans un package contenant au moins une classe et qui est marquée comme 'opened' dans **module-info.java**.
  * *internal package* : la resource est dans un package contenant au moins une classe et n'apparaît pas dans **module-info.java**.
  * *opened directory* : la resource est dans un "package" ne contenant aucune classe et n'apparaît pas dans **module-info.java**.
  * *internal directory* : la resource est dans un "package" ne contenant aucune classe et n'apparaît pas dans **module-info.java**.

A noter que le concept de *exported directory* (ie la resource est dans un "package" ne contenant aucune classe et qui 
n'apparaît pas dans **module-info.java**) n'est pas supporté par Java.
L'erreur suivante se produit.
```text
error: package is empty or does not exist
```



| Test ID | Resource loader | Resource module | Resource path    | Projets         | Résultat |
|---------|-----------------|-----------------|------------------|-----------------|----------|
| 005     | commons-io      | theapp          | root package     | theapp_b        | KO       |
| 006     | commons-io      | theapp          | exported package | theapp_b        | KO       |
| 007     | commons-io      | theapp          | internal package | theapp_b        | KO       |
| 008     | commons-io      | theapp          | opened package   | theapp_b        | KO       |
| 009     | theapp          | lib             | root package     | theapp_c, lib_d | KO       |
| 010     | theapp          | lib             | exported package | theapp_c, lib_d | KO       |
| 011     | theapp          | lib             | internal package | theapp_c, lib_d | KO       |
| 012     | theapp          | lib             | opened package   | theapp_c, lib_d | KO       |




## Tests de chargement de ressource locale via URL d'un named module
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources locales (ie dans le JAR courant).

Les tests à effectuer étant locaux, ils sont assez simples&nbsp;:

| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 001     | theapp          | theapp          | root package       | theapp_a        | OK       |
| 002     | theapp          | theapp          | exported package   | theapp_a        | OK       |
| 003     | theapp          | theapp          | internal package   | theapp_a        | OK       |
| 004     | theapp          | theapp          | opened package     | theapp_a        | OK       |
| 005     | theapp          | theapp          | internal directory | theapp_a        | OK       |
| 006     | theapp          | theapp          | opened directory   | theapp_a        | OK       |

On note que tous les résultats sont OK, car la notion de module n'a pas cours au sein d'un même module.

Tout le code source est disponible dans le projet [theapp_a](./theapp_a/)


## Test de chargement de resource externe via URL d'un named module
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources externes dans un 
'named module'.
La classe utilisée pour charger la resource appartient à l'application qui ne contient pas la resource.


| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 101     | theapp          | lib             | root package       | theapp_b, lib_b | KO       |
| 102     | theapp          | lib             | exported package   | theapp_b, lib_b | KO       |
| 103     | theapp          | lib             | internal package   | theapp_b, lib_b | KO       |
| 104     | theapp          | lib             | opened package     | theapp_b, lib_b | KO       |
| 105     | theapp          | lib             | internal directory | theapp_b, lib_b | KO       |
| 106     | theapp          | lib             | opened directory   | theapp_b, lib_b | KO       |




## Test de chargement de resource externe via URL d'un named module depuis le classloader du module
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources externes dans un
'named module'.
La classe utilisée pour charger la resource appartient à la librairie qui contient la resource.

| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 201     | theapp          | lib             | root package       | theapp_c, lib_b | KO       |
| 202     | theapp          | lib             | exported package   | theapp_c, lib_b | KO       |
| 203     | theapp          | lib             | internal package   | theapp_c, lib_b | KO       |
| 204     | theapp          | lib             | opened package     | theapp_c, lib_b | OK       |
| 205     | theapp          | lib             | internal directory | theapp_c, lib_b | KO       |
| 206     | theapp          | lib             | opened directory   | theapp_c, lib_b | OK       |

On constate que cela fonctionne dans les deux cas où le package et le répertoire ont été indiqués comme open. 


## Test de chargement de resource externe via URL d'un named module depuis le classloader du module via une méthode d'un module unnamed 
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources externes dans un
'named module'.



| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 201     | theapp          | lib             | root package       | theapp_d, lib_b | KO       |
| 202     | theapp          | lib             | exported package   | theapp_d, lib_b | KO       |
| 203     | theapp          | lib             | internal package   | theapp_d, lib_b | KO       |
| 204     | theapp          | lib             | opened package     | theapp_d, lib_b | KO       |
| 205     | theapp          | lib             | internal directory | theapp_d, lib_b | KO       |
| 206     | theapp          | lib             | opened directory   | theapp_d, lib_b | KO       |

On constate que cela ne fonctionne dans aucun cas.


## Test de chargement de resource externe via InputSteam d'un named module depuis le classloader du module via une méthode d'un module unnamed
Cette série de tests portent sur le cas d'une application 'named module' qui charge des ressources externes dans un
'named module'.



| Test ID | Resource loader | Resource module | Resource path      | Projets         | Résultat |
|---------|-----------------|-----------------|--------------------|-----------------|----------|
| 201     | theapp          | lib             | root package       | theapp_e, lib_b | KO       |
| 202     | theapp          | lib             | exported package   | theapp_e, lib_b | KO       |
| 203     | theapp          | lib             | internal package   | theapp_e, lib_b | KO       |
| 204     | theapp          | lib             | opened package     | theapp_e, lib_b | KO       |
| 205     | theapp          | lib             | internal directory | theapp_e, lib_b | KO       |
| 206     | theapp          | lib             | opened directory   | theapp_e, lib_b | KO       |

On constate que cela fonctionne dans les deux cas où le package et le répertoire ont été indiqués comme open.


## Sources
* S1: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Class.html#getResource(java.lang.String)
* S2: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Module.html#getResourceAsStream(java.lang.String)