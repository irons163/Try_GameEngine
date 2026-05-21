package com.example.try_gameengine.extension_module.skill

import android.test.AndroidTestCase

class SkillTest : AndroidTestCase() {
    @kotlin.Throws(java.lang.Exception::class)
    fun testSimpleSkillInfo() {
        val skillA: ASkill = SkillA(10)
        val skillB: ASkill = SkillB(10)

        val skillALevelConditionToLearnSkillB = 5
        skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB)

        for (i in 0..<skillALevelConditionToLearnSkillB) {
            val isSkillBLearningAble = skillB.isSkillLearnAble()

            assertEquals(true, skillA.getCurrentSkillLevel() == i)

            if (skillA.getCurrentSkillLevel() == skillALevelConditionToLearnSkillB) {
                break
            } else {
                assertEquals(false, isSkillBLearningAble)
                skillA.increaseSkillLevel()
            }
        }

        assertEquals(true, skillB.isSkillLearnAble())
        assertEquals(true, skillA.getCurrentSkillLevel() == skillALevelConditionToLearnSkillB)
    }

    @kotlin.Throws(java.lang.Exception::class)
    fun testSkillWithNoIDInfo() {
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

        assertEquals(true, skillC.isSkillLearnAble())
        assertEquals(true, skillA.getCurrentSkillLevel() == skillALevelConditionToLearnSkillC)
        assertEquals(true, skillB.getCurrentSkillLevel() == skillBLevelConditionToLearnSkillC)
        assertEquals(true, skillC.getCurrentSkillLevel() == 0)
    }

    @kotlin.Throws(java.lang.Exception::class)
    fun testSkillWithIDInfo() {
        val skillA: ASkill = SkillA("SkillA", 10)
        val skillB: ASkill = SkillB("SkillB", 10)
        val skillC: ASkill = SkillC("SkillC", 10)

        val skillALevelConditionToLearnSkillB = 3
        skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB)
        val skillALevelConditionToLearnSkillC = 5
        skillC.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillC)
        val skillBLevelConditionToLearnSkillC = 7
        skillC.addNextSkillWithConditionSkillLevel(skillB, skillBLevelConditionToLearnSkillC)

        meetConditionForLearn(skillC)

        assertEquals(true, skillC.isSkillLearnAble())
        assertEquals(true, skillA.getCurrentSkillLevel() == skillALevelConditionToLearnSkillC)
        assertEquals(true, skillB.getCurrentSkillLevel() == skillBLevelConditionToLearnSkillC)
        assertEquals(true, skillC.getCurrentSkillLevel() == 0)
    }

    @kotlin.Throws(java.lang.Exception::class)
    fun testBadSkillWithNoIDInfo() {
        val skillA: ASkill = SkillA(10)
        val skillB: ASkill = SkillB(10)
        val skillC: ASkill = SkillC(10)

        val skillALevelConditionToLearnSkillB = 3
        skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB)
        val skillALevelConditionToLearnSkillC = 5
        skillC.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillC)
        val skillBLevelConditionToLearnSkillC = 7
        skillC.addNextSkillWithConditionSkillLevel(skillB, skillBLevelConditionToLearnSkillC)
        val skillCLevelConditionToLearnSkillA = 1
        skillA.addNextSkillWithConditionSkillLevel(skillC, skillCLevelConditionToLearnSkillA)

        var isStackOverflowError = false
        try {
            meetConditionForLearn(skillC)

            assertEquals(true, skillC.isSkillLearnAble())
            assertEquals(true, skillA.getCurrentSkillLevel() == skillALevelConditionToLearnSkillC)
            assertEquals(true, skillB.getCurrentSkillLevel() == skillBLevelConditionToLearnSkillC)
            assertEquals(true, skillC.getCurrentSkillLevel() == 0)
        } catch (e: StackOverflowError) {
            // TODO: handle exception
            isStackOverflowError = true
        }
        assertEquals(true, isStackOverflowError)
    }

    @kotlin.Throws(java.lang.Exception::class)
    fun testBadSkillWithIDInfo() {
        val skillA: ASkill = SkillA("SkillA", 10)
        val skillB: ASkill = SkillB("SkillB", 10)
        val skillC: ASkill = SkillC("SkillC", 10)

        var isRuntimeException = false
        try {
            val skillALevelConditionToLearnSkillB = 3
            skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB)
            val skillALevelConditionToLearnSkillC = 5
            skillC.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillC)
            val skillBLevelConditionToLearnSkillC = 7
            skillC.addNextSkillWithConditionSkillLevel(skillB, skillBLevelConditionToLearnSkillC)
            val skillCLevelConditionToLearnSkillA = 1
            skillA.addNextSkillWithConditionSkillLevel(skillC, skillCLevelConditionToLearnSkillA)
        } catch (e: RuntimeException) {
            // TODO: handle exception
            isRuntimeException = true
        }
        assertEquals(true, isRuntimeException)
    }

    @kotlin.Throws(java.lang.Exception::class)
    fun testIncreaseDecreaseSkillWithIDInfo() {
        val skillA: ASkill = SkillA("SkillA", 10)
        val skillB: ASkill = SkillB("SkillB", 10)
        val skillC: ASkill = SkillC("SkillC", 10)

        val skillALevelConditionToLearnSkillB = 3
        skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB)
        val skillALevelConditionToLearnSkillC = 5
        skillC.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillC)
        val skillBLevelConditionToLearnSkillC = 7
        skillC.addNextSkillWithConditionSkillLevel(skillB, skillBLevelConditionToLearnSkillC)

        assertEquals(true, skillA.isSkillLearnAble())
        assertEquals(false, skillB.isSkillLearnAble())
        assertEquals(false, skillC.isSkillLearnAble())


        //increase
        assertEquals(true, skillA.increaseSkillLevel()) //A=1
        assertEquals(false, skillB.increaseSkillLevel()) //B=0
        assertEquals(false, skillC.increaseSkillLevel()) //C=0

        assertEquals(true, skillA.getCurrentSkillLevel() == 1)
        assertEquals(true, skillB.getCurrentSkillLevel() == 0)
        assertEquals(true, skillC.getCurrentSkillLevel() == 0)

        assertEquals(true, skillA.increaseSkillLevel()) //A=2
        assertEquals(true, skillA.increaseSkillLevel()) //A=3

        assertEquals(true, skillA.isSkillLearnAble())
        assertEquals(true, skillB.isSkillLearnAble())
        assertEquals(false, skillC.isSkillLearnAble())

        assertEquals(true, skillA.increaseSkillLevel()) //A=4
        assertEquals(true, skillA.increaseSkillLevel()) //A=5

        assertEquals(false, skillC.isSkillLearnAble())

        assertEquals(true, skillB.increaseSkillLevel()) //B=1
        assertEquals(true, skillB.increaseSkillLevel()) //B=2
        assertEquals(true, skillB.increaseSkillLevel()) //B=3
        assertEquals(true, skillB.increaseSkillLevel()) //B=4
        assertEquals(true, skillB.increaseSkillLevel()) //B=5
        assertEquals(true, skillB.increaseSkillLevel()) //B=6

        assertEquals(false, skillC.isSkillLearnAble())

        assertEquals(true, skillB.increaseSkillLevel()) //B=7

        assertEquals(true, skillC.isSkillLearnAble())

        assertEquals(true, skillC.increaseSkillLevel())

        assertEquals(true, skillA.getCurrentSkillLevel() == 5) //A=5
        assertEquals(true, skillB.getCurrentSkillLevel() == 7) //B=7
        assertEquals(true, skillC.getCurrentSkillLevel() == 1) //C=1

        assertEquals(true, skillA.increaseSkillLevel()) //A=6
        assertEquals(true, skillA.increaseSkillLevel()) //A=7
        assertEquals(true, skillA.increaseSkillLevel()) //A=8
        assertEquals(true, skillA.increaseSkillLevel()) //A=9
        assertEquals(true, skillA.increaseSkillLevel()) //A=10
        assertEquals(false, skillA.increaseSkillLevel()) //A>max, increase fail!

        assertEquals(true, skillA.getCurrentSkillLevel() == skillA.getMaxSkillLevel()) //A=10

        assertEquals(true, skillA.isSkillLearnAble())
        assertEquals(true, skillB.isSkillLearnAble())
        assertEquals(true, skillC.isSkillLearnAble())


        //decrease
        val skillTree: MutableList<ASkill?> = ArrayList<ASkill?>()
        skillTree.add(skillA)
        skillTree.add(skillB)
        skillTree.add(skillC)

        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=9,B=7,C=1
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=8,B=7,C=1
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=7,B=7,C=1
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=6,B=7,C=1
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=5,B=7,C=1
        assertEquals(true, skillB.increaseSkillLevel()) //B=8
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //A=5,B=7,C=1
        assertEquals(true, skillC.increaseSkillLevel()) //C=2
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillC,
                skillTree
            )
        ) //A=5,B=7,C=1

        assertEquals(
            false,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //C need A=5, decrease fail!
        assertEquals(
            false,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //C need B=7, decrease fail!
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillC,
                skillTree
            )
        ) //C=0
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=4
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=3
        assertEquals(
            false,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //B need A=3, decrease fail!
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=6
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=5
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=4
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=3
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=2
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=1
        assertEquals(
            false,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //B need A=3, decrease fail!
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillB,
                skillTree
            )
        ) //B=0
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=2
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=1
        assertEquals(
            true,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A=0
        assertEquals(
            false,
            SkillManager.decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(
                skillA,
                skillTree
            )
        ) //A<0, decrease fail!

        assertEquals(true, skillA.getCurrentSkillLevel() == 0)
        assertEquals(true, skillB.getCurrentSkillLevel() == 0)
        assertEquals(true, skillC.getCurrentSkillLevel() == 0)
    }

    private fun meetConditionForLearn(skillForLearn: ASkill) {
        for (conditionSkillSet in skillForLearn.getConditionSkills().entries) {
            val conditionSkill = conditionSkillSet.key
            val skillLevelCondition = conditionSkillSet.value

            val currentSkillLevelForConditionSkill = conditionSkill.getCurrentSkillLevel()
            for (i in 0..<conditionSkill.getMaxSkillLevel()) {
                val isSkillBLearningAble = conditionSkill.isSkillLearnAble()

                assertEquals(
                    true,
                    conditionSkill.getCurrentSkillLevel() == currentSkillLevelForConditionSkill + i
                )

                if (!isSkillBLearningAble) {
                    meetConditionForLearn(conditionSkill)
                }

                assertEquals(true, conditionSkill.isSkillLearnAble())

                if (conditionSkill.getCurrentSkillLevel() >= skillLevelCondition) {
                    break
                } else {
                    assertEquals(false, skillForLearn.isSkillLearnAble())
                    conditionSkill.increaseSkillLevel()
                }
            }
        }

        assertEquals(true, skillForLearn.isSkillLearnAble())
    }
}
