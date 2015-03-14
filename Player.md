# Player #

## Old documentation ##

From the player's point of view this probably is the most important object type. It's the player himself. It's based on the class CGWGameObject and declared as follows:
```
class CGWPlayer : public CGWGameObject
```
It adds the following variables and methods to the base class:

Variables
|Type	|Name	|Function|
|:----|:----|:-------|
|CJLibBob	|CBob	|The Bob class used for drawing and pixelwise updating of the player|
|int	|nScore	|How many points the player's got? They're stored in this variable|
|int	|nGems	|Number of gems collected by the player|
|int	|nLives	|The player's lives|
|int[.md](.md)	|nCollection	|All the stuff that the player is carrying with him/her stored in an array of integers. Every object that the player can collect has a int code with a #defined name. Note: Gems don't get to this list but to nGems.|


What you might not see in this table is that the pixelwise moving is handled by the Bob. It has special methods designed for that. In the other object types we can't use bobs because of their big memory usage, that's why we have to create the UpdatePosition-method separately in every class. But now to the methods.

Methods:
|Type	|Name	|Function|
|:----|:----|:-------|
|void	|UpdatePosition	|The implementation of the abstract method defined in CGWGameObject|
|void	|UpdatePickUpStatus	|When the player gets a gem or a box, or loses one, this method is called to update the variables (nScore, nGems and nCollection)|
|void	|Draw	|Draw the player on the given surface using the bob's drawing methods.|


I think that's it. There aren't too many functions here because most of the stuff is still being done in the game main functions (game.cpp|.h).

## Dying ##
**When does the player die?**
When...
  * A rock falls on him/her
  * A gem falls on him/her
  * He/she runs out of time
  * A monster catches him/her
  * A bomb explodes near him/her
  * She/he presses the 'K' button

**What happens then?**

When one of the things above happen, the player dies. First there's an explosion that clears nine blocks around the player:
XXX
XOX
XXX
All the blocks (X) around the player (O) are cleared to sand. To see how that's done, click here to read about the explosions in GemWars.

After the explosion, the player loses one life:
```
GameVar.players[0].lives--;
if(GameVar.players[0].lives<0) GAME_STATE=GAME_END;
```
And as you can see, if there aren't any lives left, the GAME\_STATE is set to GAME\_END wich means that we go back to the menu. Otherwise GAME\_STATE is set to RESTART\_LEVEL.

RESTART\_LEVEL is handled in WinMain like this:
```
case RESTART_LEVEL:
{
	GameEnd();
	GameInit();
}
break;
```

First the variables are emptied in GameEnd(); and then the level is restarted in GameInit();. In GameInit(); the GAME\_STATE variable is set to GAME and the game goes on.

'Jarkko Laine, 7.11.1999'