package com.example.try_gameengine.action

class GravityReflectionPathByHorizontalMirrorAppendDecorator(action: MovementActionItemMoveByGravity) :
    CopyMoveDecorator(action) {
    override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        val copy =
            super.coreCalculationMovementActionInfo(action) as MovementActionItemMoveByGravity
        copy.setPathType(IGravityController.PathType.REFLECTION_PATH_BY_HORIZONTAL_MIRROR)
        return copy
    }
}
