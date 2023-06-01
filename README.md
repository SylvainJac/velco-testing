# velco-testing
Velco testing

Velco testing permet de transformer un fichier .txt en Json en retour de la requête.

## End point
Le endpoint exposé est le suivant:

### Path
POST /files

### Params
- sort: asc, desc
- field: numReference, type, price, size 

### Body
Type form-data avec une key file contenant un fichier .txt

### Authentification
Une authentification basic est en place:

username: username

password: password


Exemple:
```
curl -L 'http://localhost:8080/files?sort=asc&field=size' -H 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' -F 'file=@"/home/projet/velco/velco.txt"'
```
Exemple de réponse:
```
{
    "inputFile": "velco.txt",
    "references": [
        {
            "numReference": "1450100040",
            "size": 27,
            "price": 45.12,
            "type": "R"
        },
        {
            "numReference": "1450100049",
            "size": 45,
            "price": 13.0,
            "type": "B"
        },
        {
            "numReference": "1450100050",
            "size": 46,
            "price": 14.0,
            "type": "R"
        },
        {
            "numReference": "1450100048",
            "size": 145,
            "price": 12.0,
            "type": "G"
        }
    ],
    "errors": [
        {
            "line": 2,
            "message": "NUM_REFERENCE",
            "value": "G;G;12.0;145"
        },
        {
            "line": 4,
            "message": "NUM_REFERENCE",
            "value": "G;G;12.0;145"
        },
        {
            "line": 6,
            "message": "TYPE",
            "value": "1450100051;T;14.0;46"
        },
        {
            "line": 7,
            "message": "PRICE",
            "value": "1450100052;R;R;46"
        },
        {
            "line": 8,
            "message": "SIZE",
            "value": "1450100053;R;14.0;E"
        }
    ],
    "metadata": {
        "referenceCount": 4,
        "errorCount": 5,
        "lineCount": 9
    }
}
```
## Fichier d'entrée
Le fichier d'entrée doit avoir une forme particulière, si ce n'est pas au bon format une erreur va être retourné pour cette ligne.
- Numéro de référence un nombre de longueur de 10 digit
- Un type R,G ou B
- Un prix en type double
- Une size en entier


Exemple:
```
1450100040;R;45.12;27
1450100048;G;12.0;145
```

## Configuration
Pour augmenter sa ré-utilisabilité un certain nombre de paramètre on été mis en configuration:

- velco.separator, le séparateur de chaque colonne
- velco.extension, l'extension du fichier voulu
- velco.user.username, username pour l'authentification
- velco.user.password, le mot de passe pour l'authentification
- velco.index.numReference, l'index de la colonne du numéro de référence
- velco.index.type, l'index de la colonne du type
- velco.index.price, l'index de la colonne du prix
- velco.index.size, l'index de la colonne de la taille
