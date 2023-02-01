# resource-module

## Aperçu
Cet article constitue une étude pour comprendre la gestion des resources dans des projets java avec des modules.

Les analyses sont menées par la documentation et surtout les résultats sont issus directement de programme de tests dont 
les sources sont en copie.

L'objectif est de fournir une liste exhaustive des moyens d'accès à une ressource et les localisations possibles d'une 
ressource et si l'accès a été possible ou pas. 


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


## Matrices d'accès aux ressources

### Série de tests A&nbsp;:
* resource loader : application class
* resource module : application

Cette série de tests permet juste de vérifier le bon fonctionnement de la méthode de chargement de ressources.


| Test ID | Resource loader   | Resource module | Resource path      |  Résultat |
|---------|-------------------|-----------------|--------------------|-----------|
| A01     | application class | application     | root package       |  OK       |
| A02     | application class | application     | exported package   |  OK       |
| A03     | application class | application     | internal package   |  OK       |
| A04     | application class | application     | opened package     |  OK       |
| A05     | application class | application     | internal directory |  OK       |
| A06     | application class | application     | opened directory   |  OK       |

On note que tous les résultats sont OK, car la notion de module n'a pas cours au sein d'un même module.

Le code source est le suivant :
```java
package com.example.resource_module.theapp;

public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = ResourceReader.class.getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1000];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                stringBuilder.append(buffer, 0, size);
            }
            return stringBuilder.toString();
        }
    }
}
```

Le détail est disponible [ici](documentation/tsa_local_resource_loading_url_named-module.md)



### Série de tests B&nbsp;:
* resource loader : application class
* resource module : library

| Test ID | Resource loader   | Resource module | Resource path      |  Résultat |
|---------|-------------------|-----------------|--------------------|-----------|
| B01     | application class | library         | root package       |  KO       |
| B02     | application class | library         | exported package   |  KO       |
| B03     | application class | library         | internal package   |  KO       |
| B04     | application class | library         | opened package     |  KO       |
| B05     | application class | library         | internal directory |  KO       |
| B06     | application class | library         | opened directory   |  KO       |

On constate que tous les résultats sont KO.

Le code source est le suivant :
```Java
package com.example.resource_module.theapp;
//[...]
public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = ResourceReader.class.getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1000];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                stringBuilder.append(buffer, 0, size);
            }
            return stringBuilder.toString();
        }
    }
}


```

Le détail est disponible [ici](documentation/tsb_external_resource_loading_url_named-module.md)



### Série de tests C&nbsp;:
* resource loader : library class
* resource module : library


| Test ID | Resource loader | Resource module  | Resource path      | Résultat  |
|---------|-----------------|------------------|--------------------|-----------|
| C01     | library class   | library          | root package       | **OK**    |
| C02     | library class   | library          | exported package   | KO        |
| C03     | library class   | library          | internal package   | KO        |
| C04     | library class   | library          | opened package     | **OK**    |
| C05     | library class   | library          | internal directory | KO        |
| C06     | library class   | library          | opened directory   | **OK**    |

On constate que cela fonctionne dans les deux cas où le package et le répertoire ont été indiqués comme open.

Le code source est le suivant :
```java
package com.example.resource_module.theapp;

import com.example.resource_module.lib_b.ExportedSample;

public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in = ExportedSample.class.getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1000];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                stringBuilder.append(buffer, 0, size);
            }
            return stringBuilder.toString();
        }
    }
}
```

Le détail est disponible [ici](documentation/tsc_external_resource_loading_url_named-module_local-classloader.md)



### Série de tests D&nbsp;:
* resource loader : library classloader
* resource module : library

| Test ID | Resource loader     | Resource module | Resource path      | Résultat |
|---------|---------------------|-----------------|--------------------|----------|
| D01     | library classloader | library         | root package       | KO       |
| D02     | library classloader | library         | exported package   | KO       |
| D03     | library classloader | library         | internal package   | KO       |
| D04     | library classloader | library         | opened package     | KO       |
| D05     | library classloader | library         | internal directory | KO       |
| D06     | library classloader | library         | opened directory   | KO       |

On constate que rien ne fonctionne.
D'une manière générale, le ClassLoader de la librairie ne permet pas de charger les ressources, alors qu'une classe 
chargée par ce ClassLoader le peut.  


Le code source est le suivant :
```java
package com.example.resource_module.theapp;

import com.example.resource_module.lib_b.ExportedSample;

public class ResourceReader {

    public static String loadAsString(String resourcePath) throws IOException {
        try (InputStream in =  ExportedSample.class.getClassLoader().getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1000];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                stringBuilder.append(buffer, 0, size);
            }
            return stringBuilder.toString();
        }
    }
}
```

## Cas de la ressource dans un "root package"

Le test C01 montre qu'une classe de la librairie peut charger une ressource dans le "root package" de la librairie.
C'est étonnant, car, avec les modules java :
* une application avec module ne s'exécute pas quand il existe une classe dans la racine d'un module (module application ou librairie. 
* il n'est pas possible d'instancier une classe du "root package", que ce soit une classe locale ou une classe dans une librairie
* le "root package" n'est pas exprimable dans un fichier "module-info.java" 

Message d'erreur affiché à l'exécution quand il y a une classe dans un "root package" (RootLib dans l'exemple) : 
```text
Error occurred during initialization of boot layer
java.lang.module.FindException: Error reading module: [...]\lib_b\build\libs\lib_b-1.0.jar
Caused by: java.lang.module.InvalidModuleDescriptorException: RootLib.class found in top-level directory (unnamed package not allowed in module)

Caused by: java.lang.module.InvalidModuleDescriptorException: RootLib.class found in top-level directory (unnamed package not allowed in module)
```


Ce n'est probablement pas un bogue, car ce potentiel dysfonctionnement est détectable très facilement.
La raison est sans doute ailleurs.
Une hypothèse est la rétro-compatibilité afin d'autoriser pour le chargement de certaines ressources. 




## Conclusion

Les méthodes et contextes permettant de charger une resource dans une librairie sont les suivants :

| Test ID | Resource loader   | Resource module | Resource path      | Résultat           |
|---------|-------------------|-----------------|--------------------|--------------------|
| C01     | library class     | library         | root package       | *OK* **(WARNING)** |
| C04     | library class     | library         | opened package     | OK                 |
| C06     | library class     | library         | opened directory   | OK                 |

On constate que seules les ressources déclarées dans un package "opened" et chargé depuis une classe de la librairie.

Étonnamment, une ressource placée dans le "root package" d'une librairie est accessible.
Néanmoins, comme l'usage "root packages" est très restrictif avec les modules Java, le dépôt de ressources en 
son sein est à proscrire.  

Seul reste le principe suivant :

**Les ressources accessibles à l'extérieur doivent appartenir à un package "opened" et être chargées depuis une classe 
locale du module (JAR) contenant la ressource.** 



## Sources
* S1: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Class.html#getResource(java.lang.String)
* S2: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Module.html#getResourceAsStream(java.lang.String)