package com.example.try_gameengine.extension_module.skill

class SkillA : ASkill {
    constructor(maxSkillLevel: Int) : super(maxSkillLevel)

    constructor(name: String?, maxSkillLevel: Int) : super(name, maxSkillLevel)
}
