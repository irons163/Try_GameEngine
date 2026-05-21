package com.example.try_gameengine.action

class GravityReflectionPathByVerticalMirrorAppendDecorator(action: MovementActionItemMoveByGravity) :
    CopyMoveDecorator(action) {
    override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        val copy =
            super.coreCalculationMovementActionInfo(action) as MovementActionItemMoveByGravity
        copy.setPathType(IGravityController.PathType.REFLECTION_PATH_BY_VERTICAL_MIRROR)
        return copy
    }
}
