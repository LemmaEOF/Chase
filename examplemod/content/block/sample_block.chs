abstract from chase:block/block # builtin!

required material

optional [luminance] with state

from mod:block/block
x = 5

array = [2, 3, 5, 8, 10]

callmethod(5)

[interact] with world, pos, state, hit
    if world.isDay and x < 10
        world.setBlockState(pos, state.with(PROPERTY, true))
    ;
;

tomlobj = `
[object]
key = "value"
`