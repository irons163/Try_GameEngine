package com.example.try_gameengine.extension_module.skill;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.try_gameengine.action.listener.DefaultActionListener;

public abstract class ASkill{
	private boolean a;
	private int currentSkillLevel;
	private int maxSkillLevel;
	private String ID;
	
	private Map<ASkill, Integer> conditionSkills = new HashMap<ASkill, Integer>();
	
	public ASkill(int maxSkillLevel){
		this.maxSkillLevel = maxSkillLevel;
	}
	
	public ASkill(String ID, int maxSkillLevel){
		this.ID = ID;
		this.maxSkillLevel = maxSkillLevel;
	}
	
//	public abstract void addNextSkillWithConditionSkillLevel(ASkill skill, int skillLevel);
//	
//	public abstract void isSkillLearnAble();
//	
//	public abstract boolean isMeetConditionOfTheTargetSkill(ASkill targetSkill, int skillLevelCodition);
	
	public Map<ASkill, Integer> getConditionSkills(){
		return conditionSkills;
	}
	
	public int getMaxSkillLevel(){
		return maxSkillLevel;
	}
	
	public int getCurrentSkillLevel(){
		return currentSkillLevel;
	}
	
	public String getID(){
		return ID;
	}
	
	public boolean increaseSkillLevel(){
		if(currentSkillLevel >= maxSkillLevel)
			return false;
		if(currentSkillLevel==0 && !isSkillLearnAble())
			return false;
		
		currentSkillLevel++;
		return true;
	}
	
	boolean decreaseSkillLevel(){
		currentSkillLevel--;
		return true;
	}
	
	void setCurrentSkillLevel(int currentSkillLevel){
		this.currentSkillLevel = currentSkillLevel;
	}
	
	void setMaxSkillLevel(int maxSkillLevel){
		this.maxSkillLevel = maxSkillLevel;
	}
	
	private void checkTargetSkillIsValid(ASkill targetSkill){
		for(Map.Entry<ASkill, Integer> conditionSkillSet : targetSkill.getConditionSkills().entrySet()){
			ASkill conditionSkill = conditionSkillSet.getKey();
			if(ID!=null && ID.equals(conditionSkill.getID())){
				throw new RuntimeException();
			}else{
				checkTargetSkillIsValid(conditionSkill);
			}
		}
	}
	
//	@Override
	public boolean isSkillLearnAble() {
		// TODO Auto-generated method stub
		boolean isSkillLearnAble = true;
		for(Map.Entry<ASkill, Integer> entry : getConditionSkills().entrySet()){
			ASkill skill = entry.getKey();
			int skillLevelCodition = entry.getValue();
			if(!skill.isMeetConditionOfTheTargetSkill(this, skillLevelCodition)){
				isSkillLearnAble = false;
				break;
			}
		}
		return isSkillLearnAble;
	}

//	@Override
	public boolean isMeetConditionOfTheTargetSkill(ASkill skill, int skillLevelCodition) {
		// TODO Auto-generated method stub
		if(getCurrentSkillLevel() >= skillLevelCodition)
			return true;
		return false;
	}

//	@Override
	public void addNextSkillWithConditionSkillLevel(ASkill skill, int skillLevel) {
		// TODO Auto-generated method stub
		checkTargetSkillIsValid(skill);
		getConditionSkills().put(skill, skillLevel);
	}
}
