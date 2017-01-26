package com.example.try_gameengine.extension_module.skill;

import java.util.List;
import java.util.Map.Entry;

public class SkillManager {
	
	public static void testSkillInfo() throws Exception{
		
		ASkill skillA = new SkillA(10);
		ASkill skillB = new SkillB(10);
		ASkill skillC = new SkillC(10);
		
		int skillALevelConditionToLearnSkillB = 3;
		skillB.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillB);
		int skillALevelConditionToLearnSkillC = 5;
		skillC.addNextSkillWithConditionSkillLevel(skillA, skillALevelConditionToLearnSkillC);
		int skillBLevelConditionToLearnSkillC = 7;
		skillC.addNextSkillWithConditionSkillLevel(skillB, skillBLevelConditionToLearnSkillC);
		
		meetConditionForLearn(skillC);
		
	}
	
	private static void meetConditionForLearn(ASkill skillForLearn){
		for(Entry<ASkill, Integer> conditionSkillSet: skillForLearn.getConditionSkills().entrySet()){
			ASkill conditionSkill = conditionSkillSet.getKey();
			int skillLevelCondition = conditionSkillSet.getValue();
			
//			int currentSkillLevelForConditionSkill = conditionSkill.getCurrentSkillLevel();
			for(int i = 0; i < conditionSkill.getMaxSkillLevel(); i++){
				boolean isSkillBLearningAble = conditionSkill.isSkillLearnAble();
				
				if(!isSkillBLearningAble){
					meetConditionForLearn(conditionSkill);
				}
				
				if(conditionSkill.getCurrentSkillLevel() >= skillLevelCondition){
					break;
				}else{
					conditionSkill.increaseSkillLevel();
				}		
			}
		}
	}
	
	public static boolean decreaseSkillLevelToTargetSkillByCheckWholeSkillTreeValid(ASkill targetSkill, List<ASkill> SkillTree){
		int skillLevelIfdecrease = targetSkill.getCurrentSkillLevel()-1;	
		return setSkillLevelToTargetSkillByCheckWholeSkillTreeValid(skillLevelIfdecrease, targetSkill, SkillTree);
	}
	
	public static boolean setSkillLevelToTargetSkillByCheckWholeSkillTreeValid(int newSkillLevel, ASkill targetSkill, List<ASkill> SkillTree){
		int currentSkillLevel = targetSkill.getCurrentSkillLevel();
		
		targetSkill.setCurrentSkillLevel(newSkillLevel);
		if(checkSkillTreeValid(SkillTree)){
			return true;
		}else{
			targetSkill.setCurrentSkillLevel(currentSkillLevel);
			return false;
		}
	}
	
	public static boolean checkSkillTreeValid(List<ASkill> SkillTree){
		boolean isSkillTreeValid = true;
		for(ASkill skillForCheck : SkillTree){
			if(!checkSkillValid(skillForCheck)){
				isSkillTreeValid = false;
				break;
			}
		}
		return isSkillTreeValid;
	}
	
	public static boolean checkSkillValid(ASkill skillForCheck){
		if(skillForCheck.getCurrentSkillLevel() < 0 || skillForCheck.getCurrentSkillLevel() > skillForCheck.getMaxSkillLevel())
			return false;
		
		for(Entry<ASkill, Integer> conditionSkillSet: skillForCheck.getConditionSkills().entrySet()){
			ASkill conditionSkill = conditionSkillSet.getKey();
			int skillLevelCondition = conditionSkillSet.getValue();
			boolean isSkillLearnAble = conditionSkill.isSkillLearnAble();
			
			if(!isSkillLearnAble)
				break;
			
			if(skillForCheck.getCurrentSkillLevel() > 0 && skillLevelCondition > conditionSkill.getCurrentSkillLevel()){
				return false;
			}
		}
		return true;
	}
	
//	public int countSkillSetCurrenLevel(){
//		
//	}
//	
//	public boolean countSkillSetValid(){
//		
//	}
	
	
}
