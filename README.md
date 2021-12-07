Application for calculating french taxes on crypto gains.

Application pour calculer ses gains sur les cessions de crypto à l'aide de Coingecko (https://www.coingecko.com/)

Pré 2019: nous utilisons le calcul applicable au céssions de biens meubles

Post 2019: nous utilisons le nouveau régime spécifique pour les investisseurs occasionels en crypto-actif

**Rien ne garantit la fiabilité des calculs, contactez un avocat fiscaliste en cas de doute**

# Utilisation

## Qui est concerné par cette application ?

- Les traders occasionnels ayant un faible volume intra-day :

En effet nous utilisons Coingecko et l'API ne contient pas de données intra-day. Donc il est impossible de calculer vos gains qaund l'achat et la vente se font dans la même journée.

- L'application ne gère pas les profits et pertes issus du trading de produits dérivés.

Donc dans votre CSV faites attention à ne pas inclure de données venant de Bitmex ou autre car il y aura surement une erreur.

- Pour l'instant l'application ne calcul pas les "soultes"

## Les données de trading

Pour calculer vos gains nous avons besoins des données de trading sous forme d'un CSV comme ceci :

`Type MontantAchat CryptoAchat MontantVente CryptoVente MontantFrais CryptoFrais Echange Groupe Commentaire Date`

`"Trade","100.0","BTC","1000.00023","EUR","1.00","EUR","Kraken","international","Wow quelle affaire!","2017-01-01 01:00:00"`

(exemple dans : ressources/cointracking-table0.csv)

Nous pouvons facilement créer un CSV avec le même format chez https://cointracking.info/.
Il est facile d'importer vos csv issus d'échanges sur cette plateforme et garder votre vie privée sans utiliser les services d'API si vous le souhaitez.
Créez juste un email spécifique à cet utilisation et utilisez un VPN.


Il ne sert à rien d'importer les données concernant les transferts ( `Deposit` et `Withdrawal`).

### Transactions spécifiques: `Spend` et `Income`

Pour ces transactions il faut renseigner un montant en euro `GOODORSERVICEPRICEINEUR` dans la colonne commentaire :

exemple :
```
"Spend","","","1.00000000","BTC","0.00010000","BTC","","","achat d'une casquette GOODORSERVICEPRICEINEUR=500.00","2019-02-01 00:00:00"
```
(placé n'importe ou dans le commentaire, insensible aux minuscules/majuscules, possibilité d'avoir un nombre entier, **mais il ne faut pas d'espace** entre le nom de la variable et le montant)


# Installer

Pour l'instant nous utilisons IntelliJ IDEA pour compiler et lancer l'application.
Si vous n'avez pas IntelliJ, il est possible de télécharger gratuitement la version community : https://www.jetbrains.com/idea/download/

Télécharger ce dossier et ouvrez-le avec Intellij `File > Open`


## Lancer le programme

Sur IntelliJ il faut renseigner le chemin de votre CSV avec le paramètre `-f` dans le deuxième champ présent dans `Run > Edit Configurations > Application > Main > Build and run`

Exemple : `-f /home/monutilisateur/data.csv`

Ou sur windows quelque chose comme cela : 

`-f C:\Documents\data.csv`

Ensuite vous pouvez cliquer sur le button Play `Run 'Main'`
