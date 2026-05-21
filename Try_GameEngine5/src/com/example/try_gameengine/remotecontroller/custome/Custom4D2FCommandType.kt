package com.example.try_gameengine.remotecontroller.custome

/**
 * `Custom4D2FCommandType` is a custom command type set for 4D2F(4 Direction keys and 2 Function keys), total 6 keys.
 * Which key has tow action `PressUp` and `PressDown`, so plus `None` action, it has 13 command types.
 * 
 * @author irons
 // */
enum class Custom4D2FCommandType {
    None,
    UPKeyUpCommand,
    UPKeyDownCommand,
    DownKeyUpCommand,
    DownKeyDownCommand,
    LeftKeyUpCommand,
    LeftKeyDownCommand,
    RightKeyUpCommand,
    RightKeyDownCommand,
    EnterKeyUpCommand,
    EnterKeyDownCommand,
    CancelKeyUpCommand,
    CancelKeyDownCommand
}
