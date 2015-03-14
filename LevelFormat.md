# Level file format #

The level file is a plain text file. Level dimensions can vary and it does not have to be square. The level is separated into different layers.

There are two different level formats, of which the newer (MrTiles) is used. Differences are the use of #'s instead of zeros and a different layer order. Also are missing three lines from the end of the file.

## File names ##
  * **e#m#.gem** where the first number represents the episode number and the latter the map number.
  * **race#.gem** for the 2-player race mode.

## Layers ##
| **Name** | **Description** |
|:---------|:----------------|
| LAYER\_GROUND | Ground layer. Includes mainly sand and walls. |
| LAYER\_SPECIAL | Start, Goal and Enemies |
| LAYER\_OBJECTS | Gems and Boulders |
| LAYER\_COLLISION | Unwalkable areas marked by 1's |

## Tiles ##
![http://gem-wars.googlecode.com/svn/trunk/src/resources/images/winbd.png](http://gem-wars.googlecode.com/svn/trunk/src/resources/images/winbd.png)

| **id** | **Description** | **Layer** | **More information** |
|:-------|:----------------|:----------|:---------------------|
| 0 | Empty, transparent block | all |
| 1 | Red diamond | OBJECTS |
| 2 | Sand | GROUND |
| 3 | Yellow wall | GROUND |
| 4 | Blue diamond | OBJECTS |
| 5 | White boulder | OBJECTS | Animation in texture rockbob1.bmp |
| 6 | Ground | GROUND |
| 7 | Green diamond | OBJECTS |
| 8 | Dark boulder | OBJECTS | Animation in texture rockbob2.bmp |
| 9 | Button | special? |
| 10 | Magic grey wall | GROUND | Animation in texture Magic.bmp |
| 11 | Metal wall | GROUND |
| 12 | Red wall | GROUND |
| 13 | Goal tile | none | appears over id 17 when level goal is achieved |
| 14 | Bomb box | special? |
| 15 | Brown wall | GROUND |
| 16 | Start | SPECIAL |
| 17 | Goal | SPECIAL |
| 18 | Monster | SPECIAL |
| 19 | Unused |  |


## MrTiles file format ##
|**Line number**| **Description** |
|:--------------|:----------------|
| 1 | Map name |
| 2 | Map creator name |
| 3 | Gem count |
| 4 | Time |
| 5 | Layer name |
| 6 | Width or tiles count horizontaly |
| 7 | Height or tiles count vertically |
| 8 | Tile filename |
| 9 ... 19 | Ground texture numbers |
| ... | Object-layers repeat the same format |
| ... | Special-layer repeat the same format |
| ... | Collison-layer repeat the same format |

```
Muurahaisten aarteet
Lauri Laine
15
100
LAYER_GROUND
26
20
D:\mrtiles\28x28tile.bmp
11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 
11 6 6 6 11 12 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 11 
11 6 6 6 6 12 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 11 
11 6 6 6 6 12 6 6 6 6 6 6 6 12 12 12 12 12 6 6 6 6 6 6 6 11 
11 6 12 12 6 12 6 12 12 12 12 6 6 6 6 6 6 6 6 6 12 12 12 12 6 11 
11 6 6 6 6 12 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 11 
11 6 6 6 6 12 12 12 12 12 12 12 12 12 12 6 12 12 12 12 12 12 12 12 12 11 
11 6 12 12 6 12 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 11 
11 6 6 6 6 12 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 11 
11 6 6 6 6 12 2 2 6 6 6 2 2 2 6 6 6 2 2 2 6 6 6 2 2 11 
11 6 12 12 6 12 2 2 6 6 6 2 2 2 6 6 6 2 2 2 6 6 6 2 2 11 
11 6 6 6 6 12 2 2 6 6 6 2 2 2 6 6 6 2 2 2 6 6 6 2 2 11 
11 6 6 6 6 12 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 11 
11 6 12 12 6 12 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 11 
11 6 6 6 6 12 2 2 6 6 6 2 2 2 6 6 6 2 2 2 6 6 6 2 2 11 
11 6 6 6 6 12 2 2 6 6 6 2 2 2 6 6 6 2 2 2 6 6 6 2 2 11 
11 6 12 12 6 12 2 2 6 6 6 2 2 2 6 6 6 2 2 2 6 6 6 2 2 11 
11 6 6 6 6 12 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 11 
11 6 6 6 6 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 11 
11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 
LAYER_OBJECTS
26
20
D:\mrtiles\28x28tile.bmp
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # 1 4 1 4 1 # # # # # # # # 
# # # # # # # 7 7 7 7 # # # # # # # # # 7 7 7 7 # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # 4 # # # # # 4 # # # # # 4 # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # 1 # # # # # 1 # # # # # 1 # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
LAYER_SPECIAL
26
20
D:\mrtiles\28x28tile.bmp
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# 16 # # 17 # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # 18 # # # # # # # # # # 
# # # # 18 # # 18 # # # # # # # # # # # # # # # 18 # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # 18 # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # 18 # # # # # # # 18 # # # # # 18 # # # # # 18 # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # 18 # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # 18 # # # # # 18 # # # # # 18 # # # 
# # # 18 # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
# # # # # # # # # # # # # # # # # # # # # # # # # # 
LAYER_COLLISION
26
20
D:\mrtiles\28x28tile.bmp
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 
1 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 1 
1 0 1 1 0 1 0 1 1 1 1 0 0 0 0 0 0 0 0 0 1 1 1 1 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 
1 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 


```

## Original file format (deprecated) ##
For archive purposes only.

|**Line number**| **Description** |
|:--------------|:----------------|
| 1 | Map name |
| 2 | Map creator name |
| 3 | Gem count |
| 4 | Time |
| 5 | Layer name |
| 6 | Width or tiles count horizontaly |
| 7 | Height or tiles count vertically |
| 8 | Start text |
| 9 ... 19 | Ground texture numbers |
| ... | Special-layer repeat the same format |
| ... | Object-layers repeat the same format |
| ... | Collison-layer repeat the same format |
| Third last | ??? |
| Second last | ??? |
| Last line | ??? |

```
Ykkönen
Lauri Laine
9
65
LAYER_GROUND
23
11
Hi Mom!! Hi Dad!! How are you?
11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 
11 6 6 6 6 6 6 6 6 6 15 2 2 2 15 2 2 6 15 6 6 6 11 
11 6 15 15 15 15 15 15 15 6 15 6 6 2 15 2 15 6 15 15 15 6 11 
11 6 15 2 2 2 2 6 15 6 15 15 15 2 15 2 15 6 6 6 6 6 11 
11 6 15 6 6 2 15 2 15 6 6 2 2 2 15 2 15 15 15 15 6 15 11 
11 6 15 15 15 2 15 6 15 15 15 15 15 15 15 2 15 6 6 6 2 6 11 
11 6 6 6 15 6 15 6 6 6 6 2 2 15 6 2 15 6 15 15 6 15 11 
11 6 15 6 6 6 15 15 15 15 15 15 2 2 2 2 15 6 15 2 2 6 11 
11 6 15 15 15 6 15 2 2 6 2 15 15 15 15 15 15 2 6 15 15 2 11 
11 6 6 6 15 6 2 2 2 6 6 6 2 2 2 2 2 2 2 2 2 2 11 
11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 
LAYER_SPECIAL
23
11
Hi Mom!! Hi Dad!! How are you?
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 7 5 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 7 5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 7 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 5 0 0 0 0 0 5 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 0 0 0 7 0 
0 0 0 0 0 0 0 0 0 5 0 0 0 0 0 0 0 0 8 0 0 0 0 
0 0 0 7 0 0 0 0 0 8 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
LAYER_OBJECTS
23
11
Hi Mom!! Hi Dad!! How are you?
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 17 0 0 0 
0 16 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 9 0 0 0 0 0 
0 0 0 0 0 0 0 0 9 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
LAYER_COLLISION
23
11
Hi Mom!! Hi Dad!! How are you?
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 
1 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 1 0 0 0 1 
1 0 1 1 1 1 1 1 1 0 1 0 0 0 1 0 1 0 1 1 1 0 1 
1 0 1 0 0 0 0 0 1 0 1 1 1 0 1 0 1 0 0 0 0 0 1 
1 0 1 0 0 0 1 0 1 0 0 0 0 0 1 0 1 1 1 1 0 1 1 
1 0 1 1 1 0 1 0 1 1 1 1 1 1 1 0 1 0 0 0 0 0 1 
1 0 0 0 1 0 1 0 0 0 0 0 0 1 0 0 1 0 1 1 0 1 1 
1 0 1 0 0 0 1 1 1 1 1 1 0 0 0 0 1 0 1 0 0 0 1 
1 0 1 1 1 0 1 0 0 0 0 1 1 1 1 1 1 0 0 1 1 0 1 
1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 
Buttons: 0
Lurkki
50

```