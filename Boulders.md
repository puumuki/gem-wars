# Boulders #

Rocks are objects that can fall, be pushed or explode. Their job is to kill the player, block his ways and in multiplayer modes they can also be used against other players. All the rocks have to be able to do is to move. We must also remember that there are two different rock types (rocks that look different). So, what's in the rock?

### Variables ###

| **Type** | **Name** | **Function** |
|:---------|:---------|:-------------|
| int  | nType | What kind of rock is this. Used for knowing which to draw. We don't want the rock to be change its looks when it moves, do we?|
| int | nPixPosX / Y | 	The pixelwise position used for moving the rock. |

Because all the rocks use same images with no animation, we don't have to give each rock pictures of its own. So. We have a struct called RockImageHolder that holds all the different (both of them) rock images grabbed from the tile file.


### Methods ###
| **Type** | **Name** | **Function** |
|:---------|:---------|:-------------|
| void | UpdatePosition | The implementation of the abstract method defined in CGWGameObject |
| void | UpdatePickUpStatus | When the player gets a gem or a box, or loses one, this method is called to update the variables (nScore, nGems and nCollection) |
| void | Draw | Draw the player on the given surface using the bob's drawing methods. |

And that's all there is about this rock. Remember that we also need to handle the player's relations with the rocks (i.e. pushing, dying), but not in this part, but in the game main functions (game.cpp|.h)