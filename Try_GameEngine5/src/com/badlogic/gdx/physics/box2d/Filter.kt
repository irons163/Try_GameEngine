package com.badlogic.gdx.physics.box2d

/**
 * This holds contact filtering data.
 * @author mzechner
 // */
class Filter {
    /**
     * The collision category bits. Normally you would just set one bit.
     // */
    var categoryBits: Short = 0

    /**
     * The collision mask bits. This states the categories that this
     * shape would accept for collision.
     // */
    var maskBits: Short = 0

    /**
     * Collision groups allow a certain group of objects to never collide (negative)
     * or always collide (positive). Zero means no collision group. Non-zero group
     * filtering always wins against the mask bits.
     // */
    var groupIndex: Short = 0
}
