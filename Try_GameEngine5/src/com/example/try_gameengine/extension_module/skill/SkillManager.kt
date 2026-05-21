package com.example.try_gameengine.extension_module.skill

object SkillManager {
    @Throws(Exception::class)
    fun testSkillInfo() {
        val skillA: ASkill = SkillA(10)
        val skillB: ASkill = SkillB(10)
        val skillC: ASkill = SkillC(10)

        val skillALevelConditionToLearnSkillB = 3
        skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB)
        val skillALevelConditionToLearnSkillC = 5
        skillC.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillC)
        val skillBLevelConditionToLearnSkillC = 7
        skillC.addNextSkillWithConditionSkillLevel(skillB, skillBLevelConditionToLearnSkillC)

        meetConditionForLearn(skillC)
    }

    private fun meetConditionForLearn(skillForLearn: ASkill) {
        for (conditionSkillSet in skillForLearn.getConditionSkills().entries) {
            val conditionSkill = conditionSkillSet.key ?: continue
            val skillLevelCondition = conditionSkillSet.value ?: continue


//			int currentSkillLevelForConditionSkill = conditionSkill.getCurrentSkillLevel();
            for (i in 0..<conditionSkill.getMaxSkillLevel()) {
                val isSkillBLearningAble = conditionSkill.isSkillLearnAble()

                if (!isSkillBLearningAble) {
                    meetConditionForLearn(conditionSkill)
                }

                if (conditionSkill.getCurrentSkillLevel() >= skillLevelCondition) {
                    break
                } else {
                    conditionSkill.increaseSkillLevel()
                }
            }
        }
    }

    fun decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
        targetSkill: ASkill,
        SkillTree: MutableList<ASkill>
    ): Boolean {
        val skillLevelIfdecrease = targetSkill.getCurrentSkillLevel() - 1
        return setSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
            skillLevelIfdecrease,
            targetSkill,
            SkillTree
        )
    }

    fun setSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
        newSkillLevel: Int,
        targetSkill: ASkill,
        SkillTree: MutableList<ASkill>
    ): Boolean {
        val currentSkillLevel = targetSkill.getCurrentSkillLevel()

        targetSkill.setCurrentSkillLevel(newSkillLevel)
        if (checkSkillTreeValid(SkillTree)) {
            return true
        } else {
            targetSkill.setCurrentSkillLevel(currentSkillLevel)
            return false
        }
    }

    fun checkSkillTreeValid(SkillTree: MutableList<ASkill>): Boolean {
        var isSkillTreeValid = true
        for (skillForCheck in SkillTree) {
            if (!checkSkillValid(skillForCheck)) {
                isSkillTreeValid = false
                break
            }
        }
        return isSkillTreeValid
    }

    fun checkSkillValid(skillForCheck: ASkill): Boolean {
        if (skillForCheck.getCurrentSkillLevel() < 0 || skillForCheck.getCurrentSkillLevel() > skillForCheck.getMaxSkillLevel()) return false

        for (conditionSkillSet in skillForCheck.getConditionSkills().entries) {
            val conditionSkill = conditionSkillSet.key ?: continue
            val skillLevelCondition = conditionSkillSet.value ?: continue
            val isSkillLearnAble = conditionSkill.isSkillLearnAble()

            if (!isSkillLearnAble) break

            if (skillForCheck.getCurrentSkillLevel() > 0 && skillLevelCondition > conditionSkill.getCurrentSkillLevel()) {
                return false
            }
        }
        return true
    } //	public int countSkillSetCurrenLevel(){
    //		
    //	}
    //	
    //	public boolean countSkillSetValid(){
    //		
    //	}
}
