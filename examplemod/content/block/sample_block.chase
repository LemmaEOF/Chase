abstract from chase:block/block # builtin!

required material

optional [luminance] with state

from chase:block/block
x = 5

y = mine-craft:block
z = x-y:z

array = [2, 3, 5, 8, 10]

[interact] with world, pos, state, hit
    if world.isDay() and x < 10
        world.setBlockState(pos, state.with(PROPERTY, true))
    ;
;

obj = `
int = 1
float = 0.5
string = "name"
array = [1, 2, 3]
[object]
    key = "value"
;

objarray = [
    []
        key = "value"
        key2 = 5
    ;
    []
        key = "value2"
        key2 = 7
    ;
]
`
