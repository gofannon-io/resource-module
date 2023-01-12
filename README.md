# resource-module

Cet article regroupe un ensemble de tests pour comprendre la gestion des resources dans des projets java modularisés.

## Objectifs détaillés

L'accès à une resource est conditionné par 3 facteurs :
* *Resource module* : le JAR/module contenant la resource
* *Resource path* : la localisation du fichier resource au sein du JAR/module 
* *Resource loader* : le module contenant la classe qui effectue le chargement

La combinatoire de ces trois facteurs doit permettre de mieux comprendre le fonctionnement de l'accès aux modules.

Dans le cadre des tests, l'environnement sera le suivant :
* *theapp* une application *Named module*
* *lib* une librairie *Named module*' qui est utilisée par *theapp*
* *commons-io*, une librairie *Unnamed module* qui est utilisée par *theapp* et/ou *liba*


| Test ID | Resource loader | Resource module | Resource path    | Projets         | Résultat |
|---------|-----------------|-----------------|------------------|-----------------|----------|
| 001     | theapp          | theapp          | root package     | theapp_a        | OK       |
| 002     | theapp          | theapp          | exported package | theapp_a        | OK       |
| 003     | theapp          | theapp          | internal package | theapp_a        | OK       |
| 004     | theapp          | theapp          | opened package   | theapp_a        | OK       |
| 005     | commons-io      | theapp          | root package     | theapp_b        | KO       |
| 006     | commons-io      | theapp          | exported package | theapp_b        | KO       |
| 007     | commons-io      | theapp          | internal package | theapp_b        | KO       |
| 008     | commons-io      | theapp          | opened package   | theapp_b        | KO       |
| 009     | theapp          | lib             | root package     | theapp_c, lib_d | KO       |
| 010     | theapp          | lib             | exported package | theapp_c, lib_d | KO       |
| 011     | theapp          | lib             | internal package | theapp_c, lib_d | KO       |
| 012     | theapp          | lib             | opened package   | theapp_c, lib_d | KO       |



