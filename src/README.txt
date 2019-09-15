Coding-style: https://google.github.io/styleguide/javaguide.html#s1-introduction

Other notes:

Please check the JSON file within the Game/src/resources folder. I might have changed some value for testing.

Even if the size of stickman is invalid (not tiny, normal large or giant), the base size is always tiny

If there is an error with reading the file (no file exist, cannot read file, empty file etc), there will be an exception error raised.

Stickman initial position is always positive value (I used java.Math library). If the X is too large, the player might not be on the screen as the current level has a height of 400 and width of 440 pixels.

The player speed is 8, its jump height is 30 and the floorheight is 50.
The speed of objects might be slow. It is because I multiplied the velocity by 0.017

Since the tick() method is called every 0.017 second, we adjust the speed to that as well