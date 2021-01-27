from chase:block/block # This is the parent code - it's a built-in from my library

import chase:block/materials as materials # Import a reference I'll use later
import chase:block/properties as properties # Another reference for later

# Define some values that the loader mod can read once my script is done
material = materials.rock
hardness = 5
# Define a storage object, with its own internal set of keys and values
material_color = `
    index = 53
    [color] # Sub-object in the data storage
        red = 0x5F # 0x means hexadecimal, since # is already used for comments
        green = 0xEC
        blue = 0x94
    ; # Semicolon marks that a block is over, so no need to worry about keeping up indentation
`

# Define a function that the loader mod will call later - this will activate when we right-click on the block
# state = the block state that was interacted with
# world = the world it happened in
# pos = the position it happened at
# player = the player who interacted with it
# hand = the hand (main or off) that they used to interact
# hit = information about where specifically on the block they right-clicked
[use] with state, world, pos, player, hand, hit
    if world.isDay() # Check if it's daytime
        world.setBlockState(pos, state.with(properties.lit, false)) # If it's day, set the "lit" property to false (ex. for a lantern)
        return true
    ;
    return false
;

