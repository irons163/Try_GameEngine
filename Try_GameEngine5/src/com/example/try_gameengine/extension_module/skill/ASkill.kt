package com.example.try_gameengine.extension_module.skill

abstract class ASkill {
    private val a = false
    private var currentSkillLevel = 0
    private var maxSkillLevel: Int
    private var ID: String? = null

    private val conditionSkills: MutableMap<ASkill?, Int?> = HashMap<ASkill?, Int?>()

    constructor(maxSkillLevel: Int) {
        this.maxSkillLevel = maxSkillLevel
    }

    constructor(ID: String?, maxSkillLevel: Int) {
        this.ID = ID
        this.maxSkillLevel = maxSkillLevel
    }

    //	public abstract void addNextSkillWithConditionSkillLevel(ASkill skill, int skillLevel);
    //	
    //	public abstract void isSkillLearnAble();
    //	
    //	public abstract boolean isMeetConditionOfTheTargetSkill(ASkill targetSkill, int skillLevelCodition);
    fun getConditionSkills(): MutableMap<ASkill?, Int?> {
        return conditionSkills
    }

    fun getMaxSkillLevel(): Int {
        return maxSkillLevel
    }

    fun getCurrentSkillLevel(): Int {
        return currentSkillLevel
    }

    fun getID(): String? {
        return ID
    }

    fun increaseSkillLevel(): Boolean {
        if (currentSkillLevel >= maxSkillLevel) return false
        if (currentSkillLevel == 0 && !isSkillLearnAble()) return false

        currentSkillLevel++
        return true
    }

    fun decreaseSkillLevel(): Boolean {
        currentSkillLevel--
        return true
    }

    fun setCurrentSkillLevel(currentSkillLevel: Int) {
        this.currentSkillLevel = currentSkillLevel
    }

    fun setMaxSkillLevel(maxSkillLevel: Int) {
        this.maxSkillLevel = maxSkillLevel
    }

    private fun checkTargetSkillIsValid(targetSkill: ASkill) {
        for (conditionSkillSet in targetSkill.getConditionSkills().entries) {
            val conditionSkill: ASkill = conditionSkillSet.key!!
            if (ID != null && ID == conditionSkill.getID()) {
                throw RuntimeException()
            } else {
                checkTargetSkillIsValid(conditionSkill)
            }
        }
    }

    //	@Override
    fun isSkillLearnAble(): Boolean {
        // TODO Auto-generated method stub
        var isSkillLearnAble = true
        for (entry in getConditionSkills().entries) {
            val skill: ASkill = entry.key!!
            val skillLevelCodition: Int = entry.value!!
            if (!skill.isMeetConditionOfTheTargetSkill(this, skillLevelCodition)) {
                isSkillLearnAble = false
                break
            }
        }
        return isSkillLearnAble
    }

    //	@Override
    fun isMeetConditionOfTheTargetSkill(skill: ASkill?, skillLevelCodition: Int): Boolean {
        // TODO Auto-generated method stub
        if (getCurrentSkillLevel() >= skillLevelCodition) return true
        return false
    }

    //	@Override
    fun addNextSkillWithConditionSkillLevel(skill: ASkill, skillLevel: Int) {
        // TODO Auto-generated method stub
        checkTargetSkillIsValid(skill)
        getConditionSkills().put(skill, skillLevel)
    }
}
