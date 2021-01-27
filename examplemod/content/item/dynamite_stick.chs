from `chase:item/item` # Built-in parent helps set up what we need for item

# Contains all the creative tabs that exist, so we can tell the game which to put us in
import `chase:inventory/item_groups` as ItemGroups
# Fancy type we need for returning from [use]
import `chase:util/action_result` as ActionResult
# We aren't gonna define the thrown dynamite stick in this sample code, so pretend we do
import `custom:thrown_dynamite_stick` as Dynamite

# Used for our example function below
import `chase:entity/player` as Player
import `chase:util/hand` as Hand

item_group = ItemGroups.misc # Puts it into the "miscellaneous" item group

# Called whenever you right-click in the air with this item
# Parent defines this, so we already know the types of our ins and outs
# world = the world this is happening in
# player = the player throwing the dynamite stick
# hand = the hand (main or off) the player is throwing the dynamite with
# stack = the stack of dynamite
[use] with world, player, hand, stack:
    if not world.isClient(): # Make sure this only happens on the server so we don't get a desync - the server will sync to the client
        # Create a new copy of the entity at the position of the player's head
        entity = Dynamite.create(x = player.get_pos().x, y = player.get_pos().y + 1.5, z = player.get_pos().z)
        # Set the entity to have velocity in the direction the player's looking
        entity.set_velocity(player.get_look_direction()) # Single arg has an implicit name
        # Spawn the entity!
        world.spawn_entity(entity)
        # Take 1 from the stack - it succeeded
        stack.decrement(1)
        # Tell the game that it succeeded and it doesn't need to try anything else
        return ActionResult.of(result = ActionResult.success, value = stack)
    ;
    # On client-side, tell the game to pass on execution to the server
    return ActionResult.of(result = ActionResult.pass, value = stack)
;

# Sample syntax for data (efectively a syntactical sugar over json)
# Data can only store literals, arrays, or other objects (so that I don't have to worry about doing more lmao)
sample_obj = data:
    x = 5
    y = ["a", "b", "c"]
    in = data:
        why = false
    ;
    data_array = [
        data:
            a = 3
        ; # if there's an LF we don't need a comma
        data:
            a = 5
        ;
    ]
;

# Defining a function of our own requires explicit types
test_func = function with player: Player, hand: Hand -> boolean:
    return player.get_stack_in_hand(hand).getItem() == `minecraft:dirt`
;